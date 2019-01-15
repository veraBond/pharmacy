package com.bandarovich.pharmacy.command.impl;

import com.bandarovich.pharmacy.command.JspAttribute;
import com.bandarovich.pharmacy.command.PharmacyCommand;
import com.bandarovich.pharmacy.command.Router;

import javax.servlet.http.HttpServletRequest;

public class StartPageCommand implements PharmacyCommand {
    @Override
    public Router execute(HttpServletRequest request) {
        String startPage = (String)request.getSession().getAttribute(JspAttribute.START_PAGE);
        Router router = new Router();
        router.setRedirect(startPage);
        return router;
    }
}
