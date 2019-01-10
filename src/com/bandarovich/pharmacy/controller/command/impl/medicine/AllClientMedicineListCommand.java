package com.bandarovich.pharmacy.controller.command.impl.medicine;

import com.bandarovich.pharmacy.controller.command.JspPath;
import com.bandarovich.pharmacy.controller.command.JspAttribute;
import com.bandarovich.pharmacy.controller.command.PharmacyCommand;
import com.bandarovich.pharmacy.entity.Medicine;
import com.bandarovich.pharmacy.service.ServiceException;
import com.bandarovich.pharmacy.service.impl.MedicineServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Set;

public class AllClientMedicineListCommand implements PharmacyCommand {
    private final static Logger logger = LogManager.getLogger();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("AllClientMedicineListCommand started.");
        try{
            List<Medicine> medicineList =  MedicineServiceImpl.INSTANCE.findAllClientMedicines();
            logger.info(medicineList);
            request.setAttribute(JspAttribute.MEDICINE_LIST, medicineList);
        } catch (ServiceException e){
            logger.error(e.getMessage(), e);
        }
        request.getRequestDispatcher(JspPath.START_PAGE).forward(request, response);

    }
}