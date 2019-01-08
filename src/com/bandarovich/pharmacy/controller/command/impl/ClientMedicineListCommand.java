package com.bandarovich.pharmacy.controller.command.impl;

import com.bandarovich.pharmacy.controller.JspPath;
import com.bandarovich.pharmacy.controller.command.JspAttribute;
import com.bandarovich.pharmacy.controller.command.PharmacyCommand;
import com.bandarovich.pharmacy.entity.Medicine;
import com.bandarovich.pharmacy.service.MedicineService;
import com.bandarovich.pharmacy.service.ServiceException;
import com.bandarovich.pharmacy.service.impl.MedicineServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

public class ClientMedicineListCommand implements PharmacyCommand {
    private final static Logger logger = LogManager.getLogger();

    @Override
    public String execute(HttpServletRequest request) {
        String mail = (String)request.getSession().getAttribute(JspAttribute.MAIL);
        String nextPage;
        try{
            MedicineService medicineService = new MedicineServiceImpl();
            Set<Medicine> clientMedicines = medicineService.findClientMedicines(mail);
            Set<Medicine> availableMedicines = medicineService.findAllClientMedicines();
            availableMedicines.addAll(clientMedicines);
            request.setAttribute(JspAttribute.MEDICINE_LIST, availableMedicines);
            nextPage = JspPath.CLIENT_PAGE;
        } catch (ServiceException e){
            logger.error(e);
            request.setAttribute(JspAttribute.ERROR_MESSAGE, "Error while loading medicine list.");
            nextPage = JspPath.COMMAND_ERROR_PAGE;
        }
        return nextPage;
    }
}
