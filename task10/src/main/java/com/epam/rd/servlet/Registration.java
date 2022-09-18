package com.epam.rd.servlet;

import com.epam.rd.context.ApplicationContext;
import com.epam.rd.entity.User;
import com.epam.rd.service.IUserService;
import com.epam.rd.validator.CaptchaValidator;
import com.epam.rd.validator.UserValidator;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Arrays;

@WebServlet("/reg")
public class Registration extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("jsp/registration.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = collectUser(req);
        UserValidator userValidator = new UserValidator(user);

        String captcha = req.getParameter("captcha");

        // receive a captcha key by one of three ways
        String captchaKey = null;
        String captchaStorageMethod = (String) ApplicationContext.getInstance().getAttribute("captchaStorageMethod");
        if (captchaStorageMethod.equals("session")) {
            captchaKey = (String) req.getSession().getAttribute("captcha");
        } else if (captchaStorageMethod.equals("cookie")) {
            captchaKey = Arrays.stream(req.getCookies())
                    .filter(c -> c.getName().equals("captcha"))
                    .map(Cookie::getValue)
                    .findFirst()
                    .orElse(null);
        } else if (captchaStorageMethod.equals("hiddenTag")) {
            captchaKey = req.getParameter("captchaKey");
        }

        if (!CaptchaValidator.validate(captchaKey, captcha) || !userValidator.validate()) {
            putDataBack(req, user, userValidator);
            req.getRequestDispatcher("jsp/registration.jsp").forward(req, resp);
            return;
        }

        IUserService userService = (IUserService) ApplicationContext.getInstance().getAttribute("userService");
        userService.addNewUser(user);
        resp.sendRedirect(req.getContextPath());
    }

    private static User collectUser(HttpServletRequest req) {
        String username = req.getParameter("username");
        String name = req.getParameter("name");
        String surname = req.getParameter("surname");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        return new User(0, username, name, surname, email, password);
    }

    private static void putDataBack(HttpServletRequest req, User user, UserValidator validator) {
        if (!validator.isValidationProcessed()) {
            req.setAttribute("login", user.getLogin());
            req.setAttribute("name", user.getName());
            req.setAttribute("surname", user.getSurname());
            req.setAttribute("email", user.getEmail());
            return;
        }

        if (validator.isLoginValid()) {
            req.setAttribute("login", user.getLogin());
        }
        if (validator.isNameValid()) {
            req.setAttribute("name", user.getName());
        }
        if (validator.isSurnameValid()) {
            req.setAttribute("surname", user.getSurname());
        }
        if (validator.isEmailValid()) {
            req.setAttribute("email", user.getEmail());
        }
    }

}
