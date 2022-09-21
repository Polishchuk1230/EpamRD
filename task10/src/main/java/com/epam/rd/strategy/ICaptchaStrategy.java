package com.epam.rd.strategy;

import com.epam.rd.context.util.CaptchaStorageMethod;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.jsp.PageContext;

public interface ICaptchaStrategy {

    String getKey(HttpServletRequest request);

    void storeKey(String key, PageContext pageContext);

    CaptchaStorageMethod getMethod();
}
