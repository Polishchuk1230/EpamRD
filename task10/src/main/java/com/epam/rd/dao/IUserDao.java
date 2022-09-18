package com.epam.rd.dao;

import com.epam.rd.entity.User;

import java.util.List;

public interface IUserDao {
    List<User> findAll();

    User findById(int id);

    User findByLogin(String login);

    User findByEmail(String email);

    boolean add(User user);

    boolean removeById(int id);
}
