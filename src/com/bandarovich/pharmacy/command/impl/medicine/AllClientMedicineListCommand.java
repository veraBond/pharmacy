package com.bandarovich.pharmacy.command.impl.medicine;

import com.bandarovich.pharmacy.command.JspAttribute;
import com.bandarovich.pharmacy.command.PharmacyCommand;
import com.bandarovich.pharmacy.command.Router;
import com.bandarovich.pharmacy.entity.Medicine;
import com.bandarovich.pharmacy.service.ServiceException;
import com.bandarovich.pharmacy.service.impl.MedicineServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class AllClientMedicineListCommand implements PharmacyCommand {
    private final static Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request){
//        try{
//            List<Medicine> medicineList =  MedicineServiceImpl.INSTANCE.findAllClientAvailableMedicines();
//            logger.info(medicineList);
//            request.setAttribute(JspAttribute.MEDICINE_LIST, medicineList);
//        } catch (ServiceException e){
//            logger.error(e.getMessage(), e);
//        }
        return null;
    }
}
