package com.bandarovich.pharmacy.util;

import com.bandarovich.pharmacy.service.ServiceException;
import com.bandarovich.pharmacy.service.impl.MedicineServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PharmacyValidator {
    private final static Logger logger = LogManager.getLogger();
    private final static String USER_NAME_PATTERN = "[\\p{Alpha}]{2,}";
    private final static String USER_MAIL_PATTERN = "[\\w]{2,}[@][\\p{Alpha}]{2,}[.][\\p{Alpha}]{2,}";
    private final static String USER_PASSWORD_PATTERN = "[\\w]{5,}";
    private final static int AVAILABLE_PRESCRIPTION_MEDICINE_QUANTITY = 5;

    private PharmacyValidator(){}

    public static boolean prescriptionMedicineQuantityIsCorrect(int medicineNumber, int quantity){
        int storageAmount = 0;
        try{
            storageAmount = MedicineServiceImpl.INSTANCE.findMedicineStorageAmount(medicineNumber);
        } catch (ServiceException e){
            logger.error(e);
        }
        return (quantity <= AVAILABLE_PRESCRIPTION_MEDICINE_QUANTITY && quantity <= storageAmount);
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
