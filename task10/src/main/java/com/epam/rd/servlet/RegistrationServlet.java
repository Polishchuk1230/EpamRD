package com.epam.rd.servlet;

import com.epam.rd.context.ApplicationContext;
import com.epam.rd.context.util.BeanName;
import com.epam.rd.entity.User;
import com.epam.rd.service.ICaptchaService;
import com.epam.rd.service.IUserService;
import com.epam.rd.validator.UserValidator;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
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
        User user = collectUser(req);

        String captcha = req.getParameter("captcha");

        ICaptchaService captchaService = (ICaptchaService) ApplicationContext.getInstance().getAttribute(BeanName.CAPTCHA_SERVICE);

        // receive a captcha key by one of three ways
        String captchaKey = captchaService.getKey(req);

        Map<String, Boolean> criticalFields = new HashMap<>(Map.of(
                "username", true,
                "name", true,
                "surname", true,
                "email", true
        ));
        if (!captchaService.checkCaptcha(captchaKey, captcha) || !UserValidator.validate(user, criticalFields)) {
            putDataBack(req, user, criticalFields);
            req.getRequestDispatcher(REG_JSP).forward(req, resp);
            return;
        }

        setUpSubscriptions(req, user);

        // hash password
        user.setPassword(DigestUtils.md2Hex(user.getPassword()));

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

    private static void putDataBack(HttpServletRequest req, User user, Map<String, Boolean> validFields) {
        validFields.entrySet().stream()
                .filter(Map.Entry::getValue)
                .forEach(entry ->
                        req.setAttribute(entry.getKey(), getFieldValue(user, entry.getKey())));
    }

    /**
     * Get the value of the User's field using just a field name
     */
    private static String getFieldValue(User user, String fieldName) {
        Method method = Arrays.stream(User.class.getMethods())
                .filter(m -> m.getName().equalsIgnoreCase("get" + fieldName))
                .findFirst()
                .orElseThrow();
        try {
            return (String) method.invoke(user);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return "We couldn't get the value";
    }

}
