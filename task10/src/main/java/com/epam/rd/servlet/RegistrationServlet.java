package com.epam.rd.servlet;

import com.epam.rd.context.ApplicationContext;
import com.epam.rd.context.util.BeanName;
import com.epam.rd.entity.User;
import com.epam.rd.service.ICaptchaService;
import com.epam.rd.service.IUserService;
import com.epam.rd.servlet.util.Parameters;
import com.epam.rd.validator.UserValidator;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.IOException;
import java.util.List;

@WebServlet("/reg")
public class RegistrationServlet extends HttpServlet {
    private static final String REG_JSP = "jsp/registration.jsp";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(REG_JSP).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        IUserService userService = (IUserService) ApplicationContext.getInstance().getAttribute(BeanName.USER_SERVICE);

        User user = collectUser(req);

        String captcha = req.getParameter(Parameters.CAPTCHA_PARAMETER);

        ICaptchaService captchaService = (ICaptchaService) ApplicationContext.getInstance().getAttribute(BeanName.CAPTCHA_SERVICE);
        captchaService.removeOldCaptchas();

        // receive a captcha key by one of three ways
        String captchaKey = captchaService.getKey(req);

        if (!captchaService.validate(captchaKey, captcha) || !UserValidator.validate(user)) {
            req.setAttribute(Parameters.USER, user);
            req.getRequestDispatcher(REG_JSP).forward(req, resp);
            return;
        }

        boolean usernameIsUnique = userService.isUsernameUnique(user);
        boolean emailIsUnique = userService.isEmailUnique(user);
        if (!usernameIsUnique || !emailIsUnique) {
            if (!usernameIsUnique) {
                req.setAttribute("usernameIsNotUnique", "Username is not free. Choose another one.");
                user.setUsername(null);
            }
            if (!emailIsUnique) {
                req.setAttribute("emailIsNotUnique", "Email is not free. Choose another one.");
                user.setEmail(null);
            }
            req.setAttribute(Parameters.USER, user);
            req.getRequestDispatcher(REG_JSP).forward(req, resp);
            return;
        }

        setUpSubscriptions(req, user);

        // hash password
        user.setPassword(DigestUtils.md2Hex(user.getPassword()));

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
        String username = req.getParameter(Parameters.USER_USERNAME);
        String name = req.getParameter(Parameters.USER_NAME);
        String surname = req.getParameter(Parameters.USER_SURNAME);
        String email = req.getParameter(Parameters.USER_EMAIL);
        String password = req.getParameter(Parameters.USER_PASSWORD);
        return new User(0, username, name, surname, email, password);
    }

}
