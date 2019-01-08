package com.bandarovich.pharmacy.dao.impl;

import com.bandarovich.pharmacy.dao.DaoException;
import com.bandarovich.pharmacy.dao.PharmacyDao;
import com.bandarovich.pharmacy.entity.Medicine;
import com.bandarovich.pharmacy.entity.Pharmacy;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class MedicineDao extends PharmacyDao <String, Medicine> {
    private final static String FIND_ALL_CLIENT_MEDICINES =
            "SELECT medicineNumber, medicineName, dosage, medicine_group.groupName, package_type.typeName, " +
                    "packageAmount, price, prescriptionNeed, storageAmount " +
                    "FROM medicines JOIN medicine_group ON medicines.groupType = medicine_group.groupId " +
                    "JOIN package_type ON medicines.packageType = package_type.packageTypeId " +
                    "WHERE prescriptionNeed = 0";

    private final static String FIND_CLIENT_MEDICINES =
            "SELECT medicineNumber, medicineName, dosage, medicine_group.groupName, package_type.typeName, " +
                    "packageAmount, price, prescriptionNeed, storageAmount " +
                    "FROM medicines JOIN medicine_group ON medicines.groupType = medicine_group.groupId " +
                    "JOIN package_type ON medicines.packageType = package_type.packageTypeId " +
                    "JOIN prescriptions ON medicineId = prescriptions.medicine " +
                    "JOIN users ON prescriptions.client = users.userId " +
                    "WHERE users.mail = ?";

    private final static String MEDICINE_NUMBER = "medicineNumber";
    private final static String MEDICINE_NAME = "medicineName";
    private final static String DOSAGE = "dosage";
    private final static String GROUP_TYPE = "medicine_group.groupName";
    private final static String PACKAGE_TYPE = "package_type.typeName";
    private final static String PACKAGE_AMOUNT = "packageAmount";
    private final static String PRICE = "price";
    private final static String PRESCRIPTION_NEED = "prescriptionNeed";
    private final static String STORAGE_AMOUNT = "storageAmount";

    @Override
    public List<Pharmacy> findAll() throws DaoException {
        return null;
    }

    @Override
    public Medicine findEntity(String id) throws DaoException {
        return null;
    }

    @Override
    public boolean delete(String id) throws DaoException {
        return false;
    }

    @Override
    public int create(Medicine entity) throws DaoException {
        return 0;
    }

    public Set<Medicine> findAllClientMedicines() throws DaoException{
        try(PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_CLIENT_MEDICINES)){
            ResultSet resultSet = preparedStatement.executeQuery();
            Map<Integer, Medicine> medicineMap = buildMedicines(resultSet);
            return medicineMap.values().stream().collect(Collectors.toSet());
        } catch (SQLException e){
            logger.error("Error in find medicine list");
            throw new DaoException(e);
        }
    }

    public Set<Medicine> findClientMedicines(String mail) throws DaoException{
        try(PreparedStatement preparedStatement = connection.prepareStatement(FIND_CLIENT_MEDICINES)){
            preparedStatement.setString(1, mail);
            ResultSet resultSet = preparedStatement.executeQuery();
            Map<Integer, Medicine> medicineMap = buildMedicines(resultSet);
            return medicineMap.values().stream().collect(Collectors.toSet());
        } catch (SQLException e){
            logger.error("Error in find client medicine list");
            throw new DaoException(e);
        }
    }

    private Map<Integer, Medicine> buildMedicines(ResultSet resultSet) throws SQLException{
        Map<Integer, Medicine> medicineMap = new HashMap<>();
        while (resultSet.next()) {
            int medicineNumber = resultSet.getInt(MEDICINE_NUMBER);
            String groupType = resultSet.getString(GROUP_TYPE);
            if (medicineMap.containsKey(medicineNumber)) {
                medicineMap.get(medicineNumber).getGroupType().add(groupType);
            } else {
                List<String> groups = new LinkedList<>();
                groups.add(groupType);
                String medicineName = resultSet.getString(MEDICINE_NAME);
                int dosage = resultSet.getInt(DOSAGE);
                String packageType = resultSet.getString(PACKAGE_TYPE);
                int packageAmount = resultSet.getInt(PACKAGE_AMOUNT);
                double price = resultSet.getDouble(PRICE);
                boolean prescriptionNeed = resultSet.getBoolean(PRESCRIPTION_NEED);
                int storageAmount = resultSet.getInt(STORAGE_AMOUNT);
                Medicine medicine = new Medicine(medicineNumber, medicineName, dosage, groups, packageType, packageAmount, price, prescriptionNeed, storageAmount);
                medicineMap.put(medicineNumber, medicine);
            }
        }
        return medicineMap;
    }
}
