package com.bandarovich.pharmacy.service.impl;

import com.bandarovich.pharmacy.command.JspAttribute;
import com.bandarovich.pharmacy.dao.DaoException;
import com.bandarovich.pharmacy.dao.TransactionHelper;
import com.bandarovich.pharmacy.dao.impl.UserDaoImpl;
import com.bandarovich.pharmacy.entity.PharmacyUser;
import com.bandarovich.pharmacy.service.ServiceException;
import com.bandarovich.pharmacy.service.UserService;
import com.bandarovich.pharmacy.util.PasswordCoding;
import com.bandarovich.pharmacy.util.UserValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * The Class UserServiceImpl.
 */
public class UserServiceImpl implements UserService {
    
    /** The Constant logger. */
    private static final Logger logger = LogManager.getLogger();
    
    /** The Constant INSTANCE. */
    public static final UserService INSTANCE = new UserServiceImpl();
    
    /**
     * Instantiates a new user service impl.
     */
    private UserServiceImpl(){}

    /* (non-Javadoc)
     * @see com.bandarovich.pharmacy.service.UserService#findUser(java.lang.String, java.lang.String)
     */
    @Override
    public Optional<PharmacyUser> findUser(String mail, String password) throws ServiceException{
        UserDaoImpl userDao = new UserDaoImpl();
        try{
            TransactionHelper.beginTransaction(userDao);
            return userDao.findUser(mail, PasswordCoding.codePassword(password));
        } catch (DaoException e){
            throw new ServiceException(e);
        } finally {
            TransactionHelper.endTransaction(userDao);
        }
    }

    /* (non-Javadoc)
     * @see com.bandarovich.pharmacy.service.UserService#register(com.bandarovich.pharmacy.entity.PharmacyUser)
     */
    @Override
    public List<String> register(PharmacyUser user) throws ServiceException{
        List<String> errors = new LinkedList<>();
        boolean existUser = findUser(user.getMail());
        if(existUser){
            errors.add(JspAttribute.REGISTRATION_USER_EXISTS);
        }
        errors.addAll(formErrorList(user));
        if(errors.isEmpty()){
            UserDaoImpl userDao = new UserDaoImpl();
            try{
                TransactionHelper.beginTransaction(userDao);
                String password = PasswordCoding.codePassword(user.getPassword());
                user.setPassword(password);
                int result = userDao.create(user);
                if(result != 1){
                    TransactionHelper.rollBack(userDao);
                    throw new ServiceException("Could not register user.");
                }
                TransactionHelper.commit(userDao);
            } catch (DaoException e){
                try{
                    TransactionHelper.rollBack(userDao);
                } catch (DaoException daoExc){
                    logger.error("Could not roll back in register command", daoExc);
                }
                throw new ServiceException(e);
            } finally {
                TransactionHelper.endTransaction(userDao);
            }
        }
        return errors;
    }

    /* (non-Javadoc)
     * @see com.bandarovich.pharmacy.service.UserService#findUser(java.lang.String)
     */
    public boolean findUser(String mail) throws ServiceException{
        UserDaoImpl userDao = new UserDaoImpl();
        try{
            TransactionHelper.beginTransaction(userDao);
            Optional<PharmacyUser> user = userDao.findEntity(mail);
            return user.isPresent();
        } catch (DaoException e){
            throw new ServiceException(e);
        } finally {
            TransactionHelper.endTransaction(userDao);
        }
    }

    /**
     * Form error list.
     *
     * @param user the user
     * @return the list
     */
    private List<String> formErrorList(PharmacyUser user){
        List<String> errors = new LinkedList<>();
        boolean nameIsCorrect = UserValidator.userNameIsCorrect(user.getName());
        boolean mailIsCorrect = UserValidator.mailIsCorrect(user.getMail());
        boolean passwordIsCorrect = UserValidator.passwordIsCorrect(user.getPassword());
        if (!nameIsCorrect) {
            errors.add(JspAttribute.INCORRECT_NAME_REGISTRATION);
        }
        if (!mailIsCorrect) {
            errors.add(JspAttribute.INCORRECT_MAIL_REGISTRATION);
        }
        if (!passwordIsCorrect) {
            errors.add(JspAttribute.INCORRECT_PASSWORD_REGISTRATION);
        }
        return errors;
    }
}
