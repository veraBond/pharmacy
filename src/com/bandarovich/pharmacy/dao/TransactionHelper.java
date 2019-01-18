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

    public static void beginTransaction(PharmacyDao dao, PharmacyDao ... daos) throws DaoException{
        beginTransactionDao(dao);
        for(PharmacyDao d: daos){
            beginTransactionDao(d);
        }
    }

    public static void endTransaction(PharmacyDao dao, PharmacyDao ... daos) {
        endTransactionDao(dao);
        for(PharmacyDao d: daos){
            endTransactionDao(d);
        }
    }

    public static void commit(PharmacyDao dao, PharmacyDao ... daos) throws DaoException{
        commitDao(dao);
        for(PharmacyDao d: daos){
            commitDao(d);
        }
    }

    public static void rollBack(PharmacyDao dao, PharmacyDao ... daos) throws DaoException{
        rollBackDao(dao);
        for(PharmacyDao d: daos){
            rollBackDao(d);
        }
    }

    private static void beginTransactionDao(PharmacyDao dao) throws DaoException{
        try{
            Connection connection = ConnectionPool.getInstance().takeConnection();
            dao.setConnection(connection);
            connection.setAutoCommit(false);
        } catch (PoolException | SQLException e){
            throw new DaoException(e);
        }
    }

    private static void endTransactionDao(PharmacyDao dao){
        try{
            dao.getConnection().setAutoCommit(true);
            dao.getConnection().close();
        } catch (SQLException e){
            logger.error("Could not end transaction with " + dao, e);
        }
    }

    private static void commitDao(PharmacyDao dao) throws DaoException{
        try{
            dao.getConnection().commit();
        } catch (SQLException e){
            rollBackDao(dao);
            throw new DaoException(e);
        }
    }

    private static void rollBackDao(PharmacyDao dao) throws DaoException{
        try{
            dao.getConnection().rollback();
        } catch (SQLException e){
            throw new DaoException(e);
        }
    }
}
