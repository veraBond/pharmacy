package com.bandarovich.pharmacy.dao.impl;

import com.bandarovich.pharmacy.dao.DaoException;
import com.bandarovich.pharmacy.dao.PharmacyDao;
import com.bandarovich.pharmacy.entity.PharmacyPosition;
import com.bandarovich.pharmacy.entity.PharmacyUser;
import com.bandarovich.pharmacy.util.Coder;

import java.sql.*;
import java.util.List;
import java.util.Optional;

public class UserDao extends PharmacyDao<String, PharmacyUser> {
    private final static String CREATE =
            "INSERT INTO users(position, userName, mail, password) VALUES (?, ?, ?, ?)";
    private final static String FIND_ENTITY =
            "SELECT position, userName, password FROM users WHERE mail = ?";
    private final static String FIND_NAME =
            "SELECT userName FROM users WHERE position = ? AND mail = ? AND password = ?";
    private final static String POSITION = "position";
    private final static String USER_NAME = "userName";
    private final static String PASSWORD = "password";

    @Override
    public List<PharmacyUser> findAll() {
        return null;
    }

    @Override
    public Optional<PharmacyUser> findEntity(String mail) throws DaoException{
        try(PreparedStatement preparedStatement = connection.prepareStatement(FIND_ENTITY)){
            preparedStatement.setString(1, mail);
            ResultSet resultSet = preparedStatement.executeQuery();
            PharmacyUser user = null;
            if(resultSet.next()){
                PharmacyPosition position = PharmacyPosition.valueOf(resultSet.getString(POSITION).toUpperCase());
                String userName = resultSet.getString(USER_NAME);
                String password = resultSet.getString(PASSWORD);
                user = new PharmacyUser(position, userName, mail, password);
                return Optional.of(user);
            } else {
                return Optional.empty();
            }
        } catch (SQLException e){
            logger.error("Error in findEntity method: " + e.getMessage());
            throw new DaoException(e);
        }
    }

    @Override
    public boolean delete(String mail) {
        return false;
    }
//TODO coding in dao or in service
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

    public Optional<String> findUserName(PharmacyPosition position, String mail, String password) throws DaoException{
        try(PreparedStatement preparedStatement = connection.prepareStatement(FIND_NAME)){
            preparedStatement.setString(1, position.name());
            preparedStatement.setString(2, mail);
            preparedStatement.setString(3, Coder.codePassword(password));
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return Optional.of(resultSet.getString(USER_NAME));
            } else {
                return Optional.empty();
            }
        } catch (SQLException e){
            logger.error("Error in findUser method: " + e.getMessage());
            throw new DaoException(e);
        }
    }
}
