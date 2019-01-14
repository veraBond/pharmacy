package com.bandarovich.pharmacy.command.impl.prescription;

import com.bandarovich.pharmacy.command.JspAttribute;
import com.bandarovich.pharmacy.command.JspPath;
import com.bandarovich.pharmacy.command.PharmacyCommand;
import com.bandarovich.pharmacy.command.Router;
import com.bandarovich.pharmacy.service.ServiceException;
import com.bandarovich.pharmacy.service.impl.PrescriptionServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class WritePrescriptionCommand implements PharmacyCommand {
    private final static Logger logger = LogManager.getLogger();
    private final static String PRESCRIPTION_SUCCESSFUL_WRITTEN = "completed";
    private final static String PRESCRIPTION_UNSUCCESSFUL_WRITTEN = "Service error";

    @Override
    public Router execute(HttpServletRequest request) {
//        String clientMail = request.getParameter(JspAttribute.CLIENT_MAIL);
//        logger.info(clientMail);
//        String doctorMail = (String)request.getSession().getAttribute(JspAttribute.MAIL);
//        int medicineNumber = Integer.parseInt(request.getParameter(JspAttribute.MEDICINE_NUMBER));
//        int amount = Integer.parseInt(request.getParameter(JspAttribute.QUANTITY));
//        try{
//            request.setAttribute(JspAttribute.MEDICINE_LIST, request.getParameter(JspAttribute.MEDICINE_LIST));
//            List<String> errors = PrescriptionServiceImpl.INSTANCE.writePrescription(medicineNumber, clientMail, doctorMail, amount);
//            if(errors.isEmpty()) {
//                request.setAttribute(JspAttribute.PRESCRIPTION_STATUS, PRESCRIPTION_SUCCESSFUL_WRITTEN);
//                response.sendRedirect(JspPath.DOCTOR_MEDICINE_PAGE);
//            } else {
//                request.setAttribute(JspAttribute.INPUT_ERRORS, errors);
//                request.getRequestDispatcher(JspPath.DOCTOR_MEDICINE_PAGE).forward(request, response);
//            }
//        } catch (ServiceException e){
//            logger.error(e);
//            request.setAttribute(JspAttribute.ERROR_MESSAGE, e);
//            request.setAttribute(JspAttribute.PREVIOUS_PAGE, JspPath.DOCTOR_MEDICINE_PAGE);
//            response.sendError(500, PRESCRIPTION_UNSUCCESSFUL_WRITTEN);
//        }
        return null;
    }
}
