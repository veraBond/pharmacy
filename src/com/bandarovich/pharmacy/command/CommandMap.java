package com.bandarovich.pharmacy.command;

import com.bandarovich.pharmacy.command.impl.SetLocaleCommand;
import com.bandarovich.pharmacy.command.impl.StartPageCommand;
import com.bandarovich.pharmacy.command.impl.medicine.AddMedicineCommand;
import com.bandarovich.pharmacy.command.impl.medicine.CompleteAddMedicineCommand;
import com.bandarovich.pharmacy.command.impl.order.BookMedicineCommand;
import com.bandarovich.pharmacy.command.impl.medicine.DoctorMedicineListCommand;
import com.bandarovich.pharmacy.command.impl.order.CompleteOrderCommand;
import com.bandarovich.pharmacy.command.impl.prescription.*;
import com.bandarovich.pharmacy.command.impl.medicine.ClientMedicineListCommand;
import com.bandarovich.pharmacy.command.impl.user.LogInCommand;
import com.bandarovich.pharmacy.command.impl.user.LogOutCommand;
import com.bandarovich.pharmacy.command.impl.user.RegistrationCommand;

import java.util.EnumMap;

public class CommandMap {
    private EnumMap<CommandType, PharmacyCommand> commandMap = new EnumMap<CommandType, PharmacyCommand>(CommandType.class){
        {
            put(CommandType.START_PAGE, new StartPageCommand());
            put(CommandType.SET_LOCALE, new SetLocaleCommand());
            put(CommandType.LOG_IN, new LogInCommand());
            put(CommandType.REGISTRATION, new RegistrationCommand());
            put(CommandType.LOG_OUT, new LogOutCommand());
            put(CommandType.CLIENT_MEDICINE_LIST, new ClientMedicineListCommand());
            put(CommandType.BOOK_MEDICINE, new BookMedicineCommand());
            put(CommandType.COMPLETE_ORDER, new CompleteOrderCommand());
            put(CommandType.CLIENT_PRESCRIPTION_LIST, new ClientPrescriptionListCommand());
            put(CommandType.REQUEST_PRESCRIPTION_FOR_EXTENSION, new RequestPrescriptionForExtensionCommand());
            put(CommandType.DOCTOR_MEDICINE_LIST, new DoctorMedicineListCommand());
            put(CommandType.WRITE_PRESCRIPTION, new WritePrescriptionCommand());
            put(CommandType.COMPLETE_WRITE_PRESCRIPTION, new CompleteWritePrescriptionCommand());
            put(CommandType.DOCTOR_PRESCRIPTION_LIST, new DoctorPrescriptionListCommand());
            put(CommandType.EXTEND_PRESCRIPTION, new ExtendPrescriptionCommand());
            put(CommandType.COMPLETE_EXTEND_PRESCRIPTION, new CompleteExtendPrescriptionCommand());
            put(CommandType.ADD_MEDICINE, new AddMedicineCommand());
            put(CommandType.COMPLETE_ADD_MEDICINE, new CompleteAddMedicineCommand());
        }
    };

    private CommandMap(){}

    private static class CommandMapHolder{
        private final static CommandMap INSTANCE = new CommandMap();
    }

    public static CommandMap getInstance(){
        return CommandMapHolder.INSTANCE;
    }

    public PharmacyCommand get(String commandName){
        CommandType key = CommandType.valueOf(commandName.replace('-','_').toUpperCase());
        return commandMap.get(key);
    }
}
