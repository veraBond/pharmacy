package com.bandarovich.pharmacy.entity;

public class Prescription extends Pharmacy {
    private int prescriptionId;
    private String clientMail;
    private String doctorMail;
    private int medicineId;
    private int availableMedicineAmount;
    private PrescriptionStatus status;
    private boolean isRequestedForExtension;

    public Prescription(int prescriptionId, int medicineId, String clientMail, String doctorMail, int availableMedicineAmount, PrescriptionStatus status, boolean isRequestedForExtension) {
        this.prescriptionId = prescriptionId;
        this.clientMail = clientMail;
        this.doctorMail = doctorMail;
        this.medicineId = medicineId;
        this.availableMedicineAmount = availableMedicineAmount;
        this.status = status;
        this.isRequestedForExtension = isRequestedForExtension;
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

    public PrescriptionStatus getStatus() {
        return status;
    }

    public void setStatus(PrescriptionStatus status) {
        this.status = status;
    }

    public boolean isRequestedForExtension() {
        return isRequestedForExtension;
    }

    public void setRequestedForExtension(boolean requestedForExtension) {
        isRequestedForExtension = requestedForExtension;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Prescription that = (Prescription) o;

        if (prescriptionId != that.prescriptionId) return false;
        if (medicineId != that.medicineId) return false;
        if (availableMedicineAmount != that.availableMedicineAmount) return false;
        if (isRequestedForExtension != that.isRequestedForExtension) return false;
        if (!clientMail.equals(that.clientMail)) return false;
        if (!doctorMail.equals(that.doctorMail)) return false;
        return status == that.status;
    }

    @Override
    public int hashCode() {
        int result = prescriptionId;
        result = 31 * result + clientMail.hashCode();
        result = 31 * result + doctorMail.hashCode();
        result = 31 * result + medicineId;
        result = 31 * result + availableMedicineAmount;
        result = 31 * result + status.hashCode();
        result = 31 * result + (isRequestedForExtension ? 1 : 0);
        return result;
    }
}
