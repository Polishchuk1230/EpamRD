package com.epam.rd.dao.impl;

import com.epam.rd.dao.ICartDao;
import com.epam.rd.entity.Product;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class CartDao implements ICartDao {
    @Override
    public Map<Product, Integer> findByUserId(int userId) {
        String query = """
                SELECT * FROM cart
                JOIN products ON cart.product_id = products.id
                JOIN suppliers ON products.supplier_id = suppliers.id
                
                WHERE user_id = """ + userId;

        FunctionThrowsSQLExc<Statement, Map<Product, Integer>> findCart = statement -> {
            statement.execute(query);
            ResultSet resultSet = statement.getResultSet();
            return mapCart(resultSet);
        };

        return provideStatementForFunction(findCart);
    }

    @Override
    public boolean addProduct(int userId, int productId, int quantity) {
        String query = String.format("INSERT INTO cart (user_id, product_id, quantity) VALUES (%d, %d, %d)",
                userId, productId, quantity);

        return provideStatementForFunction(simpleUpdate(query));
    }

    @Override
    public boolean removeProduct(int userId, int productId) {
        String query = String.format("DELETE FROM cart WHERE user_id = %d AND product_id = %d", userId, productId);

        return provideStatementForFunction(simpleUpdate(query));
    }

    @Override
    public boolean changeQuantity(int userId, int productId, int newQuantity) {
        String query = String.format("UPDATE cart SET quantity = %d WHERE user_id = %d AND product_id = %d",
                newQuantity, userId, productId);

        return provideStatementForFunction(simpleUpdate(query));
    }

    @Override
    public boolean clear(int userId) {
        String query = "DELETE FROM cart WHERE user_id = " + userId;

        FunctionThrowsSQLExc<Statement, Boolean> clearCart = statement -> {
            statement.executeUpdate(query);
            return true;
        };

        return provideStatementForFunction(clearCart);
    }

    private FunctionThrowsSQLExc<Statement, Boolean> simpleUpdate(String sqlQuery) {
        return statement -> {
            int affectedRows = statement.executeUpdate(sqlQuery);
            return affectedRows > 0;
        };
    }

    private Map<Product, Integer> mapCart(ResultSet resultSet) throws SQLException {
        Map<Product, Integer> cart = new HashMap<>();

        while (resultSet.next()) {
            Product product = ProductDao.mapProduct(resultSet);
            cart.put(product, resultSet.getInt("cart.quantity"));
        }

        return cart;
    }
}
