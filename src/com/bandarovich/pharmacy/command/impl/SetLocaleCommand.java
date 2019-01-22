package com.bandarovich.pharmacy.command.impl;

import com.bandarovich.pharmacy.command.JspPath;
import com.bandarovich.pharmacy.command.PharmacyCommand;
import com.bandarovich.pharmacy.command.Router;

import javax.servlet.http.HttpServletRequest;

public class SetLocaleCommand implements PharmacyCommand {
    private static final String LANGUAGE = "language";

    @Override
    public Router execute(HttpServletRequest request) {
        String locale = request.getParameter(LANGUAGE);
        request.getSession().setAttribute(LANGUAGE, locale);
        Router router = new Router();
        router.setRedirect(JspPath.START_PAGE);
        return router;
    }
}
