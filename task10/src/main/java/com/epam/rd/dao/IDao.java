package com.epam.rd.dao;

import com.epam.rd.dao.util.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public interface IDao {
    /*
     * The methods below are designed to minimize the boiler code of (1)getting Connection, (2)creating Statement and
     * (3)using try/catch in dao-classes
     * */
    default  <T> T provideStatementForFunction(FunctionThrowsSQLExc<Statement, T> function) {
        try (Connection connection = ConnectionPool.getConnection();
             Statement statement = connection.createStatement())
        {
            return function.apply(statement);
        }
        catch (SQLException e) {
            return null;
        }
    }

    // This one provides PreparedStatement
    default  <T> T providePrepStatementForFunction(FunctionThrowsSQLExc<PreparedStatement, T> function, String sql) {
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement prepStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS))
        {
            return function.apply(prepStatement);
        }
        catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /*
     * Functional interface designed to use Statement object in lambdas without requirement of catching SQLException
     * during their creation.
     * */
    @FunctionalInterface
    interface FunctionThrowsSQLExc<T, R> {
        R apply(T t) throws SQLException;
    }
}
