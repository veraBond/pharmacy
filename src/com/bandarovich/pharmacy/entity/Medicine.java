package com.bandarovich.pharmacy.entity;

public class Medicine extends Pharmacy{
    private int medicineId;
    private String name;
    private int dosage;
    private String group;
    private String packageType;
    private int packageAmount;
    private double price;
    private boolean needPrescription;
    private int storageAmount;

    public Medicine(int medicineId, String name, int dosage, String group, String packageType, int packageAmount, double price, boolean needPrescription, int storageAmount) {
        this.medicineId = medicineId;
        this.name = name;
        this.dosage = dosage;
        this.group = group;
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

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
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

    public void setNeedPrescription(boolean needPrescription) {
        this.needPrescription = needPrescription;
    }

    public int getStorageAmount() {
        return storageAmount;
    }

    public void setStorageAmount(int storageAmount) {
        this.storageAmount = storageAmount;
    }

    public boolean isNeedPrescription() {
        return needPrescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Medicine medicine = (Medicine) o;

        if (medicineId != medicine.medicineId) return false;
        if (dosage != medicine.dosage) return false;
        if (packageAmount != medicine.packageAmount) return false;
        if (Double.compare(medicine.price, price) != 0) return false;
        if (needPrescription != medicine.needPrescription) return false;
        if (storageAmount != medicine.storageAmount) return false;
        if (name != null ? !name.equals(medicine.name) : medicine.name != null) return false;
        if (group != null ? !group.equals(medicine.group) : medicine.group != null)
            return false;
        return packageType != null ? packageType.equals(medicine.packageType) : medicine.packageType == null;
    }

    @Override
    public int hashCode() {
        int result;
        result = medicineId;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + dosage;
        result = 31 * result + (group != null ? group.hashCode() : 0);
        result = 31 * result + (packageType != null ? packageType.hashCode() : 0);
        result = 31 * result + packageAmount;
        result = 31 * result + (int) (price);
        result = 31 * result + (needPrescription ? 1 : 0);
        result = 31 * result + storageAmount;
        return result;
    }

    @Override
    public String toString() {
        return "Medicine{" + "medicineId=" + medicineId + ", name='" + name + '\'' + ", dosage=" + dosage + ", group=" + group + ", packageType='" + packageType + '\'' + ", packageAmount=" + packageAmount + ", price=" + price + ", needPrescription=" + needPrescription + ", storageAmount=" + storageAmount + '}';
    }
}
