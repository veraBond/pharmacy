package com.bandarovich.pharmacy.dao;

import com.bandarovich.pharmacy.entity.Pharmacy;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

/**
 * The Class PharmacyDao.
 *
 * @param <K> the key type
 * @param <T> the generic type
 */
public abstract class PharmacyDao <K, T extends Pharmacy> {
    
    /** The connection. */
    protected Connection connection;

    /**
     * Find entity.
     *
     * @param id the id
     * @return the optional
     * @throws DaoException the dao exception
     */
    public abstract Optional<T> findEntity(K id) throws DaoException;
    
    /**
     * Find all.
     *
     * @return the list
     * @throws DaoException the dao exception
     */
    public abstract List<T> findAll() throws DaoException;
    
    /**
     * Creates the.
     *
     * @param entity the entity
     * @return the int
     * @throws DaoException the dao exception
     */
    public abstract int create(T entity) throws DaoException;
    
    /**
     * Update.
     *
     * @param entity the entity
     * @return the int
     * @throws DaoException the dao exception
     */
    public abstract int update(T entity) throws DaoException;
    
    /**
     * Delete.
     *
     * @param id the id
     * @return the int
     * @throws DaoException the dao exception
     */
    public abstract int delete(K id) throws DaoException;
    
    /**
     * Find max id.
     *
     * @return the int
     * @throws DaoException the dao exception
     */
    public abstract int findMaxId() throws DaoException;

    /**
     * Sets the connection.
     *
     * @param connection the new connection
     */
    void setConnection(Connection connection){
        this.connection = connection;
    }

    /**
     * Gets the connection.
     *
     * @return the connection
     */
    Connection getConnection(){
        return connection;
    }
}
