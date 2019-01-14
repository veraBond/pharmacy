package com.bandarovich.pharmacy.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface PharmacyCommand {
   Router execute(HttpServletRequest request);
}
