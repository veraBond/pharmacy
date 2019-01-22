package com.bandarovich.pharmacy.entity;

/**
 * The Class PharmacyOrder.
 */
public class PharmacyOrder extends Pharmacy{
    
    /** The order id. */
    private int orderId;
    
    /** The client mail. */
    private String clientMail;
    
    /** The medicine id. */
    private int medicineId;
    
    /** The order amount. */
    private int orderAmount;
    
    /** The total cost. */
    private double totalCost;

    /**
     * Instantiates a new pharmacy order.
     *
     * @param orderId the order id
     * @param clientMail the client mail
     * @param medicineId the medicine id
     * @param orderAmount the order amount
     * @param totalCost the total cost
     */
    public PharmacyOrder(int orderId, String clientMail, int medicineId, int orderAmount, double totalCost) {
        this.orderId = orderId;
        this.clientMail = clientMail;
        this.medicineId = medicineId;
        this.orderAmount = orderAmount;
        this.totalCost = totalCost;
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
     * Gets the order amount.
     *
     * @return the order amount
     */
    public int getOrderAmount() {
        return orderAmount;
    }

    /**
     * Sets the order amount.
     *
     * @param orderAmount the new order amount
     */
    public void setOrderAmount(int orderAmount) {
        this.orderAmount = orderAmount;
    }

    /**
     * Gets the total cost.
     *
     * @return the total cost
     */
    public double getTotalCost() {
        return totalCost;
    }

    /**
     * Sets the total cost.
     *
     * @param totalCost the new total cost
     */
    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    /**
     * Gets the order id.
     *
     * @return the order id
     */
    public int getOrderId() {
        return orderId;
    }

    /**
     * Sets the order id.
     *
     * @param orderId the new order id
     */
    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
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

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
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

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "PharmacyOrder{" + "orderId=" + orderId + ", clientMail='" + clientMail + '\'' + ", medicineId=" + medicineId + ", orderAmount=" + orderAmount + ", totalCost=" + totalCost + '}';
    }
}
