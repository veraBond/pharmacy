package com.bandarovich.pharmacy.util;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


public class UserValidatorTest {
    @DataProvider(name = "nameProvider")
    public Object[][] dataForNameValidator() {
        return new Object[][]{
                {"kate", true},
                {"Ilia_Z", false},
                {"o", false},
                {"abcdefghijklmnopqrstuv", false},
                {"vera1995", false},
        };
    }
    @Test(dataProvider = "nameProvider")
    public void testUserNameIsCorrect(String name, boolean expected) {
        boolean actual = UserValidator.userNameIsCorrect(name);
        Assert.assertEquals(actual, expected);
    }

    @DataProvider(name = "emailProvider")
    public Object[][] dataForEmailValidator() {
        return new Object[][]{
                {"vera@gmail.com", true},
                {"Ilia_Z.com", false},
                {"kate95@ru.ru", true},
                {"mike_16@g.com", false},
        };
    }
    @Test(dataProvider = "emailProvider")
    public void testMailIsCorrect(String mail, boolean expected) {
        boolean actual = UserValidator.mailIsCorrect(mail);
        Assert.assertEquals(actual, expected);
    }

    @DataProvider(name = "passwordProvider")
    public Object[][] dataForPasswordValidator() {
        return new Object[][]{
                {"vera@gmail.com", true},
                {"1234", false},
                {"////////", true},
                {"_16@g", true},
        };
    }
    @Test(dataProvider = "passwordProvider")
    public void testPasswordIsCorrect(String password, boolean expected) {
        boolean actual = UserValidator.passwordIsCorrect(password);
        Assert.assertEquals(actual, expected);
    }
}