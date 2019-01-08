package com.bandarovich.pharmacy.dao.impl;

import com.bandarovich.pharmacy.dao.DaoException;
import com.bandarovich.pharmacy.dao.PharmacyDao;
import com.bandarovich.pharmacy.entity.Pharmacy;
import com.bandarovich.pharmacy.entity.PharmacyPosition;
import com.bandarovich.pharmacy.entity.PharmacyUser;
import com.bandarovich.pharmacy.util.Coder;
import org.apache.commons.codec.digest.DigestUtils;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class UserDao extends PharmacyDao<String, PharmacyUser> {
    private final static String CREATE =
            "INSERT INTO users(position, userName, mail, password) VALUES (?, ?, ?, ?)";
    private final static String FIND_USER =
            "SELECT position, userName, password FROM users WHERE mail = ?";
    private final static String POSITION = "position";
    private final static String USER_NAME = "userName";
    private final static String PASSWORD = "password";

    @Override
    public List<Pharmacy> findAll() {
        return null;
    }

    //TODO: rewrite method with object PharmacyUser return. NULL if not found.
    @Override
    public PharmacyUser findEntity(String mail) throws DaoException{
        try(PreparedStatement preparedStatement = connection.prepareStatement(FIND_USER)){
            preparedStatement.setString(1, mail);
            ResultSet resultSet = preparedStatement.executeQuery();
            PharmacyUser user = null;
            if(resultSet.next()){
                PharmacyPosition position = PharmacyPosition.valueOf(resultSet.getString(POSITION).toUpperCase());
                String userName = resultSet.getString(USER_NAME);
                String password = resultSet.getString(PASSWORD);
                user = new PharmacyUser(position, userName, mail, password);

            }
            return user;
        } catch (SQLException e){
            logger.error("Error in findEntity method: " + e.getMessage());
            throw new DaoException(e);
        }
    }

    @Override
    public boolean delete(String mail) {
        return false;
    }

//TODO try with resource
    @Override
    public int create(PharmacyUser user)throws DaoException {
        try(PreparedStatement preparedStatement = connection.prepareStatement(CREATE)){
            preparedStatement.setString(1, user.getPosition().name());
            preparedStatement.setString(2, user.getName());
            preparedStatement.setString(3, user.getMail());
            preparedStatement.setString(4, Coder.codePassword(user.getPassword()));
            return preparedStatement.executeUpdate();
        } catch (SQLException e){
            throw new DaoException(e);
        }
    }


}
