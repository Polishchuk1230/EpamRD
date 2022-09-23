package com.epam.rd.strategy.impl;

import com.epam.rd.context.util.CaptchaStorageMethod;
import com.epam.rd.servlet.util.Parameters;
import com.epam.rd.strategy.ICaptchaStrategy;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.jsp.PageContext;

public class SessionCaptchaStrategy implements ICaptchaStrategy {

    @Override
    public String getKey(HttpServletRequest request) {
        return (String) request.getSession().getAttribute(Parameters.CAPTCHA_PARAMETER);
    }

    @Override
    public void storeKey(String key, PageContext pageContext) {
        pageContext.getSession().setAttribute(Parameters.CAPTCHA_PARAMETER, key);
    }

    @Override
    public CaptchaStorageMethod getMethod() {
        return CaptchaStorageMethod.SESSION;
    }
}
