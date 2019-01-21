package com.bandarovich.pharmacy.util;


public class PharmacyValidator {
    private final static String NAME_PATTERN = "[\\p{Alpha}]{2,20}";
    private final static String USER_MAIL_PATTERN = "[\\p{Alnum}]{2,20}[@][\\p{Lower}]{2,10}[.][\\p{Lower}]{2,5}";
    private final static String USER_PASSWORD_PATTERN = "[\\p{Graph}]{5,20}";
    private final static int MEDICINE_DOSAGE_MAX = 1000;
    private final static int MEDICINE_DOSAGE_MIN = 1;
    private final static int PACKAGE_AMOUNT_MAX = 1000;
    private final static int PACKAGE_AMOUNT_MIN = 1;
    private final static int PRICE_MAX = 1000;
    private final static int PRICE_MIN = 1;
    private final static int STORAGE_AMOUNT_MAX = 1000;
    private final static int STORAGE_AMOUNT_MIN = 0;

    private PharmacyValidator(){}

    public static boolean medicineNameIsCorrect(String medicineName){
        return medicineName.matches(NAME_PATTERN);
    }

    public static boolean medicineDosageIsCorrect(int medicineDosage){
        return medicineDosage >= MEDICINE_DOSAGE_MIN && medicineDosage <= MEDICINE_DOSAGE_MAX;
    }

    public static boolean medicineGroupIsCorrect(String medicineGroup){
        return medicineGroup != null;
    }

    public static boolean packageTypeIsCorrect(String packageType){
        return packageType != null;
    }

    public static boolean packageAmountIsCorrect(int packageAmount){
        return packageAmount >= PACKAGE_AMOUNT_MIN && packageAmount <= PACKAGE_AMOUNT_MAX;
    }

    public static boolean priceIsCorrect(double price){
        return price >= PRICE_MIN && price <= PRICE_MAX;
    }

    public static boolean storageAmountIsCorrect(int storageAmount){
        return storageAmount >= STORAGE_AMOUNT_MIN && storageAmount <= STORAGE_AMOUNT_MAX;
    }

    public static boolean userNameIsCorrect(String name){
        return name.matches(NAME_PATTERN);
    }

    public static boolean mailIsCorrect(String mail){
        return mail.matches(USER_MAIL_PATTERN);
    }

    public static boolean passwordIsCorrect(String password){
        return password.matches(USER_PASSWORD_PATTERN);
    }
}
