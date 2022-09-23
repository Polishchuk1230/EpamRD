package com.epam.rd.strategy.impl;

import com.epam.rd.context.util.CaptchaStorageMethod;
import com.epam.rd.servlet.util.Parameters;
import com.epam.rd.strategy.ICaptchaStrategy;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.jsp.PageContext;

public class HiddenTagCaptchaStrategy implements ICaptchaStrategy {

    @Override
    public String getKey(HttpServletRequest request) {
        return request.getParameter(Parameters.CAPTCHA_KEY_PARAMETER);
    }

    @Override
    public void storeKey(String key, PageContext pageContext) {
        // we do not implement this method as well as the key will be stored in an HTTP-form by CaptchaTag
    }

    @Override
    public CaptchaStorageMethod getMethod() {
        return CaptchaStorageMethod.HIDDEN_TAG;
    }
}
