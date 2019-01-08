package com.bandarovich.pharmacy.service.impl;

import com.bandarovich.pharmacy.dao.DaoException;
import com.bandarovich.pharmacy.dao.TransactionHelper;
import com.bandarovich.pharmacy.dao.impl.UserDao;
import com.bandarovich.pharmacy.entity.PharmacyPosition;
import com.bandarovich.pharmacy.entity.PharmacyUser;
import com.bandarovich.pharmacy.service.ServiceException;
import com.bandarovich.pharmacy.service.UserService;
import com.bandarovich.pharmacy.util.PharmacyValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

public class UserServiceImpl implements UserService {

    private final static Logger logger = LogManager.getLogger();

    @Override
    public List<PharmacyUser> logIn(PharmacyPosition position, String mail, String password) throws ServiceException{
        UserDao userDao = new UserDao();
        try{
           TransactionHelper.beginTransaction(userDao);
           List<PharmacyUser> userList = userDao.findEntity(mail);
           if(!userList.isEmpty()){
               PharmacyUser selectedUser = userList.get(0);
               boolean positionIsCorrect = PharmacyValidator.positionIsCorrect(position.name(), selectedUser.getPosition().name());
               boolean passwordIsCorrect = PharmacyValidator.passwordIsCorrect(password, selectedUser.getPassword());
               if(!positionIsCorrect || !passwordIsCorrect){
                   userList.clear();
               }
           }
           return userList;
        } catch (DaoException e){
            logger.error("Error in logIn method: " + e.getMessage());
            throw new ServiceException(e);
        } finally {
            TransactionHelper.endTransaction(userDao);
        }
    }

    @Override
    public Set<String> register(PharmacyUser user) throws ServiceException{
        try{
            Set<String> errors = formErrorSet(user);
            if(errors.isEmpty()){
                UserDao userDao = new UserDao();
                try{ //TODO see try/catch Spring JDBC Template
                    TransactionHelper.beginTransaction(userDao);
                    int result = userDao.create(user);
                    if(result != 1){
                        throw new ServiceException("Could not register user.");
                    }
                    TransactionHelper.commit(userDao);
                } catch (DaoException e){
                    try{
                        logger.error("Could not register user. Roll back", e);
                        TransactionHelper.rollBack(userDao);
                    } catch (DaoException daoExc){
                        logger.error("Could not roll back.", daoExc);
                    }
                    throw new ServiceException();
                } finally {
                    TransactionHelper.endTransaction(userDao);
                    logger.info("End Transaction with user " + user.toString());
                }
            }
            return errors;
        } catch (DaoException e){
            logger.error(e.getMessage());
            throw new ServiceException(e);
        }
    }

    private Set<String> formErrorSet(PharmacyUser user) throws DaoException{
        Set<String> errors = new HashSet<>();
        UserDao userDao = new UserDao();
        try{
            TransactionHelper.beginTransaction(userDao);
            List<PharmacyUser> userList = userDao.findEntity(user.getMail());
            if(!userList.isEmpty()){
                errors.add("User with this e-mail address already exists");
            }
        } finally {
            TransactionHelper.endTransaction(userDao);
        }
        boolean nameIsCorrect = PharmacyValidator.userNameIsCorrect(user.getName());
        boolean mailIsCorrect = PharmacyValidator.mailIsCorrect(user.getMail());
        boolean passwordIsCorrect = PharmacyValidator.passwordIsCorrect(user.getPassword());
        if (!nameIsCorrect) {
            errors.add("Name must contain only letters (at least 2).");
        }
        if (!mailIsCorrect) {
            errors.add("Check your e-mail address! It must be like ivan@gmail.com");
        }
        if (!passwordIsCorrect) {
            errors.add("Password must contain only word symbols (at least 5).");
        }
        return errors;
    }
}
