package com.epam.rd.dao.impl;

import com.epam.rd.dao.ISubscriptionDao;
import com.epam.rd.entity.Subscription;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SubscriptionDao implements ISubscriptionDao {
    private Subscription mapSubscription(ResultSet resultSet) throws SQLException {
        return new Subscription(
                resultSet.getInt("subscriptions.id"),
                resultSet.getString("subscriptions.name"));
    }

    @Override
    public List<Subscription> findAll() {
        String query = "SELECT * FROM subscriptions";

        FunctionThrowsSQLExc<Statement, List<Subscription>> findAllFunction = statement -> {
            statement.execute(query);
            ResultSet resultSet = statement.getResultSet();

            List<Subscription> result = new ArrayList<>();
            while (resultSet != null && resultSet.next()) {
                result.add(
                        mapSubscription(resultSet));
            }
            return result;
        };

        return provideStatementForFunction(findAllFunction);
    }

    @Override
    public List<Subscription> findByUserId(int userId) {
        String query = """
                SELECT subscriptions.name FROM subscriptions
                JOIN users_subscriptions ON subscriptions.id = users_subscriptions.subscription_id
                JOIN users ON users.id = users_subscriptions.user_id
                WHERE users.id = ?
                """;

        FunctionThrowsSQLExc<Statement, List<Subscription>> findByUserIdFunction = statement -> {
            statement.execute(query);
            ResultSet resultSet = statement.getResultSet();

            List<Subscription> result = new ArrayList<>();
            while (resultSet != null && resultSet.next()) {
                result.add(
                        mapSubscription(resultSet));
            }
            return result;
        };

        return provideStatementForFunction(findByUserIdFunction);
    }

    @Override
    public boolean subscribeUser(int userId, List<Subscription> subscriptions) {
        String query = "INSERT INTO users_subscriptions VALUES (?, ?)";
        for (int i = 1; i < subscriptions.size(); i++) {
            query += ", (?, ?)";
        }

        FunctionThrowsSQLExc<PreparedStatement, Boolean> subscribeUserFunction = statement -> {
            for (int i = 1; i <= subscriptions.size(); i++) {
                statement.setInt(i * 2 - 1, userId);
                statement.setInt(i * 2, subscriptions.get(i-1).getId());
            }
            int affectedRows = statement.executeUpdate();
            return affectedRows > 0;
        };

        return providePrepStatementForFunction(subscribeUserFunction, query);
    }
}
