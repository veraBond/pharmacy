package com.bandarovich.pharmacy.service;

import com.bandarovich.pharmacy.entity.Medicine;

import java.util.Set;

public interface MedicineService {
    Set<Medicine> findAllClientMedicines() throws ServiceException;
    Set<Medicine> findClientMedicines(String mail) throws ServiceException;
}
