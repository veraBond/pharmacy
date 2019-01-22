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
 * The Class CompleteModifyMedicineCommand.
 */
public class CompleteModifyMedicineCommand implements PharmacyCommand {
    
    /** The Constant logger. */
    private static final Logger logger = LogManager.getLogger();
    
    /** The Constant NEED. */
    private static final String NEED = "yes";
    
    /** The Constant COMPLETE_MODIFY_MEDICINE_ERROR_MESSAGE. */
    private static final String COMPLETE_MODIFY_MEDICINE_ERROR_MESSAGE = "Could not modify medicine. ";

    /* (non-Javadoc)
     * @see com.bandarovich.pharmacy.command.PharmacyCommand#execute(HttpServletRequest)
     */
    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router();
        int medicineId = Integer.parseInt(request.getParameter(JspAttribute.MEDICINE_ID));
        String medicineName = request.getParameter(JspAttribute.MEDICINE_NAME);
        int dosage = Integer.parseInt(request.getParameter(JspAttribute.MEDICINE_DOSAGE));
        String medicineGroup = request.getParameter(JspAttribute.MEDICINE_GROUP);
        String packageType = request.getParameter(JspAttribute.PACKAGE_TYPE);
        int packageAmount = Integer.parseInt(request.getParameter(JspAttribute.PACKAGE_AMOUNT));
        double price = Double.parseDouble(request.getParameter(JspAttribute.MEDICINE_PRICE));
        boolean prescriptionNeed = request.getParameter(JspAttribute.PRESCRIPTION_NEED).equals(NEED);
        int storageAmount = Integer.parseInt(request.getParameter(JspAttribute.STORAGE_AMOUNT));
        try{
            List<String> errors = MedicineServiceImpl.INSTANCE.validateMedicine(
                    medicineName, dosage, medicineGroup, packageType, packageAmount, price, storageAmount);
            if(errors.isEmpty()){
                MedicineServiceImpl.INSTANCE.updateMedicine(medicineId, medicineName, dosage, medicineGroup, packageType, packageAmount, price, prescriptionNeed, storageAmount);
                router.setRedirect(JspPath.SUCCESSFULLY_COMPLETE_PAGE);
            } else {
                request.setAttribute(JspAttribute.MEDICINE_ID, medicineId);
                request.setAttribute(JspAttribute.MEDICINE_NAME, medicineName);
                request.setAttribute(JspAttribute.MEDICINE_DOSAGE, dosage);
                request.setAttribute(JspAttribute.PACKAGE_AMOUNT, packageAmount);
                request.setAttribute(JspAttribute.MEDICINE_PRICE, price);
                request.setAttribute(JspAttribute.STORAGE_AMOUNT, storageAmount);
                request.setAttribute(JspAttribute.MEDICINE_GROUP_LIST, request.getParameter(JspAttribute.MEDICINE_GROUP_LIST));
                request.setAttribute(JspAttribute.PACKAGE_TYPE_LIST, request.getParameter(JspAttribute.PACKAGE_TYPE_LIST));
                errors.forEach(error -> request.setAttribute(error, Boolean.TRUE));
                router.setForward(JspPath.PHARMACIST_ADD_MEDICINE_PAGE);
            }
        } catch (ServiceException e){
            logger.error(COMPLETE_MODIFY_MEDICINE_ERROR_MESSAGE, e);
            router.setRedirect(JspPath.COMMAND_ERROR_PAGE);
            request.getSession().setAttribute(JspAttribute.ERROR, e);
            request.getSession().setAttribute(JspAttribute.ERROR_MESSAGE, COMPLETE_MODIFY_MEDICINE_ERROR_MESSAGE );
        }
        return router;
    }
}
