package com.bandarovich.pharmacy.controller.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public interface PharmacyCommand {

   String execute(HttpServletRequest request);
}
