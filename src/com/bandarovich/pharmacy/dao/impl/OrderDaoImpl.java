package com.bandarovich.pharmacy.dao.impl;

import com.bandarovich.pharmacy.dao.DaoException;
import com.bandarovich.pharmacy.dao.OrderDao;
import com.bandarovich.pharmacy.dao.PharmacyDao;
import com.bandarovich.pharmacy.entity.PharmacyOrder;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * The Class OrderDaoImpl.
 */
public class OrderDaoImpl extends PharmacyDao<Integer, PharmacyOrder> implements OrderDao {
    
    /** The Constant FIND_ENTITY. */
    private static final String FIND_ENTITY =
            "SELECT orderId, mail, medicineId, amount, totalCost " +
                    "FROM orders INNER JOIN users ON (clientId = userId) " +
                    "WHERE orderId = ?";
    
    /** The Constant FIND_ALL. */
    private static final String FIND_ALL =
            "SELECT orderId, mail, medicineId, amount, totalCost " +
                    "FROM orders INNER JOIN users ON (clientId = userId) ORDER BY orderId";
    
    /** The Constant CREATE. */
    private static final String CREATE =
            "INSERT INTO orders " +
                    "SET orderId = ?, " +
                    "clientId = (SELECT clients.userId FROM users AS clients WHERE clients.mail = ?), " +
                    "medicineId = ?, amount = ?, totalCost = ?";
    
    /** The Constant UPDATE. */
    private static final String UPDATE =
            "UPDATE orders " +
                    "SET clientId = (SELECT clients.userId FROM users AS clients WHERE clients.mail = ?), " +
                    "medicineId = ?, amount = ?, totalCost = ? " +
                    "WHERE orderId = ?";
    
    /** The Constant DELETE. */
    private static final String DELETE =
            "DELETE FROM orders WHERE orderId = ?";
    
    /** The Constant FIND_MAX_ID. */
    private static final String FIND_MAX_ID =
            "SELECT MAX(orderId) FROM orders";

    /** The Constant MAX_ORDER_ID. */
    private static final String MAX_ORDER_ID = "MAX(orderId)";
    
    /** The Constant ORDER_ID. */
    private static final String ORDER_ID = "orderId";
    
    /** The Constant MAIL. */
    private static final String MAIL = "mail";
    
    /** The Constant MEDICINE_ID. */
    private static final String MEDICINE_ID = "medicineId";
    
    /** The Constant AMOUNT. */
    private static final String AMOUNT = "amount";
    
    /** The Constant TOTAL_COST. */
    private static final String TOTAL_COST = "totalCost";

    /* (non-Javadoc)
     * @see com.bandarovich.pharmacy.dao.PharmacyDao#findEntity(java.lang.Object)
     */
    @Override
    public Optional<PharmacyOrder> findEntity(Integer id) throws DaoException {
        try(PreparedStatement preparedStatement = connection.prepareStatement(FIND_ENTITY)){
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return Optional.of(buildOrder(resultSet));
            } else {
                return Optional.empty();
            }
        } catch (SQLException e){
            throw new DaoException(e);
        }
    }

    /* (non-Javadoc)
     * @see com.bandarovich.pharmacy.dao.PharmacyDao#findAll()
     */
    @Override
    public List<PharmacyOrder> findAll() throws DaoException {
        try(PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL)){
            ResultSet resultSet = preparedStatement.executeQuery();
            return buildOrderList(resultSet);
        } catch (SQLException e){
            throw new DaoException(e);
        }
    }

    /* (non-Javadoc)
     * @see com.bandarovich.pharmacy.dao.PharmacyDao#create(com.bandarovich.pharmacy.entity.Pharmacy)
     */
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

    /* (non-Javadoc)
     * @see com.bandarovich.pharmacy.dao.PharmacyDao#update(com.bandarovich.pharmacy.entity.Pharmacy)
     */
    @Override
    public int update(PharmacyOrder order) throws DaoException {
        try(PreparedStatement preparedStatement = connection.prepareStatement(UPDATE)){
            preparedStatement.setString(1, order.getClientMail());
            preparedStatement.setInt(2, order.getMedicineId());
            preparedStatement.setInt(3, order.getOrderAmount());
            preparedStatement.setDouble(4, order.getTotalCost());
            preparedStatement.setInt(5, order.getOrderId());
            return preparedStatement.executeUpdate();
        } catch (SQLException e){
            throw new DaoException(e);
        }
    }

    /* (non-Javadoc)
     * @see com.bandarovich.pharmacy.dao.PharmacyDao#delete(java.lang.Object)
     */
    @Override
    public int delete(Integer id) throws DaoException {
        try(PreparedStatement preparedStatement = connection.prepareStatement(DELETE)){
            preparedStatement.setInt(1, id);
            return preparedStatement.executeUpdate();
        } catch (SQLException e){
            throw new DaoException(e);
        }
    }

    /* (non-Javadoc)
     * @see com.bandarovich.pharmacy.dao.PharmacyDao#findMaxId()
     */
    @Override
    public int findMaxId() throws DaoException {
        try(PreparedStatement preparedStatement = connection.prepareStatement(FIND_MAX_ID)){
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next() ? resultSet.getInt(MAX_ORDER_ID) : 0;
        } catch (SQLException e){
            throw new DaoException(e);
        }
    }

    /**
     * Builds the order list.
     *
     * @param resultSet the result set
     * @return the list
     * @throws SQLException the SQL exception
     */
    private List<PharmacyOrder> buildOrderList(ResultSet resultSet) throws SQLException{
        List<PharmacyOrder> orders = new LinkedList<>();
        while(resultSet.next()){
            orders.add(buildOrder(resultSet));
        }
        return orders;
    }

    /**
     * Builds the order.
     *
     * @param resultSet the result set
     * @return the pharmacy order
     * @throws SQLException the SQL exception
     */
    private PharmacyOrder buildOrder(ResultSet resultSet) throws SQLException{
        int orderId = resultSet.getInt(ORDER_ID);
        String clientMail = resultSet.getString(MAIL);
        int medicineId = resultSet.getInt(MEDICINE_ID);
        int amount = resultSet.getInt(AMOUNT);
        double totalCost = resultSet.getDouble(TOTAL_COST);
        return new PharmacyOrder(orderId, clientMail, medicineId, amount, totalCost);
    }

}
