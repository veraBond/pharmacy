package com.bandarovich.pharmacy.service;

import com.bandarovich.pharmacy.entity.Medicine;
import com.bandarovich.pharmacy.entity.Prescription;
import javafx.util.Pair;

import java.util.List;

/**
 * The Interface PrescriptionService.
 */
public interface PrescriptionService {
    
    /**
     * Find client prescription list.
     *
     * @param mail the mail
     * @return the list
     * @throws ServiceException the service exception
     */
    List<Pair<Prescription, Medicine>> findClientPrescriptionList(String mail) throws ServiceException;
    
    /**
     * Find doctor prescription list.
     *
     * @param mail the mail
     * @return the list
     * @throws ServiceException the service exception
     */
    List<Pair<Prescription, Medicine>> findDoctorPrescriptionList(String mail) throws ServiceException;
    
    /**
     * Write prescription.
     *
     * @param medicineId the medicine id
     * @param clientMail the client mail
     * @param doctorMail the doctor mail
     * @param amount the amount
     * @throws ServiceException the service exception
     */
    void writePrescription(int medicineId, String clientMail, String doctorMail, int amount) throws ServiceException;
    
    /**
     * Request prescription for extension.
     *
     * @param prescriptionId the prescription id
     * @throws ServiceException the service exception
     */
    void requestPrescriptionForExtension(int prescriptionId) throws ServiceException;
    
    /**
     * Extend prescription.
     *
     * @param prescriptionId the prescription id
     * @param amount the amount
     * @throws ServiceException the service exception
     */
    void extendPrescription(int prescriptionId, int amount) throws ServiceException;
}
