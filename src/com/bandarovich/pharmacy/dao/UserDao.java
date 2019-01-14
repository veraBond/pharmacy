package com.bandarovich.pharmacy.dao;

import com.bandarovich.pharmacy.entity.PharmacyUser;

import java.util.Optional;
//TODO create abstract parametrised interface for all daos methods (find, update, delete, create)?
public interface UserDao {
    Optional<PharmacyUser> findUser(String mail, String password) throws DaoException;
    Optional<Integer> findUserId(String mail) throws DaoException;
}
