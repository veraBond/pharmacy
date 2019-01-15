package com.bandarovich.pharmacy.dao.impl;

import com.bandarovich.pharmacy.dao.DaoException;
import com.bandarovich.pharmacy.dao.PharmacyDao;
import com.bandarovich.pharmacy.dao.UserDao;
import com.bandarovich.pharmacy.entity.PharmacyPosition;
import com.bandarovich.pharmacy.entity.PharmacyUser;
import com.bandarovich.pharmacy.util.PasswordCoding;

import java.sql.*;
import java.util.List;
import java.util.Optional;

public class UserDaoImpl extends PharmacyDao<String, PharmacyUser> implements UserDao {
    private final static String CREATE =
            "INSERT INTO users(position, userName, mail, password) VALUES (?, ?, ?, ?)";
    private final static String FIND_ENTITY =
            "SELECT position, userName, password FROM users WHERE mail = ?";
    private final static String FIND_USER_BY_MAIL_PASSWORD =
            "SELECT position, userName FROM users WHERE mail = ? AND password = ?";
    private final static String FIND_USER_ID =
            "SELECT userId FROM users WHERE mail = ?";

    private final static String USER_ID = "userId";
    private final static String POSITION = "position";
    private final static String USER_NAME = "userName";
    private final static String PASSWORD = "password";

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
            throw new DaoException(e);
        }
    }

    @Override
    public List<PharmacyUser> findAll() {
        return null;
    }

    @Override
    public int create(PharmacyUser user)throws DaoException {
        try(PreparedStatement preparedStatement = connection.prepareStatement(CREATE)){
            preparedStatement.setString(1, user.getPosition().name());
            preparedStatement.setString(2, user.getName());
            preparedStatement.setString(3, user.getMail());
            preparedStatement.setString(4, PasswordCoding.codePassword(user.getPassword()));
            return preparedStatement.executeUpdate();
        } catch (SQLException e){

            throw new DaoException(e);
        }
    }

    @Override
    public int update(PharmacyUser entity) throws DaoException {
        return 0;
    }

    @Override
    public int delete(String mail) {
        return 0;
    }

    @Override
    public int findMaxId() throws DaoException {
        return 0;
    }

    public Optional<PharmacyUser> findUser(String mail, String password) throws DaoException{
        try(PreparedStatement preparedStatement = connection.prepareStatement(FIND_USER_BY_MAIL_PASSWORD)){
            preparedStatement.setString(1, mail);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                PharmacyPosition position = PharmacyPosition.valueOf(resultSet.getString(POSITION).toUpperCase());
                String userName = resultSet.getString(USER_NAME);
                return Optional.of(new PharmacyUser(position, userName, mail, password));
            } else {
                return Optional.empty();
            }
        } catch (SQLException e){
            throw new DaoException(e);
        }
    }

    public Optional<Integer> findUserId(String mail) throws DaoException{
        try(PreparedStatement preparedStatement = connection.prepareStatement(FIND_USER_ID)){
            preparedStatement.setString(1, mail);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return Optional.of(resultSet.getInt(USER_ID));
            } else {
                return Optional.empty();
            }
        } catch (SQLException e){
            throw new DaoException(e);
        }
    }
}
