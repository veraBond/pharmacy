package com.bandarovich.pharmacy.command.impl.medicine;

import com.bandarovich.pharmacy.command.JspPath;
import com.bandarovich.pharmacy.command.JspAttribute;
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
    private final static Logger logger = LogManager.getLogger();
    private final static String CLIENT_MEDICINE_LIST_ERROR_MESSAGE = "Error while loading medicine list.";

    @Override
    public Router execute(HttpServletRequest request){
        String mail = (String)request.getSession().getAttribute(JspAttribute.MAIL);
        Router router = new Router();
        try{
            List<Medicine> clientMedicines = MedicineServiceImpl.INSTANCE.findClientMedicineList(mail);
            List<Medicine> availableMedicines = MedicineServiceImpl.INSTANCE.findAllClientAvailableMedicineList();
            availableMedicines.addAll(clientMedicines);
            request.getSession().setAttribute(JspAttribute.CLIENT_MEDICINE_LIST, availableMedicines);
            router.setForward(JspPath.CLIENT_PAGE);
        } catch (ServiceException e){
            logger.error(CLIENT_MEDICINE_LIST_ERROR_MESSAGE, e);
            request.getSession().setAttribute(JspAttribute.ERROR_MESSAGE, CLIENT_MEDICINE_LIST_ERROR_MESSAGE + e);
            router.setRedirect(JspPath.COMMAND_ERROR_PAGE);
        }
        return router;
    }
}
