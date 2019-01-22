package com.bandarovich.pharmacy.service.impl;

import com.bandarovich.pharmacy.command.JspAttribute;
import com.bandarovich.pharmacy.dao.DaoException;
import com.bandarovich.pharmacy.dao.TransactionHelper;
import com.bandarovich.pharmacy.dao.impl.MedicineDaoImpl;
import com.bandarovich.pharmacy.dao.impl.PrescriptionDaoImpl;
import com.bandarovich.pharmacy.dao.impl.UserDaoImpl;
import com.bandarovich.pharmacy.entity.Medicine;
import com.bandarovich.pharmacy.service.MedicineService;
import com.bandarovich.pharmacy.service.ServiceException;
import com.bandarovich.pharmacy.util.MedicineValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.LinkedList;
import java.util.List;

/**
 * The Class MedicineServiceImpl.
 */
public class MedicineServiceImpl implements MedicineService {
    
    /** The Constant logger. */
    private static final Logger logger = LogManager.getLogger();
    
    /** The Constant INSTANCE. */
    public static final MedicineService INSTANCE = new MedicineServiceImpl();

    /**
     * Instantiates a new medicine service impl.
     */
    private MedicineServiceImpl(){}

    /* (non-Javadoc)
     * @see com.bandarovich.pharmacy.service.MedicineService#findMedicine(int)
     */
    @Override
    public Medicine findMedicine(int medicineId) throws ServiceException{
        MedicineDaoImpl medicineDao = new MedicineDaoImpl();
        try{
            TransactionHelper.beginTransaction(medicineDao);
            return medicineDao.findEntity(medicineId).orElseThrow(ServiceException::new);
        } catch (DaoException e){
            throw new ServiceException(e);
        } finally {
            TransactionHelper.endTransaction(medicineDao);
        }
    }

    /* (non-Javadoc)
     * @see com.bandarovich.pharmacy.service.MedicineService#findAllMedicineList()
     */
    @Override
    public List<Medicine> findAllMedicineList() throws ServiceException {
        MedicineDaoImpl medicineDao = new MedicineDaoImpl();
        try{
            TransactionHelper.beginTransaction(medicineDao);
            return medicineDao.findAll();
        } catch (DaoException e){
            throw new ServiceException(e);
        } finally {
            TransactionHelper.endTransaction(medicineDao);
        }
    }

    /* (non-Javadoc)
     * @see com.bandarovich.pharmacy.service.MedicineService#findClientMedicineList(java.lang.String)
     */
    @Override
    public List<Medicine> findClientMedicineList(String mail) throws ServiceException {
        MedicineDaoImpl medicineDao = new MedicineDaoImpl();
        try{
            TransactionHelper.beginTransaction(medicineDao);
            List<Medicine> clientMedicines = medicineDao.findClientMedicineList(mail);
            clientMedicines.addAll(medicineDao.findAllClientAvailableMedicineList());
            return clientMedicines;
        } catch (DaoException e){
            throw new ServiceException(e);
        } finally {
            TransactionHelper.endTransaction(medicineDao);
        }
    }

    /* (non-Javadoc)
     * @see com.bandarovich.pharmacy.service.MedicineService#findDoctorMedicineList()
     */
    @Override
    public List<Medicine> findDoctorMedicineList() throws ServiceException {
        MedicineDaoImpl medicineDao = new MedicineDaoImpl();
        try{
            TransactionHelper.beginTransaction(medicineDao);
            return medicineDao.findDoctorMedicineList();
        } catch (DaoException e){
            throw new ServiceException(e);
        } finally {
            TransactionHelper.endTransaction(medicineDao);
        }
    }

    /* (non-Javadoc)
     * @see com.bandarovich.pharmacy.service.MedicineService#findMedicineGroupList()
     */
    @Override
    public List<String> findMedicineGroupList() throws ServiceException {
        MedicineDaoImpl medicineDao = new MedicineDaoImpl();
        try{
            TransactionHelper.beginTransaction(medicineDao);
            return medicineDao.findMedicineGroupList();
        } catch (DaoException e){
            throw new ServiceException(e);
        } finally {
            TransactionHelper.endTransaction(medicineDao);
        }
    }

    /* (non-Javadoc)
     * @see com.bandarovich.pharmacy.service.MedicineService#findPackageTypeList()
     */
    @Override
    public List<String> findPackageTypeList() throws ServiceException {
        MedicineDaoImpl medicineDao = new MedicineDaoImpl();
        try{
            TransactionHelper.beginTransaction(medicineDao);
            return medicineDao.findPackageTypeList();
        } catch (DaoException e){
            throw new ServiceException(e);
        } finally {
            TransactionHelper.endTransaction(medicineDao);
        }
    }

    /* (non-Javadoc)
     * @see com.bandarovich.pharmacy.service.MedicineService#findAvailableClientMedicineAmount(int, java.lang.String)
     */
    @Override
    public int findAvailableClientMedicineAmount(int medicineId, String mail) throws ServiceException {
        MedicineDaoImpl medicineDao = new MedicineDaoImpl();
        PrescriptionDaoImpl prescriptionDao = new PrescriptionDaoImpl();
        UserDaoImpl userDao = new UserDaoImpl();
        try{
            TransactionHelper.beginTransaction(medicineDao, prescriptionDao, userDao);
            Medicine medicine = medicineDao.findEntity(medicineId).orElseThrow(ServiceException::new);
            int clientId = userDao.findUserId(mail).orElseThrow(ServiceException::new);
            int availableClientMedicineAmount = medicineDao.findMedicineStorageAmount(medicineId);
            if(medicine.isNeedPrescription()){
                int prescriptionAvailableAmount = prescriptionDao.findClientAvailableAmount(clientId, medicineId);
                if(prescriptionAvailableAmount < availableClientMedicineAmount){
                    availableClientMedicineAmount = prescriptionAvailableAmount;
                }
           }
            return availableClientMedicineAmount;
        } catch (DaoException e) {
            throw new ServiceException(e);
        } finally {
            TransactionHelper.endTransaction(medicineDao, prescriptionDao, userDao);
        }
    }

    /* (non-Javadoc)
     * @see com.bandarovich.pharmacy.service.MedicineService#findAvailableDoctorMedicineAmount(int)
     */
    @Override
    public int findAvailableDoctorMedicineAmount(int medicineId) throws ServiceException {
        MedicineDaoImpl medicineDao = new MedicineDaoImpl();
        try{
            TransactionHelper.beginTransaction(medicineDao);
            return medicineDao.findMedicineStorageAmount(medicineId);
        } catch (DaoException e){
            throw new ServiceException(e);
        } finally {
            TransactionHelper.endTransaction(medicineDao);
        }
    }

    /* (non-Javadoc)
     * @see com.bandarovich.pharmacy.service.MedicineService#formMedicine(java.lang.String, int, java.lang.String, java.lang.String, int, double, boolean, int)
     */
    @Override
    public void formMedicine(String medicineName, int dosage, String medicineGroup, String packageType, int packageAmount, double price, boolean prescriptionNeed, int storageAmount)throws ServiceException {
        MedicineDaoImpl medicineDao = new MedicineDaoImpl();
        try{
            TransactionHelper.beginTransaction(medicineDao);
            int id = medicineDao.findMaxId() + 1;
            medicineDao.create(new Medicine(id, medicineName, dosage, medicineGroup, packageType, packageAmount, price, prescriptionNeed, storageAmount));
            TransactionHelper.commit(medicineDao);
        } catch (DaoException e) {
            try {
                TransactionHelper.rollBack(medicineDao);
            } catch (DaoException rbexc) {
                logger.error("Roll back error in formMedicine. ", rbexc);
            }
            throw new ServiceException(e);
        } finally {
            TransactionHelper.endTransaction(medicineDao);
        }
    }

    /* (non-Javadoc)
     * @see com.bandarovich.pharmacy.service.MedicineService#validateMedicine(java.lang.String, int, java.lang.String, java.lang.String, int, double, int)
     */
    @Override
    public List<String> validateMedicine(String medicineName, int dosage, String medicineGroup, String packageType,
                                       int packageAmount, double price, int storageAmount){
        List<String> errors = new LinkedList<>();
        if(!MedicineValidator.medicineNameIsCorrect(medicineName)){
            errors.add(JspAttribute.INCORRECT_MEDICINE_NAME);
        }
        if(!MedicineValidator.medicineDosageIsCorrect(dosage)){
            errors.add(JspAttribute.INCORRECT_MEDICINE_DOSAGE);
        }
        if(!MedicineValidator.medicineGroupIsCorrect(medicineGroup)){
            errors.add(JspAttribute.INCORRECT_MEDICINE_GROUP);
        }
        if(!MedicineValidator.packageTypeIsCorrect(packageType)){
            errors.add(JspAttribute.INCORRECT_PACKAGE_TYPE);
        }
        if(!MedicineValidator.packageAmountIsCorrect(packageAmount)){
            errors.add(JspAttribute.INCORRECT_PACKAGE_AMOUNT);
        }
        if(!MedicineValidator.priceIsCorrect(price)){
            errors.add(JspAttribute.INCORRECT_PRICE);
        }
        if(!MedicineValidator.storageAmountIsCorrect(storageAmount)){
            errors.add(JspAttribute.INCORRECT_STORAGE_AMOUNT);
        }
        return errors;
    }

    /* (non-Javadoc)
     * @see com.bandarovich.pharmacy.service.MedicineService#updateMedicine(int, java.lang.String, int, java.lang.String, java.lang.String, int, double, boolean, int)
     */
    @Override
    public void updateMedicine(int medicineId, String medicineName, int dosage, String medicineGroup, String packageType, int packageAmount, double price, boolean prescriptionNeed, int storageAmount) throws ServiceException {
        Medicine medicine = new Medicine(medicineId, medicineName, dosage, medicineGroup, packageType, packageAmount, price, prescriptionNeed, storageAmount);
        MedicineDaoImpl medicineDao = new MedicineDaoImpl();
        try{
            TransactionHelper.beginTransaction(medicineDao);
            medicineDao.update(medicine);
            TransactionHelper.commit(medicineDao);
        }  catch (DaoException e) {
            try {
                TransactionHelper.rollBack(medicineDao);
            } catch (DaoException rbexc) {
                logger.error("Roll back error in updateMedicine. ", rbexc);
            }
            throw new ServiceException(e);
        } finally {
            TransactionHelper.endTransaction(medicineDao);
        }
    }

    /* (non-Javadoc)
     * @see com.bandarovich.pharmacy.service.MedicineService#deleteMedicine(int)
     */
    @Override
    public void deleteMedicine(int medicineId) throws ServiceException {
        MedicineDaoImpl medicineDao = new MedicineDaoImpl();
        try{
            TransactionHelper.beginTransaction(medicineDao);
            medicineDao.delete(medicineId);
            TransactionHelper.commit(medicineDao);
        }  catch (DaoException e) {
            try {
                TransactionHelper.rollBack(medicineDao);
            } catch (DaoException rbexc) {
                logger.error("Roll back error in delete medicine. ", rbexc);
            }
            throw new ServiceException(e);
        } finally {
            TransactionHelper.endTransaction(medicineDao);
        }
    }
}
