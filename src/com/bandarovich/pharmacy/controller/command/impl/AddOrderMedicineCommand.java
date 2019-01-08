package com.bandarovich.pharmacy.controller.command.impl;

import com.bandarovich.pharmacy.controller.JspPath;
import com.bandarovich.pharmacy.controller.command.JspAttribute;
import com.bandarovich.pharmacy.controller.command.PharmacyCommand;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddOrderMedicineCommand implements PharmacyCommand {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int medicineNumber = Integer.parseInt(request.getParameter(JspAttribute.ADDED_MEDICINE));
        String clientMail = (String)request.getSession().getAttribute(JspAttribute.MAIL);
        request.getRequestDispatcher(JspPath.CLIENT_ORDER_PAGE).forward(request, response);

    }
}
