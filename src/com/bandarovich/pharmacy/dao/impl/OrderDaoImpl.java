package com.bandarovich.pharmacy.dao.impl;

import com.bandarovich.pharmacy.dao.DaoException;
import com.bandarovich.pharmacy.dao.OrderDao;
import com.bandarovich.pharmacy.dao.PharmacyDao;
import com.bandarovich.pharmacy.entity.PharmacyOrder;
import com.bandarovich.pharmacy.service.impl.UserServiceImpl;
import com.bandarovich.pharmacy.util.InputDataService;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class OrderDaoImpl extends PharmacyDao<Integer, PharmacyOrder> implements OrderDao {
    private final static String CREATE =
            "INSERT INTO orders " +
                    "SET orderId = ?, " +
                    "clientId = (SELECT clients.userId FROM users AS clients WHERE clients.mail = ?), " +
                    "medicineId = ?, amount = ?, totalCost = ?";
    private final static String DELETE =
            "DELETE FROM orders WHERE orderId = ?";
    private final static String FIND_MAX_ID =
            "SELECT MAX(orderId) FROM orders";

    private final static String MAX_ORDER_ID = "MAX(orderId)";

    @Override
    public Optional<PharmacyOrder> findEntity(Integer id) throws DaoException {
        return null;
    }

    @Override
    public List<PharmacyOrder> findAll() throws DaoException {
        return null;
    }
//TODO can I call UserDao(MedicineDao) methods in this dao?
    @Override
    public int create(PharmacyOrder order) throws DaoException {
        try(PreparedStatement preparedStatement = connection.prepareStatement(CREATE)){
            preparedStatement.setInt(1, order.getOrderId());
            preparedStatement.setString(2, order.getClientMail());
            preparedStatement.setInt(3, order.getMedicineId());
            preparedStatement.setInt(4, order.getOrderAmount());
            preparedStatement.setDouble(5, order.getTotalCost());
            return preparedStatement.executeUpdate();
        } catch (SQLException e){
            throw new DaoException(e);
        }
    }

    @Override
    public int update(PharmacyOrder entity) throws DaoException {
        return 0;
    }

    @Override
    public int findMaxId() throws DaoException {
        try(PreparedStatement preparedStatement = connection.prepareStatement(FIND_MAX_ID)){
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next() ? resultSet.getInt(MAX_ORDER_ID) : 0;
        } catch (SQLException e){
            throw new DaoException(e);
        }
    }

    @Override
    public int delete(Integer id) throws DaoException {
        try(PreparedStatement preparedStatement = connection.prepareStatement(DELETE)){
            preparedStatement.setInt(1, id);
            return preparedStatement.executeUpdate();
        } catch (SQLException e){
            throw new DaoException(e);
        }
    }

}
