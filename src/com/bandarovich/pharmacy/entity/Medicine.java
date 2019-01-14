package com.bandarovich.pharmacy.entity;

import java.util.Objects;

public class Medicine extends Pharmacy{
    private int medicineId;
    private String name;
    private int dosage;
    private String medicineGroup;
    private String packageType;
    private int packageAmount;
    private double price;
    private boolean needPrescription;
    private int storageAmount;

    public Medicine(int medicineId, String name, int dosage, String medicineGroup, String packageType, int packageAmount, double price, boolean needPrescription, int storageAmount) {
        this.medicineId = medicineId;
        this.name = name;
        this.dosage = dosage;
        this.medicineGroup = medicineGroup;
        this.packageType = packageType;
        this.packageAmount = packageAmount;
        this.price = price;
        this.needPrescription = needPrescription;
        this.storageAmount = storageAmount;
    }

    public int getMedicineId() {
        return medicineId;
    }

    public void setMedicineId(int medicineId) {
        this.medicineId = medicineId;
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

    public String getMedicineGroup() {
        return medicineGroup;
    }

    public void setMedicineGroup(String medicineGroup) {
        this.medicineGroup = medicineGroup;
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

    public boolean needPrescription() {
        return needPrescription;
    }

    public void setNeedPrescription(boolean needPrescription) {
        this.needPrescription = needPrescription;
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
        return (medicineId == medicine.medicineId &&
                name.equals(medicine.name) &&
                dosage == medicine.dosage &&
                medicineGroup.equals(medicine.medicineGroup) &&
                packageType.equals(medicine.packageType) &&
                packageAmount == medicine.packageAmount &&
                Double.compare(medicine.price, price) == 0 &&
                needPrescription == medicine.needPrescription &&
                storageAmount == medicine.storageAmount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(medicineId, name, dosage, medicineGroup, packageType, packageAmount, price, needPrescription, storageAmount);
    }

    @Override
    public String toString() {
        return "Medicine{" + "medicineId=" + medicineId + ", name='" + name + '\'' + ", dosage=" + dosage + ", medicineGroup=" + medicineGroup + ", packageType='" + packageType + '\'' + ", packageAmount=" + packageAmount + ", price=" + price + ", needPrescription=" + needPrescription + ", storageAmount=" + storageAmount + '}';
    }
}
