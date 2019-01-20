package com.bandarovich.pharmacy.command.impl.order;

import com.bandarovich.pharmacy.command.JspAttribute;
import com.bandarovich.pharmacy.command.JspPath;
import com.bandarovich.pharmacy.command.PharmacyCommand;
import com.bandarovich.pharmacy.command.Router;
import com.bandarovich.pharmacy.entity.Medicine;
import com.bandarovich.pharmacy.entity.PharmacyOrder;
import com.bandarovich.pharmacy.service.ServiceException;
import com.bandarovich.pharmacy.service.impl.MedicineServiceImpl;
import com.bandarovich.pharmacy.service.impl.OrderServiceImpl;
import com.bandarovich.pharmacy.service.impl.PrescriptionServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class CompleteOrderCommand implements PharmacyCommand {
    private final static Logger logger = LogManager.getLogger();
    private final static String COMPLETE_ERROR_MESSAGE = "Could not complete order with client ";

    @Override
    public Router execute(HttpServletRequest request) {
        int medicineId = Integer.parseInt(request.getParameter(JspAttribute.MEDICINE_ID));
        int orderAmount = Integer.parseInt(request.getParameter(JspAttribute.BOOK_MEDICINE_QUANTITY));
        int availableAmount = Integer.parseInt(request.getParameter(JspAttribute.AVAILABLE_MEDICINE_QUANTITY));
        String clientMail = (String)request.getSession().getAttribute(JspAttribute.MAIL);
        Router router = new Router();
        try{
            Medicine medicine = MedicineServiceImpl.INSTANCE.findMedicine(medicineId);
            if(orderAmount <= availableAmount){
                double totalCost = OrderServiceImpl.INSTANCE.completeOrder(clientMail, medicineId, orderAmount);
                request.getSession().setAttribute(JspAttribute.TOTAL_COST, totalCost);
                router.setRedirect(JspPath.SUCCESSFULLY_COMPLETE_PAGE);
            } else {
                request.setAttribute(JspAttribute.MEDICINE, medicine);
                request.setAttribute(JspAttribute.AVAILABLE_MEDICINE_QUANTITY, availableAmount);
                request.setAttribute(JspAttribute.INCORRECT_QUANTITY, Boolean.TRUE);
                router.setForward(JspPath.CLIENT_ORDER_PAGE);
            }
        } catch (ServiceException e){
            logger.error(COMPLETE_ERROR_MESSAGE + clientMail, e);
            request.getSession().setAttribute(JspAttribute.ERROR_MESSAGE, COMPLETE_ERROR_MESSAGE);
            request.getSession().setAttribute(JspAttribute.ERROR, e);
            router.setRedirect(JspPath.COMMAND_ERROR_PAGE);
        }
        return router;
    }
}
