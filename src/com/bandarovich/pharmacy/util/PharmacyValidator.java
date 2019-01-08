package com.bandarovich.pharmacy.util;

public class PharmacyValidator {
    private final static String USER_NAME_PATTERN = "[\\p{Alpha}]{2,}";
    private final static String USER_MAIL_PATTERN = "[\\w]{2,}[@][\\p{Alpha}]{2,}[.][\\p{Alpha}]{2,}";
    private final static String USER_PASSWORD_PATTERN = "[\\w]{5,}";
// TODO see pattern Memento

    private PharmacyValidator(){}

    public static boolean positionIsCorrect(String inputPosition, String existingPosition){
        return inputPosition.equalsIgnoreCase(existingPosition);
    }

    public static boolean passwordIsCorrect(String inputPassword, String existingPassword){
        return Coder.codePassword(inputPassword).equals(existingPassword);
    }

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
