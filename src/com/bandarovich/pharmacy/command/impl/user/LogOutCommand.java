package com.bandarovich.pharmacy.command.impl.user;

import com.bandarovich.pharmacy.command.PharmacyCommand;
import com.bandarovich.pharmacy.command.Router;

import javax.servlet.http.HttpServletRequest;

/**
 * The Class LogOutCommand.
 */
public class LogOutCommand implements PharmacyCommand {
    
    /* (non-Javadoc)
     * @see com.bandarovich.pharmacy.command.PharmacyCommand#execute(HttpServletRequest)
     */
    @Override
    public Router execute(HttpServletRequest request)  {
        request.getSession().invalidate();
        return new Router();
    }
}
