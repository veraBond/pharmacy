package com.bandarovich.pharmacy.util;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * The Class PasswordCoding.
 */
public class PasswordCoding {
    
    /**
     * Instantiates a new password coding.
     */
    private PasswordCoding(){}
    
    /**
     * Code password.
     *
     * @param password the password
     * @return the string
     */
    public static String codePassword(String password){
        return DigestUtils.shaHex(password);
    }
}
