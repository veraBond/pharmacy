package com.bandarovich.pharmacy.controller.command.impl.user;

import com.bandarovich.pharmacy.controller.command.JspAttribute;
import com.bandarovich.pharmacy.controller.command.JspPath;
import com.bandarovich.pharmacy.controller.command.PharmacyCommand;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
//TODO check log out
public class LogOutCommand implements PharmacyCommand {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().setAttribute(JspAttribute.POSITION, null);
        request.getSession().setAttribute(JspAttribute.MAIL, null);
        request.getSession().setAttribute(JspAttribute.USER_NAME, null);
        response.sendRedirect(JspPath.START_PAGE);
    }
}
