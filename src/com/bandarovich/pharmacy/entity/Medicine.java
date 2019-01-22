package com.bandarovich.pharmacy.entity;

/**
 * The Class Medicine.
 */
public class Medicine extends Pharmacy{
    
    /** The medicine id. */
    private int medicineId;
    
    /** The name. */
    private String name;
    
    /** The dosage. */
    private int dosage;
    
    /** The group. */
    private String group;
    
    /** The package type. */
    private String packageType;
    
    /** The package amount. */
    private int packageAmount;
    
    /** The price. */
    private double price;
    
    /** The need prescription. */
    private boolean needPrescription;
    
    /** The storage amount. */
    private int storageAmount;

    /**
     * Instantiates a new medicine.
     *
     * @param medicineId the medicine id
     * @param name the name
     * @param dosage the dosage
     * @param group the group
     * @param packageType the package type
     * @param packageAmount the package amount
     * @param price the price
     * @param needPrescription the need prescription
     * @param storageAmount the storage amount
     */
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

    /**
     * Gets the medicine id.
     *
     * @return the medicine id
     */
    public int getMedicineId() {
        return medicineId;
    }

    /**
     * Sets the medicine id.
     *
     * @param medicineId the new medicine id
     */
    public void setMedicineId(int medicineId) {
        this.medicineId = medicineId;
    }

    /**
     * Gets the name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name.
     *
     * @param name the new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the dosage.
     *
     * @return the dosage
     */
    public int getDosage() {
        return dosage;
    }

    /**
     * Sets the dosage.
     *
     * @param dosage the new dosage
     */
    public void setDosage(int dosage) {
        this.dosage = dosage;
    }

    /**
     * Gets the group.
     *
     * @return the group
     */
    public String getGroup() {
        return group;
    }

    /**
     * Sets the group.
     *
     * @param group the new group
     */
    public void setGroup(String group) {
        this.group = group;
    }

    /**
     * Gets the package type.
     *
     * @return the package type
     */
    public String getPackageType() {
        return packageType;
    }

    /**
     * Sets the package type.
     *
     * @param packageType the new package type
     */
    public void setPackageType(String packageType) {
        this.packageType = packageType;
    }

    /**
     * Gets the package amount.
     *
     * @return the package amount
     */
    public int getPackageAmount() {
        return packageAmount;
    }

    /**
     * Sets the package amount.
     *
     * @param packageAmount the new package amount
     */
    public void setPackageAmount(int packageAmount) {
        this.packageAmount = packageAmount;
    }

    /**
     * Gets the price.
     *
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * Sets the price.
     *
     * @param price the new price
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Sets the need prescription.
     *
     * @param needPrescription the new need prescription
     */
    public void setNeedPrescription(boolean needPrescription) {
        this.needPrescription = needPrescription;
    }

    /**
     * Gets the storage amount.
     *
     * @return the storage amount
     */
    public int getStorageAmount() {
        return storageAmount;
    }

    /**
     * Sets the storage amount.
     *
     * @param storageAmount the new storage amount
     */
    public void setStorageAmount(int storageAmount) {
        this.storageAmount = storageAmount;
    }

    /**
     * Checks if is need prescription.
     *
     * @return true, if is need prescription
     */
    public boolean isNeedPrescription() {
        return needPrescription;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
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

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
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

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Medicine{" + "medicineId=" + medicineId + ", name='" + name + '\'' + ", dosage=" + dosage + ", group=" + group + ", packageType='" + packageType + '\'' + ", packageAmount=" + packageAmount + ", price=" + price + ", needPrescription=" + needPrescription + ", storageAmount=" + storageAmount + '}';
    }
}
