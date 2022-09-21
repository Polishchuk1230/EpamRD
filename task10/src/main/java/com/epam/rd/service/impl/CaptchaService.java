package com.epam.rd.service.impl;

import com.epam.rd.context.util.CaptchaStorageMethod;
import com.epam.rd.service.ICaptchaService;
import com.epam.rd.strategy.ICaptchaStrategy;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.jsp.PageContext;

import java.util.Map;
import java.util.Random;
import java.util.Set;

public class CaptchaService implements ICaptchaService {
    public static final int MAX_CAPTCHA_AGE = 1000 * 60 * 2;

    private ICaptchaStrategy captchaStrategy;

    private Map<String, String> captchaStorage;

    public CaptchaService(ICaptchaStrategy captchaStrategy, Map<String, String> captchaStorage) {
        this.captchaStrategy = captchaStrategy;
        this.captchaStorage = captchaStorage;
    }

    @Override
    public String generateKey() {
        int iTotalChars = 6;

        String key = System.currentTimeMillis() + "";

        long randomLong = new Random().nextLong();
        captchaStorage.put(key, (Long.toString(Math.abs(randomLong), 36)).substring(0, iTotalChars));

        return key;
    }

    @Override
    public void putKey(String key, PageContext pageContext) {
        captchaStrategy.storeKey(key, pageContext);
    }

    @Override
    public String getKey(HttpServletRequest request) {
        return captchaStrategy.getKey(request);
    }

    @Override
    public void removeOldCaptchas() {
        long keysForDelete = System.currentTimeMillis() - MAX_CAPTCHA_AGE;
        Set<String> keys = Set.copyOf(captchaStorage.keySet());
        for (String k : keys) {
            if (Long.parseLong(k) < keysForDelete) {
                captchaStorage.remove(k);
            }
        }
    }

    @Override
    public boolean validate(String key, String value) {
        removeOldCaptchas();

        String captchaValue = captchaStorage.remove(key);
        if (captchaValue == null) {
            return false;
        }

        return captchaValue.equals(value);
    }

    @Override
    public Map<String, String> getStorage() {
        return captchaStorage;
    }

    @Override
    public CaptchaStorageMethod getStorageMethod() {
        return captchaStrategy.getMethod();
    }
}
