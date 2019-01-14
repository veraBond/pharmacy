package com.bandarovich.pharmacy.dao;

import com.bandarovich.pharmacy.entity.Pharmacy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public abstract class PharmacyDao <K, T extends Pharmacy> {
    final static Logger logger = LogManager.getLogger();
    protected Connection connection;
    public abstract Optional<T> findEntity(K id) throws DaoException;
    public abstract List<T> findAll() throws DaoException;
    public abstract int create(T entity) throws DaoException;
    public abstract int update(T entity) throws DaoException;
    public abstract int delete(K id) throws DaoException;
    public abstract int findMaxId() throws DaoException;

    void setConnection(Connection connection){
        this.connection = connection;
    }

    Connection getConnection(){
        return connection;
    }
}
