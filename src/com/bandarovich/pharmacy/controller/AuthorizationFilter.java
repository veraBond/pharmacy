package com.bandarovich.pharmacy.controller;

import com.bandarovich.pharmacy.command.JspAttribute;
import com.bandarovich.pharmacy.command.JspPath;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * The Class AuthorizationFilter.
 */
public class AuthorizationFilter implements Filter {

    /**
     * Inits the AuthorizationFilter.
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
        String mail = (String) request.getSession().getAttribute(JspAttribute.MAIL);
        if(mail == null) {
            request.getRequestDispatcher(JspPath.START_PAGE).forward(request, response);
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    /**
     * Destroy AuthorizationFilter.
     */
    @Override
    public void destroy() {
    }
}
