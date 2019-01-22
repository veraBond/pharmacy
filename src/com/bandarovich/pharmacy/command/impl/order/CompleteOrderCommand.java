package com.bandarovich.pharmacy.command.impl.order;

import com.bandarovich.pharmacy.command.JspAttribute;
import com.bandarovich.pharmacy.command.JspPath;
import com.bandarovich.pharmacy.command.PharmacyCommand;
import com.bandarovich.pharmacy.command.Router;
import com.bandarovich.pharmacy.entity.Medicine;
import com.bandarovich.pharmacy.service.ServiceException;
import com.bandarovich.pharmacy.service.impl.MedicineServiceImpl;
import com.bandarovich.pharmacy.service.impl.OrderServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

/**
 * The Class CompleteOrderCommand.
 */
public class CompleteOrderCommand implements PharmacyCommand {
    
    /** The Constant logger. */
    private static final Logger logger = LogManager.getLogger();
    
    /** The Constant COMPLETE_ERROR_MESSAGE. */
    private static final String COMPLETE_ERROR_MESSAGE = "Could not complete order with client ";

    /* (non-Javadoc)
     * @see com.bandarovich.pharmacy.command.PharmacyCommand#execute(HttpServletRequest)
     */
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
                double cost = OrderServiceImpl.INSTANCE.completeOrder(clientMail, medicineId, orderAmount);
                double totalCost = cost + (Double)request.getSession().getAttribute(JspAttribute.TOTAL_COST);
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
