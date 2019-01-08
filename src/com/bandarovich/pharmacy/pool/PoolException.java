package com.bandarovich.pharmacy.pool;

public class PoolException extends Exception {
    public PoolException() {
        super();
    }

    public PoolException(String message) {
        super(message);
    }

    public PoolException(String message, Throwable cause) {
        super(message, cause);
    }

    public PoolException(Throwable cause) {
        super(cause);
    }
}
