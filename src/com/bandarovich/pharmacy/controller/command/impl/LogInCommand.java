package com.bandarovich.pharmacy.controller.command.impl;

import com.bandarovich.pharmacy.controller.JspPath;
import com.bandarovich.pharmacy.controller.command.JspAttribute;
import com.bandarovich.pharmacy.controller.command.PharmacyCommand;
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
import java.util.List;

public class LogInCommand implements PharmacyCommand {
    private final static Logger logger = LogManager.getLogger();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PharmacyPosition position = PharmacyPosition.valueOf(request.getParameter(JspAttribute.POSITION).toUpperCase());
        String mail = request.getParameter(JspAttribute.MAIL);
        String password = request.getParameter(JspAttribute.PASSWORD);
        UserService service = new UserServiceImpl(); //TODO stateless vs statefull
        String nextPage;
        try{
            List<PharmacyUser> selectedUser = service.logIn(position, mail, password);
            if(!selectedUser.isEmpty()){
                request.getSession().setAttribute(JspAttribute.USER_NAME, selectedUser.get(0).getName());
                request.getSession().setAttribute(JspAttribute.POSITION, position);
                request.getSession().setAttribute(JspAttribute.MAIL, mail);
                switch (position) {
                    case CLIENT:
                        nextPage = new ClientMedicineListCommand().execute(request);
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
            request.setAttribute(JspAttribute.ERROR_MESSAGE, "Log in error.");
            nextPage = JspPath.LOGIN_PAGE;
        }
        request.getRequestDispatcher(nextPage).forward(request, response);

    }
}
