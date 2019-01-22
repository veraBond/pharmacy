package com.bandarovich.pharmacy.entity;


public class PharmacyOrder extends Pharmacy{
    private int orderId;
    private String clientMail;
    private int medicineId;
    private int orderAmount;
    private double totalCost;

    public PharmacyOrder(int orderId, String clientMail, int medicineId, int orderAmount, double totalCost) {
        this.orderId = orderId;
        this.clientMail = clientMail;
        this.medicineId = medicineId;
        this.orderAmount = orderAmount;
        this.totalCost = totalCost;
    }

    public String getClientMail() {
        return clientMail;
    }

    public void setClientMail(String clientMail) {
        this.clientMail = clientMail;
    }

    public int getMedicineId() {
        return medicineId;
    }

    public void setMedicineId(int medicineId) {
        this.medicineId = medicineId;
    }

    public int getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(int orderAmount) {
        this.orderAmount = orderAmount;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PharmacyOrder that = (PharmacyOrder) o;

        if (orderId != that.orderId) return false;
        if (medicineId != that.medicineId) return false;
        if (orderAmount != that.orderAmount) return false;
        if (Double.compare(that.totalCost, totalCost) != 0) return false;
        return clientMail != null ? clientMail.equals(that.clientMail) : that.clientMail == null;
    }

    @Override
    public int hashCode() {
        int result;
        result = orderId;
        result = 31 * result + (clientMail != null ? clientMail.hashCode() : 0);
        result = 31 * result + medicineId;
        result = 31 * result + orderAmount;
        result = 31 * result + (int) (totalCost);
        return result;
    }

    @Override
    public String toString() {
        return "PharmacyOrder{" + "orderId=" + orderId + ", clientMail='" + clientMail + '\'' + ", medicineId=" + medicineId + ", orderAmount=" + orderAmount + ", totalCost=" + totalCost + '}';
    }
}
