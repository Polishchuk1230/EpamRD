package com.epam.rd.validator;

import com.epam.rd.entity.User;

public class UserValidator {
    public static boolean validate(User user) {
        boolean result = true;

        if (!validateLogin(user.getUsername())) {
            result = false;
            user.setUsername(null);
        }
        if (!validateEmail(user.getEmail())) {
            result = false;
            user.setEmail(null);
        }
        if (!validatePassword(user.getPassword())) {
            result = false;
        }
        if (!validateName(user.getName())) {
            result = false;
            user.setName(null);
        }
        if (!validateName(user.getSurname())) {
            result = false;
            user.setSurname(null);
        }

        return result;
    }

    private static boolean validateLogin(String login) {
        String rgx = "^\\w{4,10}$";
        return login.matches(rgx);
    }

    private static boolean validateEmail(String email) {
        String rgx = "^\\w+@[\\w.]+.[a-zA-Z]{1,3}$";
        return email.matches(rgx);
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
