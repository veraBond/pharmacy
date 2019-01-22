package com.bandarovich.pharmacy.util;

/**
 * The Class MedicineValidator.
 */
public class MedicineValidator {
    
    /** The Constant NAME_PATTERN. */
    private static final String NAME_PATTERN = "[\\p{Alpha}]{2,20}";
    
    /** The Constant MEDICINE_DOSAGE_MAX. */
    private static final int MEDICINE_DOSAGE_MAX = 1000;
    
    /** The Constant MEDICINE_DOSAGE_MIN. */
    private static final int MEDICINE_DOSAGE_MIN = 1;
    
    /** The Constant PACKAGE_AMOUNT_MAX. */
    private static final int PACKAGE_AMOUNT_MAX = 1000;
    
    /** The Constant PACKAGE_AMOUNT_MIN. */
    private static final int PACKAGE_AMOUNT_MIN = 1;
    
    /** The Constant PRICE_MAX. */
    private static final int PRICE_MAX = 1000;
    
    /** The Constant PRICE_MIN. */
    private static final int PRICE_MIN = 1;
    
    /** The Constant STORAGE_AMOUNT_MAX. */
    private static final int STORAGE_AMOUNT_MAX = 1000;
    
    /** The Constant STORAGE_AMOUNT_MIN. */
    private static final int STORAGE_AMOUNT_MIN = 0;

    /**
     * Instantiates a new medicine validator.
     */
    private MedicineValidator(){}

    /**
     * Medicine name is correct.
     *
     * @param medicineName the medicine name
     * @return true, if successful
     */
    public static boolean medicineNameIsCorrect(String medicineName){
        return medicineName.matches(NAME_PATTERN);
    }

    /**
     * Medicine dosage is correct.
     *
     * @param medicineDosage the medicine dosage
     * @return true, if successful
     */
    public static boolean medicineDosageIsCorrect(int medicineDosage){
        return medicineDosage >= MEDICINE_DOSAGE_MIN && medicineDosage <= MEDICINE_DOSAGE_MAX;
    }

    /**
     * Medicine group is correct.
     *
     * @param medicineGroup the medicine group
     * @return true, if successful
     */
    public static boolean medicineGroupIsCorrect(String medicineGroup){
        return medicineGroup != null;
    }

    /**
     * Package type is correct.
     *
     * @param packageType the package type
     * @return true, if successful
     */
    public static boolean packageTypeIsCorrect(String packageType){
        return packageType != null;
    }

    /**
     * Package amount is correct.
     *
     * @param packageAmount the package amount
     * @return true, if successful
     */
    public static boolean packageAmountIsCorrect(int packageAmount){
        return packageAmount >= PACKAGE_AMOUNT_MIN && packageAmount <= PACKAGE_AMOUNT_MAX;
    }

    /**
     * Price is correct.
     *
     * @param price the price
     * @return true, if successful
     */
    public static boolean priceIsCorrect(double price){
        return price >= PRICE_MIN && price <= PRICE_MAX;
    }

    /**
     * Storage amount is correct.
     *
     * @param storageAmount the storage amount
     * @return true, if successful
     */
    public static boolean storageAmountIsCorrect(int storageAmount){
        return storageAmount >= STORAGE_AMOUNT_MIN && storageAmount <= STORAGE_AMOUNT_MAX;
    }
}
