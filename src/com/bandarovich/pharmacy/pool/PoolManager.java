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

public class PoolManager {
    private final static Logger logger = LogManager.getLogger(PoolManager.class);
    private final static String PROPERTY_PATH = "/property/dataBase.properties";
    private Properties properties;
    private final static String POOL_SIZE = "poolSize";
    private final static String URL = "url";

    PoolManager(){
        properties = new Properties();
        try {
            properties.load(this.getClass().getResourceAsStream(PROPERTY_PATH));
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            logger.info("MySQL Driver has been registered.");
        } catch ( IOException | SQLException e){
            logger.error("Pool Manager init error", e);
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
