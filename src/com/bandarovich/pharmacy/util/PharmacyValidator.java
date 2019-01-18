package com.bandarovich.pharmacy.util;

import com.bandarovich.pharmacy.service.ServiceException;
import com.bandarovich.pharmacy.service.impl.MedicineServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PharmacyValidator {
    private final static Logger logger = LogManager.getLogger();
    private final static String USER_NAME_PATTERN = "[\\p{Alpha}]{2,20}";
    private final static String USER_MAIL_PATTERN = "[\\p{Alnum}]{2,20}[@][\\p{Lower}]{2,10}[.][\\p{Lower}]{2,5}";
    private final static String USER_PASSWORD_PATTERN = "[\\p{Graph}]{5,20}";

    private PharmacyValidator(){}

    public static boolean userNameIsCorrect(String name){
        return name.matches(USER_NAME_PATTERN);
    }

    public static boolean mailIsCorrect(String mail){
        return mail.matches(USER_MAIL_PATTERN);
    }

    public static boolean passwordIsCorrect(String password){
        return password.matches(USER_PASSWORD_PATTERN);
    }
}
