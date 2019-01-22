package com.bandarovich.pharmacy.util;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


public class MedicineValidatorTest {

    @DataProvider(name = "nameProvider")
    public Object[][] dataForNameValidator() {
        return new Object[][]{
                {"abc", true},
                {"bo", true},
                {"abc2", false},
                {"abc_2", false},
        };
    }
    @Test(dataProvider = "nameProvider")
    public void testMedicineNameIsCorrect(String name, boolean expected) {
        boolean actual = MedicineValidator.medicineNameIsCorrect(name);
        Assert.assertEquals(actual, expected);
    }

    @DataProvider(name = "amountProvider")
    public Object[][] dataForAmountValidator() {
        return new Object[][]{
                {1, true},
                {567, true},
                {1300, false},
                {-20, false},
        };
    }
    @Test(dataProvider = "amountProvider")
    public void testMedicineDosageIsCorrect(int dosage, boolean expected) {
        boolean actual = MedicineValidator.medicineDosageIsCorrect(dosage);
        Assert.assertEquals(actual, expected);
    }

    @Test(dataProvider = "amountProvider")
    public void testPackageAmountIsCorrect(int amount, boolean expected) {
        boolean actual = MedicineValidator.packageAmountIsCorrect(amount);
        Assert.assertEquals(actual, expected);
    }

    @Test(dataProvider = "amountProvider")
    public void testStorageAmountIsCorrect(int amount, boolean expected) {
        boolean actual = MedicineValidator.storageAmountIsCorrect(amount);
        Assert.assertEquals(actual, expected);
    }

    @DataProvider(name = "priceProvider")
    public Object[][] dataForPriceValidator() {
        return new Object[][]{
                {1, true},
                {5.67, true},
                {1300, false},
                {-20, false},
                {-2.0, false},
        };
    }
    @Test(dataProvider = "priceProvider")
    public void testPriceIsCorrect(double price, boolean expected) {
        boolean actual = MedicineValidator.priceIsCorrect(price);
        Assert.assertEquals(actual, expected);
    }
}