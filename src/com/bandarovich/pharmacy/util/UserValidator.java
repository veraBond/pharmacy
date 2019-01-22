package com.bandarovich.pharmacy.util;

/**
 * The Class UserValidator.
 */
public class UserValidator {
    
    /** The Constant NAME_PATTERN. */
    private static final String NAME_PATTERN = "[\\p{Alpha}]{2,20}";
    
    /** The Constant USER_MAIL_PATTERN. */
    private static final String USER_MAIL_PATTERN = "[\\p{Alnum}]{2,20}[@][\\p{Lower}]{2,10}[.][\\p{Lower}]{2,5}";
    
    /** The Constant USER_PASSWORD_PATTERN. */
    private static final String USER_PASSWORD_PATTERN = "[\\p{Graph}]{5,20}";

    /**
     * Instantiates a new user validator.
     */
    private UserValidator(){}

    /**
     * User name is correct.
     *
     * @param name the name
     * @return true, if successful
     */
    public static boolean userNameIsCorrect(String name){
        return name.matches(NAME_PATTERN);
    }

    /**
     * Mail is correct.
     *
     * @param mail the mail
     * @return true, if successful
     */
    public static boolean mailIsCorrect(String mail){
        return mail.matches(USER_MAIL_PATTERN);
    }

    /**
     * Password is correct.
     *
     * @param password the password
     * @return true, if successful
     */
    public static boolean passwordIsCorrect(String password){
        return password.matches(USER_PASSWORD_PATTERN);
    }

}
