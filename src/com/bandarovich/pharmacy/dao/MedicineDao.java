package com.bandarovich.pharmacy.dao;

import com.bandarovich.pharmacy.entity.Medicine;

import java.util.List;

public interface MedicineDao {
    List<Medicine> findAllClientAvailableMedicineList() throws DaoException;
    List<Medicine> findClientMedicineList(String mail) throws DaoException;
    List<Medicine> findDoctorMedicineList() throws  DaoException;
    String findMedicineGroupSet() throws  DaoException;
    List<String> findPackageTypeList() throws  DaoException;
    int findMedicineStorageAmount(int medicineId) throws DaoException;
}
