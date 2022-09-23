package com.epam.rd.service.impl;

import com.epam.rd.dao.IUserDao;
import com.epam.rd.entity.User;
import com.epam.rd.service.IUserService;

public class UserService implements IUserService {
    private IUserDao userDao;

    public UserService(IUserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public boolean addNewUser(User user) {
        return userDao.add(user);
    }

    @Override
    public boolean isUsernameUnique(User user) {
        return userDao.findByUsername(user.getUsername()) == null;
    }

    @Override
    public boolean isEmailUnique(User user) {
        return userDao.findByUsername(user.getEmail()) == null;
    }
}
