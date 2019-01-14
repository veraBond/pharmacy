package com.bandarovich.pharmacy.dao.impl;

import com.bandarovich.pharmacy.dao.DaoException;
import com.bandarovich.pharmacy.dao.MedicineDao;
import com.bandarovich.pharmacy.dao.PharmacyDao;
import com.bandarovich.pharmacy.entity.Medicine;

import java.sql.Array;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class MedicineDaoImpl extends PharmacyDao <Integer, Medicine> implements MedicineDao {
    private final static String FIND_ENTITY =
            "SELECT medicineId, medicineName, dosage, medicineGroup, package_type.typeName, " +
                    "packageAmount, price, prescriptionNeed, storageAmount "+
                    "FROM medicines INNER JOIN package_type ON medicines.packageType = package_type.packageTypeId " +
                    "WHERE medicineId = ?";
    private final static String FIND_ALL_CLIENT_MEDICINES =
            "SELECT medicineId, medicineName, dosage, medicineGroup, package_type.typeName, " +
                    "packageAmount, price, prescriptionNeed, storageAmount " +
                    "FROM medicines INNER JOIN package_type ON medicines.packageType = package_type.packageTypeId " +
                    "WHERE prescriptionNeed = 0";
    private final static String FIND_CLIENT_MEDICINES =
            "SELECT medicines.medicineId, medicineName, dosage, medicineGroup, package_type.typeName, " +
                    "packageAmount, price, prescriptionNeed, storageAmount " +
                    "FROM medicines INNER JOIN package_type ON medicines.packageType = package_type.packageTypeId " +
                    "INNER JOIN prescriptions USING (medicineId) " +
                    "INNER JOIN users ON prescriptions.clientId = users.userId " +
                    "WHERE users.mail = ?";
    private final static String FIND_DOCTOR_MEDICINES =
            "SELECT medicineId, medicineName, dosage, medicineGroup, package_type.typeName, " +
                    "packageAmount, price, prescriptionNeed, storageAmount " +
                    "FROM medicines INNER JOIN package_type ON medicines.packageType = package_type.packageTypeId " +
                    "WHERE prescriptionNeed = 1";
    private final static String FIND_MEDICINE_STORAGE_AMOUNT =
            "SELECT storageAmount FROM medicines WHERE medicineId = ?";
    private final static String FIND_MAX_ID =
            "SELECT MAX(medicineId) FROM medicines";
    private final static String UPDATE =
            "UPDATE medicines " +
                    "SET medicineName = ?, dosage = ?, medicineGroup = ?, " +
                    "packageType = (SELECT packageTypeId FROM package_type where typeName = ?), " +
                    "packageAmount = ?, price = ?, prescriptionNeed = ?, storageAmount = ? " +
                    "WHERE medicineId = ?";

    private final static String MEDICINE_ID = "medicineId";
    private final static String MEDICINE_NAME = "medicineName";
    private final static String DOSAGE = "dosage";
    private final static String MEDICINE_GROUP = "medicineGroup";
    private final static String PACKAGE_TYPE = "package_type.typeName";
    private final static String PACKAGE_AMOUNT = "packageAmount";
    private final static String PRICE = "price";
    private final static String PRESCRIPTION_NEED = "prescriptionNeed";
    private final static String STORAGE_AMOUNT = "storageAmount";

    @Override
    public List<Medicine> findAll() throws DaoException {
        return null;
    }

    @Override
    public Optional<Medicine> findEntity(Integer medicineId) throws DaoException {
        try(PreparedStatement preparedStatement = connection.prepareStatement(FIND_ENTITY)){
            preparedStatement.setInt(1, medicineId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                Medicine medicine = buildMedicine(resultSet);
                return Optional.of(medicine);
            } else {
                return Optional.empty();
            }
        } catch (SQLException e){
            throw new DaoException(e);
        }
    }

    @Override
    public int create(Medicine entity) throws DaoException {
        return 0;
    }

    @Override
    public int update(Medicine medicine) throws DaoException {
        try(PreparedStatement preparedStatement = connection.prepareStatement(UPDATE)){
            preparedStatement.setString(1, medicine.getName());
            preparedStatement.setInt(2, medicine.getDosage());
            preparedStatement.setString(3, medicine.getMedicineGroup());
            preparedStatement.setString(4, medicine.getPackageType());
            preparedStatement.setInt(5, medicine.getPackageAmount());
            preparedStatement.setDouble(6, medicine.getPrice());
            preparedStatement.setBoolean(7, medicine.needPrescription());
            preparedStatement.setInt(8, medicine.getStorageAmount());
            preparedStatement.setInt(9, medicine.getMedicineId());
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
    public int findMaxId() throws DaoException {
        try(PreparedStatement preparedStatement = connection.prepareStatement(FIND_MAX_ID)){
            ResultSet resultSet = preparedStatement.executeQuery();
            return (resultSet.next() ? resultSet.getInt(MEDICINE_ID) : 0);
        } catch (SQLException e){
            throw new DaoException(e);
        }
    }

    public List<Medicine> findAllClientAvailableMedicineList() throws DaoException{
        try(PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_CLIENT_MEDICINES)){
            ResultSet resultSet = preparedStatement.executeQuery();
            return buildMedicineList(resultSet);
        } catch (SQLException e){
            throw new DaoException(e);
        }
    }

    public List<Medicine> findClientMedicineList(String mail) throws DaoException{
        try(PreparedStatement preparedStatement = connection.prepareStatement(FIND_CLIENT_MEDICINES)){
            preparedStatement.setString(1, mail);
            ResultSet resultSet = preparedStatement.executeQuery();
            return buildMedicineList(resultSet);
        } catch (SQLException e){
            throw new DaoException(e);
        }
    }

    public List<Medicine> findDoctorMedicineList() throws  DaoException{
        try(PreparedStatement preparedStatement = connection.prepareStatement(FIND_DOCTOR_MEDICINES)){
            ResultSet resultSet = preparedStatement.executeQuery();
            return buildMedicineList(resultSet);
        } catch (SQLException e){
            throw new DaoException(e);
        }
    }

    public int findMedicineStorageAmount(int medicineId) throws DaoException{
        try(PreparedStatement preparedStatement = connection.prepareStatement(FIND_MEDICINE_STORAGE_AMOUNT)){
            preparedStatement.setInt(1, medicineId);
            ResultSet resultSet = preparedStatement.executeQuery();
            return (resultSet.next() ? resultSet.getInt(STORAGE_AMOUNT) : 0);
        } catch (SQLException e){
            throw new DaoException(e);
        }
    }

    private List<Medicine> buildMedicineList(ResultSet resultSet) throws SQLException{
        List<Medicine> medicines = new LinkedList<>();
        while(resultSet.next()){
            medicines.add(buildMedicine(resultSet));
        }
        return medicines;
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
        Medicine medicine = new Medicine(medicineId, medicineName, dosage, groups, packageType, packageAmount, price, prescriptionNeed, storageAmount);
        return medicine;
    }
}
