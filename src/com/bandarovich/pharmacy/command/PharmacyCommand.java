package com.bandarovich.pharmacy.command;

import javax.servlet.http.HttpServletRequest;

public interface PharmacyCommand {
   Router execute(HttpServletRequest request);
}
