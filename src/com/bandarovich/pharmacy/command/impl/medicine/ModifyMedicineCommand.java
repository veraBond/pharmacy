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
import java.util.List;

/**
 * The Class ModifyMedicineCommand.
 */
public class ModifyMedicineCommand implements PharmacyCommand {
    
    /** The Constant logger. */
    private static final Logger logger = LogManager.getLogger();
    
    /** The Constant MODIFY_MEDICINE_ERROR_MESSAGE. */
    private static final String MODIFY_MEDICINE_ERROR_MESSAGE = "Could not modify medicine. ";

    /* (non-Javadoc)
     * @see com.bandarovich.pharmacy.command.PharmacyCommand#execute(HttpServletRequest)
     */
    @Override
    public Router execute(HttpServletRequest request) {
        int medicineId = Integer.parseInt(request.getParameter(JspAttribute.MEDICINE_ID));
        Router router = new Router();
        try{
            Medicine medicine = MedicineServiceImpl.INSTANCE.findMedicine(medicineId);
            request.setAttribute(JspAttribute.MEDICINE_ID, medicine.getMedicineId());
            request.setAttribute(JspAttribute.MEDICINE_NAME, medicine.getName());
            request.setAttribute(JspAttribute.MEDICINE_DOSAGE, medicine.getDosage());
            request.setAttribute(JspAttribute.MEDICINE_GROUP, medicine.getGroup());
            request.setAttribute(JspAttribute.PACKAGE_TYPE, medicine.getPackageType());
            request.setAttribute(JspAttribute.PACKAGE_AMOUNT, medicine.getPackageAmount());
            request.setAttribute(JspAttribute.MEDICINE_PRICE, medicine.getPrice());
            request.setAttribute(JspAttribute.PRESCRIPTION_NEED, medicine.isNeedPrescription());
            request.setAttribute(JspAttribute.STORAGE_AMOUNT, medicine.getStorageAmount());
            List<String> medicineGroup = MedicineServiceImpl.INSTANCE.findMedicineGroupList();
            List<String> packageType = MedicineServiceImpl.INSTANCE.findPackageTypeList();
            request.setAttribute(JspAttribute.MEDICINE_GROUP_LIST, medicineGroup);
            request.setAttribute(JspAttribute.PACKAGE_TYPE_LIST, packageType);
            router.setForward(JspPath.PHARMACIST_MODIFY_MEDICINE_PAGE);
        } catch (ServiceException e){
            logger.error(MODIFY_MEDICINE_ERROR_MESSAGE, e);
            router.setRedirect(JspPath.COMMAND_ERROR_PAGE);
            request.getSession().setAttribute(JspAttribute.ERROR, e);
            request.getSession().setAttribute(JspAttribute.ERROR_MESSAGE, MODIFY_MEDICINE_ERROR_MESSAGE );
        }
        return router;
    }
}
