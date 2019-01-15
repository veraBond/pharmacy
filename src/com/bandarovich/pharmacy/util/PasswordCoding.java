package com.bandarovich.pharmacy.util;

import org.apache.commons.codec.digest.DigestUtils;

public class PasswordCoding {
    private PasswordCoding(){}
    public static String codePassword(String password){
        return DigestUtils.shaHex(password);
    }
}
