package com.bandarovich.pharmacy.dao.impl;

import com.bandarovich.pharmacy.dao.DaoException;
import com.bandarovich.pharmacy.dao.PharmacyDao;
import com.bandarovich.pharmacy.dao.UserDao;
import com.bandarovich.pharmacy.entity.PharmacyPosition;
import com.bandarovich.pharmacy.entity.PharmacyUser;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * The Class UserDaoImpl.
 */
public class UserDaoImpl extends PharmacyDao<String, PharmacyUser> implements UserDao {
    
    /** The Constant FIND_ENTITY. */
    private static final String FIND_ENTITY =
            "SELECT position, userName, mail, password FROM users WHERE mail = ?";
    
    /** The Constant FIND_ALL. */
    private static final String FIND_ALL =
            "SELECT position, userName, mail, password FROM users";
    
    /** The Constant CREATE. */
    private static final String CREATE =
            "INSERT INTO users(position, userName, mail, password) VALUES (?, ?, ?, ?)";
    
    /** The Constant UPDATE. */
    private static final String UPDATE =
            "UPDATE users SET position = ?, userName = ?, password = ? WHERE mail = ?";
    
    /** The Constant DELETE. */
    private static final String DELETE =
            "UPDATE users SET isDeleted = TRUE WHERE mail = ?";
    
    /** The Constant FIND_MAX_ID. */
    private static final String FIND_MAX_ID =
            "SELECT MAX(userId) FROM users";
    
    /** The Constant FIND_USER_BY_MAIL_PASSWORD. */
    private static final String FIND_USER_BY_MAIL_PASSWORD =
            "SELECT position, userName FROM users WHERE mail = ? AND password = ?";
    
    /** The Constant FIND_USER_ID. */
    private static final String FIND_USER_ID =
            "SELECT userId FROM users WHERE mail = ?";

    /** The Constant USER_ID. */
    private static final String USER_ID = "userId";
    
    /** The Constant POSITION. */
    private static final String POSITION = "position";
    
    /** The Constant USER_NAME. */
    private static final String USER_NAME = "userName";
    
    /** The Constant MAIL. */
    private static final String MAIL = "mail";
    
    /** The Constant PASSWORD. */
    private static final String PASSWORD = "password";

    /* (non-Javadoc)
     * @see com.bandarovich.pharmacy.dao.PharmacyDao#findEntity(java.lang.Object)
     */
    @Override
    public Optional<PharmacyUser> findEntity(String mail) throws DaoException{
        try(PreparedStatement preparedStatement = connection.prepareStatement(FIND_ENTITY)){
            preparedStatement.setString(1, mail);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return Optional.of(buildUser(resultSet));
            } else {
                return Optional.empty();
            }
        } catch (SQLException e){
            throw new DaoException(e);
        }
    }

    /* (non-Javadoc)
     * @see com.bandarovich.pharmacy.dao.PharmacyDao#findAll()
     */
    @Override
    public List<PharmacyUser> findAll() throws DaoException{
        try(PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL)){
            ResultSet resultSet = preparedStatement.executeQuery();
            List<PharmacyUser> users = new LinkedList<>();
            while(resultSet.next()){
                users.add(buildUser(resultSet));
            }
            return users;
        } catch (SQLException e){
            throw new DaoException(e);
        }
    }

    /* (non-Javadoc)
     * @see com.bandarovich.pharmacy.dao.PharmacyDao#create(com.bandarovich.pharmacy.entity.Pharmacy)
     */
    @Override
    public int create(PharmacyUser user)throws DaoException {
        try(PreparedStatement preparedStatement = connection.prepareStatement(CREATE)){
            preparedStatement.setString(1, user.getPosition().name());
            preparedStatement.setString(2, user.getName());
            preparedStatement.setString(3, user.getMail());
            preparedStatement.setString(4, user.getPassword());
            return preparedStatement.executeUpdate();
        } catch (SQLException e){

            throw new DaoException(e);
        }
    }

    /* (non-Javadoc)
     * @see com.bandarovich.pharmacy.dao.PharmacyDao#update(com.bandarovich.pharmacy.entity.Pharmacy)
     */
    @Override
    public int update(PharmacyUser user) throws DaoException {
        try(PreparedStatement preparedStatement = connection.prepareStatement(UPDATE)){
            preparedStatement.setString(1, user.getPosition().name());
            preparedStatement.setString(2, user.getName());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setString(4, user.getMail());
            return preparedStatement.executeUpdate();
        } catch (SQLException e){
            throw new DaoException(e);
        }
    }

    /* (non-Javadoc)
     * @see com.bandarovich.pharmacy.dao.PharmacyDao#delete(java.lang.Object)
     */
    @Override
    public int delete(String mail) throws DaoException{
        try(PreparedStatement preparedStatement = connection.prepareStatement(DELETE)){
            preparedStatement.setString(1, mail);
            return preparedStatement.executeUpdate();
        } catch (SQLException e){
            throw new DaoException(e);
        }
    }

    /* (non-Javadoc)
     * @see com.bandarovich.pharmacy.dao.PharmacyDao#findMaxId()
     */
    @Override
    public int findMaxId() throws DaoException {
        try(PreparedStatement preparedStatement = connection.prepareStatement(FIND_MAX_ID)){
            ResultSet result = preparedStatement.executeQuery();
            if(result.next()){
                return result.getInt(USER_ID);
            }
            return 0;
        } catch (SQLException e){
            throw new DaoException(e);
        }
    }

    /* (non-Javadoc)
     * @see com.bandarovich.pharmacy.dao.UserDao#findUser(java.lang.String, java.lang.String)
     */
    @Override
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

    /* (non-Javadoc)
     * @see com.bandarovich.pharmacy.dao.UserDao#findUserId(java.lang.String)
     */
    @Override
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

    /**
     * Builds the user.
     *
     * @param resultSet the result set
     * @return the pharmacy user
     * @throws SQLException the SQL exception
     */
    private PharmacyUser buildUser(ResultSet resultSet) throws SQLException{
        PharmacyPosition position = PharmacyPosition.valueOf(resultSet.getString(POSITION).toUpperCase());
        String userName = resultSet.getString(USER_NAME);
        String password = resultSet.getString(PASSWORD);
        String mail = resultSet.getString(MAIL);
        return new PharmacyUser(position, userName, mail, password);
    }
}
