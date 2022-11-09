package com.epam.rd.dao.impl;

import com.epam.rd.dao.IOrderDao;
import com.epam.rd.entity.Order;
import com.epam.rd.entity.Product;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

public class OrderDao implements IOrderDao {

    @Override
    public boolean save(Order order) {
        String addOrderQuery = "INSERT INTO orders (user_id, time, status_id, status_description) VALUES (?, ?, ?, ?)";
        String addOrderItemPattern = "INSERT INTO orders_products (order_id, product_id, quantity, current_price) VALUES (%d, %d, %d, %f)";

        FunctionThrowsSQLExc<PreparedStatement, Boolean> insertFunction = statement -> {
            statement.getConnection().setAutoCommit(false);

            statement.setInt(1, order.getUser().getId());
            statement.setTimestamp(2, new Timestamp(order.getTime().toEpochSecond(ZoneOffset.of("+3")) * 1000));
            statement.setInt(3, order.getOrderStatus().getId());
            statement.setString(4, order.getStatusDescription());

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                statement.getConnection().rollback();
                return false;
            }
            else if (order.getId() == 0) {
                ResultSet generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) order.setId(generatedKeys.getInt(1));
            }

            for (Map.Entry<Product, Integer> entry : order.getCart().entrySet()) {
                statement.addBatch(String.format(addOrderItemPattern, order.getId(), entry.getKey().getId(), entry.getValue(), entry.getKey().getPrice()));
            }
            int[] affectedRowsArray = statement.executeBatch();
            if (Arrays.stream(affectedRowsArray).anyMatch(i -> i == 0)) {
                statement.getConnection().rollback();
                return false;
            }

            statement.getConnection().commit();
            return true;
        };

        Boolean result = providePrepStatementForFunction(insertFunction, addOrderQuery);
        return Objects.nonNull(result) && result;
    }
}
