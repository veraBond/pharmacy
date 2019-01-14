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

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ClientPrescriptionListCommand implements PharmacyCommand {
    private final static Logger logger = LogManager.getLogger();
    private final static String EMPTY_LIST_MESSAGE = "You have no prescriptions yet";

    @Override
    public Router execute(HttpServletRequest request) {
//        String mail = (String)request.getSession().getAttribute(JspAttribute.MAIL);
//        String nextPage;
//        try{
//            List<Pair<Prescription, Medicine>> clientPrescriptionList = PrescriptionServiceImpl.INSTANCE.findClientPrescriptionList(mail);
//            if(!clientPrescriptionList.isEmpty()) {
//                request.setAttribute(JspAttribute.CLIENT_PRESCRIPTION_LIST, clientPrescriptionList);
//            } else {
//                request.setAttribute(JspAttribute.MESSAGE, EMPTY_LIST_MESSAGE);
//            }
//            nextPage = JspPath.CLIENT_PRESCRIPTION_PAGE;
//        } catch (ServiceException e){
//            logger.error(e);
//            request.setAttribute(JspAttribute.ERROR_MESSAGE, "Error while loading medicine list.");
//            nextPage = JspPath.COMMAND_ERROR_PAGE;
//        }
//        request.getRequestDispatcher(nextPage).forward(request, response);
        return null;
    }
}
