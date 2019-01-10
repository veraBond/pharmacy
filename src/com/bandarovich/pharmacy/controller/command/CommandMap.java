package com.bandarovich.pharmacy.controller.command;


import com.bandarovich.pharmacy.controller.command.impl.medicine.AllClientMedicineListCommand;
import com.bandarovich.pharmacy.controller.command.impl.medicine.ClientMedicineListCommand;
import com.bandarovich.pharmacy.controller.command.impl.order.AddOrderMedicineCommand;
import com.bandarovich.pharmacy.controller.command.impl.order.TryOrderMedicineCommand;
import com.bandarovich.pharmacy.controller.command.impl.user.LogInCommand;
import com.bandarovich.pharmacy.controller.command.impl.user.LogOutCommand;
import com.bandarovich.pharmacy.controller.command.impl.user.RegistrationCommand;

import java.util.EnumMap;

public class CommandMap {
    private EnumMap<CommandType, PharmacyCommand> commandMap = new EnumMap<CommandType, PharmacyCommand>(CommandType.class){
        {
            put(CommandType.REGISTRATION, new RegistrationCommand());
            put(CommandType.LOG_IN, new LogInCommand());
            put(CommandType.LOG_OUT, new LogOutCommand());
            put(CommandType.CLIENT_MEDICINE_LIST, new ClientMedicineListCommand());
            put(CommandType.ALL_CLIENT_MEDICINE_LIST, new AllClientMedicineListCommand());
            put(CommandType.TRY_ORDER_MEDICINE, new TryOrderMedicineCommand());
            put(CommandType.ADD_ORDER_MEDICINE, new AddOrderMedicineCommand());
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
