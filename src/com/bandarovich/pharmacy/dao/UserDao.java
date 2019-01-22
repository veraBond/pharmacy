package com.bandarovich.pharmacy.dao;

import com.bandarovich.pharmacy.entity.PharmacyUser;

import java.util.Optional;

/**
 * The Interface UserDao.
 */
public interface UserDao {
    
    /**
     * Find user.
     *
     * @param mail the mail
     * @param password the password
     * @return the optional
     * @throws DaoException the dao exception
     */
    Optional<PharmacyUser> findUser(String mail, String password) throws DaoException;
    
    /**
     * Find user id.
     *
     * @param mail the mail
     * @return the optional
     * @throws DaoException the dao exception
     */
    Optional<Integer> findUserId(String mail) throws DaoException;
}
