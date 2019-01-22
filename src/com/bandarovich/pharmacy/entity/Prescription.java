package com.bandarovich.pharmacy.entity;

public class Prescription extends Pharmacy {
    private int prescriptionId;
    private String clientMail;
    private String doctorMail;
    private int medicineId;
    private int availableMedicineAmount;
    private boolean requestedForExtension;

    public Prescription(int prescriptionId, int medicineId, String clientMail, String doctorMail, int availableMedicineAmount, boolean requestedForExtension) {
        this.prescriptionId = prescriptionId;
        this.clientMail = clientMail;
        this.doctorMail = doctorMail;
        this.medicineId = medicineId;
        this.availableMedicineAmount = availableMedicineAmount;
        this.requestedForExtension = requestedForExtension;
    }

    public int getPrescriptionId() {
        return prescriptionId;
    }

    public void setPrescriptionId(int prescriptionId) {
        this.prescriptionId = prescriptionId;
    }

    public String getClientMail() {
        return clientMail;
    }

    public void setClientMail(String clientMail) {
        this.clientMail = clientMail;
    }

    public String getDoctorMail() {
        return doctorMail;
    }

    public void setDoctorMail(String doctorMail) {
        this.doctorMail = doctorMail;
    }

    public int getMedicineId() {
        return medicineId;
    }

    public void setMedicineId(int medicineId) {
        this.medicineId = medicineId;
    }

    public int getAvailableMedicineAmount() {
        return availableMedicineAmount;
    }

    public void setAvailableMedicineAmount(int availableMedicineAmount) {
        this.availableMedicineAmount = availableMedicineAmount;
    }

    public boolean isRequestedForExtension() {
        return requestedForExtension;
    }

    public void setRequestedForExtension(boolean requestedForExtension) {
        this.requestedForExtension = requestedForExtension;
    }

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

    @Override
    public String toString() {
        return "Prescription{" + "prescriptionId=" + prescriptionId + ", clientMail='" + clientMail + '\'' + ", doctorMail='" + doctorMail + '\'' + ", medicineId=" + medicineId + ", availableMedicineAmount=" + availableMedicineAmount + ", requestedForExtension=" + requestedForExtension + '}';
    }
}
