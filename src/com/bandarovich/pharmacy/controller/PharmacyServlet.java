package com.bandarovich.pharmacy.controller;

import com.bandarovich.pharmacy.controller.command.CommandMap;
import com.bandarovich.pharmacy.controller.command.JspAttribute;
import com.bandarovich.pharmacy.controller.command.JspPath;
import com.bandarovich.pharmacy.controller.command.PharmacyCommand;
import com.bandarovich.pharmacy.pool.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class PharmacyServlet extends HttpServlet {
    private final static Logger logger = LogManager.getLogger();
    private final static String COMMAND_PARAMETER = "command";
    //TODO exception handle correct?
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String commandName = request.getParameter(COMMAND_PARAMETER);
        PharmacyCommand command = CommandMap.getInstance().get(commandName);
        try{
            command.execute(request,response);
        } catch (Exception e){
            e.printStackTrace();
            request.setAttribute(JspAttribute.ERROR_MESSAGE, e);
            request.getRequestDispatcher(JspPath.COMMAND_ERROR_PAGE).forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    public void destroy() {
        ConnectionPool.getInstance().closePool();
    }
}
