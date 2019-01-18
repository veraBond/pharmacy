package com.bandarovich.pharmacy.dao;

import com.bandarovich.pharmacy.entity.Medicine;

import java.util.List;

public interface MedicineDao {
    List<Medicine> findAllClientAvailableMedicineList() throws DaoException;
    List<Medicine> findClientMedicineList(String mail) throws DaoException;
    List<Medicine> findDoctorMedicineList() throws  DaoException;
    int findMedicineStorageAmount(int medicineId) throws DaoException;
    int updateStorageAmount(int medicineId, int orderAmount) throws DaoException;
}
