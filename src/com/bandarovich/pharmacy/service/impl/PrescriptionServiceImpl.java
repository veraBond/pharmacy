package com.bandarovich.pharmacy.service.impl;

import com.bandarovich.pharmacy.dao.DaoException;
import com.bandarovich.pharmacy.dao.TransactionHelper;
import com.bandarovich.pharmacy.dao.impl.MedicineDaoImpl;
import com.bandarovich.pharmacy.dao.impl.PrescriptionDaoImpl;
import com.bandarovich.pharmacy.dao.impl.UserDaoImpl;
import com.bandarovich.pharmacy.entity.Medicine;
import com.bandarovich.pharmacy.entity.PharmacyUser;
import com.bandarovich.pharmacy.entity.Prescription;
import com.bandarovich.pharmacy.entity.PrescriptionStatus;
import com.bandarovich.pharmacy.service.PrescriptionService;
import com.bandarovich.pharmacy.service.ServiceException;
import com.bandarovich.pharmacy.util.PharmacyValidator;
import javafx.util.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class PrescriptionServiceImpl implements PrescriptionService {
    private final static Logger logger = LogManager.getLogger();
    public final static PrescriptionService INSTANCE = new PrescriptionServiceImpl();

    private PrescriptionServiceImpl(){}

    @Override
    public List<Pair<Prescription, Medicine>> findClientPrescriptionList(String mail) throws ServiceException {
        PrescriptionDaoImpl prescriptionDao = new PrescriptionDaoImpl();
        try{
            TransactionHelper.beginTransaction(prescriptionDao);
            return prescriptionDao.findClientPrescriptionList(mail);
        } catch (DaoException e){
            throw new ServiceException(e);
        } finally {
            TransactionHelper.endTransaction(prescriptionDao);
        }
    }

    @Override
    public List<Pair<Prescription, Medicine>> findDoctorPrescriptionList(String mail) throws ServiceException {
        return null;
    }

    @Override
    public boolean writePrescription(int medicineId, String clientMail, String doctorMail, int amount) throws ServiceException {
        PrescriptionDaoImpl prescriptionDao = new PrescriptionDaoImpl();
        UserDaoImpl userDao = new UserDaoImpl();
        try{
            TransactionHelper.beginTransaction(prescriptionDao, userDao);
            Optional<PharmacyUser> client = userDao.findEntity(clientMail);
            if(client.isPresent()){
                int prescriptionId = prescriptionDao.findMaxId() + 1;
                Prescription prescription = new Prescription(prescriptionId, medicineId, clientMail, doctorMail, amount, PrescriptionStatus.ACTIVE, false);
                int result = prescriptionDao.create(prescription);
                if(result == 1){
                    TransactionHelper.commit(prescriptionDao);
                    return true;
                } else {
                    throw new ServiceException("Could not create prescription.");
                }
            } else {
                return false;
            }
        } catch (DaoException e){
            try{
                TransactionHelper.rollBack(prescriptionDao);
            } catch (DaoException daoExc){
                logger.error("Could not roll back.", daoExc);
            }
            throw new ServiceException(e);
        } finally {
            TransactionHelper.endTransaction(prescriptionDao, userDao);
        }
    }

    @Override
    public boolean updatePrescriptionAfterMedicineOrder(int medicineId, String clientMail, int orderAmount) throws ServiceException {
        PrescriptionDaoImpl prescriptionDao = new PrescriptionDaoImpl();
        try{
            TransactionHelper.beginTransaction(prescriptionDao);
            int update = prescriptionDao.updatePrescriptionAmount(orderAmount, medicineId, clientMail);
            if(update <= 1){
                TransactionHelper.commit(prescriptionDao);
                return true;
            } else {
                TransactionHelper.rollBack(prescriptionDao);
                return false;
            }
        } catch (DaoException e){
            try{
                TransactionHelper.rollBack(prescriptionDao);
            } catch (DaoException rollBackExc){
                logger.error("Could not roll back after updating prescription. ", rollBackExc);
            }
            throw new ServiceException(e);
        } finally {
            TransactionHelper.endTransaction(prescriptionDao);
        }
    }

    @Override
    public boolean requestPrescriptionForExtension(int prescriptionId) throws ServiceException{
        PrescriptionDaoImpl prescriptionDao = new PrescriptionDaoImpl();
        try{
            TransactionHelper.beginTransaction(prescriptionDao);
            int result = prescriptionDao.requestPrescriptionForExtension(prescriptionId);
            if(result == 1) {
                TransactionHelper.commit(prescriptionDao);
                return true;
            } else {
                return false;
            }
        } catch (DaoException e){
            try{
                TransactionHelper.rollBack(prescriptionDao);
            } catch (DaoException rollBackExc){
                logger.error("Could not roll back after requestPrescriptionForExtension method. ", rollBackExc);
            }
            throw new ServiceException(e);
        } finally {
            TransactionHelper.endTransaction(prescriptionDao);
        }
    }
}
