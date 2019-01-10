package com.bandarovich.pharmacy.pool;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

public class ConnectionPool {
    private BlockingQueue<ProxyConnection> availableConnections;
    private BlockingQueue<ProxyConnection> usedConnections;
    private int size;
    private final static Logger logger = LogManager.getLogger();
    private PoolManager poolManager;

    private static ConnectionPool instance;
    private static ReentrantLock lock = new ReentrantLock();
    private static AtomicBoolean isCreate = new AtomicBoolean(false);

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

    private ConnectionPool(){
        poolManager = new PoolManager();
        size = poolManager.takePoolSize();
        init();
    }

    private void init() {
        availableConnections = new LinkedBlockingQueue<>(size);
        usedConnections = new LinkedBlockingQueue<>();
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

//timer task - время от времени запускается и контролирует целостность пула.

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

    public void releaseConnection(Connection connection) throws SQLException {
        if (connection.getClass() != ProxyConnection.class){
            throw new SQLException("Not appropriate connection:" + connection.getClass());
        }
        ProxyConnection proxyConnection = (ProxyConnection)connection;
        try{
            connection.setAutoCommit(true);
            usedConnections.remove(proxyConnection);
            availableConnections.put(proxyConnection);
            logger.info("Connection released to the connection pool.");
        } catch (SQLException e){
            logger.error("Could not release connection to the pool. ", e);
        } catch (InterruptedException e){
            logger.error("Could not put connection back to the pool - interrupted.", e);
            Thread.currentThread().interrupt();
        }
    }

    public void closePool() {
        for(int i = 0; i < size; i++){
            try {
                ProxyConnection connection = availableConnections.take();
                connection.closeConnection();
                logger.info("Connection " + i + " in connectionPool is closed.");
            } catch (InterruptedException | SQLException e){
                logger.error("Error in closing connection " + i + " in connectionPool.", e);
            }
        }
        poolManager.deregisterDrivers();
    }
}
