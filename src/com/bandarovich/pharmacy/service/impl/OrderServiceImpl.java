package com.bandarovich.pharmacy.service.impl;

import com.bandarovich.pharmacy.dao.DaoException;
import com.bandarovich.pharmacy.dao.TransactionHelper;
import com.bandarovich.pharmacy.dao.impl.MedicineDaoImpl;
import com.bandarovich.pharmacy.dao.impl.OrderDaoImpl;
import com.bandarovich.pharmacy.dao.impl.PrescriptionDaoImpl;
import com.bandarovich.pharmacy.entity.Medicine;
import com.bandarovich.pharmacy.entity.PharmacyOrder;
import com.bandarovich.pharmacy.entity.Prescription;
import com.bandarovich.pharmacy.service.OrderService;
import com.bandarovich.pharmacy.service.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;


public class OrderServiceImpl implements OrderService {
    private static final Logger logger = LogManager.getLogger();
    public static final OrderService INSTANCE = new OrderServiceImpl();

    private OrderServiceImpl(){}

    @Override
    public double completeOrder(String clientMail, int medicineId, int quantity) throws ServiceException{
        updatePrescription(clientMail, medicineId, quantity);
        updateStorageAmount(medicineId, quantity);
        return createOrder(clientMail, medicineId, quantity);
    }

    private double createOrder(String clientMail, int medicineId, int quantity) throws ServiceException{
        OrderDaoImpl orderDao = new OrderDaoImpl();
        MedicineDaoImpl medicineDao = new MedicineDaoImpl();
        try {
            TransactionHelper.beginTransaction(orderDao, medicineDao);
            Medicine medicine = medicineDao.findEntity(medicineId).orElseThrow(ServiceException::new);
            int id = orderDao.findMaxId() + 1;
            double totalCost = quantity * medicine.getPrice();
            PharmacyOrder order = new PharmacyOrder(id, clientMail, medicineId, quantity, totalCost);
            boolean createOrder = orderDao.create(order) == 1;
            if (createOrder) {
                TransactionHelper.commit(orderDao);
                return totalCost;
            } else {
                TransactionHelper.rollBack(orderDao);
                throw new ServiceException("Could not create order. ");
            }
        } catch (DaoException e) {
            try {
                TransactionHelper.rollBack(orderDao);
            } catch (DaoException rollbackExc) {
                logger.error("Could not roll back in createOrder. ", rollbackExc);
            }
            throw new ServiceException(e);
        } finally {
            TransactionHelper.endTransaction(orderDao, medicineDao);
        }
    }

    private void updateStorageAmount(int medicineId, int quantity) throws ServiceException{
        MedicineDaoImpl medicineDao = new MedicineDaoImpl();
        try {
            TransactionHelper.beginTransaction(medicineDao);
            Medicine medicine = medicineDao.findEntity(medicineId).orElseThrow(ServiceException::new);
            int updateStorageAmount = medicine.getStorageAmount() - quantity;
            medicine.setStorageAmount(updateStorageAmount);
            boolean updateMedicine = medicineDao.update(medicine) == 1;
            if (updateMedicine) {
                TransactionHelper.commit(medicineDao);
            } else {
                TransactionHelper.rollBack(medicineDao);
                throw new ServiceException("Could not update medicine storage. ");
            }
        } catch (DaoException e) {
            try {
                TransactionHelper.rollBack(medicineDao);
            } catch (DaoException rollbackExc) {
                logger.error("Could not roll back in update storage amount. ", rollbackExc);
            }
            throw new ServiceException(e);
        } finally {
            TransactionHelper.endTransaction(medicineDao);
        }
    }

    private void updatePrescription(String clientMail, int medicineId, int quantity) throws ServiceException{
        PrescriptionDaoImpl prescriptionDao = new PrescriptionDaoImpl();
        try {
            TransactionHelper.beginTransaction(prescriptionDao);
            Optional<Prescription> prescriptionOptional = prescriptionDao.findPrescription(medicineId, clientMail);
            boolean updatePrescription = true;
            if(prescriptionOptional.isPresent()){
                int updatePrescriptionAmount = prescriptionOptional.get().getAvailableMedicineAmount() - quantity;
                prescriptionOptional.get().setAvailableMedicineAmount(updatePrescriptionAmount);
                updatePrescription = prescriptionDao.update(prescriptionOptional.get()) == 1;
            }
            if (updatePrescription) {
                TransactionHelper.commit(prescriptionDao);
            } else {
                TransactionHelper.rollBack(prescriptionDao);
                throw new ServiceException("Could not update prescription amount. ");
            }
        } catch (DaoException e) {
            try {
                TransactionHelper.rollBack(prescriptionDao);
            } catch (DaoException rollbackExc) {
                logger.error("Could not roll back in update prescription amount. ", rollbackExc);
            }
            throw new ServiceException(e);
        } finally {
            TransactionHelper.endTransaction(prescriptionDao);
        }
    }
}
