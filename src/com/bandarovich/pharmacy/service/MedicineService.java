package com.bandarovich.pharmacy.service;

import com.bandarovich.pharmacy.entity.Medicine;

import java.util.List;
import java.util.Set;

public interface MedicineService {
    List<Medicine> findAllClientMedicines() throws ServiceException;
    List<Medicine> findClientMedicines(String mail) throws ServiceException;
}
