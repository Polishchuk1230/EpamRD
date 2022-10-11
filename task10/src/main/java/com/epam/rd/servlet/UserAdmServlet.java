package com.epam.rd.servlet;

import com.epam.rd.context.ApplicationContext;
import com.epam.rd.context.util.BeanNames;
import com.epam.rd.dao.IUserDao;
import com.epam.rd.entity.User;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/users")
public class UserAdmServlet extends HttpServlet {
    private IUserDao userDao;

    @Override
    public void init(ServletConfig config) throws ServletException {
        userDao = (IUserDao) ApplicationContext.getInstance().getAttribute(BeanNames.USER_DAO);
        super.init(config);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<User> users = userDao.findAll();
        for (User user : users) {
            resp.getWriter().println(user);
        }
        resp.getWriter().flush();
    }
}
