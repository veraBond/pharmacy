package com.bandarovich.pharmacy.service.impl;

import com.bandarovich.pharmacy.dao.DaoException;
import com.bandarovich.pharmacy.dao.TransactionHelper;
import com.bandarovich.pharmacy.dao.impl.PrescriptionDaoImpl;
import com.bandarovich.pharmacy.entity.Medicine;
import com.bandarovich.pharmacy.entity.Prescription;
import com.bandarovich.pharmacy.service.PrescriptionService;
import com.bandarovich.pharmacy.service.ServiceException;
import javafx.util.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class PrescriptionServiceImpl implements PrescriptionService {
    private static final Logger logger = LogManager.getLogger();
    public static final PrescriptionService INSTANCE = new PrescriptionServiceImpl();

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
        PrescriptionDaoImpl prescriptionDao = new PrescriptionDaoImpl();
        try{
            TransactionHelper.beginTransaction(prescriptionDao);
            return prescriptionDao.findDoctorPrescriptionList(mail);
        } catch (DaoException e){
            throw new ServiceException(e);
        } finally {
            TransactionHelper.endTransaction(prescriptionDao);
        }
    }

    @Override
    public void writePrescription(int medicineId, String clientMail, String doctorMail, int amount) throws ServiceException {
        PrescriptionDaoImpl prescriptionDao = new PrescriptionDaoImpl();
        try{
            TransactionHelper.beginTransaction(prescriptionDao);
            int prescriptionId = prescriptionDao.findMaxId() + 1;
            Prescription prescription = new Prescription(prescriptionId, medicineId, clientMail, doctorMail, amount, false);
            int result = prescriptionDao.create(prescription);
            if(result == 1){
                TransactionHelper.commit(prescriptionDao);
            } else {
                TransactionHelper.rollBack(prescriptionDao);
                throw new ServiceException("Could not create prescription.");
            }
        } catch (DaoException e){
            try{
                TransactionHelper.rollBack(prescriptionDao);
            } catch (DaoException daoExc){
                logger.error("Could not roll back.", daoExc);
            }
            throw new ServiceException(e);
        } finally {
            TransactionHelper.endTransaction(prescriptionDao);
        }
    }

    @Override
    public void requestPrescriptionForExtension(int prescriptionId) throws ServiceException{
        PrescriptionDaoImpl prescriptionDao = new PrescriptionDaoImpl();
        try{
            TransactionHelper.beginTransaction(prescriptionDao);
            int result = prescriptionDao.requestPrescriptionForExtension(prescriptionId);
            if(result == 1) {
                TransactionHelper.commit(prescriptionDao);
            } else {
                TransactionHelper.rollBack(prescriptionDao);
                throw new ServiceException("Could not request prescription for extension. ");
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

    @Override
    public void extendPrescription(int prescriptionId, int amount) throws ServiceException {
        PrescriptionDaoImpl prescriptionDao = new PrescriptionDaoImpl();
        try{
            TransactionHelper.beginTransaction(prescriptionDao);
            int result = prescriptionDao.extendPrescription(prescriptionId, amount);
            if(result == 1) {
                TransactionHelper.commit(prescriptionDao);
            } else {
                TransactionHelper.rollBack(prescriptionDao);
                throw new ServiceException("Could not extend prescription. ");
            }
        } catch (DaoException e){
            try{
                TransactionHelper.rollBack(prescriptionDao);
            } catch (DaoException rollBackExc){
                logger.error("Could not roll back after extend prescription method. ", rollBackExc);
            }
            throw new ServiceException(e);
        } finally {
            TransactionHelper.endTransaction(prescriptionDao);
        }
    }
}
