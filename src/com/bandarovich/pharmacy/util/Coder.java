package com.bandarovich.pharmacy.util;

import org.apache.commons.codec.digest.DigestUtils;

public class Coder {
    private Coder(){}
    public static String codePassword(String password){
        return DigestUtils.shaHex(password);
    }
}
