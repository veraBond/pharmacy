package com.bandarovich.pharmacy.controller.command.impl;

import com.bandarovich.pharmacy.controller.command.PharmacyCommand;
import com.bandarovich.pharmacy.controller.command.JspAttribute;
import com.bandarovich.pharmacy.controller.JspPath;
import com.bandarovich.pharmacy.entity.PharmacyPosition;
import com.bandarovich.pharmacy.entity.PharmacyUser;
import com.bandarovich.pharmacy.service.ServiceException;
import com.bandarovich.pharmacy.service.impl.UserServiceImpl;
import com.bandarovich.pharmacy.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

public class RegistrationCommand implements PharmacyCommand {
    private final static Logger logger = LogManager.getLogger();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PharmacyPosition position = PharmacyPosition.valueOf(request.getParameter(JspAttribute.POSITION).toUpperCase());
        PharmacyUser user = new PharmacyUser(position,
                request.getParameter(JspAttribute.USER_NAME),
                request.getParameter(JspAttribute.MAIL),
                request.getParameter(JspAttribute.PASSWORD));
        UserService service = new UserServiceImpl();
        String nextPage;
        try {
            Set<String> errors = service.register(user);
            logger.info(errors);
            if(errors.isEmpty()){
                request.getSession().setAttribute(JspAttribute.USER_NAME, user.getName());
                request.getSession().setAttribute(JspAttribute.POSITION, user.getPosition().name());
                request.getSession().setAttribute(JspAttribute.MAIL, user.getMail());
                switch (position) {
                    case CLIENT:

                        nextPage = JspPath.CLIENT_PAGE;// new ClientMedicineListCommand().execute(request);
                        break;
                    case DOCTOR:
                        nextPage = JspPath.DOCTOR_PAGE;
                        break;
                    case PHARMACIST:
                        nextPage = JspPath.PHARMACIST_PAGE;
                        break;
                    default:
                        request.setAttribute(JspAttribute.ERROR_MESSAGE, "Registration error.");
                        nextPage = JspPath.COMMAND_ERROR_PAGE;
                }
            }
            else{
                request.setAttribute(JspAttribute.USER_NAME, user.getName());
                request.setAttribute(JspAttribute.MAIL, user.getMail());
                request.setAttribute(JspAttribute.POSITION, user.getPosition());
                request.setAttribute(JspAttribute.INPUT_ERRORS, errors);
                nextPage = JspPath.REGISTRATION_PAGE;
            }
        } catch (ServiceException e){
            logger.error(e);
            request.setAttribute(JspAttribute.ERROR_MESSAGE, "Registration error.");
            nextPage = JspPath.COMMAND_ERROR_PAGE;
        }
        response.sendRedirect(nextPage);
        //request.getRequestDispatcher(nextPage).forward(request, response);

    }
}
