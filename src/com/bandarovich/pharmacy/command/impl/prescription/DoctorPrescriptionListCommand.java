package com.bandarovich.pharmacy.command.impl.prescription;

import com.bandarovich.pharmacy.command.PharmacyCommand;
import com.bandarovich.pharmacy.command.Router;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DoctorPrescriptionListCommand implements PharmacyCommand {
    @Override
    public Router execute(HttpServletRequest request) {
        return null;
    }
}
