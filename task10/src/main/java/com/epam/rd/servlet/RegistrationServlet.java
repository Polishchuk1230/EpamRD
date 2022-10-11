package com.epam.rd.servlet;

import com.epam.rd.context.ApplicationContext;
import com.epam.rd.context.util.BeanNames;
import com.epam.rd.entity.Subscription;
import com.epam.rd.entity.User;
import com.epam.rd.service.ICaptchaService;
import com.epam.rd.service.ISubscriptionService;
import com.epam.rd.service.IUserService;
import com.epam.rd.servlet.util.Parameters;
import com.epam.rd.validator.UserValidator;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@WebServlet("/reg")
@MultipartConfig
public class RegistrationServlet extends HttpServlet {
    private ICaptchaService captchaService;
    private ISubscriptionService subscriptionService;
    private IUserService userService;

    private static final String REG_JSP = "jsp/registration.jsp";

    @Override
    public void init(ServletConfig config) throws ServletException {
        captchaService = (ICaptchaService) ApplicationContext.getInstance().getAttribute(BeanNames.CAPTCHA_SERVICE);
        subscriptionService = (ISubscriptionService) ApplicationContext.getInstance().getAttribute(BeanNames.SUBSCRIPTION_SERVICE);
        userService = (IUserService) ApplicationContext.getInstance().getAttribute(BeanNames.USER_SERVICE);
        super.init(config);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("subscriptions", subscriptionService.findAll());
        req.getRequestDispatcher(REG_JSP).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = collectUser(req);

        String captcha = req.getParameter(Parameters.CAPTCHA_PARAMETER);

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

        // uploading the avatar
        Part part = req.getPart("avatar");
        if (part != null && !part.getSubmittedFileName().isEmpty()) {
            File dir = new File(getServletContext().getInitParameter("images"));
            if (!dir.exists() && !dir.mkdirs()) {
                // can't create a directory
                return;
            }

            String extension = part.getSubmittedFileName().substring(part.getSubmittedFileName().lastIndexOf('.'));
            String avatarFile = UUID.randomUUID() + extension;
            part.write(dir.getPath() + File.separator + avatarFile);
            user.setAvatar(avatarFile);
        }

        // hash password
        user.setPassword(DigestUtils.md2Hex(user.getPassword()));

        boolean success = userService.addNewUser(user);
        if (success) {
            setUpSubscriptions(req, user);
            req.getSession().setAttribute("user", user);
        }
        resp.sendRedirect(req.getContextPath());
    }

    private static void setUpSubscriptions(HttpServletRequest req, User user) {
        ISubscriptionService subscriptionService = (ISubscriptionService) ApplicationContext.getInstance().getAttribute(BeanNames.SUBSCRIPTION_SERVICE);
        List<Subscription> possibleSubscriptions = subscriptionService.findAll();
        for (Subscription sub : possibleSubscriptions) {
            if (req.getParameter("sub-" + sub.getName()) != null) {
                user.getSubscriptions().add(sub);
            }
        }
        if (!user.getSubscriptions().isEmpty()) {
            subscriptionService.subscribeUser(user.getId(), user.getSubscriptions());
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
