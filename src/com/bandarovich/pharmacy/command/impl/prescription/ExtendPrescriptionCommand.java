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

public class ExtendPrescriptionCommand implements PharmacyCommand {
    private static final Logger logger = LogManager.getLogger();
    private static final String EXTEND_PRESCRIPTION_ERROR_MESSAGE = "Could not extend prescription. ";

    @Override
    public Router execute(HttpServletRequest request) {
        int prescriptionId = Integer.parseInt(request.getParameter(JspAttribute.PRESCRIPTION_ID));
        int medicineId = Integer.parseInt(request.getParameter(JspAttribute.MEDICINE_ID));
        String clientMail = request.getParameter(JspAttribute.CLIENT_MAIL);
        Router router = new Router();
        try{
            Medicine medicine = MedicineServiceImpl.INSTANCE.findMedicine(medicineId);
            int availableMedicineQuantity = MedicineServiceImpl.INSTANCE.findAvailableDoctorMedicineAmount(medicineId);
            request.setAttribute(JspAttribute.PRESCRIPTION_ID, prescriptionId);
            request.setAttribute(JspAttribute.CLIENT_MAIL, clientMail);
            request.setAttribute(JspAttribute.MEDICINE, medicine);
            request.setAttribute(JspAttribute.AVAILABLE_MEDICINE_QUANTITY, availableMedicineQuantity);
            router.setForward(JspPath.DOCTOR_EXTEND_PRESCRIPTION_PAGE);
        } catch (ServiceException e){
            request.getSession().setAttribute(JspAttribute.ERROR_MESSAGE, EXTEND_PRESCRIPTION_ERROR_MESSAGE);
            request.getSession().setAttribute(JspAttribute.ERROR, e);
            router.setForward(JspPath.COMMAND_ERROR_PAGE);
            logger.error(EXTEND_PRESCRIPTION_ERROR_MESSAGE, e);
        }
        return router;
    }
}
