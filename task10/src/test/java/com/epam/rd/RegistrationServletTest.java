package com.epam.rd;

import com.epam.rd.context.ApplicationContext;
import com.epam.rd.context.util.BeanName;
import com.epam.rd.dao.IUserDao;
import com.epam.rd.dao.impl.UserDaoImpl;
import com.epam.rd.entity.User;
import com.epam.rd.service.IUserService;
import com.epam.rd.service.impl.UserService;
import com.epam.rd.servlet.RegistrationServlet;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegistrationServletTest extends Mockito {

    @Test
    public void testRegDoPost() {
        User expectedUser = new User(0, "mockUN", "John", "Smith", "john_smith102@gmail.com", "John111");
        String captchaStorageMethod = "cookie";

        // mock response
        HttpServletResponse mockedResponse = Mockito.mock(HttpServletResponse.class);

        // mock request
        HttpServletRequest mockedRequest = Mockito.mock(HttpServletRequest.class);
        Mockito.when(mockedRequest.getMethod()).thenReturn("POST");
        Mockito.when(mockedRequest.getParameter("captcha")).thenReturn("a1b2c3");

        Mockito.when(mockedRequest.getParameter("username")).thenReturn(expectedUser.getLogin());
        Mockito.when(mockedRequest.getParameter("name")).thenReturn(expectedUser.getName());
        Mockito.when(mockedRequest.getParameter("surname")).thenReturn(expectedUser.getSurname());
        Mockito.when(mockedRequest.getParameter("email")).thenReturn(expectedUser.getEmail());
        Mockito.when(mockedRequest.getParameter("password")).thenReturn(expectedUser.getPassword());

        String captchaKey = System.currentTimeMillis() + "";

        Cookie[] cookies = { new Cookie("captcha", captchaKey) };
        Mockito.when(mockedRequest.getCookies()).thenReturn(cookies);

        // mock ApplicationContext
        ServletContext mockedServletContext = Mockito.mock(ServletContext.class);
        Mockito.when(mockedServletContext.getAttribute(BeanName.CAPTCHA_STORAGE_METHOD)).thenReturn(captchaStorageMethod);

        Map<String, String> captchaStorage = new HashMap<>(Map.of(captchaKey, "a1b2c3"));
        Mockito.when(mockedServletContext.getAttribute(BeanName.CAPTCHA_STORAGE)).thenReturn(captchaStorage);

        ApplicationContext.create(mockedServletContext);

        List<User> users = new ArrayList<>();
        Mockito.when(mockedServletContext.getAttribute(BeanName.USERS)).thenReturn(users);

        IUserDao userDao = new UserDaoImpl();
        Mockito.when(mockedServletContext.getAttribute(BeanName.USER_DAO)).thenReturn(userDao);

        IUserService userService = new UserService(userDao);
        Mockito.when(mockedServletContext.getAttribute(BeanName.USER_SERVICE)).thenReturn(userService);

        Mockito.when(mockedServletContext.getAttribute(BeanName.SUBSCRIPTIONS)).thenReturn(List.of());

        try {
            new RegistrationServlet().service(mockedRequest, mockedResponse);
        } catch (ServletException | IOException e) {
            Assert.fail();
        }

        User actualUser = userDao.findByLogin(expectedUser.getLogin());
        Assert.assertEquals(expectedUser.getName(), actualUser.getName());
        Assert.assertEquals(expectedUser.getSurname(), actualUser.getSurname());
        Assert.assertEquals(expectedUser.getEmail(), actualUser.getEmail());
        Assert.assertEquals(expectedUser.getPassword(), actualUser.getPassword());
    }
}
