package com.bandarovich.pharmacy.command.impl;

import com.bandarovich.pharmacy.command.JspAttribute;
import com.bandarovich.pharmacy.command.JspPath;
import com.bandarovich.pharmacy.command.PharmacyCommand;
import com.bandarovich.pharmacy.command.Router;
import com.bandarovich.pharmacy.entity.Medicine;
import com.bandarovich.pharmacy.entity.PharmacyPosition;
import com.bandarovich.pharmacy.service.ServiceException;
import com.bandarovich.pharmacy.service.impl.MedicineServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class StartPageCommand implements PharmacyCommand {
    private final static Logger logger = LogManager.getLogger();
    private final static String POSITION_ERROR_MESSAGE = "Unexpected position.";
    private final static String START_PAGE_ERROR_MESSAGE = "Could not load data fo start page. ";

    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router();
        String startPage = (String) request.getSession().getAttribute(JspAttribute.START_PAGE);
        if(startPage == null){
            startPage = JspPath.START_PAGE;
        }
        router.setRedirect(startPage);
        try{
            String mail = (String)request.getSession().getAttribute(JspAttribute.MAIL);
            PharmacyPosition position = (PharmacyPosition)request.getSession().getAttribute(JspAttribute.POSITION);
            List<Medicine> clientMedicineList = MedicineServiceImpl.INSTANCE.findClientMedicineList(mail);
            request.getSession().setAttribute(JspAttribute.CLIENT_MEDICINE_LIST, clientMedicineList);
            switch (position) {
                case CLIENT:
                    break;
                case DOCTOR:
                    List<Medicine> doctorMedicines = MedicineServiceImpl.INSTANCE.findDoctorMedicineList();
                    request.getSession().setAttribute( JspAttribute.DOCTOR_MEDICINE_LIST, doctorMedicines);
                    break;
                case PHARMACIST:
                    List<Medicine> medicines = MedicineServiceImpl.INSTANCE.findAllMedicineList();
                    request.getSession().setAttribute( JspAttribute.PHARMACIST_MEDICINE_LIST, medicines);
                    break;
                default:
                    logger.warn(POSITION_ERROR_MESSAGE + position);
                    request.getSession().setAttribute(JspAttribute.ERROR_MESSAGE, POSITION_ERROR_MESSAGE);
                    router.setRedirect(JspPath.COMMAND_ERROR_PAGE);
            }
        } catch (ServiceException e){
            logger.error(START_PAGE_ERROR_MESSAGE, e);
            router.setRedirect(JspPath.COMMAND_ERROR_PAGE);
            request.getSession().setAttribute(JspAttribute.ERROR, e);
            request.getSession().setAttribute(JspAttribute.ERROR_MESSAGE, START_PAGE_ERROR_MESSAGE);
        }

        return router;
    }
}
