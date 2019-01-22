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

class PoolManager {
    private static final Logger logger = LogManager.getLogger(PoolManager.class);
    private static final String PROPERTY_PATH = "/dao/dataBase.properties";
    private static final String POOL_SIZE = "poolSize";
    private static final String URL = "url";
    private Properties properties;

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

    int takePoolSize(){
        return Integer.parseInt(properties.getProperty(POOL_SIZE));
    }

    Connection takeConnection() throws PoolException {
        try {
            return DriverManager.getConnection(properties.getProperty(URL), properties);
        } catch (SQLException e){
            throw new PoolException("Could not get connection with database", e);
        }
    }

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
