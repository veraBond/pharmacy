package com.bandarovich.pharmacy.pool;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Properties;

/**
 * The Class PoolManager.
 */
class PoolManager {
    
    /** The Constant logger. */
    private static final Logger logger = LogManager.getLogger(PoolManager.class);
    
    /** The Constant PROPERTY_PATH. */
    private static final String PROPERTY_PATH = "/dao/dataBase.properties";
    
    /** The Constant POOL_SIZE. */
    private static final String POOL_SIZE = "poolSize";
    
    /** The Constant URL. */
    private static final String URL = "url";
    
    /** The properties. */
    private Properties properties;

    /**
     * Instantiates a new pool manager.
     */
    PoolManager(){
        properties = new Properties();
        try {
            properties.load(this.getClass().getResourceAsStream(PROPERTY_PATH));
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            logger.info("MySQL Driver has been registered.");
        } catch ( IOException | SQLException e){
            throw new RuntimeException(e);
        }
    }

    /**
     * Take pool size.
     *
     * @return the int
     */
    int takePoolSize(){
        return Integer.parseInt(properties.getProperty(POOL_SIZE));
    }

    /**
     * Take connection.
     *
     * @return the connection
     * @throws PoolException the pool exception
     */
    Connection takeConnection() throws PoolException {
        try {
            return DriverManager.getConnection(properties.getProperty(URL), properties);
        } catch (SQLException e){
            throw new PoolException("Could not get connection with database", e);
        }
    }

    /**
     * Deregister drivers.
     */
    void deregisterDrivers() {
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            Driver driver = drivers.nextElement();
            try {
                DriverManager.deregisterDriver(driver);
            } catch (SQLException e){
                logger.error("Could not deregister driver " + driver, e);
            }
        }
    }
}
