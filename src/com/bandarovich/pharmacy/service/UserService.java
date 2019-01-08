package com.bandarovich.pharmacy.service;

import com.bandarovich.pharmacy.entity.PharmacyPosition;
import com.bandarovich.pharmacy.entity.PharmacyUser;

import java.util.List;
import java.util.Set;

public interface UserService {
    Set<String> register(PharmacyUser user) throws ServiceException;
    List<PharmacyUser> logIn(PharmacyPosition position, String mail, String password) throws ServiceException;
}
