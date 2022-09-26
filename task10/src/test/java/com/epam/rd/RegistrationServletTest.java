package com.epam.rd;

import com.epam.rd.context.ApplicationContext;
import com.epam.rd.context.util.BeanNames;
import com.epam.rd.dao.IUserDao;
import com.epam.rd.dao.impl.UserDaoImpl;
import com.epam.rd.entity.User;
import com.epam.rd.service.ICaptchaService;
import com.epam.rd.service.ISubscriptionService;
import com.epam.rd.service.IUserService;
import com.epam.rd.service.impl.CaptchaService;
import com.epam.rd.service.impl.UserService;
import com.epam.rd.servlet.RegistrationServlet;
import com.epam.rd.strategy.impl.CookieCaptchaStrategy;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegistrationServletTest {

    @Test
    public void testRegDoPost() {
        User expectedUser = new User(0, "mockUN", "John", "Smith", "john_smith102@gmail.com", "John111");
        String captchaStorageMethod = "cookie";

        // mock session
        HttpSession mockedSession = Mockito.mock(HttpSession.class);

        // mock response
        HttpServletResponse mockedResponse = Mockito.mock(HttpServletResponse.class);

        // mock request
        HttpServletRequest mockedRequest = Mockito.mock(HttpServletRequest.class);
        Mockito.when(mockedRequest.getSession()).thenReturn(mockedSession);

        Mockito.when(mockedRequest.getMethod()).thenReturn("POST");
        Mockito.when(mockedRequest.getParameter("captcha")).thenReturn("a1b2c3");

        Mockito.when(mockedRequest.getParameter("username")).thenReturn(expectedUser.getUsername());
        Mockito.when(mockedRequest.getParameter("name")).thenReturn(expectedUser.getName());
        Mockito.when(mockedRequest.getParameter("surname")).thenReturn(expectedUser.getSurname());
        Mockito.when(mockedRequest.getParameter("email")).thenReturn(expectedUser.getEmail());
        Mockito.when(mockedRequest.getParameter("password")).thenReturn(expectedUser.getPassword());

        String captchaKey = System.currentTimeMillis() + "";

        Cookie[] cookies = { new Cookie("captcha", captchaKey) };
        Mockito.when(mockedRequest.getCookies()).thenReturn(cookies);

        // mock subscription service
        ISubscriptionService mockedSubscriptionService = Mockito.mock(ISubscriptionService.class);
        Mockito.when(mockedSubscriptionService.findAll()).thenReturn(List.of());

        // mock ApplicationContext
        ServletContext mockedServletContext = Mockito.mock(ServletContext.class);

        Mockito.when(mockedServletContext.getAttribute(BeanNames.CAPTCHA_STORAGE_METHOD)).thenReturn(captchaStorageMethod);

        Mockito.when(mockedServletContext.getAttribute(BeanNames.SUBSCRIPTION_SERVICE)).thenReturn(mockedSubscriptionService);

        Map<String, String> captchaStorage = new HashMap<>(Map.of(captchaKey, "a1b2c3"));

        ICaptchaService captchaService = new CaptchaService(new CookieCaptchaStrategy(), captchaStorage);
        Mockito.when(mockedServletContext.getAttribute(BeanNames.CAPTCHA_SERVICE)).thenReturn(captchaService);

        ApplicationContext.create(mockedServletContext);

        List<User> users = new ArrayList<>();
        Mockito.when(mockedServletContext.getAttribute(BeanNames.USERS)).thenReturn(users);

        IUserDao userDao = new UserDaoImpl();
        Mockito.when(mockedServletContext.getAttribute(BeanNames.USER_DAO)).thenReturn(userDao);

        IUserService userService = new UserService(userDao);
        Mockito.when(mockedServletContext.getAttribute(BeanNames.USER_SERVICE)).thenReturn(userService);

        ServletConfig mockedServletConfig = Mockito.mock(ServletConfig.class);

        try {
            RegistrationServlet registrationServlet = new RegistrationServlet();
            registrationServlet.init(mockedServletConfig);
            registrationServlet.service(mockedRequest, mockedResponse);
        } catch (ServletException | IOException e) {
            Assert.fail();
        }

        User actualUser = userDao.findByUsername(expectedUser.getUsername());
        Assert.assertEquals(expectedUser.getName(), actualUser.getName());
        Assert.assertEquals(expectedUser.getSurname(), actualUser.getSurname());
        Assert.assertEquals(expectedUser.getEmail(), actualUser.getEmail());
        Assert.assertEquals(DigestUtils.md2Hex(expectedUser.getPassword()), actualUser.getPassword());
    }
}
