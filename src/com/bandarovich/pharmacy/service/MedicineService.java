package com.bandarovich.pharmacy.service;

import com.bandarovich.pharmacy.entity.Medicine;

import java.util.List;

/**
 * The Interface MedicineService.
 */
public interface MedicineService {
    
    /**
     * Find medicine.
     *
     * @param medicineId the medicine id
     * @return the medicine
     * @throws ServiceException the service exception
     */
    Medicine findMedicine(int medicineId) throws ServiceException;
    
    /**
     * Find all medicine list.
     *
     * @return the list
     * @throws ServiceException the service exception
     */
    List<Medicine> findAllMedicineList() throws ServiceException;
    
    /**
     * Find client medicine list.
     *
     * @param mail the mail
     * @return the list
     * @throws ServiceException the service exception
     */
    List<Medicine> findClientMedicineList(String mail) throws ServiceException;
    
    /**
     * Find doctor medicine list.
     *
     * @return the list
     * @throws ServiceException the service exception
     */
    List<Medicine> findDoctorMedicineList() throws ServiceException;
    
    /**
     * Find medicine group list.
     *
     * @return the list
     * @throws ServiceException the service exception
     */
    List<String> findMedicineGroupList() throws ServiceException;
    
    /**
     * Find package type list.
     *
     * @return the list
     * @throws ServiceException the service exception
     */
    List<String> findPackageTypeList() throws ServiceException;
    
    /**
     * Find available client medicine amount.
     *
     * @param medicineId the medicine id
     * @param mail the mail
     * @return the int
     * @throws ServiceException the service exception
     */
    int findAvailableClientMedicineAmount(int medicineId, String mail) throws ServiceException;
    
    /**
     * Find available doctor medicine amount.
     *
     * @param medicineId the medicine id
     * @return the int
     * @throws ServiceException the service exception
     */
    int findAvailableDoctorMedicineAmount(int medicineId) throws ServiceException;
    
    /**
     * Form medicine.
     *
     * @param medicineName the medicine name
     * @param dosage the dosage
     * @param medicineGroup the medicine group
     * @param packageType the package type
     * @param packageAmount the package amount
     * @param price the price
     * @param prescriptionNeed the prescription need
     * @param storageAmount the storage amount
     * @throws ServiceException the service exception
     */
    void formMedicine(String medicineName, int dosage, String medicineGroup, String packageType,
                      int packageAmount, double price, boolean prescriptionNeed, int storageAmount) throws ServiceException;
    
    /**
     * Validate medicine.
     *
     * @param medicineName the medicine name
     * @param dosage the dosage
     * @param medicineGroup the medicine group
     * @param packageType the package type
     * @param packageAmount the package amount
     * @param price the price
     * @param storageAmount the storage amount
     * @return the list
     * @throws ServiceException the service exception
     */
    List<String> validateMedicine(String medicineName, int dosage, String medicineGroup, String packageType,
                      int packageAmount, double price, int storageAmount) throws ServiceException;
    
    /**
     * Update medicine.
     *
     * @param medicineId the medicine id
     * @param medicineName the medicine name
     * @param dosage the dosage
     * @param medicineGroup the medicine group
     * @param packageType the package type
     * @param packageAmount the package amount
     * @param price the price
     * @param prescriptionNeed the prescription need
     * @param storageAmount the storage amount
     * @throws ServiceException the service exception
     */
    void updateMedicine(int medicineId, String medicineName, int dosage, String medicineGroup, String packageType, int packageAmount, double price, boolean prescriptionNeed, int storageAmount) throws ServiceException;
    
    /**
     * Delete medicine.
     *
     * @param medicineId the medicine id
     * @throws ServiceException the service exception
     */
    void deleteMedicine(int medicineId) throws ServiceException;
}
