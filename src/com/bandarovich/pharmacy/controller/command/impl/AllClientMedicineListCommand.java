package com.bandarovich.pharmacy.controller.command.impl;

import com.bandarovich.pharmacy.controller.JspPath;
import com.bandarovich.pharmacy.controller.command.JspAttribute;
import com.bandarovich.pharmacy.controller.command.PharmacyCommand;
import com.bandarovich.pharmacy.entity.Medicine;
import com.bandarovich.pharmacy.service.ServiceException;
import com.bandarovich.pharmacy.service.impl.MedicineServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

public class AllClientMedicineListCommand implements PharmacyCommand {
    private final static Logger logger = LogManager.getLogger();

    @Override
    public String execute(HttpServletRequest request) {
        logger.info("AllClientMedicineListCommand started.");
        try{
            Set<Medicine> medicineList = new MedicineServiceImpl().findAllClientMedicines();
            logger.info(medicineList);
            request.setAttribute(JspAttribute.MEDICINE_LIST, medicineList);
        } catch (ServiceException e){
            logger.error(e.getMessage(), e);
        }
        return JspPath.START_PAGE;
    }
}
