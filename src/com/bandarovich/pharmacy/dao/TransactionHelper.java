package com.bandarovich.pharmacy.dao;

import com.bandarovich.pharmacy.pool.ConnectionPool;
import com.bandarovich.pharmacy.pool.PoolException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

public class TransactionHelper {
    private final static Logger logger = LogManager.getLogger();

    private TransactionHelper(){}

    public static void beginTransaction(PharmacyDao dao) throws DaoException{
        try{
            Connection connection = ConnectionPool.getInstance().takeConnection();
            dao.setConnection(connection);
            connection.setAutoCommit(false);
        } catch (PoolException | SQLException e){
            logger.error("Error in beginning transaction: " + e.getMessage());
           throw new DaoException();
        }
    }

    public static void endTransaction(PharmacyDao dao) {
        try{
            dao.getConnection().setAutoCommit(true);
            dao.getConnection().close();
        } catch (SQLException e){
            logger.error("Could not end transaction with " + dao + ": " + e.getMessage());
        }
    }

    public static void commit(PharmacyDao dao) throws DaoException{
        try{
            dao.getConnection().commit();
        } catch (SQLException e){
            rollBack(dao);
            throw new DaoException("Could not commit action.", e);
        }
    }

    public static void rollBack(PharmacyDao dao) throws DaoException{
        try{
            dao.getConnection().rollback();
        } catch (SQLException e){
            throw new DaoException("Could not roll back.", e);
        }
    }
}
