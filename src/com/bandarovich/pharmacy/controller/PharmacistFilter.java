package com.bandarovich.pharmacy.controller;

import com.bandarovich.pharmacy.command.JspAttribute;
import com.bandarovich.pharmacy.command.JspPath;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * The Class PharmacistFilter.
 */
public class PharmacistFilter implements Filter {
    
    /** The Constant FORBIDDEN_ACCESS_MESSAGE. */
    private static final String FORBIDDEN_ACCESS_MESSAGE = "Forbidden. Access denied.";

    /**
     * Inits the PharmacistFilter.
     *
     * @param fConfig the f config
     * @throws ServletException the servlet exception
     */
    public void init(FilterConfig fConfig) throws ServletException {
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
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException  {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        Boolean isPharmacist = (Boolean) request.getSession().getAttribute(JspAttribute.IS_PHARMACIST);
        if(isPharmacist == null || !isPharmacist) {
                request.getSession().setAttribute(JspAttribute.ERROR_MESSAGE, FORBIDDEN_ACCESS_MESSAGE);
                request.getRequestDispatcher(JspPath.COMMAND_ERROR_PAGE).forward(request, response);
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    /**
     * Destroy PharmacistFilter.
     */
    public void destroy()  {
    }
}