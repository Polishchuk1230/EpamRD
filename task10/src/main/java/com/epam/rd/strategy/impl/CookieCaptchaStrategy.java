package com.epam.rd.strategy.impl;

import com.epam.rd.context.util.CaptchaStorageMethod;
import com.epam.rd.strategy.ICaptchaStrategy;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.jsp.PageContext;

import java.util.Arrays;

public class CookieCaptchaStrategy implements ICaptchaStrategy {

    @Override
    public String getKey(HttpServletRequest request) {
        return Arrays.stream(request.getCookies())
                .filter(c -> c.getName().equals("captcha"))
                .map(Cookie::getValue)
                .findFirst()
                .orElse(null);
    }

    @Override
    public void storeKey(String key, PageContext pageContext) {
        ((HttpServletResponse) pageContext.getResponse()).addCookie(new Cookie("captcha", key));
    }

    @Override
    public CaptchaStorageMethod getMethod() {
        return CaptchaStorageMethod.COOKIE;
    }
}
