package com.bandarovich.pharmacy.pool;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

/**
 * The Class ConnectionPool.
 */
public class ConnectionPool {
    
    /** The Constant logger. */
    private static final Logger logger = LogManager.getLogger();
    
    /** The instance. */
    private static ConnectionPool instance;
    
    /** The lock. */
    private static ReentrantLock lock = new ReentrantLock();
    
    /** The is create. */
    private static AtomicBoolean isCreate = new AtomicBoolean(false);
    
    /** The available connections. */
    private BlockingQueue<ProxyConnection> availableConnections;
    
    /** The used connections. */
    private BlockingQueue<ProxyConnection> usedConnections;
    
    /** The size. */
    private int size;
    
    /** The pool manager. */
    private PoolManager poolManager;

    /**
     * Gets the single instance of ConnectionPool.
     *
     * @return single instance of ConnectionPool
     */
    public static ConnectionPool getInstance(){
        if(!isCreate.get()){
            try{
                lock.lock();
                if(instance == null){
                    instance = new ConnectionPool();
                    isCreate.set(true);
                }
            } finally {
                lock.unlock();
            }
        }
        return instance;
    }

    /**
     * Instantiates a new connection pool.
     */
    private ConnectionPool(){
        poolManager = new PoolManager();
        size = poolManager.takePoolSize();
        init();
    }

    /**
     * Inits the.
     */
    private void init() {
        availableConnections = new LinkedBlockingQueue<>(size);
        usedConnections = new ArrayBlockingQueue<>(size);
        for(int i = 0; i < size; i++) {
            try{
                Connection connection = poolManager.takeConnection();
                ProxyConnection proxyConnection = new ProxyConnection(connection);
                availableConnections.put(proxyConnection);
                logger.info("Connection " + i + " has been put to the pool.");
            } catch (PoolException e){
                logger.error("Could not get connection " + i + " with database.", e);
            } catch (InterruptedException e){
                logger.error("Connection interrupted.", e);
                Thread.currentThread().interrupt();
            }
        }
        if(availableConnections.isEmpty()){
           throw new RuntimeException("Could not connect to database");
        }
        size = availableConnections.size();
    }

    /**
     * Take connection.
     *
     * @return the connection
     * @throws PoolException the pool exception
     */
    public Connection takeConnection() throws  PoolException{
        try{
            ProxyConnection connection = availableConnections.take();
            usedConnections.put(connection);
            return connection;
        } catch (InterruptedException e){
            Thread.currentThread().interrupt();
            throw new PoolException("Could not take connection - interrupted.", e);
        }
    }

    /**
     * Release connection.
     *
     * @param connection the connection
     * @throws SQLException the SQL exception
     */
    void releaseConnection(Connection connection) throws SQLException {
        if (connection.getClass() != ProxyConnection.class){
            throw new SQLException("Not appropriate connection:" + connection.getClass());
        }
        ProxyConnection proxyConnection = (ProxyConnection)connection;
        try{
            connection.setAutoCommit(true);
            usedConnections.remove(proxyConnection);
            availableConnections.put(proxyConnection);
        } catch (SQLException e){
            logger.error("Could not release connection to the pool. ", e);
            addNewConnection();
        } catch (InterruptedException e){
            logger.error("Could not put connection back to the pool - interrupted.", e);
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Close pool.
     */
    public void closePool() {
        for(int i = 0; i < size; i++){
            try {
                ProxyConnection connection = availableConnections.take();
                connection.closeConnection();
                logger.info("Connection " + i + " is closed.");
            } catch (InterruptedException | SQLException e){
                logger.error("Error in closing connection " + i, e);
            }
        }
        poolManager.deregisterDrivers();
    }

    /**
     * Adds the new connection.
     */
    private void addNewConnection(){
        try{
            Connection connection = poolManager.takeConnection();
            ProxyConnection proxyConnection = new ProxyConnection(connection);
            availableConnections.put(proxyConnection);
            logger.info("Connection has been put to the pool.");
        } catch (PoolException poolExc){
            logger.error("Could not put new connection to the pool. ", poolExc);
        } catch (InterruptedException intExc){
            logger.error("Could not put connection back to the pool - interrupted.", intExc);
            Thread.currentThread().interrupt();
        }
    }
}
