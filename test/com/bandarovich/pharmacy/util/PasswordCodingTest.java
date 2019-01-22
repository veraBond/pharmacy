package com.bandarovich.pharmacy.util;

import org.testng.Assert;
import org.testng.annotations.Test;


public class PasswordCodingTest {

    @Test
    public void testCodePassword() {
        String password = "abcdefg";
        boolean actual = PasswordCoding.codePassword(password).equals(password);
        boolean expected = false;
        Assert.assertEquals(actual, expected);
    }
}