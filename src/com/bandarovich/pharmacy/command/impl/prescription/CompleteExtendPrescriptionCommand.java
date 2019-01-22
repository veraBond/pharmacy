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

/**
 * The Class CompleteExtendPrescriptionCommand.
 */
public class CompleteExtendPrescriptionCommand implements PharmacyCommand {
    
    /** The Constant COMPLETE_EXTEND_PRESCRIPTION_ERROR_MESSAGE. */
    private static final String COMPLETE_EXTEND_PRESCRIPTION_ERROR_MESSAGE = "Could not extend prescription. ";
    
    /** The Constant logger. */
    private static final Logger logger = LogManager.getLogger();

    /* (non-Javadoc)
     * @see com.bandarovich.pharmacy.command.PharmacyCommand#execute(HttpServletRequest)
     */
    @Override
    public Router execute(HttpServletRequest request) {
        String clientMail = request.getParameter(JspAttribute.CLIENT_MAIL);
        int prescriptionId = Integer.parseInt(request.getParameter(JspAttribute.PRESCRIPTION_ID));
        int medicineId = Integer.parseInt(request.getParameter(JspAttribute.MEDICINE_ID));
        int prescriptionAmount = Integer.parseInt(request.getParameter(JspAttribute.PRESCRIPTION_MEDICINE_QUANTITY));
        int availableAmount = Integer.parseInt(request.getParameter(JspAttribute.AVAILABLE_MEDICINE_QUANTITY));
        Router router = new Router();
        try{
            Medicine medicine = MedicineServiceImpl.INSTANCE.findMedicine(medicineId);
            boolean rightQuantity = prescriptionAmount <= availableAmount;
            if(!rightQuantity){
                request.setAttribute(JspAttribute.MEDICINE, medicine);
                request.setAttribute(JspAttribute.AVAILABLE_MEDICINE_QUANTITY, availableAmount);
                request.setAttribute(JspAttribute.INCORRECT_QUANTITY, Boolean.TRUE);
                request.setAttribute(JspAttribute.CLIENT_MAIL, clientMail);
                request.setAttribute(JspAttribute.PRESCRIPTION_MEDICINE_QUANTITY, prescriptionAmount);
                router.setForward(JspPath.DOCTOR_EXTEND_PRESCRIPTION_PAGE);
            } else {
                PrescriptionServiceImpl.INSTANCE.extendPrescription(prescriptionId, prescriptionAmount);
                router.setRedirect(JspPath.SUCCESSFULLY_COMPLETE_PAGE);
            }
        } catch (ServiceException e){
            router.setRedirect(JspPath.COMMAND_ERROR_PAGE);
            logger.error(COMPLETE_EXTEND_PRESCRIPTION_ERROR_MESSAGE, e);
            request.getSession().setAttribute(JspAttribute.ERROR, e);
            request.getSession().setAttribute(JspAttribute.ERROR_MESSAGE, COMPLETE_EXTEND_PRESCRIPTION_ERROR_MESSAGE );
         }
        return router;
    }
}
