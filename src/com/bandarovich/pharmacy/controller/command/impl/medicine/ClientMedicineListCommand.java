package com.bandarovich.pharmacy.controller.command.impl.medicine;

import com.bandarovich.pharmacy.controller.command.JspPath;
import com.bandarovich.pharmacy.controller.command.JspAttribute;
import com.bandarovich.pharmacy.controller.command.PharmacyCommand;
import com.bandarovich.pharmacy.entity.Medicine;
import com.bandarovich.pharmacy.service.ServiceException;
import com.bandarovich.pharmacy.service.impl.MedicineServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ClientMedicineListCommand implements PharmacyCommand {
    private final static Logger logger = LogManager.getLogger();
//TODO client.jsp 76 ok?
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String mail = (String)request.getSession().getAttribute(JspAttribute.MAIL);
        String nextPage;
        try{
            List<Medicine> clientMedicines = MedicineServiceImpl.INSTANCE.findClientMedicines(mail);
            List<Medicine> availableMedicines = MedicineServiceImpl.INSTANCE.findAllClientMedicines();
            availableMedicines.addAll(clientMedicines);
            request.setAttribute(JspAttribute.MEDICINE_LIST, availableMedicines);
            nextPage = JspPath.CLIENT_PAGE;
        } catch (ServiceException e){
            logger.error(e);
            request.setAttribute(JspAttribute.ERROR_MESSAGE, "Error while loading medicine list.");
            nextPage = JspPath.COMMAND_ERROR_PAGE;
        }
        request.getRequestDispatcher(nextPage).forward(request, response);
    }
}
