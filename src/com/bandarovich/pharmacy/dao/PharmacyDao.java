package com.bandarovich.pharmacy.dao;

import com.bandarovich.pharmacy.entity.Pharmacy;
import com.mysql.cj.xdevapi.Collection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public abstract class PharmacyDao <K, T extends Pharmacy> {

    protected final static Logger logger = LogManager.getLogger();
    protected Connection connection;

    protected abstract List<Pharmacy> findAll() throws DaoException;
    protected abstract T findEntity(K id) throws DaoException;
    protected abstract boolean delete(K id) throws DaoException;
    protected abstract int create(T entity) throws DaoException;

    void setConnection(Connection connection){
        this.connection = connection;
    }

    Connection getConnection(){
        return connection;
    }
}
