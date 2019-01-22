/*
 * 
 */
package com.bandarovich.pharmacy.command;

import javax.servlet.http.HttpServletRequest;

/**
 * The Interface PharmacyCommand.
 */
public interface PharmacyCommand {
   
   /**
    * Execute.
    *
    * @param request the request
    * @return the router
    */
   Router execute(HttpServletRequest request);
}
