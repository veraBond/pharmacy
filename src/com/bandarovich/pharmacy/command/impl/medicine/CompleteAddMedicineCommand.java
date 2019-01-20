package com.bandarovich.pharmacy.command.impl.medicine;

import com.bandarovich.pharmacy.command.PharmacyCommand;
import com.bandarovich.pharmacy.command.Router;

import javax.servlet.http.HttpServletRequest;

public class CompleteAddMedicineCommand implements PharmacyCommand {
    @Override
    public Router execute(HttpServletRequest request) {
        return null;
    }
}
