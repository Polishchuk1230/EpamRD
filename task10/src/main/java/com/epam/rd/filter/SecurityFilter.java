package com.epam.rd.filter;

import com.epam.rd.context.ApplicationContext;
import com.epam.rd.context.util.BeanNames;
import com.epam.rd.entity.Role;
import com.epam.rd.entity.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class SecurityFilter extends HttpFilter {

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        String reqURI = req.getRequestURI().toLowerCase().substring(req.getRequestURI().lastIndexOf("/"));
        // static data is free to access
        if (reqURI.endsWith(".js") || reqURI.endsWith(".css") || reqURI.endsWith("/img")) {
            super.doFilter(req, res, chain);
        }

        User user = (User) req.getSession().getAttribute("user");
        Map<String, List<Role>> constraints =
                (Map<String, List<Role>>) ApplicationContext.getInstance().getAttribute(BeanNames.CONSTRAINTS);

        // the page is free to access
        if (constraints.keySet().stream().noneMatch(reqURI::equalsIgnoreCase)) {
            // skip if structure
        }
        // the user isn't authenticated
        else if (user == null) {
            res.sendRedirect(req.getContextPath());
            return;
        }
        // the user isn't authorized
        else if (!constraints.get(reqURI).contains(user.getRole())) {
            res.sendError(403, "You have not access to this page.");
            return;
        }

        super.doFilter(req, res, chain);
    }
}
