package com.bandarovich.pharmacy.service;

import com.bandarovich.pharmacy.entity.Medicine;
import com.bandarovich.pharmacy.entity.Prescription;
import javafx.util.Pair;

import java.util.List;

public interface PrescriptionService {
    List<Pair<Prescription, Medicine>> findClientPrescriptionList(String mail) throws ServiceException;
    List<Pair<Prescription, Medicine>> findDoctorPrescriptionList(String mail) throws ServiceException;
    void writePrescription(int medicineId, String clientMail, String doctorMail, int amount) throws ServiceException;
    void requestPrescriptionForExtension(int prescriptionId) throws ServiceException;
    void extendPrescription(int prescriptionId, int amount) throws ServiceException;
}
