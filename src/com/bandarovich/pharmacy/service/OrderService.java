package com.bandarovich.pharmacy.service;


public interface OrderService {
    double completeOrder(String clientMail, int medicineId, int quantity) throws ServiceException;
}
