package com.bandarovich.pharmacy.dao;

import com.bandarovich.pharmacy.pool.ConnectionPool;
import com.bandarovich.pharmacy.pool.PoolException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * The Class TransactionHelper.
 */
public class TransactionHelper {
    
    /** The Constant logger. */
    private static final Logger logger = LogManager.getLogger();

    /**
     * Instantiates a new transaction helper.
     */
    private TransactionHelper(){}

    /**
     * Begin transaction.
     *
     * @param dao the dao
     * @param daos the daos
     * @throws DaoException the dao exception
     */
    public static void beginTransaction(PharmacyDao dao, PharmacyDao ... daos) throws DaoException{
        beginTransactionDao(dao);
        for(PharmacyDao d: daos){
            beginTransactionDao(d);
        }
    }

    /**
     * End transaction.
     *
     * @param dao the dao
     * @param daos the daos
     */
    public static void endTransaction(PharmacyDao dao, PharmacyDao ... daos) {
        endTransactionDao(dao);
        for(PharmacyDao d: daos){
            endTransactionDao(d);
        }
    }

    /**
     * Commit.
     *
     * @param dao the dao
     * @param daos the daos
     * @throws DaoException the dao exception
     */
    public static void commit(PharmacyDao dao, PharmacyDao ... daos) throws DaoException{
        commitDao(dao);
        for(PharmacyDao d: daos){
            commitDao(d);
        }
    }

    /**
     * Roll back.
     *
     * @param dao the dao
     * @param daos the daos
     * @throws DaoException the dao exception
     */
    public static void rollBack(PharmacyDao dao, PharmacyDao ... daos) throws DaoException{
        rollBackDao(dao);
        for(PharmacyDao d: daos){
            rollBackDao(d);
        }
    }

    /**
     * Begin transaction dao.
     *
     * @param dao the dao
     * @throws DaoException the dao exception
     */
    private static void beginTransactionDao(PharmacyDao dao) throws DaoException{
        try{
            Connection connection = ConnectionPool.getInstance().takeConnection();
            dao.setConnection(connection);
            connection.setAutoCommit(false);
        } catch (PoolException | SQLException e){
            throw new DaoException(e);
        }
    }

    /**
     * End transaction dao.
     *
     * @param dao the dao
     */
    private static void endTransactionDao(PharmacyDao dao){
        try{
            dao.getConnection().setAutoCommit(true);
            dao.getConnection().close();
        } catch (SQLException e){
            logger.error("Could not end transaction with " + dao, e);
        }
    }

    /**
     * Commit dao.
     *
     * @param dao the dao
     * @throws DaoException the dao exception
     */
    private static void commitDao(PharmacyDao dao) throws DaoException{
        try{
            dao.getConnection().commit();
        } catch (SQLException e){
            rollBackDao(dao);
            throw new DaoException(e);
        }
    }

    /**
     * Roll back dao.
     *
     * @param dao the dao
     * @throws DaoException the dao exception
     */
    private static void rollBackDao(PharmacyDao dao) throws DaoException{
        try{
            dao.getConnection().rollback();
        } catch (SQLException e){
            throw new DaoException(e);
        }
    }
}
