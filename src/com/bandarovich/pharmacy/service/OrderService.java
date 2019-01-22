package com.bandarovich.pharmacy.service;

/**
 * The Interface OrderService.
 */
public interface OrderService {
    
    /**
     * Complete order.
     *
     * @param clientMail the client mail
     * @param medicineId the medicine id
     * @param quantity the quantity
     * @return the double
     * @throws ServiceException the service exception
     */
    double completeOrder(String clientMail, int medicineId, int quantity) throws ServiceException;
}
