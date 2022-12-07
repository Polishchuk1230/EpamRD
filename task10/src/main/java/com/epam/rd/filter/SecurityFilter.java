package com.epam.rd.filter;

import com.epam.rd.context.ApplicationContext;
import com.epam.rd.context.util.BeanNames;
import com.epam.rd.entity.User;
import com.epam.rd.service.ISecurityService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class SecurityFilter extends HttpFilter {
    private ISecurityService securityService;

    @Override
    public void init() throws ServletException {
        securityService = (ISecurityService) ApplicationContext.getInstance().getAttribute(BeanNames.SECURITY_SERVICE);
    }

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        String reqURI = req.getRequestURI().toLowerCase().substring(req.getRequestURI().lastIndexOf("/"));
        User user = (User) req.getSession().getAttribute("user");

        if (securityService.isResourcePublic(reqURI)) {
            // skip if-structure
            // let it be doFilter()
        }
        // if the user isn't authenticated
        else if (user == null) {
            // redirect it to the login page
            res.sendRedirect(req.getContextPath());
            return;
        }
        else if (securityService.isAccessDenied(reqURI, user)) {
            res.sendError(403, "You have not access to this page.");
            return;
        }

        super.doFilter(req, res, chain);
    }
}
