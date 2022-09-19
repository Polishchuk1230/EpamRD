package com.epam.rd.listener;

import com.epam.rd.context.ApplicationContext;
import com.epam.rd.context.util.BeanName;
import com.epam.rd.dao.IUserDao;
import com.epam.rd.dao.impl.UserDaoImpl;
import com.epam.rd.entity.User;
import com.epam.rd.service.IUserService;
import com.epam.rd.service.impl.UserService;
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

        context.setAttribute(BeanName.CAPTCHA_STORAGE, new HashMap<String, String>());

        // here we choose one of three possible ways to store the captch's key.
        ResourceBundle settings = ResourceBundle.getBundle("settings");
        context.setAttribute(BeanName.CAPTCHA_STORAGE_METHOD, settings.getString(BeanName.CAPTCHA_STORAGE_METHOD));
    }
}
