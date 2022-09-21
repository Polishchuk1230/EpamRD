package com.epam.rd.listener;

import com.epam.rd.context.ApplicationContext;
import com.epam.rd.context.util.BeanName;
import com.epam.rd.context.util.CaptchaStorageMethod;
import com.epam.rd.dao.IUserDao;
import com.epam.rd.dao.impl.UserDaoImpl;
import com.epam.rd.entity.User;
import com.epam.rd.service.ICaptchaService;
import com.epam.rd.service.IUserService;
import com.epam.rd.service.impl.CaptchaService;
import com.epam.rd.service.impl.UserService;
import com.epam.rd.strategy.ICaptchaStrategy;
import com.epam.rd.strategy.impl.CookieCaptchaStrategy;
import com.epam.rd.strategy.impl.HiddenTagCaptchaStrategy;
import com.epam.rd.strategy.impl.SessionCaptchaStrategy;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import java.util.*;

/**
 * A listener which initialize the ApplicationContext when the application starts.
 */
@WebListener
public class ApplicationContextInitializer implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        if (ApplicationContext.isCreated()) {
            return;
        }

        // take ServletContext instance
        ServletContext servletContext = sce.getServletContext();

        // create the new ApplicationContext's instance
        ApplicationContext.create(servletContext);

        // set up the ApplicationContext
        ApplicationContext context = ApplicationContext.getInstance();

        List<User> users = new ArrayList<>(Arrays.asList(
                new User(4, "racoon24", "Oleg", "Enotov", "racoon24@gmail.com", null),
                new User(17, "asdf", "Jon", "Smith", "smith_mail@gmail.com", null)
        ));
        context.setAttribute(BeanName.USERS, users);

        IUserDao userDao = new UserDaoImpl();
        context.setAttribute(BeanName.USER_DAO, userDao);

        IUserService userService = new UserService(userDao);
        context.setAttribute(BeanName.USER_SERVICE, userService);

        List<String> possibleSubscriptions = new ArrayList<>(Arrays.asList("subscriptionME", "subscriptionAll"));
        context.setAttribute(BeanName.SUBSCRIPTIONS, possibleSubscriptions);

        // here we choose one of three possible ways to store the captcha's key.
        ResourceBundle settings = ResourceBundle.getBundle("settings");
        ICaptchaStrategy captchaStrategy;
        switch (CaptchaStorageMethod.valueOf(settings.getString(BeanName.CAPTCHA_STORAGE_METHOD))) {
            default:
            case COOKIE: captchaStrategy = new CookieCaptchaStrategy(); break;
            case HIDDEN_TAG: captchaStrategy = new HiddenTagCaptchaStrategy(); break;
            case SESSION: captchaStrategy = new SessionCaptchaStrategy(); break;
        }
        ICaptchaService captchaService = new CaptchaService(captchaStrategy, new HashMap<>());
        context.setAttribute(BeanName.CAPTCHA_SERVICE, captchaService);
    }
}
