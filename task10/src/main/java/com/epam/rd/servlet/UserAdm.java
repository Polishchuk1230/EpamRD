package com.epam.rd.servlet;

import com.epam.rd.dao.IUserDao;
import com.epam.rd.entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/users")
public class UserAdm extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        IUserDao userDao = (IUserDao) req.getServletContext().getAttribute("userDao");
        List<User> users = userDao.findAll();
        for (User user : users) {
            resp.getWriter().println(user);
        }
        resp.getWriter().flush();
    }
}
