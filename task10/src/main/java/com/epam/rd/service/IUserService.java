package com.epam.rd.service;

import com.epam.rd.entity.User;

public interface IUserService {
    boolean addNewUser(User user);

    boolean isUsernameUnique(User user);

    boolean isEmailUnique(User user);
}
