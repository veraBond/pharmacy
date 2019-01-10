package com.bandarovich.pharmacy.controller.command.impl.user;

import com.bandarovich.pharmacy.controller.command.JspPath;
import com.bandarovich.pharmacy.controller.command.JspAttribute;
import com.bandarovich.pharmacy.controller.command.PharmacyCommand;
import com.bandarovich.pharmacy.entity.Medicine;
import com.bandarovich.pharmacy.entity.PharmacyPosition;
import com.bandarovich.pharmacy.service.ServiceException;
import com.bandarovich.pharmacy.service.impl.MedicineServiceImpl;
import com.bandarovich.pharmacy.service.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class LogInCommand implements PharmacyCommand{
    private final static Logger logger = LogManager.getLogger();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PharmacyPosition position = PharmacyPosition.valueOf(request.getParameter(JspAttribute.POSITION).toUpperCase());
        String mail = request.getParameter(JspAttribute.MAIL);
        String password = request.getParameter(JspAttribute.PASSWORD);
        String nextPage;
        try{
            Optional<String> userName = UserServiceImpl.INSTANCE.findUserName(position, mail, password);
            if(userName.isPresent()){
                request.getSession().setAttribute(JspAttribute.USER_NAME, userName.get());
                request.getSession().setAttribute(JspAttribute.POSITION, position);
                request.getSession().setAttribute(JspAttribute.MAIL, mail);
                switch (position) {
                    case CLIENT:
                        nextPage = JspPath.CLIENT_PAGE;
                        List<Medicine> availableMedicines = findClientMedicines(mail);
                        request.setAttribute(JspAttribute.MEDICINE_LIST, availableMedicines);
                        break;
                    case DOCTOR:
                        nextPage = JspPath.DOCTOR_PAGE;
                        break;
                    case PHARMACIST:
                        nextPage = JspPath.PHARMACIST_PAGE;
                        break;
                    default:
                        request.setAttribute(JspAttribute.ERROR_MESSAGE, "Log in error.");
                        nextPage = JspPath.COMMAND_ERROR_PAGE;
                }
            } else {
                request.setAttribute(JspAttribute.ERROR_MESSAGE, "Incorrect e-mail address or password.");
                nextPage = JspPath.LOGIN_PAGE;
            }
        } catch (ServiceException e){
            logger.error(e.getMessage());
            request.setAttribute(JspAttribute.ERROR_MESSAGE, "Log in error.");
            nextPage = JspPath.START_PAGE;
        }
        request.getRequestDispatcher(nextPage).forward(request, response);
    }

    private List<Medicine> findClientMedicines(String mail) throws ServiceException{
        List<Medicine> clientMedicines = MedicineServiceImpl.INSTANCE.findClientMedicines(mail);
        List<Medicine> availableMedicines = MedicineServiceImpl.INSTANCE.findAllClientMedicines();
        availableMedicines.addAll(clientMedicines);
        return availableMedicines;
    }
}
