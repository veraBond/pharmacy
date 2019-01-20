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

public class PharmacistMedicineListCommand implements PharmacyCommand {
    private final static Logger logger = LogManager.getLogger();
    private final static String PHARMACY_MEDICINE_LIST_ERROR_MESSAGE = "Error while loading medicine list.";

    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router();
        try {
            List<Medicine> pharmacistMedicines = MedicineServiceImpl.INSTANCE.findAllMedicineList();
            request.setAttribute(JspAttribute.CLIENT_MEDICINE_LIST, pharmacistMedicines);
            router.setForward(JspPath.PHARMACIST_PAGE);
        } catch (ServiceException e) {
            request.getSession().setAttribute(JspAttribute.ERROR, e);
            request.getSession().setAttribute(JspAttribute.ERROR_MESSAGE, PHARMACY_MEDICINE_LIST_ERROR_MESSAGE);
            router.setForward(JspPath.COMMAND_ERROR_PAGE);
            logger.error(PHARMACY_MEDICINE_LIST_ERROR_MESSAGE, e);
        }
        return router;
    }
}
