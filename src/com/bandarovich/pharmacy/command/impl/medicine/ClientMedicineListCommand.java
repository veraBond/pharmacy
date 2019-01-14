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
    @Override
    public Router execute(HttpServletRequest request){
//        String mail = (String)request.getSession().getAttribute(JspAttribute.MAIL);
//        String nextPage;
//        try{
//            List<Medicine> clientMedicines = MedicineServiceImpl.INSTANCE.findClientMedicines(mail);
//            List<Medicine> availableMedicines = MedicineServiceImpl.INSTANCE.findAllClientAvailableMedicines();
//            availableMedicines.addAll(clientMedicines);
//            request.setAttribute(JspAttribute.MEDICINE_LIST, availableMedicines);
//            nextPage = JspPath.CLIENT_PAGE;
//
//        } catch (ServiceException e){
//            logger.error(e);
//            request.setAttribute(JspAttribute.ERROR_MESSAGE, "Error while loading medicine list.");
//            nextPage = JspPath.COMMAND_ERROR_PAGE;
//        }
        return null;
    }
}
