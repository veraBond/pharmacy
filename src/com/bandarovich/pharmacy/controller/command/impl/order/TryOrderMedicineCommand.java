package com.bandarovich.pharmacy.controller.command.impl.order;

import com.bandarovich.pharmacy.controller.command.PharmacyCommand;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TryOrderMedicineCommand implements PharmacyCommand {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
