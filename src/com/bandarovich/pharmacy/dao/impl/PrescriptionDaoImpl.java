package com.bandarovich.pharmacy.dao.impl;

import com.bandarovich.pharmacy.dao.DaoException;
import com.bandarovich.pharmacy.dao.PharmacyDao;
import com.bandarovich.pharmacy.dao.PrescriptionDao;
import com.bandarovich.pharmacy.entity.Medicine;
import com.bandarovich.pharmacy.entity.Prescription;
import javafx.util.Pair;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * The Class PrescriptionDaoImpl.
 */
public class PrescriptionDaoImpl extends PharmacyDao<Integer, Prescription> implements PrescriptionDao {
    
    /** The Constant FIND_ENTITY. */
    private static final String FIND_ENTITY =
            "SELECT prescriptionId, medicineId, clients.mail, doctors.mail, amount, isRequestedForExtension " +
                    "FROM prescriptions INNER JOIN users clients ON(clients.userId = clientId) " +
                    "INNER JOIN users doctors ON(doctors.userId = doctorId) " +
                    "WHERE prescriptionId = ?";
    
    /** The Constant FIND_ALL. */
    private static final String FIND_ALL =
            "SELECT prescriptionId, medicineId, clients.mail, doctors.mail, amount, isRequestedForExtension " +
                    "FROM prescriptions INNER JOIN users clients ON(clients.userId = clientId) " +
                    "INNER JOIN users doctors ON(doctors.userId = doctorId) ORDER BY prescriptionId";
    
    /** The Constant CREATE. */
    private static final String CREATE =
            "INSERT INTO prescriptions " +
                    "SET prescriptionId = ?, " +
                    "medicineId = ?, " +
                    "clientId = (SELECT userId FROM users WHERE mail = ?), " +
                    "doctorId = (SELECT userId FROM users WHERE mail = ?), " +
                    "amount = ?, isRequestedForExtension = ? ";
    
    /** The Constant UPDATE. */
    private static final String UPDATE =
            "UPDATE prescriptions " +
                    "SET medicineId = ?, " +
                    "clientId = (SELECT clients.userId FROM users AS clients where clients.mail = ?), " +
                    "doctorId = (SELECT doctors.userId FROM users AS doctors where doctors.mail = ?), " +
                    "amount = ?, isRequestedForExtension = ? " +
                    "WHERE prescriptionId = ?";
    
    /** The Constant DELETE. */
    private static final String DELETE =
            "DELETE FROM prescriptions WHERE prescriptionId = ?";
    
    /** The Constant FIND_MAX_ID. */
    private static final String FIND_MAX_ID =
            "SELECT MAX(prescriptionId) FROM prescriptions";
    
    /** The Constant FIND_PRESCRIPTION_BY_MEDICINE_ID_CLIENT_MAIL. */
    private static final String FIND_PRESCRIPTION_BY_MEDICINE_ID_CLIENT_MAIL =
            "SELECT prescriptionId FROM prescriptions " +
                    "WHERE medicineId = ? AND clientId = (SELECT userId FROM users WHERE mail = ?)";
    
    /** The Constant FIND_CLIENT_PRESCRIPTIONS. */
    private static final String FIND_CLIENT_PRESCRIPTIONS =
            "SELECT prescriptionId, medicineId, clients.mail, doctors.mail, amount, isRequestedForExtension, " +
                    "medicineName, dosage, medicine_group.groupName, package_type.typeName, packageAmount, price, prescriptionNeed, storageAmount " +
                    "FROM prescriptions INNER JOIN medicines USING (medicineId) " +
                    "INNER JOIN users AS clients ON prescriptions.clientId = clients.userId, " +
                    "users AS doctors, package_type, medicine_group " +
                    "WHERE doctors.userId = prescriptions.doctorId AND clients.mail = ? AND prescriptionNeed = TRUE " +
                    "AND packageTypeId = packageType AND medicineGroup = groupId AND medicines.isDeleted = FALSE " +
                    "ORDER BY prescriptionId";
    
    /** The Constant FIND_DOCTOR_PRESCRIPTIONS. */
    private static final String FIND_DOCTOR_PRESCRIPTIONS =
            "SELECT prescriptionId, medicineId, clients.mail, doctors.mail, amount, isRequestedForExtension, " +
                    "medicineName, dosage, medicine_group.groupName, package_type.typeName, packageAmount, price, prescriptionNeed, storageAmount " +
                    "FROM prescriptions INNER JOIN medicines USING (medicineId) " +
                    "INNER JOIN users AS doctors ON prescriptions.doctorId = doctors.userId, " +
                    "users AS clients, package_type, medicine_group " +
                    "WHERE clients.userId = prescriptions.clientId AND doctors.mail = ?  AND prescriptionNeed = TRUE " +
                    "AND packageTypeId = packageType AND medicineGroup = groupId  AND medicines.isDeleted = FALSE " +
                    "ORDER BY isRequestedForExtension DESC, prescriptionId";
    
    /** The Constant FIND_CLIENT_AVAILABLE_AMOUNT. */
    private static final String FIND_CLIENT_AVAILABLE_AMOUNT =
            "SELECT amount FROM prescriptions " +
                    "WHERE medicineId = ? AND clientId = ?";
    
    /** The Constant REQUEST_PRESCRIPTION_FOR_EXTENSION. */
    private static final String REQUEST_PRESCRIPTION_FOR_EXTENSION =
            "UPDATE prescriptions SET isRequestedForExtension = TRUE WHERE prescriptionId = ?";
    
    /** The Constant EXTEND_PRESCRIPTION. */
    private static final String EXTEND_PRESCRIPTION =
            "UPDATE prescriptions SET isRequestedForExtension = FALSE, amount = (amount + ?) WHERE prescriptionId = ?";
    
    /** The Constant UPDATE_PRESCRIPTION_AMOUNT. */
    private static final String UPDATE_PRESCRIPTION_AMOUNT =
            "UPDATE prescriptions SET amount = (amount - ?) WHERE prescriptionId = ?";
    
    /** The Constant MAX_PRESCRIPTION_ID. */
    private static final String MAX_PRESCRIPTION_ID = "MAX(prescriptionId)";
    
    /** The Constant PRESCRIPTION_ID. */
    private static final String PRESCRIPTION_ID = "prescriptionId";
    
    /** The Constant MEDICINE_ID. */
    private static final String MEDICINE_ID = "medicineId";
    
    /** The Constant CLIENT_MAIL. */
    private static final String CLIENT_MAIL = "clients.mail";
    
    /** The Constant DOCTOR_MAIL. */
    private static final String DOCTOR_MAIL = "doctors.mail";
    
    /** The Constant AMOUNT. */
    private static final String AMOUNT = "amount";
    
    /** The Constant IS_REQUESTED_FOR_EXTENSION. */
    private static final String IS_REQUESTED_FOR_EXTENSION = "isRequestedForExtension";

    /** The Constant MEDICINE_NAME. */
    private static final String MEDICINE_NAME = "medicineName";
    
    /** The Constant DOSAGE. */
    private static final String DOSAGE = "dosage";
    
    /** The Constant MEDICINE_GROUP. */
    private static final String MEDICINE_GROUP = "medicine_group.groupName";
    
    /** The Constant PACKAGE_TYPE. */
    private static final String PACKAGE_TYPE = "package_type.typeName";
    
    /** The Constant PACKAGE_AMOUNT. */
    private static final String PACKAGE_AMOUNT = "packageAmount";
    
    /** The Constant PRICE. */
    private static final String PRICE = "price";
    
    /** The Constant PRESCRIPTION_NEED. */
    private static final String PRESCRIPTION_NEED = "prescriptionNeed";
    
    /** The Constant STORAGE_AMOUNT. */
    private static final String STORAGE_AMOUNT = "storageAmount";

    /* (non-Javadoc)
     * @see com.bandarovich.pharmacy.dao.PharmacyDao#findEntity(java.lang.Object)
     */
    @Override
    public Optional<Prescription> findEntity(Integer id) throws DaoException {
        try(PreparedStatement preparedStatement = connection.prepareStatement(FIND_ENTITY)){
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                return Optional.of(buildPrescription(resultSet));
            } else {
                return Optional.empty();
            }
        } catch (SQLException e){
            throw new DaoException(e);
        }
    }

    /* (non-Javadoc)
     * @see com.bandarovich.pharmacy.dao.PharmacyDao#findAll()
     */
    @Override
    public List<Prescription> findAll() throws DaoException {
        try(PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL)){
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Prescription> prescriptions = new LinkedList<>();
            while(resultSet.next()) {
                prescriptions.add(buildPrescription(resultSet));
            }
            return prescriptions;
        } catch (SQLException e){
            throw new DaoException(e);
        }
    }

    /* (non-Javadoc)
     * @see com.bandarovich.pharmacy.dao.PharmacyDao#create(com.bandarovich.pharmacy.entity.Pharmacy)
     */
    @Override
    public int create(Prescription prescription) throws DaoException {
        try(PreparedStatement preparedStatement = connection.prepareStatement(CREATE)){
            preparedStatement.setInt(1, prescription.getPrescriptionId());
            preparedStatement.setInt(2, prescription.getMedicineId());
            preparedStatement.setString(3, prescription.getClientMail());
            preparedStatement.setString(4, prescription.getDoctorMail());
            preparedStatement.setInt(5, prescription.getAvailableMedicineAmount());
            preparedStatement.setBoolean(6, prescription.isRequestedForExtension());
            return preparedStatement.executeUpdate();
        } catch (SQLException e){
            throw new DaoException(e);
        }
    }

    /* (non-Javadoc)
     * @see com.bandarovich.pharmacy.dao.PharmacyDao#update(com.bandarovich.pharmacy.entity.Pharmacy)
     */
    @Override
    public int update(Prescription prescription) throws DaoException {
        try(PreparedStatement preparedStatement = connection.prepareStatement(UPDATE)){
            preparedStatement.setInt(1, prescription.getMedicineId());
            preparedStatement.setString(2, prescription.getClientMail());
            preparedStatement.setString(3, prescription.getDoctorMail());
            preparedStatement.setInt(4, prescription.getAvailableMedicineAmount());
            preparedStatement.setBoolean(5, prescription.isRequestedForExtension());
            preparedStatement.setInt(6, prescription.getPrescriptionId());
            return preparedStatement.executeUpdate();
        } catch (SQLException e){
            throw new DaoException(e);
        }
    }

    /* (non-Javadoc)
     * @see com.bandarovich.pharmacy.dao.PharmacyDao#delete(java.lang.Object)
     */
    @Override
    public int delete(Integer id) throws DaoException {
        try(PreparedStatement preparedStatement = connection.prepareStatement(DELETE)){
            preparedStatement.setInt(1, id);
            return preparedStatement.executeUpdate();
        } catch (SQLException e){
            throw new DaoException(e);
        }
    }

    /* (non-Javadoc)
     * @see com.bandarovich.pharmacy.dao.PharmacyDao#findMaxId()
     */
    @Override
    public int findMaxId() throws DaoException {
        try(PreparedStatement preparedStatement = connection.prepareStatement(FIND_MAX_ID)){
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return resultSet.getInt(MAX_PRESCRIPTION_ID);
            } else {
                return 0;
            }
        } catch (SQLException e){
            throw new DaoException(e);
        }
    }

    /* (non-Javadoc)
     * @see com.bandarovich.pharmacy.dao.PrescriptionDao#findClientAvailableAmount(int, int)
     */
    @Override
    public int findClientAvailableAmount(int clientId, int medicineId) throws DaoException{
        try(PreparedStatement preparedStatement = connection.prepareStatement(FIND_CLIENT_AVAILABLE_AMOUNT)){
            preparedStatement.setInt(1, medicineId);
            preparedStatement.setInt(2, clientId);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next() ? resultSet.getInt(AMOUNT) : 0;
        } catch (SQLException e){
            throw new DaoException(e);
        }
    }

    /* (non-Javadoc)
     * @see com.bandarovich.pharmacy.dao.PrescriptionDao#findClientPrescriptionList(java.lang.String)
     */
    @Override
    public List<Pair<Prescription, Medicine>> findClientPrescriptionList(String mail) throws DaoException{
        return findUserPrescriptionList(mail, FIND_CLIENT_PRESCRIPTIONS);
    }

    /* (non-Javadoc)
     * @see com.bandarovich.pharmacy.dao.PrescriptionDao#findDoctorPrescriptionList(java.lang.String)
     */
    @Override
    public List<Pair<Prescription, Medicine>> findDoctorPrescriptionList(String mail) throws DaoException{
        return findUserPrescriptionList(mail, FIND_DOCTOR_PRESCRIPTIONS);
    }

    /* (non-Javadoc)
     * @see com.bandarovich.pharmacy.dao.PrescriptionDao#findPrescription(int, java.lang.String)
     */
    @Override
    public Optional<Prescription> findPrescription(int medicineId, String clientMail) throws DaoException {
        try(PreparedStatement preparedStatement = connection.prepareStatement(FIND_PRESCRIPTION_BY_MEDICINE_ID_CLIENT_MAIL)){
            preparedStatement.setInt(1, medicineId);
            preparedStatement.setString(2, clientMail);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                int prescriptionId = resultSet.getInt(PRESCRIPTION_ID);
                return findEntity(prescriptionId);
            } else {
                return Optional.empty();
            }
        } catch (SQLException e){
            throw new DaoException(e);
        }
    }

    /* (non-Javadoc)
     * @see com.bandarovich.pharmacy.dao.PrescriptionDao#requestPrescriptionForExtension(int)
     */
    @Override
    public int requestPrescriptionForExtension(int prescriptionId) throws DaoException {
        try(PreparedStatement preparedStatement = connection.prepareStatement(REQUEST_PRESCRIPTION_FOR_EXTENSION)){
            preparedStatement.setInt(1, prescriptionId);
            return preparedStatement.executeUpdate();
        } catch (SQLException e){
            throw new DaoException(e);
        }
    }

    /* (non-Javadoc)
     * @see com.bandarovich.pharmacy.dao.PrescriptionDao#extendPrescription(int, int)
     */
    @Override
    public int extendPrescription(int prescriptionId, int amount) throws DaoException {
        try(PreparedStatement preparedStatement = connection.prepareStatement(EXTEND_PRESCRIPTION)){
            preparedStatement.setInt(1, amount);
            preparedStatement.setInt(2, prescriptionId);
            return preparedStatement.executeUpdate();
        } catch (SQLException e){
            throw new DaoException(e);
        }
    }

    /**
     * Builds the prescription list.
     *
     * @param resultSet the result set
     * @return the list
     * @throws SQLException the SQL exception
     */
    private List<Pair<Prescription, Medicine>> buildPrescriptionList(ResultSet resultSet) throws SQLException{
            List<Pair<Prescription, Medicine>> prescriptions = new LinkedList<>();
            while (resultSet.next()) {
                Prescription prescription = buildPrescription(resultSet);
                Medicine medicine = buildMedicine(resultSet);
                prescriptions.add(new Pair<>(prescription, medicine));
            }
            return prescriptions;
    }

    /**
     * Builds the prescription.
     *
     * @param resultSet the result set
     * @return the prescription
     * @throws SQLException the SQL exception
     */
    private Prescription buildPrescription(ResultSet resultSet) throws SQLException{
        int prescriptionId = resultSet.getInt(PRESCRIPTION_ID);
        int medicineId = resultSet.getInt(MEDICINE_ID);
        String clientMail = resultSet.getString(CLIENT_MAIL);
        String doctorMail = resultSet.getString(DOCTOR_MAIL);
        int amount = resultSet.getInt(AMOUNT);
        boolean isReq = resultSet.getBoolean(IS_REQUESTED_FOR_EXTENSION);
        return new Prescription(prescriptionId, medicineId, clientMail, doctorMail, amount, isReq);
    }

    /**
     * Builds the medicine.
     *
     * @param resultSet the result set
     * @return the medicine
     * @throws SQLException the SQL exception
     */
    private Medicine buildMedicine(ResultSet resultSet) throws SQLException{
        int medicineId = resultSet.getInt(MEDICINE_ID);
        String medicineName = resultSet.getString(MEDICINE_NAME);
        int dosage = resultSet.getInt(DOSAGE);
        String groups = resultSet.getString(MEDICINE_GROUP);
        String packageType = resultSet.getString(PACKAGE_TYPE);
        int packageAmount = resultSet.getInt(PACKAGE_AMOUNT);
        double price = resultSet.getDouble(PRICE);
        boolean prescriptionNeed = resultSet.getBoolean(PRESCRIPTION_NEED);
        int storageAmount = resultSet.getInt(STORAGE_AMOUNT);
        return new Medicine(medicineId, medicineName, dosage, groups, packageType, packageAmount, price, prescriptionNeed, storageAmount);
    }

    /**
     * Find user prescription list.
     *
     * @param mail the mail
     * @param sql the sql
     * @return the list
     * @throws DaoException the dao exception
     */
    private List<Pair<Prescription, Medicine>> findUserPrescriptionList(String mail, String sql) throws DaoException{
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1, mail);
            ResultSet resultSet = preparedStatement.executeQuery();
            return buildPrescriptionList(resultSet);
        } catch (SQLException e){
            throw new DaoException(e);
        }
    }
}
