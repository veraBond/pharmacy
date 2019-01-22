package com.bandarovich.pharmacy.dao;

import com.bandarovich.pharmacy.entity.Medicine;
import com.bandarovich.pharmacy.entity.Prescription;
import javafx.util.Pair;

import java.util.List;
import java.util.Optional;

public interface PrescriptionDao {
    int findClientAvailableAmount(int clientId, int medicineId) throws DaoException;
    Optional<Prescription> findPrescription(int medicineId, String clientMail) throws DaoException;
    List<Pair<Prescription, Medicine>> findClientPrescriptionList(String mail) throws DaoException;
    List<Pair<Prescription, Medicine>> findDoctorPrescriptionList(String mail) throws DaoException;
    int requestPrescriptionForExtension(int prescriptionId) throws DaoException;
    int extendPrescription(int prescriptionId, int amount) throws DaoException;
}
