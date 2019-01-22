package com.bandarovich.pharmacy.command.impl.prescription;

import com.bandarovich.pharmacy.command.JspAttribute;
import com.bandarovich.pharmacy.command.JspPath;
import com.bandarovich.pharmacy.command.PharmacyCommand;
import com.bandarovich.pharmacy.command.Router;
import com.bandarovich.pharmacy.entity.Medicine;
import com.bandarovich.pharmacy.service.ServiceException;
import com.bandarovich.pharmacy.service.impl.MedicineServiceImpl;
import com.bandarovich.pharmacy.service.impl.PrescriptionServiceImpl;
import com.bandarovich.pharmacy.service.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

/**
 * The Class CompleteWritePrescriptionCommand.
 */
public class CompleteWritePrescriptionCommand implements PharmacyCommand {
    
    /** The Constant logger. */
    private static final Logger logger = LogManager.getLogger();
    
    /** The Constant COMPLETE_WRITE_PRESCRIPTION_ERROR_MESSAGE. */
    private static final String COMPLETE_WRITE_PRESCRIPTION_ERROR_MESSAGE = "Complete write prescription error. ";

    /* (non-Javadoc)
     * @see com.bandarovich.pharmacy.command.PharmacyCommand#execute(HttpServletRequest)
     */
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
            boolean findUser = UserServiceImpl.INSTANCE.findUser(clientMail);
            boolean rightQuantity = prescriptionAmount <= availableAmount;
            formAttributes(findUser, JspAttribute.INCORRECT_MAIL, request);
            formAttributes(rightQuantity, JspAttribute.INCORRECT_QUANTITY, request);
            if(!findUser || !rightQuantity){
                request.setAttribute(JspAttribute.MEDICINE, medicine);
                request.setAttribute(JspAttribute.CLIENT_MAIL, clientMail);
                request.setAttribute(JspAttribute.PRESCRIPTION_MEDICINE_QUANTITY, prescriptionAmount);
                request.setAttribute(JspAttribute.AVAILABLE_MEDICINE_QUANTITY, availableAmount);
                router.setForward(JspPath.DOCTOR_WRITE_PRESCRIPTION_PAGE);
            } else {
                PrescriptionServiceImpl.INSTANCE.writePrescription(medicineId, clientMail, doctorMail, prescriptionAmount);
                router.setRedirect(JspPath.SUCCESSFULLY_COMPLETE_PAGE);
            }
        } catch (ServiceException e){
            request.getSession().setAttribute(JspAttribute.ERROR, e);
            request.getSession().setAttribute(JspAttribute.ERROR_MESSAGE, COMPLETE_WRITE_PRESCRIPTION_ERROR_MESSAGE );
            router.setRedirect(JspPath.COMMAND_ERROR_PAGE);
            logger.error(COMPLETE_WRITE_PRESCRIPTION_ERROR_MESSAGE, e);
        }
        return router;
    }

    /**
     * Form attributes.
     *
     * @param inputValue the input value
     * @param attribute the attribute
     * @param request the request
     */
    private void formAttributes(boolean inputValue, String attribute, HttpServletRequest request){
        if(!inputValue){
            request.setAttribute(attribute, Boolean.TRUE);
        }
    }
}
