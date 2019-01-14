package com.bandarovich.pharmacy.util;

import org.apache.commons.codec.digest.DigestUtils;

import java.nio.charset.StandardCharsets;

public class InputDataService {
    private InputDataService(){}
    public static String codePassword(String password){
        return DigestUtils.shaHex(password);
    }
    public static String setCorrectEncoding(String str) {
        return new String(str.getBytes(StandardCharsets.ISO_8859_1));
    }
}
