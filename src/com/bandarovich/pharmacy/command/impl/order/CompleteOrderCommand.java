package com.bandarovich.pharmacy.command.impl.order;

import com.bandarovich.pharmacy.command.JspAttribute;
import com.bandarovich.pharmacy.command.JspPath;
import com.bandarovich.pharmacy.command.PharmacyCommand;
import com.bandarovich.pharmacy.command.Router;
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
    private final static String COMPLETE_ORDER_ERROR_MESSAGE = "Complete order medicine error.";
    private final static String INPUT_ERROR_MESSAGE = "Check input quantity. It must be less ";
    private final static String SUCCESSFULLY_COMPLETE_ORDER_MESSAGE = "Your order successfully completed. Total cost: ";
//TODO can I call methods from different services? or all logic put in services? (so in service call methods from other services)
    @Override
    public Router execute(HttpServletRequest request) {
        int medicineId = Integer.parseInt(request.getParameter(JspAttribute.MEDICINE_ID));
        int quantity = Integer.parseInt(request.getParameter(JspAttribute.BOOK_MEDICINE_QUANTITY));
        String clientMail = (String)request.getSession().getAttribute(JspAttribute.MAIL);
        Router router = new Router();
        try{
            Optional<PharmacyOrder> order = OrderServiceImpl.INSTANCE.completeOrder(clientMail, medicineId, quantity);
            if(order.isPresent()){
                if( PrescriptionServiceImpl.INSTANCE.updatePrescriptionAfterMedicineOrder(medicineId, clientMail, quantity) &&
                MedicineServiceImpl.INSTANCE.updateMedicineStorageAmount(medicineId, quantity)){
                    request.getSession().setAttribute(JspAttribute.MESSAGE, SUCCESSFULLY_COMPLETE_ORDER_MESSAGE + order.get().getTotalCost());
                    router.setRedirect(JspPath.SUCCESSFULLY_COMPLETE_PAGE);
                } else {
                    OrderServiceImpl.INSTANCE.deleteOrder(order.get());
                    logger.error("Could not complete order.");
                    request.getSession().setAttribute(JspAttribute.ERROR_MESSAGE, COMPLETE_ORDER_ERROR_MESSAGE );
                    router.setRedirect(JspPath.COMMAND_ERROR_PAGE);
                }
            } else {
                int availableAmount = MedicineServiceImpl.INSTANCE.findAvailableClientMedicineAmount(medicineId, clientMail);
                request.setAttribute(JspAttribute.AVAILABLE_MEDICINE_QUANTITY, availableAmount);
                request.setAttribute(JspAttribute.INPUT_ERROR_MESSAGE, INPUT_ERROR_MESSAGE + availableAmount);
                router.setForward(JspPath.CLIENT_ORDER_PAGE);
            }
        } catch (ServiceException e){
            logger.error(COMPLETE_ORDER_ERROR_MESSAGE, e);
            request.getSession().setAttribute(JspAttribute.ERROR_MESSAGE, COMPLETE_ORDER_ERROR_MESSAGE + e);
            router.setRedirect(JspPath.COMMAND_ERROR_PAGE);
        }
        return router;
    }
}
