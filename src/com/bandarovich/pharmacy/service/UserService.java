package com.bandarovich.pharmacy.service;

import com.bandarovich.pharmacy.entity.PharmacyUser;

import java.util.List;
import java.util.Optional;

/**
 * The Interface UserService.
 */
public interface UserService {
    
    /**
     * Register.
     *
     * @param user the user
     * @return the list
     * @throws ServiceException the service exception
     */
    List<String> register(PharmacyUser user) throws ServiceException;
    
    /**
     * Find user.
     *
     * @param mail the mail
     * @param password the password
     * @return the optional
     * @throws ServiceException the service exception
     */
    Optional<PharmacyUser> findUser(String mail, String password) throws ServiceException;
    
    /**
     * Find user.
     *
     * @param mail the mail
     * @return true, if successful
     * @throws ServiceException the service exception
     */
    boolean findUser(String mail) throws ServiceException;
}
