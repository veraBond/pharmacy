package com.bandarovich.pharmacy.controller;

import com.bandarovich.pharmacy.command.JspAttribute;
import com.bandarovich.pharmacy.command.JspPath;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class PharmacistFilter implements Filter {
    private final static String FORBIDDEN_ACCESS_MESSAGE = "Forbidden. Access denied.";

    public void init(FilterConfig fConfig) throws ServletException {
    }

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

    public void destroy()  {
    }
}