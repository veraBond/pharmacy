package com.bandarovich.pharmacy.dao;

import com.bandarovich.pharmacy.entity.Prescription;

import java.util.List;
import java.util.Optional;

public interface PrescriptionDao {
    int findClientAvailableAmount(int clientId, int medicineId) throws DaoException;
    List<Prescription> findClientPrescriptionList(String mail) throws DaoException;
    int findMinPrescriptionNumber() throws DaoException;
    Optional<Prescription> findPrescriptionByMedicineIdClientId(int medicineId, int clientId) throws DaoException;
}
