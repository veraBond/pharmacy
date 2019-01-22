package com.bandarovich.pharmacy.controller;

import com.bandarovich.pharmacy.command.*;
import com.bandarovich.pharmacy.pool.ConnectionPool;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * The Class PharmacyServlet.
 */
public class PharmacyServlet extends HttpServlet {
    
    /** The Constant COMMAND_PARAMETER. */
    private static final String COMMAND_PARAMETER = "command";
    
    /** The Constant COMMAND_ERROR_MESSAGE. */
    private static final String COMMAND_ERROR_MESSAGE = "Command error.";

    /**
     * Service.
     *
     * @param request the request
     * @param response the response
     * @throws ServletException the servlet exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String commandName = request.getParameter(COMMAND_PARAMETER);
        PharmacyCommand command = CommandMap.get(commandName);
        Router router = command.execute(request);
        String nextPage = router.getPage();
        switch (router.getType()) {
            case FORWARD:
                request.getRequestDispatcher(nextPage).forward(request, response);
                break;
            case REDIRECT:
                response.sendRedirect(nextPage);
                break;
            default:
                request.setAttribute(JspAttribute.ERROR_MESSAGE, COMMAND_ERROR_MESSAGE);
                request.setAttribute(JspAttribute.START_PAGE, JspPath.START_PAGE);
                response.sendRedirect(JspPath.COMMAND_ERROR_PAGE);
        }
    }

    /**
     * Destroy.
     */
    @Override
    public void destroy() {
        ConnectionPool.getInstance().closePool();
    }
}
