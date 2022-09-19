package com.epam.rd.listener;

import com.epam.rd.context.ApplicationContext;
import com.epam.rd.dao.IUserDao;
import com.epam.rd.dao.impl.UserDaoListImpl;
import com.epam.rd.service.IUserService;
import com.epam.rd.service.impl.UserService;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import java.util.HashMap;
import java.util.ResourceBundle;

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

        IUserDao userDao = new UserDaoListImpl();
        context.setAttribute("userDao", userDao);

        IUserService userService = new UserService(userDao);
        context.setAttribute("userService", userService);

        context.setAttribute("captchaStorage", new HashMap<String, String>());

        // here we choose one of three possible ways to store the captch's key.
        ResourceBundle settings = ResourceBundle.getBundle("settings");
        context.setAttribute("captchaStorageMethod", settings.getString("captchaStorageMethod"));
    }
}
