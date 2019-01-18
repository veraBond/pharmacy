package com.bandarovich.pharmacy.command.impl.prescription;

import com.bandarovich.pharmacy.command.JspAttribute;
import com.bandarovich.pharmacy.command.JspPath;
import com.bandarovich.pharmacy.command.PharmacyCommand;
import com.bandarovich.pharmacy.command.Router;
import com.bandarovich.pharmacy.entity.Medicine;
import com.bandarovich.pharmacy.service.ServiceException;
import com.bandarovich.pharmacy.service.impl.MedicineServiceImpl;
import com.bandarovich.pharmacy.service.impl.PrescriptionServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class CompleteWritePrescriptionCommand implements PharmacyCommand {
    private final static Logger logger = LogManager.getLogger();
    private final static String COMPLETE_WRITE_PRESCRIPTION_ERROR_MESSAGE = "Complete write prescription error. ";

    @Override
    public Router execute(HttpServletRequest request) {
        String doctorMail = (String)request.getSession().getAttribute(JspAttribute.MAIL);
        String clientMail = request.getParameter(JspAttribute.CLIENT_MAIL);
        int medicineId = Integer.parseInt(request.getParameter(JspAttribute.MEDICINE_ID));
        int prescriptionAmount = Integer.parseInt(request.getParameter(JspAttribute.PRESCRIPTION_MEDICINE_QUANTITY));
        int availableAmount = Integer.parseInt(request.getParameter(JspAttribute.AVAILABLE_MEDICINE_QUANTITY));
        Router router = new Router();
        try{
            Medicine medicine = MedicineServiceImpl.INSTANCE.findMedicine(medicineId);
            boolean complete = PrescriptionServiceImpl.INSTANCE.writePrescription(medicineId, clientMail, doctorMail, prescriptionAmount);
            boolean rightQuantity = prescriptionAmount <= availableAmount;
            formAttributes(complete, JspAttribute.INCORRECT_MAIL, request);
            formAttributes(rightQuantity, JspAttribute.INCORRECT_QUANTITY, request);
            if(!complete || !rightQuantity){
                request.setAttribute(JspAttribute.MEDICINE, medicine);
                request.setAttribute(JspAttribute.CLIENT_MAIL, clientMail);
                request.setAttribute(JspAttribute.PRESCRIPTION_MEDICINE_QUANTITY, prescriptionAmount);
                request.setAttribute(JspAttribute.AVAILABLE_MEDICINE_QUANTITY, availableAmount);
                router.setForward(JspPath.DOCTOR_WRITE_PRESCRIPTION_PAGE);
            } else {
                router.setRedirect(JspPath.SUCCESSFULLY_COMPLETE_PAGE);
            }
        } catch (ServiceException e){
            logger.error(COMPLETE_WRITE_PRESCRIPTION_ERROR_MESSAGE, e);
            request.getSession().setAttribute(JspAttribute.ERROR_MESSAGE, COMPLETE_WRITE_PRESCRIPTION_ERROR_MESSAGE + e);
            router.setRedirect(JspPath.COMMAND_ERROR_PAGE);
        }
        return router;
    }

    private void formAttributes(boolean inputValue, String attribute, HttpServletRequest request){
        if(!inputValue){
            request.setAttribute(attribute, Boolean.TRUE);
        }
    }
}
