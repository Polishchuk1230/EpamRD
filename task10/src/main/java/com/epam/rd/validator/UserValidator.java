package com.epam.rd.validator;

import com.epam.rd.context.ApplicationContext;
import com.epam.rd.context.util.BeanName;
import com.epam.rd.dao.IUserDao;
import com.epam.rd.entity.User;

import java.util.HashSet;
import java.util.Set;

public class UserValidator {
    private static IUserDao userDao = (IUserDao) ApplicationContext.getInstance().getAttribute(BeanName.USER_DAO);

    public static Set<String> getInvalidFields(User user) {
        Set<String> result = new HashSet<>();

        if (!validateLogin(user.getLogin())) {
            result.add("login");
        }
        if (!validateEmail(user.getEmail())) {
            result.add("email");
        }
        if (!validatePassword(user.getPassword())) {
            result.add("password");
        }
        if (!validateName(user.getName())) {
            result.add("name");
        }
        if (!validateName(user.getSurname())) {
            result.add("surname");
        }

        return result;
    }

    private static boolean validateLogin(String login) {
        String rgx = "^\\w{4,10}$";
        return login.matches(rgx) && userDao.findByLogin(login) == null;
    }

    private static boolean validateEmail(String email) {
        String rgx = "^\\w+@[\\w.]+.[a-zA-Z]{1,3}$";
        return email.matches(rgx) && userDao.findByEmail(email) == null;
    }

    private static boolean validatePassword(String password) {
        String rgx = "^\\w{4,10}$";
        return password.matches(rgx);
    }

    private static boolean validateName(String name) {
        String rgx = "^[a-zA-Z]+$";
        return name.matches(rgx);
    }
}
