package com.bandarovich.pharmacy.command.impl.prescription;

import com.bandarovich.pharmacy.command.JspAttribute;
import com.bandarovich.pharmacy.command.JspPath;
import com.bandarovich.pharmacy.command.PharmacyCommand;
import com.bandarovich.pharmacy.command.Router;
import com.bandarovich.pharmacy.service.ServiceException;
import com.bandarovich.pharmacy.service.impl.PrescriptionServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class RequestPrescriptionForExtensionCommand implements PharmacyCommand {
    private final static Logger logger = LogManager.getLogger();
    private final static String SUCCESSFULLY_REQUESTED_MESSAGE = "Request was sent successfully.";
    private final static String NOT_REQUESTED_MESSAGE = "Request was not sent successfully.";
    private final static String REQUEST_PRESCRIPTION_ERROR_MESSAGE = "Error in request prescription for extension.";

    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router();
        int prescriptionId = (Integer)(request.getAttribute(JspAttribute.PRESCRIPTION_ID));
        try{
            boolean requested = PrescriptionServiceImpl.INSTANCE.requestPrescriptionForExtension(prescriptionId);
            if(requested){
                request.getSession().setAttribute(JspAttribute.MESSAGE, SUCCESSFULLY_REQUESTED_MESSAGE);
                router.setRedirect(JspPath.SUCCESSFULLY_COMPLETE_PAGE);
            } else {
                request.getSession().setAttribute(JspAttribute.MESSAGE, NOT_REQUESTED_MESSAGE);
                router.setForward(JspPath.CLIENT_PRESCRIPTION_PAGE);
            }
        } catch (ServiceException e){
            logger.error(REQUEST_PRESCRIPTION_ERROR_MESSAGE, e);
            request.getSession().setAttribute(JspAttribute.ERROR_MESSAGE, e);
            router.setRedirect(JspPath.COMMAND_ERROR_PAGE);
        }
        return router;
    }
}
