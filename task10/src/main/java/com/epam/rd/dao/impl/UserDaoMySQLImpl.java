package com.epam.rd.dao.impl;

import com.epam.rd.dao.IUserDao;
import com.epam.rd.entity.Role;
import com.epam.rd.entity.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoMySQLImpl implements IUserDao {
    private User mapUser (ResultSet resultSet) throws SQLException {
        User user = new User(resultSet.getInt("users.id"),
                resultSet.getString("users.username"),
                resultSet.getString("users.name"),
                resultSet.getString("users.surname"),
                resultSet.getString("users.email"),
                resultSet.getString("users.password"));
        user.setAvatar(resultSet.getString("users.avatar"));

        String roleString = resultSet.getString("roles.name");
        if (roleString != null) {
            user.setRole(
                    Role.valueOf(roleString));
        }

        return user;
    }

    //package-protected
    private void unmapUser (User user, PreparedStatement statement) throws SQLException {
        statement.setInt(1, user.getId());
        statement.setString(2, user.getUsername());
        statement.setString(3, user.getName());
        statement.setString(4, user.getSurname());
        statement.setString(5, user.getEmail());
        statement.setString(6, user.getPassword());
        statement.setString(7, user.getAvatar());
    }

    @Override
    public List<User> findAll() {
        String query = "SELECT * FROM users";

        FunctionThrowsSQLExc<Statement, List<User>> findAllFunction = statement -> {
            List<User> result = new ArrayList<>();

            boolean executionResult = statement.execute(query);
            if (!executionResult) {
                return result;
            }

            ResultSet resultSet = statement.getResultSet();
            while (resultSet.next()) {
                result.add(
                        mapUser(resultSet));
            }

            return result;
        };

        return provideStatementForFunction(findAllFunction);
    }

    private FunctionThrowsSQLExc<PreparedStatement, User> findByFieldFunction(String field) {
        return statement -> {
            statement.setString(1, field);

            boolean isExecuted = statement.execute();
            if (!isExecuted) {
                return null;
            }

            ResultSet resultSet = statement.getResultSet();
            if (resultSet.next()) {
                return mapUser(resultSet);
            }

            return null;
        };
    }

    @Override
    public User findById(int id) {
        String query = """
                SELECT * FROM users
                LEFT JOIN users_roles ON users.id = users_roles.user_id
                LEFT JOIN roles ON roles.id = users_roles.role_id
                WHERE users.id = ?
                """;

        FunctionThrowsSQLExc<PreparedStatement, User> findById = findByFieldFunction(Integer.toString(id));

        return providePrepStatementForFunction(findById, query);
    }

    @Override
    public User findByUsername(String username) {
        String query = """
                SELECT * FROM users
                LEFT JOIN users_roles ON users.id = users_roles.user_id
                LEFT JOIN roles ON roles.id = users_roles.role_id
                WHERE users.username = ?
                """;

        FunctionThrowsSQLExc<PreparedStatement, User> findByUsername = findByFieldFunction(username);

        return providePrepStatementForFunction(findByUsername, query);
    }

    @Override
    public User findByEmail(String email) {
        String query = """
                SELECT * FROM users
                LEFT JOIN users_roles ON users.id = users_roles.user_id
                LEFT JOIN roles ON roles.id = users_roles.role_id
                WHERE users.email = ?
                """;

        FunctionThrowsSQLExc<PreparedStatement, User> findByEmail = findByFieldFunction(email);

        return providePrepStatementForFunction(findByEmail, query);
    }

    @Override
    public boolean add(User user) {
        String query = "INSERT INTO users (id, username, name, surname, email, password, avatar) VALUES (?, ?, ?, ?, ?, ?, ?)";

        FunctionThrowsSQLExc<PreparedStatement, Boolean> insertUserFunction = statement -> {
            unmapUser(user, statement);

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                return false;
            }

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (user.getId() == 0 && generatedKeys.next()) {
                user.setId(generatedKeys.getInt(1));
            }
            return true;
        };

        return providePrepStatementForFunction(insertUserFunction, query);
    }

    @Override
    public boolean removeById(int id) {
        String query = "DELETE FROM users WHERE users.id = " + id;

        FunctionThrowsSQLExc<Statement, Boolean> removeUserFunction = statement -> {
            int affectedRows = statement.executeUpdate(query);
            return affectedRows != 0;
        };

        return provideStatementForFunction(removeUserFunction);
    }
}
