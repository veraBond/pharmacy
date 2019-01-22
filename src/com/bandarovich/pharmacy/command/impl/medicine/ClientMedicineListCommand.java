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

public class ClientMedicineListCommand implements PharmacyCommand {
    private static final Logger logger = LogManager.getLogger();
    private static final String CLIENT_MEDICINE_LIST_ERROR_MESSAGE = "Error while loading medicine list.";

    @Override
    public Router execute(HttpServletRequest request){
        String mail = (String)request.getSession().getAttribute(JspAttribute.MAIL);
        Router router = new Router();
        try{
            List<Medicine> clientMedicines = MedicineServiceImpl.INSTANCE.findClientMedicineList(mail);
            request.setAttribute(JspAttribute.CLIENT_MEDICINE_LIST, clientMedicines);
            router.setForward(JspPath.CLIENT_PAGE);
        } catch (ServiceException e){
            logger.error(CLIENT_MEDICINE_LIST_ERROR_MESSAGE, e);
            request.getSession().setAttribute(JspAttribute.ERROR, e);
            request.getSession().setAttribute(JspAttribute.ERROR_MESSAGE, CLIENT_MEDICINE_LIST_ERROR_MESSAGE);
            router.setForward(JspPath.COMMAND_ERROR_PAGE);
        }
        return router;
    }
}
