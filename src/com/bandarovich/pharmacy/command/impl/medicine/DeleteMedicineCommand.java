package com.bandarovich.pharmacy.command.impl.medicine;

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
 * The Class DeleteMedicineCommand.
 */
public class DeleteMedicineCommand implements PharmacyCommand {
    
    /** The Constant logger. */
    private static final Logger logger = LogManager.getLogger();
    
    /** The Constant DELETE_MEDICINE_ERROR_MESSAGE. */
    private static final String DELETE_MEDICINE_ERROR_MESSAGE = "Could not delete medicine. ";

    /* (non-Javadoc)
     * @see com.bandarovich.pharmacy.command.PharmacyCommand#execute(HttpServletRequest)
     */
    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router();
        int medicineId = Integer.parseInt(request.getParameter(JspAttribute.MEDICINE_ID));
        try{
            Medicine medicine = MedicineServiceImpl.INSTANCE.findMedicine(medicineId);
            request.setAttribute(JspAttribute.MEDICINE, medicine);
            router.setForward(JspPath.PHARMACIST_DELETE_MEDICINE_PAGE);
        } catch (ServiceException e){
            request.getSession().setAttribute(JspAttribute.ERROR_MESSAGE, DELETE_MEDICINE_ERROR_MESSAGE);
            request.getSession().setAttribute(JspAttribute.ERROR, e);
            logger.error(DELETE_MEDICINE_ERROR_MESSAGE, e);
            router.setForward(JspPath.COMMAND_ERROR_PAGE);
        }
        return router;
    }
}
