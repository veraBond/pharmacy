package com.bandarovich.pharmacy.dao.impl;

import com.bandarovich.pharmacy.dao.DaoException;
import com.bandarovich.pharmacy.dao.PharmacyDao;
import com.bandarovich.pharmacy.entity.PharmacyOrder;

import java.util.List;
import java.util.Optional;

public class OrderDao extends PharmacyDao<Integer, PharmacyOrder> {
    @Override
    protected List<PharmacyOrder> findAll() throws DaoException {
        return null;
    }

    @Override
    protected Optional<PharmacyOrder> findEntity(Integer id) throws DaoException {
        return null;
    }

    @Override
    protected boolean delete(Integer id) throws DaoException {
        return false;
    }

    @Override
    protected int create(PharmacyOrder entity) throws DaoException {
        return 0;
    }
}
