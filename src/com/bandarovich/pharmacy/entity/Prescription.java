package com.bandarovich.pharmacy.entity;

/**
 * The Class Prescription.
 */
public class Prescription extends Pharmacy {
    
    /** The prescription id. */
    private int prescriptionId;
    
    /** The client mail. */
    private String clientMail;
    
    /** The doctor mail. */
    private String doctorMail;
    
    /** The medicine id. */
    private int medicineId;
    
    /** The available medicine amount. */
    private int availableMedicineAmount;
    
    /** The requested for extension. */
    private boolean requestedForExtension;

    /**
     * Instantiates a new prescription.
     *
     * @param prescriptionId the prescription id
     * @param medicineId the medicine id
     * @param clientMail the client mail
     * @param doctorMail the doctor mail
     * @param availableMedicineAmount the available medicine amount
     * @param requestedForExtension the requested for extension
     */
    public Prescription(int prescriptionId, int medicineId, String clientMail, String doctorMail, int availableMedicineAmount, boolean requestedForExtension) {
        this.prescriptionId = prescriptionId;
        this.clientMail = clientMail;
        this.doctorMail = doctorMail;
        this.medicineId = medicineId;
        this.availableMedicineAmount = availableMedicineAmount;
        this.requestedForExtension = requestedForExtension;
    }

    /**
     * Gets the prescription id.
     *
     * @return the prescription id
     */
    public int getPrescriptionId() {
        return prescriptionId;
    }

    /**
     * Sets the prescription id.
     *
     * @param prescriptionId the new prescription id
     */
    public void setPrescriptionId(int prescriptionId) {
        this.prescriptionId = prescriptionId;
    }

    /**
     * Gets the client mail.
     *
     * @return the client mail
     */
    public String getClientMail() {
        return clientMail;
    }

    /**
     * Sets the client mail.
     *
     * @param clientMail the new client mail
     */
    public void setClientMail(String clientMail) {
        this.clientMail = clientMail;
    }

    /**
     * Gets the doctor mail.
     *
     * @return the doctor mail
     */
    public String getDoctorMail() {
        return doctorMail;
    }

    /**
     * Sets the doctor mail.
     *
     * @param doctorMail the new doctor mail
     */
    public void setDoctorMail(String doctorMail) {
        this.doctorMail = doctorMail;
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
     * Gets the available medicine amount.
     *
     * @return the available medicine amount
     */
    public int getAvailableMedicineAmount() {
        return availableMedicineAmount;
    }

    /**
     * Sets the available medicine amount.
     *
     * @param availableMedicineAmount the new available medicine amount
     */
    public void setAvailableMedicineAmount(int availableMedicineAmount) {
        this.availableMedicineAmount = availableMedicineAmount;
    }

    /**
     * Checks if is requested for extension.
     *
     * @return true, if is requested for extension
     */
    public boolean isRequestedForExtension() {
        return requestedForExtension;
    }

    /**
     * Sets the requested for extension.
     *
     * @param requestedForExtension the new requested for extension
     */
    public void setRequestedForExtension(boolean requestedForExtension) {
        this.requestedForExtension = requestedForExtension;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Prescription that = (Prescription) o;

        if (prescriptionId != that.prescriptionId) return false;
        if (medicineId != that.medicineId) return false;
        if (availableMedicineAmount != that.availableMedicineAmount) return false;
        if (requestedForExtension != that.requestedForExtension) return false;
        if (clientMail != null ? !clientMail.equals(that.clientMail) : that.clientMail != null) return false;
        return doctorMail != null ? doctorMail.equals(that.doctorMail) : that.doctorMail == null;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        int result = prescriptionId;
        result = 31 * result + (clientMail != null ? clientMail.hashCode() : 0);
        result = 31 * result + (doctorMail != null ? doctorMail.hashCode() : 0);
        result = 31 * result + medicineId;
        result = 31 * result + availableMedicineAmount;
        result = 31 * result + (requestedForExtension ? 1 : 0);
        return result;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Prescription{" + "prescriptionId=" + prescriptionId + ", clientMail='" + clientMail + '\'' + ", doctorMail='" + doctorMail + '\'' + ", medicineId=" + medicineId + ", availableMedicineAmount=" + availableMedicineAmount + ", requestedForExtension=" + requestedForExtension + '}';
    }
}
