package com.bandarovich.pharmacy.service;

import com.bandarovich.pharmacy.entity.Medicine;
import com.bandarovich.pharmacy.entity.Prescription;
import javafx.util.Pair;

import java.util.List;

public interface PrescriptionService {
    List<Pair<Prescription, Medicine>> findClientPrescriptionList(String mail) throws ServiceException;
    List<Pair<Prescription, Medicine>> findDoctorPrescriptionList(String mail) throws ServiceException;
    boolean writePrescription(int medicineId, String clientMail, String doctorMail, int amount) throws ServiceException;
    boolean updatePrescriptionAfterMedicineOrder(int medicineId, String clientMail, int orderAmount) throws ServiceException;
    boolean requestPrescriptionForExtension(int prescriptionId) throws ServiceException;
}
