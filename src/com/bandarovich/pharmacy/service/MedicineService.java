package com.bandarovich.pharmacy.service;

import com.bandarovich.pharmacy.entity.Medicine;

import java.util.List;
import java.util.Optional;

public interface MedicineService {
    Medicine findMedicine(int medicineId) throws ServiceException;
    List<Medicine> findAllMedicineList() throws ServiceException;
    List<Medicine> findAllClientAvailableMedicineList() throws ServiceException;
    List<Medicine> findClientMedicineList(String mail) throws ServiceException;
    List<Medicine> findDoctorMedicineList() throws ServiceException;
    int findAvailableClientMedicineAmount(int medicineId, String mail) throws ServiceException;
    int findAvailableDoctorMedicineAmount(int medicineId) throws ServiceException;
    boolean updateMedicineStorageAmount(int medicineId, int orderAmount) throws ServiceException;
}
