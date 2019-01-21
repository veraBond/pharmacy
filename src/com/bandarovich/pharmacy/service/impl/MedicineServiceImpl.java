package com.bandarovich.pharmacy.service.impl;

import com.bandarovich.pharmacy.command.JspAttribute;
import com.bandarovich.pharmacy.dao.DaoException;
import com.bandarovich.pharmacy.dao.MedicineDao;
import com.bandarovich.pharmacy.dao.TransactionHelper;
import com.bandarovich.pharmacy.dao.impl.MedicineDaoImpl;
import com.bandarovich.pharmacy.dao.impl.PrescriptionDaoImpl;
import com.bandarovich.pharmacy.dao.impl.UserDaoImpl;
import com.bandarovich.pharmacy.entity.Medicine;
import com.bandarovich.pharmacy.service.MedicineService;
import com.bandarovich.pharmacy.service.ServiceException;
import com.bandarovich.pharmacy.util.PharmacyValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.print.DocFlavor;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class MedicineServiceImpl implements MedicineService {
    private final static Logger logger = LogManager.getLogger();
    private final static int AVAILABLE_BOOK_MEDICINE_AMOUNT = 5;
    public final static MedicineService INSTANCE = new MedicineServiceImpl();
    private final static String MEDICINE_GROUP_FIRST_SPLIT = "set\\('";
    private final static String MEDICINE_GROUP_SPLIT = "','";
    private final static String MEDICINE_GROUP_LAST_SPLIT = "',\\)";

    private MedicineServiceImpl(){}

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

    @Override
    public List<String> findMedicineGroupList() throws ServiceException {
        MedicineDaoImpl medicineDao = new MedicineDaoImpl();
        try{
            TransactionHelper.beginTransaction(medicineDao);
            String groupSet = medicineDao.findMedicineGroupSet();
            List<String> groups = Arrays.asList(groupSet.split(MEDICINE_GROUP_SPLIT));
            return groups;
        } catch (DaoException e){
            throw new ServiceException(e);
        } finally {
            TransactionHelper.endTransaction(medicineDao);
        }
    }

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

    @Override
    public int findAvailableClientMedicineAmount(int medicineId, String mail) throws ServiceException {
        MedicineDaoImpl medicineDao = new MedicineDaoImpl();
        PrescriptionDaoImpl prescriptionDao = new PrescriptionDaoImpl();
        UserDaoImpl userDao = new UserDaoImpl();
        try{
            TransactionHelper.beginTransaction(medicineDao, prescriptionDao, userDao);
            Medicine medicine = medicineDao.findEntity(medicineId).orElseThrow(ServiceException::new);
            int clientId = userDao.findUserId(mail).orElseThrow(ServiceException::new);
            int storageAmount = medicineDao.findMedicineStorageAmount(medicineId);
            int availableClientMedicineAmount = (AVAILABLE_BOOK_MEDICINE_AMOUNT < storageAmount) ? AVAILABLE_BOOK_MEDICINE_AMOUNT : storageAmount;
            boolean needPrescription = medicine.isNeedPrescription();
            if(needPrescription){
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

    @Override
    public int findAvailableDoctorMedicineAmount(int medicineId) throws ServiceException {
        MedicineDaoImpl medicineDao = new MedicineDaoImpl();
        try{
            TransactionHelper.beginTransaction(medicineDao);
            int storageAmount = medicineDao.findMedicineStorageAmount(medicineId);
            return (AVAILABLE_BOOK_MEDICINE_AMOUNT < storageAmount ? AVAILABLE_BOOK_MEDICINE_AMOUNT : storageAmount);
        } catch (DaoException e){
            throw new ServiceException(e);
        } finally {
            TransactionHelper.endTransaction(medicineDao);
        }
    }



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

    public List<String> validateMedicine(String medicineName, int dosage, String medicineGroup, String packageType,
                                       int packageAmount, double price, int storageAmount){
        List<String> errors = new LinkedList<>();
        if(!PharmacyValidator.medicineNameIsCorrect(medicineName)){
            errors.add(JspAttribute.INCORRECT_MEDICINE_NAME);
        }
        if(!PharmacyValidator.medicineDosageIsCorrect(dosage)){
            errors.add(JspAttribute.INCORRECT_MEDICINE_DOSAGE);
        }
        if(!PharmacyValidator.medicineGroupIsCorrect(medicineGroup)){
            errors.add(JspAttribute.INCORRECT_MEDICINE_GROUP);
        }
        if(!PharmacyValidator.packageTypeIsCorrect(packageType)){
            errors.add(JspAttribute.INCORRECT_PACKAGE_TYPE);
        }
        if(!PharmacyValidator.packageAmountIsCorrect(packageAmount)){
            errors.add(JspAttribute.INCORRECT_PACKAGE_AMOUNT);
        }
        if(!PharmacyValidator.priceIsCorrect(price)){
            errors.add(JspAttribute.INCORRECT_PRICE);
        }
        if(!PharmacyValidator.storageAmountIsCorrect(storageAmount)){
            errors.add(JspAttribute.INCORRECT_STORAGE_AMOUNT);
        }
        return errors;
    }

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
