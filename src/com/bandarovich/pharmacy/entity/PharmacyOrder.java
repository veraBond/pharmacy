package com.bandarovich.pharmacy.entity;

import java.util.Objects;

public class PharmacyOrder extends Pharmacy{
    private int orderNumber;
    private String clientMail;
    private int medicineNumber;
    private int orderAmount;
    private OrderStatus status;

    public PharmacyOrder(int orderNumber, String clientMail, int medicineNumber, int orderAmount, OrderStatus status) {
        this.orderNumber = orderNumber;
        this.clientMail = clientMail;
        this.medicineNumber = medicineNumber;
        this.orderAmount = orderAmount;
        this.status = status;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getClientMail() {
        return clientMail;
    }

    public void setClientMail(String clientMail) {
        this.clientMail = clientMail;
    }

    public int getMedicineNumber() {
        return medicineNumber;
    }

    public void setMedicineNumber(int medicineNumber) {
        this.medicineNumber = medicineNumber;
    }

    public int getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(int orderAmount) {
        this.orderAmount = orderAmount;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PharmacyOrder that = (PharmacyOrder) o;
        return orderNumber == that.orderNumber &&
                medicineNumber == that.medicineNumber &&
                orderAmount == that.orderAmount &&
                clientMail.equals(that.clientMail) &&
                status == that.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderNumber, clientMail, medicineNumber, orderAmount, status);
    }
}
