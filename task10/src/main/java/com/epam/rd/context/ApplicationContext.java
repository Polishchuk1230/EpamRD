package com.epam.rd.context;

import jakarta.servlet.ServletContext;

/**
 * Scope which is alive during the application is running.
 * It based on ServletContext. It is just a way to get the ServletContext instance everywhere in the application.
 */
public class ApplicationContext {
    private static ApplicationContext instance;

    private ServletContext servletContext;

    private ApplicationContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    public static boolean isCreated() {
        return instance != null;
    }

    public static void create(ServletContext servletContext) {
        if (instance == null) {
            instance = new ApplicationContext(servletContext);
        }
    }

    public static ApplicationContext getInstance() {
        return instance;
    }

    public void setAttribute(String name, Object object) {
        servletContext.setAttribute(name, object);
    }

    public Object getAttribute(String name) {
        return servletContext.getAttribute(name);
    }
}
