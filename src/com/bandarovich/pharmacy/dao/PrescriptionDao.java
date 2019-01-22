package com.bandarovich.pharmacy.dao;

import com.bandarovich.pharmacy.entity.Medicine;
import com.bandarovich.pharmacy.entity.Prescription;
import javafx.util.Pair;

import java.util.List;
import java.util.Optional;

/**
 * The Interface PrescriptionDao.
 */
public interface PrescriptionDao {
    
    /**
     * Find client available amount.
     *
     * @param clientId the client id
     * @param medicineId the medicine id
     * @return the int
     * @throws DaoException the dao exception
     */
    int findClientAvailableAmount(int clientId, int medicineId) throws DaoException;
    
    /**
     * Find prescription.
     *
     * @param medicineId the medicine id
     * @param clientMail the client mail
     * @return the optional
     * @throws DaoException the dao exception
     */
    Optional<Prescription> findPrescription(int medicineId, String clientMail) throws DaoException;
    
    /**
     * Find client prescription list.
     *
     * @param mail the mail
     * @return the list
     * @throws DaoException the dao exception
     */
    List<Pair<Prescription, Medicine>> findClientPrescriptionList(String mail) throws DaoException;
    
    /**
     * Find doctor prescription list.
     *
     * @param mail the mail
     * @return the list
     * @throws DaoException the dao exception
     */
    List<Pair<Prescription, Medicine>> findDoctorPrescriptionList(String mail) throws DaoException;
    
    /**
     * Request prescription for extension.
     *
     * @param prescriptionId the prescription id
     * @return the int
     * @throws DaoException the dao exception
     */
    int requestPrescriptionForExtension(int prescriptionId) throws DaoException;
    
    /**
     * Extend prescription.
     *
     * @param prescriptionId the prescription id
     * @param amount the amount
     * @return the int
     * @throws DaoException the dao exception
     */
    int extendPrescription(int prescriptionId, int amount) throws DaoException;
}
