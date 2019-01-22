package com.bandarovich.pharmacy.dao;

import com.bandarovich.pharmacy.entity.Medicine;

import java.util.List;

/**
 * The Interface MedicineDao.
 */
public interface MedicineDao {
    
    /**
     * Find all client available medicine list.
     *
     * @return the list
     * @throws DaoException the dao exception
     */
    List<Medicine> findAllClientAvailableMedicineList() throws DaoException;
    
    /**
     * Find client medicine list.
     *
     * @param mail the mail
     * @return the list
     * @throws DaoException the dao exception
     */
    List<Medicine> findClientMedicineList(String mail) throws DaoException;
    
    /**
     * Find doctor medicine list.
     *
     * @return the list
     * @throws DaoException the dao exception
     */
    List<Medicine> findDoctorMedicineList() throws  DaoException;
    
    /**
     * Find medicine group list.
     *
     * @return the list
     * @throws DaoException the dao exception
     */
    List<String> findMedicineGroupList() throws  DaoException;
    
    /**
     * Find package type list.
     *
     * @return the list
     * @throws DaoException the dao exception
     */
    List<String> findPackageTypeList() throws  DaoException;
    
    /**
     * Find medicine storage amount.
     *
     * @param medicineId the medicine id
     * @return the int
     * @throws DaoException the dao exception
     */
    int findMedicineStorageAmount(int medicineId) throws DaoException;
}
