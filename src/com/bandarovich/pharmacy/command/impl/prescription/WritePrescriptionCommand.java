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
import java.util.Optional;

public class WritePrescriptionCommand implements PharmacyCommand {
    private final static Logger logger = LogManager.getLogger();
    private final static String WRITE_PRESCRIPTION_ERROR_MESSAGE = "Write prescription error. ";

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
            logger.error("Could not write prescription.", e);
            request.getSession().setAttribute(JspAttribute.ERROR_MESSAGE, WRITE_PRESCRIPTION_ERROR_MESSAGE + e.getMessage());
            router.setRedirect(JspPath.COMMAND_ERROR_PAGE);
        }
        return router;
    }
}
