package com.bandarovich.pharmacy.service.impl;

import com.bandarovich.pharmacy.dao.DaoException;
import com.bandarovich.pharmacy.dao.TransactionHelper;
import com.bandarovich.pharmacy.dao.impl.MedicineDao;
import com.bandarovich.pharmacy.entity.Medicine;
import com.bandarovich.pharmacy.service.MedicineService;
import com.bandarovich.pharmacy.service.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Set;

public class MedicineServiceImpl implements MedicineService {
    private final static Logger logger = LogManager.getLogger();
    public final static MedicineServiceImpl INSTANCE = new MedicineServiceImpl();

    private MedicineServiceImpl(){}

    @Override
    public List<Medicine> findAllClientMedicines() throws ServiceException{
        MedicineDao medicineDao = new MedicineDao();
        try{
            TransactionHelper.beginTransaction(medicineDao);
            return medicineDao.findAllClientMedicines();
        } catch (DaoException e){
            logger.error("Error in MedicineDao: could not get medicine list.", e);
            throw new ServiceException(e);
        } finally {
            TransactionHelper.endTransaction(medicineDao);
        }
    }

    @Override
    public List<Medicine> findClientMedicines(String mail) throws ServiceException {
        MedicineDao medicineDao = new MedicineDao();
        try{
            TransactionHelper.beginTransaction(medicineDao);
            return medicineDao.findClientMedicines(mail);
        } catch (DaoException e){
            logger.error("Error in MedicineDao: could not get client medicine list.", e);
            throw new ServiceException(e);
        } finally {
            TransactionHelper.endTransaction(medicineDao);
        }
    }
}
