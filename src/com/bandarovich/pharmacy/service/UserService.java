package com.bandarovich.pharmacy.service;

import com.bandarovich.pharmacy.entity.PharmacyPosition;
import com.bandarovich.pharmacy.entity.PharmacyUser;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<String> register(PharmacyUser user) throws ServiceException;
    Optional<String> findUserName(PharmacyPosition position, String mail, String password) throws ServiceException;
}
