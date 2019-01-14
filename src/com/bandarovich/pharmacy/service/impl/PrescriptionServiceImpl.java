package com.bandarovich.pharmacy.service.impl;

import com.bandarovich.pharmacy.dao.DaoException;
import com.bandarovich.pharmacy.dao.TransactionHelper;
import com.bandarovich.pharmacy.dao.impl.MedicineDaoImpl;
import com.bandarovich.pharmacy.dao.impl.PrescriptionDaoImpl;
import com.bandarovich.pharmacy.dao.impl.UserDaoImpl;
import com.bandarovich.pharmacy.entity.Medicine;
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
    public List<Pair<Prescription, Medicine>> findDoctorPrescriptionList(String mail) throws ServiceException {
        return null;
    }

    @Override
    public List<Pair<Prescription, Medicine>> findClientPrescriptionList(String mail) throws ServiceException {
        PrescriptionDaoImpl prescriptionDao = new PrescriptionDaoImpl();
        MedicineDaoImpl medicineDao = new MedicineDaoImpl();
        try{
            TransactionHelper.beginTransaction(prescriptionDao, medicineDao);
            List<Prescription> prescriptions = prescriptionDao.findClientPrescriptionList(mail);
            Optional<Medicine> medicine;
            List<Pair<Prescription, Medicine>> pairList = new LinkedList<>();
            for(Prescription prescription: prescriptions){
                medicine = medicineDao.findEntity(prescription.getMedicineId());
                if(medicine.isPresent()){
                    pairList.add(new Pair<>(prescription, medicine.get()));
                }
            }
            return pairList;
        } catch (DaoException e){
            throw new ServiceException(e);
        } finally {
            TransactionHelper.endTransaction(prescriptionDao, medicineDao);
        }
    }

    @Override
    public List<String> writePrescription(int medicineNumber, String clientMail, String doctorMail, int amount) throws ServiceException {
        List<String> errors = formErrorList(clientMail, medicineNumber, amount);
        if(errors.isEmpty()){
            PrescriptionDaoImpl prescriptionDao = new PrescriptionDaoImpl();
            try{
                TransactionHelper.beginTransaction(prescriptionDao);
                int minPrescriptionNumber = prescriptionDao.findMinPrescriptionNumber();
                Prescription prescription = new Prescription(++minPrescriptionNumber, medicineNumber, clientMail, doctorMail, amount, PrescriptionStatus.ACTIVE, false, true);
                int result = prescriptionDao.create(prescription);
                if(result == 0){
                    throw new ServiceException("Could not create prescription.");
                }
                TransactionHelper.commit(prescriptionDao);
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
        return errors;
    }

    @Override
    public boolean updatePrescriptionAfterMedicineOrder(int medicineId, String clientMail, int orderAmount) throws ServiceException {
        UserDaoImpl userDao = new UserDaoImpl();
        PrescriptionDaoImpl prescriptionDao = new PrescriptionDaoImpl();
        try{
            TransactionHelper.beginTransaction(userDao, prescriptionDao);
            int clientId = userDao.findUserId(clientMail).orElseThrow(ServiceException::new);
            Optional<Prescription> prescriptionOptional = prescriptionDao.findPrescriptionByMedicineIdClientId(medicineId, clientId);
            if(prescriptionOptional.isPresent()){
                int prescriptionAmount = prescriptionOptional.get().getAvailableMedicineAmount();
                prescriptionAmount -= orderAmount;
                prescriptionOptional.get().setAvailableMedicineAmount(prescriptionAmount);
                if(prescriptionAmount == 0){
                    prescriptionOptional.get().setStatus(PrescriptionStatus.INACTIVE);
                }
                int update = prescriptionDao.update(prescriptionOptional.get());
                TransactionHelper.commit(prescriptionDao);
                return update == 1;
            } else {
                return true;
            }
        } catch (DaoException e){
            try{
                TransactionHelper.rollBack(prescriptionDao);
            } catch (DaoException rollBackExc){
                logger.error("Could not roll back after updating prescription", rollBackExc);
            }
            throw new ServiceException(e);
        } finally {
            TransactionHelper.endTransaction(prescriptionDao, userDao);
        }
    }

    private List<String> formErrorList(String clientMail, int medicineNumber, int amount){
        List<String> errors = new LinkedList<>();
        boolean mailIsCorrect = PharmacyValidator.mailIsCorrect(clientMail);
        boolean amountIsCorrect = PharmacyValidator.prescriptionMedicineQuantityIsCorrect(medicineNumber, amount);
        if (!mailIsCorrect) {
            errors.add("Check client e-mail address! It must be like ivan@gmail.com");
        }
        if (!amountIsCorrect) {
            errors.add("Medicine quantity must be no more 5. (May be there is not enough medicines in storage. Please, reduce quantity)");
        }
        return errors;
    }
}
