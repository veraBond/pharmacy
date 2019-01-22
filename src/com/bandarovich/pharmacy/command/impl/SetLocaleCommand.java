package com.bandarovich.pharmacy.command.impl;

import com.bandarovich.pharmacy.command.JspPath;
import com.bandarovich.pharmacy.command.PharmacyCommand;
import com.bandarovich.pharmacy.command.Router;

import javax.servlet.http.HttpServletRequest;

/**
 * The Class SetLocaleCommand.
 */
public class SetLocaleCommand implements PharmacyCommand {
    
    /** The Constant LANGUAGE. */
    private static final String LANGUAGE = "language";

    /* (non-Javadoc)
     * @see com.bandarovich.pharmacy.command.PharmacyCommand#execute(HttpServletRequest)
     */
    @Override
    public Router execute(HttpServletRequest request) {
        String locale = request.getParameter(LANGUAGE);
        request.getSession().setAttribute(LANGUAGE, locale);
        Router router = new Router();
        router.setRedirect(JspPath.START_PAGE);
        return router;
    }
}
