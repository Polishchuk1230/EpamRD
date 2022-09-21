package com.epam.rd.strategy.impl;

import com.epam.rd.context.util.CaptchaStorageMethod;
import com.epam.rd.strategy.ICaptchaStrategy;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.jsp.PageContext;

public class SessionCaptchaStrategy implements ICaptchaStrategy {

    @Override
    public String getKey(HttpServletRequest request) {
        return (String) request.getSession().getAttribute("captcha");
    }

    @Override
    public void storeKey(String key, PageContext pageContext) {
        pageContext.getSession().setAttribute("captcha", key);
    }

    @Override
    public CaptchaStorageMethod getMethod() {
        return CaptchaStorageMethod.SESSION;
    }
}
