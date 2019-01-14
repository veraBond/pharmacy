package com.bandarovich.pharmacy.command.impl.user;

import com.bandarovich.pharmacy.command.PharmacyCommand;
import com.bandarovich.pharmacy.command.Router;

import javax.servlet.http.HttpServletRequest;

public class LogOutCommand implements PharmacyCommand {
    @Override
    public Router execute(HttpServletRequest request)  {
        request.getSession().invalidate();
        return new Router();
    }
}
