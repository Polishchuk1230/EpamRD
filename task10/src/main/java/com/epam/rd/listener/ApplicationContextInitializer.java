package com.epam.rd.listener;

import com.epam.rd.context.ApplicationContext;
import com.epam.rd.context.util.BeanNames;
import com.epam.rd.context.util.CaptchaStorageMethod;
import com.epam.rd.dao.*;
import com.epam.rd.dao.impl.*;
import com.epam.rd.entity.User;
import com.epam.rd.service.*;
import com.epam.rd.service.impl.*;
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

        // changeQuantity up the ApplicationContext
        ApplicationContext context = ApplicationContext.getInstance();

        List<User> users = new ArrayList<>(Arrays.asList(
                new User(4, "racoon24", "Oleg", "Enotov", "racoon24@gmail.com", null),
                new User(17, "asdf", "Jon", "Smith", "smith_mail@gmail.com", null)
        ));
        context.setAttribute(BeanNames.USERS, users);

        IUserDao userDao = new UserDaoMySQLImpl();
        context.setAttribute(BeanNames.USER_DAO, userDao);

        IUserService userService = new UserService(userDao);
        context.setAttribute(BeanNames.USER_SERVICE, userService);

        // here we choose one of three possible ways to store the captcha's key.
        ResourceBundle settings = ResourceBundle.getBundle("settings");
        ICaptchaStrategy captchaStrategy;
        switch (CaptchaStorageMethod.valueOf(settings.getString(BeanNames.CAPTCHA_STORAGE_METHOD))) {
            default:
            case COOKIE: captchaStrategy = new CookieCaptchaStrategy(); break;
            case HIDDEN_TAG: captchaStrategy = new HiddenTagCaptchaStrategy(); break;
            case SESSION: captchaStrategy = new SessionCaptchaStrategy(); break;
        }
        ICaptchaService captchaService = new CaptchaService(captchaStrategy, new HashMap<>());
        context.setAttribute(BeanNames.CAPTCHA_SERVICE, captchaService);

        // SubscriptionService
        ISubscriptionDao subscriptionDao = new SubscriptionDao();
        ISubscriptionService subscriptionService = new SubscriptionService(subscriptionDao);
        context.setAttribute(BeanNames.SUBSCRIPTION_SERVICE, subscriptionService);

        // ProductService
        IProductDao productDao = new ProductDao();
        IProductService productService = new ProductService(productDao);
        context.setAttribute(BeanNames.PRODUCT_SERVICE, productService);

        // CartService
        ICartDao cartDao = new CartDao();
        ICartService cartService = new CartService(cartDao);
        context.setAttribute(BeanNames.CART_SERVICE, cartService);

        // OrderService
        IOrderDao orderDao = new OrderDao();
        IOrderService orderService = new OrderService(orderDao);
        context.setAttribute(BeanNames.ORDER_SERVICE, orderService);
    }
}
