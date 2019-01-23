package com.bandarovich.pharmacy.controller;

import com.bandarovich.pharmacy.command.JspAttribute;
import com.bandarovich.pharmacy.command.JspPath;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * The Class DoctorFilter.
 */
public class DoctorFilter implements Filter {
    
    /** The Constant FORBIDDEN_ACCESS_MESSAGE. */
    private static final String FORBIDDEN_ACCESS_MESSAGE = "Forbidden. Access denied.";

    /**
     * Inits the DoctorFilter.
     *
     * @param filterConfig the filter config
     * @throws ServletException the servlet exception
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    /**
     * Do filter.
     *
     * @param servletRequest the servlet request
     * @param servletResponse the servlet response
     * @param filterChain the filter chain
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws ServletException the servlet exception
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        Boolean isDoctor = (Boolean) request.getSession().getAttribute(JspAttribute.IS_DOCTOR);
        if(isDoctor == null || !isDoctor) {
            request.getSession().setAttribute(JspAttribute.ERROR_MESSAGE, FORBIDDEN_ACCESS_MESSAGE);
            request.getRequestDispatcher(JspPath.COMMAND_ERROR_PAGE).forward(request, response);
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    /**
     * Destroy DoctorFilter.
     */
    @Override
    public void destroy() {

    }
}
