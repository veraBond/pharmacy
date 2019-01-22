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

public class PrescriptionDaoImpl extends PharmacyDao<Integer, Prescription> implements PrescriptionDao {
    private static final String FIND_ENTITY =
            "SELECT prescriptionId, medicineId, clients.mail, doctors.mail, amount, isRequestedForExtension " +
                    "FROM prescriptions INNER JOIN users clients ON(clients.userId = clientId) " +
                    "INNER JOIN users doctors ON(doctors.userId = doctorId) " +
                    "WHERE prescriptionId = ?";
    private static final String FIND_ALL =
            "SELECT prescriptionId, medicineId, clients.mail, doctors.mail, amount, isRequestedForExtension " +
                    "FROM prescriptions INNER JOIN users clients ON(clients.userId = clientId) " +
                    "INNER JOIN users doctors ON(doctors.userId = doctorId) ORDER BY prescriptionId";
    private static final String CREATE =
            "INSERT INTO prescriptions " +
                    "SET prescriptionId = ?, " +
                    "medicineId = ?, " +
                    "clientId = (SELECT userId FROM users WHERE mail = ?), " +
                    "doctorId = (SELECT userId FROM users WHERE mail = ?), " +
                    "amount = ?, isRequestedForExtension = ? ";
    private static final String UPDATE =
            "UPDATE prescriptions " +
                    "SET medicineId = ?, " +
                    "clientId = (SELECT clients.userId FROM users AS clients where clients.mail = ?), " +
                    "doctorId = (SELECT doctors.userId FROM users AS doctors where doctors.mail = ?), " +
                    "amount = ?, isRequestedForExtension = ? " +
                    "WHERE prescriptionId = ?";
    private static final String DELETE =
            "DELETE FROM prescriptions WHERE prescriptionId = ?";
    private static final String FIND_MAX_ID =
            "SELECT MAX(prescriptionId) FROM prescriptions";
    private static final String FIND_PRESCRIPTION_BY_MEDICINE_ID_CLIENT_MAIL =
            "SELECT prescriptionId FROM prescriptions " +
                    "WHERE medicineId = ? AND clientId = (SELECT userId FROM users WHERE mail = ?)";
    private static final String FIND_CLIENT_PRESCRIPTIONS =
            "SELECT prescriptionId, medicineId, clients.mail, doctors.mail, amount, isRequestedForExtension, " +
                    "medicineName, dosage, medicine_group.groupName, package_type.typeName, packageAmount, price, prescriptionNeed, storageAmount " +
                    "FROM prescriptions INNER JOIN medicines USING (medicineId) " +
                    "INNER JOIN users AS clients ON prescriptions.clientId = clients.userId, " +
                    "users AS doctors, package_type, medicine_group " +
                    "WHERE doctors.userId = prescriptions.doctorId AND clients.mail = ? AND prescriptionNeed = TRUE " +
                    "AND packageTypeId = packageType AND medicineGroup = groupId AND medicines.isDeleted = FALSE " +
                    "ORDER BY prescriptionId";
    private static final String FIND_DOCTOR_PRESCRIPTIONS =
            "SELECT prescriptionId, medicineId, clients.mail, doctors.mail, amount, isRequestedForExtension, " +
                    "medicineName, dosage, medicine_group.groupName, package_type.typeName, packageAmount, price, prescriptionNeed, storageAmount " +
                    "FROM prescriptions INNER JOIN medicines USING (medicineId) " +
                    "INNER JOIN users AS doctors ON prescriptions.doctorId = doctors.userId, " +
                    "users AS clients, package_type, medicine_group " +
                    "WHERE clients.userId = prescriptions.clientId AND doctors.mail = ?  AND prescriptionNeed = TRUE " +
                    "AND packageTypeId = packageType AND medicineGroup = groupId  AND medicines.isDeleted = FALSE " +
                    "ORDER BY isRequestedForExtension DESC, prescriptionId";
    private static final String FIND_CLIENT_AVAILABLE_AMOUNT =
            "SELECT amount FROM prescriptions " +
                    "WHERE medicineId = ? AND clientId = ?";
    private static final String REQUEST_PRESCRIPTION_FOR_EXTENSION =
            "UPDATE prescriptions SET isRequestedForExtension = TRUE WHERE prescriptionId = ?";
    private static final String EXTEND_PRESCRIPTION =
            "UPDATE prescriptions SET isRequestedForExtension = FALSE, amount = (amount + ?) WHERE prescriptionId = ?";
    private static final String UPDATE_PRESCRIPTION_AMOUNT =
            "UPDATE prescriptions SET amount = (amount - ?) WHERE prescriptionId = ?";
    private static final String MAX_PRESCRIPTION_ID = "MAX(prescriptionId)";
    private static final String PRESCRIPTION_ID = "prescriptionId";
    private static final String MEDICINE_ID = "medicineId";
    private static final String CLIENT_MAIL = "clients.mail";
    private static final String DOCTOR_MAIL = "doctors.mail";
    private static final String AMOUNT = "amount";
    private static final String IS_REQUESTED_FOR_EXTENSION = "isRequestedForExtension";

    private static final String MEDICINE_NAME = "medicineName";
    private static final String DOSAGE = "dosage";
    private static final String MEDICINE_GROUP = "medicine_group.groupName";
    private static final String PACKAGE_TYPE = "package_type.typeName";
    private static final String PACKAGE_AMOUNT = "packageAmount";
    private static final String PRICE = "price";
    private static final String PRESCRIPTION_NEED = "prescriptionNeed";
    private static final String STORAGE_AMOUNT = "storageAmount";

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

    @Override
    public int delete(Integer id) throws DaoException {
        try(PreparedStatement preparedStatement = connection.prepareStatement(DELETE)){
            preparedStatement.setInt(1, id);
            return preparedStatement.executeUpdate();
        } catch (SQLException e){
            throw new DaoException(e);
        }
    }

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

    @Override
    public List<Pair<Prescription, Medicine>> findClientPrescriptionList(String mail) throws DaoException{
        return findUserPrescriptionList(mail, FIND_CLIENT_PRESCRIPTIONS);
    }

    @Override
    public List<Pair<Prescription, Medicine>> findDoctorPrescriptionList(String mail) throws DaoException{
        return findUserPrescriptionList(mail, FIND_DOCTOR_PRESCRIPTIONS);
    }

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

    @Override
    public int requestPrescriptionForExtension(int prescriptionId) throws DaoException {
        try(PreparedStatement preparedStatement = connection.prepareStatement(REQUEST_PRESCRIPTION_FOR_EXTENSION)){
            preparedStatement.setInt(1, prescriptionId);
            return preparedStatement.executeUpdate();
        } catch (SQLException e){
            throw new DaoException(e);
        }
    }

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

    private List<Pair<Prescription, Medicine>> buildPrescriptionList(ResultSet resultSet) throws SQLException{
            List<Pair<Prescription, Medicine>> prescriptions = new LinkedList<>();
            while (resultSet.next()) {
                Prescription prescription = buildPrescription(resultSet);
                Medicine medicine = buildMedicine(resultSet);
                prescriptions.add(new Pair<>(prescription, medicine));
            }
            return prescriptions;
    }

    private Prescription buildPrescription(ResultSet resultSet) throws SQLException{
        int prescriptionId = resultSet.getInt(PRESCRIPTION_ID);
        int medicineId = resultSet.getInt(MEDICINE_ID);
        String clientMail = resultSet.getString(CLIENT_MAIL);
        String doctorMail = resultSet.getString(DOCTOR_MAIL);
        int amount = resultSet.getInt(AMOUNT);
        boolean isReq = resultSet.getBoolean(IS_REQUESTED_FOR_EXTENSION);
        return new Prescription(prescriptionId, medicineId, clientMail, doctorMail, amount, isReq);
    }

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
