package com.bandarovich.pharmacy.service.impl;

import com.bandarovich.pharmacy.dao.DaoException;
import com.bandarovich.pharmacy.dao.TransactionHelper;
import com.bandarovich.pharmacy.dao.impl.MedicineDaoImpl;
import com.bandarovich.pharmacy.dao.impl.OrderDaoImpl;
import com.bandarovich.pharmacy.entity.Medicine;
import com.bandarovich.pharmacy.entity.PharmacyOrder;
import com.bandarovich.pharmacy.service.OrderService;
import com.bandarovich.pharmacy.service.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

public class OrderServiceImpl implements OrderService {
    private final static Logger logger = LogManager.getLogger();
    public final static OrderService INSTANCE = new OrderServiceImpl();

    private OrderServiceImpl(){}

    @Override
    public boolean deleteOrder(PharmacyOrder order) throws ServiceException {
        OrderDaoImpl orderDao = new OrderDaoImpl();
        try{
            TransactionHelper.beginTransaction(orderDao);
            int delete = orderDao.delete(order.getOrderId());
            TransactionHelper.commit(orderDao);
            return delete == 1;
        } catch (DaoException e) {
            try {
                TransactionHelper.rollBack(orderDao);
            } catch (DaoException exc) {
                logger.error("Could not roll back in deleteOrder. ", exc);
            }
            throw new ServiceException(e);
        } finally {
            TransactionHelper.endTransaction(orderDao);
        }
    }
//TODO check!!!!!
    @Override
    public PharmacyOrder completeOrder(String clientMail, int medicineId, int quantity) throws ServiceException{
        OrderDaoImpl orderDao = new OrderDaoImpl();
        MedicineDaoImpl medicineDao = new MedicineDaoImpl();
        try {
            TransactionHelper.beginTransaction(orderDao, medicineDao);
            Medicine medicine = medicineDao.findEntity(medicineId).orElseThrow(ServiceException::new);
            double totalCost = quantity * medicine.getPrice();
            int id = orderDao.findMaxId() + 1;
            PharmacyOrder order = new PharmacyOrder(id, clientMail, medicineId, quantity, totalCost);
            int result = orderDao.create(order);
            if(result == 1){
                TransactionHelper.commit(orderDao);
                return order;
            } else {
                TransactionHelper.rollBack(orderDao);
                throw new ServiceException("Could not create order.");
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
}
