package com.bandarovich.pharmacy.service;

public interface OrderService {
    boolean addOrderMedicine(int medicineNumber, String clientMail) throws ServiceException;
}
