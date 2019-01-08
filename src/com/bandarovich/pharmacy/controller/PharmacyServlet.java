package com.bandarovich.pharmacy.controller;

import com.bandarovich.pharmacy.controller.command.CommandMap;
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
//TODO read about HTTP methods get, post, put, delete, head, options
    //TODO идентпотентность
    //TODO f5 after post execute get
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String commandName = request.getParameter("command");
        logger.info("Command name: " + commandName);
        PharmacyCommand command = CommandMap.getInstance().get(commandName);
        String path;
        path = command.execute(request);
        request.getRequestDispatcher(path).forward(request, response);
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
