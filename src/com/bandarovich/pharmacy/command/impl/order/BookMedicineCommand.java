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
import java.util.Optional;

public class BookMedicineCommand implements PharmacyCommand {
    private final static Logger logger = LogManager.getLogger();
    private final static String BOOK_MEDICINE_ERROR_MESSAGE = "Book medicine error.";

    @Override
    public Router execute(HttpServletRequest request) {
        int bookMedicineNumber = Integer.parseInt(request.getParameter(JspAttribute.MEDICINE_ID));
        String clientMail = (String)request.getSession().getAttribute(JspAttribute.MAIL);
        Router router = new Router();
        try{
            Medicine medicine = MedicineServiceImpl.INSTANCE.findMedicine(bookMedicineNumber);
            int availableMedicineQuantity = MedicineServiceImpl.INSTANCE.findAvailableClientMedicineAmount(bookMedicineNumber, clientMail);
            request.setAttribute(JspAttribute.AVAILABLE_MEDICINE_QUANTITY, availableMedicineQuantity);
            request.setAttribute(JspAttribute.MEDICINE, medicine);
            router.setForward(JspPath.CLIENT_ORDER_PAGE);
        } catch (ServiceException e){
            logger.error("Could not book medicine.", e);
            request.getSession().setAttribute(JspAttribute.ERROR_MESSAGE, BOOK_MEDICINE_ERROR_MESSAGE + e.getMessage());
            router.setRedirect(JspPath.COMMAND_ERROR_PAGE);
        }
        return router;
    }
}
