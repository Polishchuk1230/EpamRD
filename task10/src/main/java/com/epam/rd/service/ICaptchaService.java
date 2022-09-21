package com.epam.rd.service;

import com.epam.rd.context.util.CaptchaStorageMethod;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.jsp.PageContext;

import java.util.Map;

public interface ICaptchaService {

    String generateKey();

    void putKey(String key, PageContext pageContext);

    void removeOldCaptchas();

    String getKey(HttpServletRequest request);

    Map<String, String> getStorage();

    CaptchaStorageMethod getStorageMethod();

    boolean validate(String key, String value);
}
