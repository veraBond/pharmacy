package com.bandarovich.pharmacy.controller;

import com.bandarovich.pharmacy.command.JspAttribute;
import com.bandarovich.pharmacy.command.JspPath;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthorizationFilter implements Filter {
    private final static String UNAUTHORIZED_ACCESS_MESSAGE = "Log in to have an access.";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

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

    @Override
    public void destroy() {

    }
}
