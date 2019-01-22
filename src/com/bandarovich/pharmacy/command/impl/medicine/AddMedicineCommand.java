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

public class AddMedicineCommand implements PharmacyCommand {
    private static final Logger logger = LogManager.getLogger();
    private static final String ADD_MEDICINE_ERROR = "Could not load data for adding medicine. ";

    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router();
        try{
            List<String> medicineGroup = MedicineServiceImpl.INSTANCE.findMedicineGroupList();
            List<String> packageType = MedicineServiceImpl.INSTANCE.findPackageTypeList();
            request.setAttribute(JspAttribute.MEDICINE_GROUP_LIST, medicineGroup);
            request.setAttribute(JspAttribute.PACKAGE_TYPE_LIST, packageType);
            router.setForward(JspPath.PHARMACIST_ADD_MEDICINE_PAGE);
        } catch (ServiceException e){
            logger.error(ADD_MEDICINE_ERROR, e);
            request.setAttribute(JspAttribute.ERROR, e);
            request.setAttribute(JspAttribute.ERROR_MESSAGE, ADD_MEDICINE_ERROR);
            router.setRedirect(JspPath.COMMAND_ERROR_PAGE);
        }
        return router;
    }
}
