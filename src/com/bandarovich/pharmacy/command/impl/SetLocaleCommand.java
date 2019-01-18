package com.bandarovich.pharmacy.command.impl;

import com.bandarovich.pharmacy.command.JspPath;
import com.bandarovich.pharmacy.command.PharmacyCommand;
import com.bandarovich.pharmacy.command.Router;

import javax.servlet.http.HttpServletRequest;

public class SetLocaleCommand implements PharmacyCommand {
    private final static String LOCALE = "locale";
    private final static String LANGUAGE = "language";
    private final static String PHARMACY_REGEX = "pharmacy";

    @Override
    public Router execute(HttpServletRequest request) {
        String language = request.getParameter(LANGUAGE);
        request.getSession().setAttribute(LOCALE, language);
        Router router = new Router();
        String currentPage = request.getRequestURI().replaceAll(PHARMACY_REGEX, "");
        router.setRedirect(currentPage);
        return router;
    }
}
