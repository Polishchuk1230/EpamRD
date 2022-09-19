package com.epam.rd.servlet;

import com.epam.rd.context.ApplicationContext;
import com.epam.rd.context.util.BeanName;
import com.epam.rd.context.util.CaptchaStoreMethod;
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
import java.util.List;
import java.util.Set;

@WebServlet("/reg")
public class RegistrationServlet extends HttpServlet {
    public static final String REG_JSP = "jsp/registration.jsp";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(REG_JSP).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = collectUser(req);

        String captcha = req.getParameter("captcha");

        // receive a captcha key by one of three ways
        String captchaKey = null;
        String captchaStorageMethod = (String) ApplicationContext.getInstance().getAttribute(BeanName.CAPTCHA_STORAGE_METHOD);
        if (captchaStorageMethod.equals(CaptchaStoreMethod.SESSION.toString())) {
            captchaKey = (String) req.getSession().getAttribute("captcha");
        } else if (captchaStorageMethod.equals(CaptchaStoreMethod.COOKIE.toString())) {
            captchaKey = Arrays.stream(req.getCookies())
                    .filter(c -> c.getName().equals("captcha"))
                    .map(Cookie::getValue)
                    .findFirst()
                    .orElse(null);
        } else if (captchaStorageMethod.equals(CaptchaStoreMethod.HIDDEN_TAG.toString())) {
            captchaKey = req.getParameter("captchaKey");
        }

        Set<String> invalidFields = UserValidator.getInvalidFields(user);
        if (!CaptchaValidator.validate(captchaKey, captcha) || !invalidFields.isEmpty()) {
            putDataBack(req, user, invalidFields);
            req.getRequestDispatcher(REG_JSP).forward(req, resp);
            return;
        }

        setUpSubscriptions(req, user);

        IUserService userService = (IUserService) ApplicationContext.getInstance().getAttribute(BeanName.USER_SERVICE);
        userService.addNewUser(user);
        resp.sendRedirect(req.getContextPath());
    }

    private static void setUpSubscriptions(HttpServletRequest req, User user) {
        List<String> possibleSubscriptions = (List<String>) ApplicationContext.getInstance().getAttribute(BeanName.SUBSCRIPTIONS);
        for (String sub : possibleSubscriptions) {
            if (req.getParameter(sub) != null) {
                user.getSubscriptions().add(sub);
            }
        }
    }

    private static User collectUser(HttpServletRequest req) {
        String username = req.getParameter("username");
        String name = req.getParameter("name");
        String surname = req.getParameter("surname");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        return new User(0, username, name, surname, email, password);
    }

    private static void putDataBack(HttpServletRequest req, User user, Set<String> invalidFields) {
        if (!invalidFields.contains("login")) {
            req.setAttribute("login", user.getLogin());
        }
        if (!invalidFields.contains("name")) {
            req.setAttribute("name", user.getName());
        }
        if (!invalidFields.contains("surname")) {
            req.setAttribute("surname", user.getSurname());
        }
        if (!invalidFields.contains("email")) {
            req.setAttribute("email", user.getEmail());
        }
    }

}
