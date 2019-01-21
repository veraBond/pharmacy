package com.bandarovich.pharmacy.command.impl.medicine;

import com.bandarovich.pharmacy.command.JspAttribute;
import com.bandarovich.pharmacy.command.JspPath;
import com.bandarovich.pharmacy.command.PharmacyCommand;
import com.bandarovich.pharmacy.command.Router;
import com.bandarovich.pharmacy.entity.Medicine;
import com.bandarovich.pharmacy.service.ServiceException;
import com.bandarovich.pharmacy.service.impl.MedicineServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class CompleteDeleteMedicineCommand implements PharmacyCommand {
    private final static Logger logger = LogManager.getLogger();
    private final static String DELETE_MEDICINE_ERROR_MESSAGE = "Could not delete medicine. ";

    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router();
        int medicineId = Integer.parseInt(request.getParameter(JspAttribute.MEDICINE_ID));
        try{
            MedicineServiceImpl.INSTANCE.deleteMedicine(medicineId);
            router.setRedirect(JspPath.SUCCESSFULLY_COMPLETE_PAGE);
        } catch (ServiceException e){
            request.getSession().setAttribute(JspAttribute.ERROR_MESSAGE, DELETE_MEDICINE_ERROR_MESSAGE);
            request.getSession().setAttribute(JspAttribute.ERROR, e);
            logger.error(DELETE_MEDICINE_ERROR_MESSAGE, e);
            router.setForward(JspPath.COMMAND_ERROR_PAGE);
        }
        return router;
    }
}