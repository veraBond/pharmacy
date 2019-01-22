package com.bandarovich.pharmacy.pool;

import org.testng.Assert;
import org.testng.annotations.Test;


public class PoolManagerTest {

    @Test
    public void testTakePoolSize() {
        int expected = 10;
        int actual = new PoolManager().takePoolSize();
        Assert.assertEquals(expected, actual);
    }
}