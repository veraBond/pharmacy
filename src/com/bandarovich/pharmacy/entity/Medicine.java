package com.bandarovich.pharmacy.entity;

import java.util.List;
import java.util.Objects;

public class Medicine extends Pharmacy{
    private int medicineNumber;
    private String name;
    private int dosage;
    private List<String> groupType;
    private String packageType;
    private int packageAmount;
    private double price;
    private boolean recipeNeed;
    private int storageAmount;

    public Medicine(int medicineNumber, String name, int dosage, List<String> groupType, String packageType, int packageAmount, double price, boolean recipeNeed, int storageAmount) {
        this.medicineNumber = medicineNumber;
        this.name = name;
        this.dosage = dosage;
        this.groupType = groupType;
        this.packageType = packageType;
        this.packageAmount = packageAmount;
        this.price = price;
        this.recipeNeed = recipeNeed;
        this.storageAmount = storageAmount;
    }

    public int getMedicineNumber() {
        return medicineNumber;
    }

    public void setMedicineNumber(int medicineNumber) {
        this.medicineNumber = medicineNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDosage() {
        return dosage;
    }

    public void setDosage(int dosage) {
        this.dosage = dosage;
    }

    public List<String> getGroupType() {
        return groupType;
    }

    public void setGroupType(List<String> groupType) {
        this.groupType = groupType;
    }

    public String getPackageType() {
        return packageType;
    }

    public void setPackageType(String packageType) {
        this.packageType = packageType;
    }

    public int getPackageAmount() {
        return packageAmount;
    }

    public void setPackageAmount(int packageAmount) {
        this.packageAmount = packageAmount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isRecipeNeed() {
        return recipeNeed;
    }

    public void setRecipeNeed(boolean recipeNeed) {
        this.recipeNeed = recipeNeed;
    }

    public int getStorageAmount() {
        return storageAmount;
    }

    public void setStorageAmount(int storageAmount) {
        this.storageAmount = storageAmount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Medicine medicine = (Medicine) o;
        return (medicineNumber == medicine.medicineNumber &&
                name.equals(medicine.name) &&
                dosage == medicine.dosage &&
                groupType.equals(medicine.groupType) &&
                packageType.equals(medicine.packageType)) &&
                packageAmount == medicine.packageAmount &&
                Double.compare(medicine.price, price) == 0 &&
                recipeNeed == medicine.recipeNeed &&
                storageAmount == medicine.storageAmount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(medicineNumber, name, dosage, groupType, packageType, packageAmount, price, recipeNeed, storageAmount);
    }
}
