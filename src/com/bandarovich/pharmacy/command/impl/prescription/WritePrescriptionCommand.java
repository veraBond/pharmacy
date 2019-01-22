package com.bandarovich.pharmacy.command.impl.prescription;

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

/**
 * The Class WritePrescriptionCommand.
 */
public class WritePrescriptionCommand implements PharmacyCommand {
    
    /** The Constant logger. */
    private static final Logger logger = LogManager.getLogger();
    
    /** The Constant WRITE_PRESCRIPTION_ERROR_MESSAGE. */
    private static final String WRITE_PRESCRIPTION_ERROR_MESSAGE = "Write prescription error. ";

    /* (non-Javadoc)
     * @see com.bandarovich.pharmacy.command.PharmacyCommand#execute(HttpServletRequest)
     */
    @Override
    public Router execute(HttpServletRequest request) {
        int medicineId = Integer.parseInt(request.getParameter(JspAttribute.MEDICINE_ID));
        Router router = new Router();
        try{
            Medicine medicine = MedicineServiceImpl.INSTANCE.findMedicine(medicineId);
            int availableMedicineQuantity = MedicineServiceImpl.INSTANCE.findAvailableDoctorMedicineAmount(medicineId);
            request.setAttribute(JspAttribute.AVAILABLE_MEDICINE_QUANTITY, availableMedicineQuantity);
            request.setAttribute(JspAttribute.MEDICINE, medicine);
            router.setForward(JspPath.DOCTOR_WRITE_PRESCRIPTION_PAGE);
        } catch (ServiceException e){
            logger.error(WRITE_PRESCRIPTION_ERROR_MESSAGE, e);
            request.getSession().setAttribute(JspAttribute.ERROR_MESSAGE, WRITE_PRESCRIPTION_ERROR_MESSAGE);
            request.getSession().setAttribute(JspAttribute.ERROR, e);
            router.setForward(JspPath.COMMAND_ERROR_PAGE);
        }
        return router;
    }
}
