package com.epam.rd.servlet;

import com.epam.rd.context.ApplicationContext;
import com.epam.rd.entity.User;
import com.epam.rd.service.IUserService;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.IOException;

@WebServlet("/auth")
public class AuthServlet extends HttpServlet {
    private IUserService userService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        userService = (IUserService) ApplicationContext.getInstance().getAttribute("userService");
        super.init(config);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = userService.findByUsername(req.getParameter("username"));
        if (user != null && user.getPassword().equals(DigestUtils.md2Hex(req.getParameter("password")))) {
            req.getSession().setAttribute("user", user);
        }

        resp.sendRedirect(req.getContextPath());
    }
}
