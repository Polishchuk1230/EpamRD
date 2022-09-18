package com.epam.rd.validator;

import com.epam.rd.context.ApplicationContext;
import com.epam.rd.dao.IUserDao;
import com.epam.rd.entity.User;

public class UserValidator {
    private IUserDao userDao = (IUserDao) ApplicationContext.getInstance().getAttribute("userDao");

    private User user;

    private boolean validationProcessed;

    private boolean loginValid;
    private boolean emailValid;
    private boolean passwordValid;
    private boolean nameValid;
    private boolean surnameValid;

    public UserValidator(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
        validationProcessed = false;
    }

    public boolean isValidationProcessed() {
        return validationProcessed;
    }

    public boolean validate() {
        validateLogin();
        validateEmail();
        validatePassword();
        nameValid = validateName(user.getName());
        surnameValid = validateName(user.getSurname());

        validationProcessed = true;

        return isLoginValid() && isEmailValid() && isPasswordValid() && isNameValid() && isSurnameValid();
    }

    private void validateLogin() {
        String rgx = "^\\w{4,10}$";
        String login = user.getLogin();
        loginValid = login.matches(rgx) && userDao.findByLogin(login) == null;
    }

    private void validateEmail() {
        String rgx = "^\\w+@[\\w.]+.[a-zA-Z]{1,3}$";
        String email = user.getEmail();
        emailValid = email.matches(rgx) && userDao.findByEmail(email) == null;
    }

    private void validatePassword() {
        String rgx = "^\\w{4,10}$";
        String password = user.getPassword();
        passwordValid = password.matches(rgx);
    }

    private boolean validateName(String name) {
        String rgx = "^[a-zA-Z]+$";
        return name.matches(rgx);
    }

    public boolean isLoginValid() {
        return loginValid;
    }

    public boolean isEmailValid() {
        return emailValid;
    }

    public boolean isPasswordValid() {
        return passwordValid;
    }

    public boolean isNameValid() {
        return nameValid;
    }

    public boolean isSurnameValid() {
        return surnameValid;
    }
}
