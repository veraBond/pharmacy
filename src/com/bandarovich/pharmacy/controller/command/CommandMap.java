package com.bandarovich.pharmacy.controller.command;


import com.bandarovich.pharmacy.controller.command.impl.*;

import java.util.EnumMap;

public class CommandMap {
    private EnumMap<CommandType, PharmacyCommand> commandMap = new EnumMap<CommandType, PharmacyCommand>(CommandType.class){
        {
            put(CommandType.REGISTRATION, new RegistrationCommand());
            put(CommandType.LOG_IN, new LogInCommand());
            put(CommandType.CLIENT_MEDICINE_LIST, new ClientMedicineListCommand());
            put(CommandType.ALL_CLIENT_MEDICINE_LIST, new AllClientMedicineListCommand());
            put(CommandType.ADD_ORDER_MEDICINE, new AddOrderMedicineCommand());
        }
    };

    private CommandMap(){
    }

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
