package com.bandarovich.pharmacy.dao.impl;

import com.bandarovich.pharmacy.dao.DaoException;
import com.bandarovich.pharmacy.dao.PharmacyDao;
import com.bandarovich.pharmacy.dao.PrescriptionDao;
import com.bandarovich.pharmacy.entity.Prescription;
import com.bandarovich.pharmacy.entity.PrescriptionStatus;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class PrescriptionDaoImpl extends PharmacyDao<Integer, Prescription> implements PrescriptionDao {
    private final static String FIND_PRESCRIPTION_BY_MEDICINE_ID_CLIENT_ID =
            "SELECT prescriptionId, medicineId, clients.mail, doctors.mail, amount, status, isRequestedForExtension, isValid " +
                    "FROM prescriptions, users AS clients, users AS doctors " +
                    "WHERE medicineId = ? AND clientId = ? AND status = 'active' AND isValid = true";
    private final static String FIND_MEDICINE_CLIENT_DOCTOR_IDS =
            "SELECT medicineId, clients.userId, doctors.userId " +
                    "FROM medicines, users AS clients, users AS doctors " +
                    "WHERE medicineId = ? AND clients.mail = ? AND doctors.mail = ?";
    private final static String FIND_CLIENT_PRESCRIPTIONS =
            "SELECT prescriptionId, medicineId, clients.mail, doctors.mail, amount, status, isRequestedForExtension " +
                    "FROM prescriptions INNER JOIN medicines USING (medicineId)" +
                    "INNER JOIN users AS clients ON prescriptions.clientId = clients.userId " +
                    "INNER JOIN users AS doctors ON prescriptions.doctorId = doctors.mail " +
                    "WHERE clients.mail = ? AND isValid = true ";
    private final static String FIND_MIN_PRESCRIPTION_NUMBER =
            "SELECT MIN(prescriptionId) prescriptionNumber FROM prescriptions";
    private final static String FIND_CLIENT_AVAILABLE_AMOUNT =
            "SELECT amount FROM prescriptions " +
                    "WHERE medicineId = ? AND clientId = ? AND status = 'active' AND isValid = true";
    private final static String CREATE =
            "INSERT INTO prescriptions(prescriptionId, medicineId, clientId, doctorId, amount, status, isRequestedForExtension, isValid) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    private final static String UPDATE =
            "UPDATE prescriptions " +
                    "SET medicineId = ?, " +
                    "clientId = (SELECT clients.userId FROM users AS clients where clients.mail = ?), " +
                    "doctorId = (SELECT doctors.userId FROM users AS doctors where doctors.mail = ?), " +
                    "amount = ?, status = ?, isRequestedForExtension = ?, isValid = ? " +
                    "WHERE prescriptionId = ?";
    private final static String PRESCRIPTION_ID = "prescriptionId";
    private final static String MEDICINE_ID = "medicineId";
    private final static String CLIENT_MAIL = "clients.mail";
    private final static String DOCTOR_MAIL = "doctors.mail";
    private final static String CLIENT_ID = "clients.userId";
    private final static String DOCTOR_ID = "doctors.userId";
    private final static String AMOUNT = "amount";
    private final static String STATUS = "status";
    private final static String IS_REQUESTED_FOR_EXTENSION = "isRequestedForExtension";
    private final static String IS_VALID = "isValid";

    @Override
    public Optional<Prescription> findEntity(Integer id) throws DaoException {
        return Optional.empty();
    }

    @Override
    public List<Prescription> findAll() throws DaoException {
        return null;
    }
    //TODO check try/catch
    @Override
    public int create(Prescription prescription) throws DaoException {
        try(PreparedStatement preparedStatement = connection.prepareStatement(FIND_MEDICINE_CLIENT_DOCTOR_IDS)){
            int result = 0;
            preparedStatement.setInt(1, prescription.getMedicineId());
            preparedStatement.setString(2, prescription.getClientMail());
            preparedStatement.setString(3, prescription.getDoctorMail());
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                try(PreparedStatement insertStatement = connection.prepareStatement(CREATE)){
                    insertStatement.setInt(1, prescription.getPrescriptionId());
                    insertStatement.setInt(2, resultSet.getInt(MEDICINE_ID));
                    insertStatement.setInt(3, resultSet.getInt(CLIENT_ID));
                    insertStatement.setInt(4, resultSet.getInt(DOCTOR_ID));
                    insertStatement.setInt(5, prescription.getAvailableMedicineAmount());
                    insertStatement.setString(6, prescription.getStatus().name().toLowerCase());
                    insertStatement.setBoolean(7, prescription.isRequestedForExtension());
                    insertStatement.setBoolean(8, prescription.isValid());
                    result += insertStatement.executeUpdate();
                }
            }
            return result;
        } catch (SQLException e){
            throw new DaoException(e);
        }
    }

    @Override
    public int findMaxId() throws DaoException {
        return 0;
    }

    @Override
    public int update(Prescription prescription) throws DaoException {
        try(PreparedStatement preparedStatement = connection.prepareStatement(UPDATE)){
            preparedStatement.setInt(1, prescription.getMedicineId());
            preparedStatement.setString(2, prescription.getClientMail());
            preparedStatement.setString(3, prescription.getDoctorMail());
            preparedStatement.setInt(4, prescription.getAvailableMedicineAmount());
            preparedStatement.setString(5, prescription.getStatus().name());
            preparedStatement.setBoolean(6, prescription.isRequestedForExtension());
            preparedStatement.setBoolean(7, prescription.isValid());
            preparedStatement.setInt(8, prescription.getPrescriptionId());
            return preparedStatement.executeUpdate();
        } catch (SQLException e){
            throw new DaoException(e);
        }
    }

    @Override
    public int delete(Integer id) throws DaoException {
        return 0;
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

    public List<Prescription> findClientPrescriptionList(String mail) throws DaoException{
        try(PreparedStatement preparedStatement = connection.prepareStatement(FIND_CLIENT_PRESCRIPTIONS)){
            preparedStatement.setString(1, mail);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Prescription> prescriptions = new LinkedList<>();
            while (resultSet.next()) {
                int prescriptionNumber = resultSet.getInt(PRESCRIPTION_ID);
                String clientMail = resultSet.getString(CLIENT_MAIL);
                String doctorMail = resultSet.getString(DOCTOR_MAIL);
                int medicineNumber = resultSet.getInt(MEDICINE_ID);
                int amount = resultSet.getInt(AMOUNT);
                PrescriptionStatus status = PrescriptionStatus.valueOf(resultSet.getString(STATUS).toUpperCase());
                boolean isRequestedForExtension = resultSet.getBoolean(IS_REQUESTED_FOR_EXTENSION);
                prescriptions.add(new Prescription(prescriptionNumber, medicineNumber, clientMail, doctorMail, amount, status, isRequestedForExtension, true));
            }
            return prescriptions;
        } catch (SQLException e){
            throw new DaoException(e);
        }
    }

    public int findMinPrescriptionNumber() throws DaoException{
        try(PreparedStatement preparedStatement = connection.prepareStatement(FIND_MIN_PRESCRIPTION_NUMBER)){
            ResultSet resultSet = preparedStatement.executeQuery();
            int min = 0;
            if(resultSet.next()){
                min = resultSet.getInt(PRESCRIPTION_ID);
            }
            return min;
        } catch (SQLException e){
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<Prescription> findPrescriptionByMedicineIdClientId(int medicineId, int clientId) throws DaoException {
        try(PreparedStatement preparedStatement = connection.prepareStatement(FIND_PRESCRIPTION_BY_MEDICINE_ID_CLIENT_ID)){
            preparedStatement.setInt(1, medicineId);
            preparedStatement.setInt(2, clientId);
            ResultSet resultSet = preparedStatement.executeQuery();
            return (resultSet.next() ? Optional.of(buildPrescription(resultSet)) : Optional.empty());
        } catch (SQLException e){
            throw new DaoException(e);
        }
    }

    private Prescription buildPrescription(ResultSet resultSet) throws SQLException{
        int prescriptionId = resultSet.getInt(PRESCRIPTION_ID);
        int medicineId = resultSet.getInt(MEDICINE_ID);
        String clientMail = resultSet.getString(CLIENT_MAIL);
        String doctorMail = resultSet.getString(DOCTOR_MAIL);
        int amount = resultSet.getInt(AMOUNT);
        PrescriptionStatus status = PrescriptionStatus.valueOf(resultSet.getString(STATUS).toUpperCase());
        boolean isReq = resultSet.getBoolean(IS_REQUESTED_FOR_EXTENSION);
        boolean isValid = resultSet.getBoolean(IS_VALID);
        return new Prescription(prescriptionId, medicineId, clientMail, doctorMail, amount, status, isReq, isValid);
    }
}
