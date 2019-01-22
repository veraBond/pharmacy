package com.bandarovich.pharmacy.command.impl.order;

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

public class BookMedicineCommand implements PharmacyCommand {
    private static final Logger logger = LogManager.getLogger();
    private static final String BOOK_MEDICINE_ERROR_MESSAGE = "Could not book medicine. ";

    @Override
    public Router execute(HttpServletRequest request) {
        int bookMedicineId = Integer.parseInt(request.getParameter(JspAttribute.MEDICINE_ID));
        String clientMail = (String)request.getSession().getAttribute(JspAttribute.MAIL);
        Router router = new Router();
        try{
            Medicine medicine = MedicineServiceImpl.INSTANCE.findMedicine(bookMedicineId);
            int availableMedicineQuantity = MedicineServiceImpl.INSTANCE.findAvailableClientMedicineAmount(bookMedicineId, clientMail);
            request.setAttribute(JspAttribute.AVAILABLE_MEDICINE_QUANTITY, availableMedicineQuantity);
            request.setAttribute(JspAttribute.MEDICINE, medicine);
            router.setForward(JspPath.CLIENT_ORDER_PAGE);
        } catch (ServiceException e){
            request.getSession().setAttribute(JspAttribute.ERROR_MESSAGE, BOOK_MEDICINE_ERROR_MESSAGE);
            request.getSession().setAttribute(JspAttribute.ERROR, e);
            logger.error(BOOK_MEDICINE_ERROR_MESSAGE, e);
            router.setForward(JspPath.COMMAND_ERROR_PAGE);
        }
        return router;
    }
}
