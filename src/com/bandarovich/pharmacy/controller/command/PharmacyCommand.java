package com.bandarovich.pharmacy.controller.command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface PharmacyCommand {
//TODO command pattern is correct? is correct to throw exc?
   void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
}
