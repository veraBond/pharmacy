package com.bandarovich.pharmacy.command.impl.prescription;

import com.bandarovich.pharmacy.command.JspAttribute;
import com.bandarovich.pharmacy.command.JspPath;
import com.bandarovich.pharmacy.command.PharmacyCommand;
import com.bandarovich.pharmacy.command.Router;
import com.bandarovich.pharmacy.entity.Medicine;
import com.bandarovich.pharmacy.entity.Prescription;
import com.bandarovich.pharmacy.service.ServiceException;
import com.bandarovich.pharmacy.service.impl.PrescriptionServiceImpl;
import javafx.util.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class ClientPrescriptionListCommand implements PharmacyCommand {
    private final static Logger logger = LogManager.getLogger();
    private final static String EMPTY_LIST_MESSAGE = "You have no prescriptions yet";
    private final static String CLIENT_PRESCRIPTION_LIST_ERROR_MESSAGE = "Error while loading prescription list";

    @Override
    public Router execute(HttpServletRequest request) {
        String mail = (String)request.getSession().getAttribute(JspAttribute.MAIL);
        Router router = new Router();
        try{
            List<Pair<Prescription, Medicine>> clientPrescriptionList = PrescriptionServiceImpl.INSTANCE.findClientPrescriptionList(mail);
            if(!clientPrescriptionList.isEmpty()) {
                request.setAttribute(JspAttribute.PRESCRIPTION_LIST, clientPrescriptionList);
            } else {
                request.setAttribute(JspAttribute.MESSAGE, EMPTY_LIST_MESSAGE);
            }
            router.setForward(JspPath.CLIENT_PRESCRIPTION_PAGE);
        } catch (ServiceException e){
            logger.error(CLIENT_PRESCRIPTION_LIST_ERROR_MESSAGE, e);
            request.getSession().setAttribute(JspAttribute.ERROR_MESSAGE, CLIENT_PRESCRIPTION_LIST_ERROR_MESSAGE + e);
            router.setRedirect(JspPath.COMMAND_ERROR_PAGE);
        }
        return router;
    }
}
