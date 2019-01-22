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
import java.util.List;

public class DoctorMedicineListCommand implements PharmacyCommand {
    private static final Logger logger = LogManager.getLogger();
    private static final String DOCTOR_MEDICINE_LIST_ERROR_MESSAGE = "Could not load doctor medicines. ";
    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router();
        try{
            List<Medicine> doctorMedicines = MedicineServiceImpl.INSTANCE.findDoctorMedicineList();
            request.setAttribute(JspAttribute.CLIENT_MEDICINE_LIST, doctorMedicines);
            router.setForward(JspPath.DOCTOR_PAGE);
        } catch (ServiceException e){
            logger.error(DOCTOR_MEDICINE_LIST_ERROR_MESSAGE, e);
            request.getSession().setAttribute(JspAttribute.ERROR_MESSAGE, DOCTOR_MEDICINE_LIST_ERROR_MESSAGE);
            request.getSession().setAttribute(JspAttribute.ERROR, e);
            router.setForward(JspPath.COMMAND_ERROR_PAGE);
        }
        return router;
    }
}
