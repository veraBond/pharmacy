package com.bandarovich.pharmacy.controller.command.impl;

import com.bandarovich.pharmacy.controller.JspPath;
import com.bandarovich.pharmacy.controller.command.JspAttribute;
import com.bandarovich.pharmacy.controller.command.PharmacyCommand;

import javax.servlet.http.HttpServletRequest;

public class AddOrderMedicineCommand implements PharmacyCommand {
    @Override
    public String execute(HttpServletRequest request) {
        int medicineNumber = Integer.parseInt(request.getParameter(JspAttribute.ADDED_MEDICINE));
        String clientMail = (String)request.getSession().getAttribute(JspAttribute.MAIL);

        return JspPath.CLIENT_ORDER_PAGE;
    }
}
