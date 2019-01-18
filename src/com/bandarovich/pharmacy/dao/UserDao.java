package com.bandarovich.pharmacy.dao;

import com.bandarovich.pharmacy.entity.PharmacyUser;

import java.util.Optional;

public interface UserDao {
    Optional<PharmacyUser> findUser(String mail, String password) throws DaoException;
    Optional<Integer> findUserId(String mail) throws DaoException;
}
