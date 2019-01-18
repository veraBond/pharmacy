package com.bandarovich.pharmacy.service;

import com.bandarovich.pharmacy.entity.PharmacyOrder;

import java.util.Optional;

public interface OrderService {
    boolean deleteOrder(PharmacyOrder order) throws ServiceException;
    PharmacyOrder completeOrder(String clientMail, int medicineId, int quantity) throws ServiceException;
}
