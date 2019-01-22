package com.bandarovich.pharmacy.pool;

/**
 * The Class PoolException.
 */
public class PoolException extends Exception {
    
    /**
     * Instantiates a new pool exception.
     */
    public PoolException() {
        super();
    }

    /**
     * Instantiates a new pool exception.
     *
     * @param message the message
     */
    public PoolException(String message) {
        super(message);
    }

    /**
     * Instantiates a new pool exception.
     *
     * @param message the message
     * @param cause the cause
     */
    public PoolException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Instantiates a new pool exception.
     *
     * @param cause the cause
     */
    public PoolException(Throwable cause) {
        super(cause);
    }
}
