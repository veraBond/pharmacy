package com.bandarovich.pharmacy.service.impl;

import com.bandarovich.pharmacy.dao.DaoException;
import com.bandarovich.pharmacy.dao.TransactionHelper;
import com.bandarovich.pharmacy.dao.impl.MedicineDaoImpl;
import com.bandarovich.pharmacy.dao.impl.PrescriptionDaoImpl;
import com.bandarovich.pharmacy.dao.impl.UserDaoImpl;
import com.bandarovich.pharmacy.entity.Medicine;
import com.bandarovich.pharmacy.service.MedicineService;
import com.bandarovich.pharmacy.service.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

public class MedicineServiceImpl implements MedicineService {
    private final static Logger logger = LogManager.getLogger();
    private final static int AVAILABLE_BOOK_MEDICINE_AMOUNT = 5;
    public final static MedicineService INSTANCE = new MedicineServiceImpl();

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
    public List<Medicine> findAllClientAvailableMedicineList() throws ServiceException{
        MedicineDaoImpl medicineDao = new MedicineDaoImpl();
        try{
            TransactionHelper.beginTransaction(medicineDao);
            return medicineDao.findAllClientAvailableMedicineList();
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
            return medicineDao.findClientMedicineList(mail);
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
            boolean needPrescription = medicine.needPrescription();
            if(needPrescription){
                int prescriptionAvailableAmount = prescriptionDao.findClientAvailableAmount(clientId, medicineId);
                availableClientMedicineAmount = (prescriptionAvailableAmount < availableClientMedicineAmount) ? prescriptionAvailableAmount : availableClientMedicineAmount;
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
            return (storageAmount > AVAILABLE_BOOK_MEDICINE_AMOUNT ? AVAILABLE_BOOK_MEDICINE_AMOUNT : storageAmount);
        } catch (DaoException e){
            throw new ServiceException(e);
        } finally {
            TransactionHelper.endTransaction(medicineDao);
        }
    }

    @Override
    public boolean updateMedicineStorageAmount(int medicineId, int orderAmount) throws ServiceException{
        MedicineDaoImpl medicineDao = new MedicineDaoImpl();
        try{
            TransactionHelper.beginTransaction(medicineDao);
            int update = medicineDao.updateStorageAmount(medicineId, orderAmount);
            if(update == 1){
                TransactionHelper.commit(medicineDao);
                return true;
            } else {
                TransactionHelper.rollBack(medicineDao);
                return false;
            }
        } catch (DaoException e){
            try{
                TransactionHelper.rollBack(medicineDao);
            } catch (DaoException rollBackExc){
                logger.error("Could not roll back in updateMedicineStorageAmount method.", rollBackExc);
            }
            throw new ServiceException(e);
        } finally {
            TransactionHelper.endTransaction(medicineDao);
        }
    }
}
