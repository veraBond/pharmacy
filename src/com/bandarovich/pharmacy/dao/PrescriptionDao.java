package com.bandarovich.pharmacy.dao;

import com.bandarovich.pharmacy.entity.Medicine;
import com.bandarovich.pharmacy.entity.Prescription;
import javafx.util.Pair;

import java.util.List;
import java.util.Optional;

public interface PrescriptionDao {
    int findClientAvailableAmount(int clientId, int medicineId) throws DaoException;
    List<Pair<Prescription, Medicine>> findClientPrescriptionList(String mail) throws DaoException;
    Optional<Prescription> findPrescriptionByMedicineIdClientId(int medicineId, int clientId) throws DaoException;
    int requestPrescriptionForExtension(int prescriptionId) throws DaoException;
    int updatePrescriptionAmount(int orderAmount, int medicineId, String clientMail) throws DaoException;
}
