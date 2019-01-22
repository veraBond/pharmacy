package com.bandarovich.pharmacy.command.impl.medicine;

import com.bandarovich.pharmacy.command.JspAttribute;
import com.bandarovich.pharmacy.command.JspPath;
import com.bandarovich.pharmacy.command.PharmacyCommand;
import com.bandarovich.pharmacy.command.Router;
import com.bandarovich.pharmacy.service.ServiceException;
import com.bandarovich.pharmacy.service.impl.MedicineServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * The Class CompleteAddMedicineCommand.
 */
public class CompleteAddMedicineCommand implements PharmacyCommand {
    
    /** The Constant logger. */
    private static final Logger logger = LogManager.getLogger();
    
    /** The Constant NEED. */
    private static final String NEED = "yes";
    
    /** The Constant COMPLETE_ADD_MEDICINE_ERROR_MESSAGE. */
    private static final String COMPLETE_ADD_MEDICINE_ERROR_MESSAGE = "Could not add medicine. ";

    /* (non-Javadoc)
     * @see com.bandarovich.pharmacy.command.PharmacyCommand#execute(HttpServletRequest)
     */
    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router();
        String medicineName = request.getParameter(JspAttribute.MEDICINE_NAME);
        int dosage = Integer.parseInt(request.getParameter(JspAttribute.MEDICINE_DOSAGE));
        String medicineGroup = request.getParameter(JspAttribute.MEDICINE_GROUP);
        int packageAmount = Integer.parseInt(request.getParameter(JspAttribute.PACKAGE_AMOUNT));
        String packageType = request.getParameter(JspAttribute.PACKAGE_TYPE);
        double price = Double.parseDouble(request.getParameter(JspAttribute.MEDICINE_PRICE));
        int storageAmount = Integer.parseInt(request.getParameter(JspAttribute.STORAGE_AMOUNT));
        boolean prescriptionNeed = request.getParameter(JspAttribute.PRESCRIPTION_NEED).equals(NEED);
        try{
            List<String> errors = MedicineServiceImpl.INSTANCE.validateMedicine(
                    medicineName, dosage, medicineGroup, packageType, packageAmount, price, storageAmount);
            if(errors.isEmpty()){
                MedicineServiceImpl.INSTANCE.formMedicine(medicineName, dosage, medicineGroup, packageType, packageAmount, price, prescriptionNeed, storageAmount);
                router.setRedirect(JspPath.SUCCESSFULLY_COMPLETE_PAGE);
            } else {
                request.setAttribute(JspAttribute.MEDICINE_NAME, medicineName);
                request.setAttribute(JspAttribute.MEDICINE_DOSAGE, dosage);
                request.setAttribute(JspAttribute.MEDICINE_PRICE, price);
                request.setAttribute(JspAttribute.PACKAGE_AMOUNT, packageAmount);
                request.setAttribute(JspAttribute.STORAGE_AMOUNT, storageAmount);
                router.setForward(JspPath.PHARMACIST_ADD_MEDICINE_PAGE);
                request.setAttribute(JspAttribute.PACKAGE_TYPE_LIST, request.getParameter(JspAttribute.PACKAGE_TYPE_LIST));
                request.setAttribute(JspAttribute.MEDICINE_GROUP_LIST, request.getParameter(JspAttribute.MEDICINE_GROUP_LIST));
                errors.forEach(error -> request.setAttribute(error, Boolean.TRUE));
            }
        } catch (ServiceException e){
            router.setRedirect(JspPath.COMMAND_ERROR_PAGE);
            logger.error(COMPLETE_ADD_MEDICINE_ERROR_MESSAGE, e);
            request.getSession().setAttribute(JspAttribute.ERROR, e);
            request.getSession().setAttribute(JspAttribute.ERROR_MESSAGE, COMPLETE_ADD_MEDICINE_ERROR_MESSAGE );
        }
        return router;
    }
}
