package com.epam.rd.validator;

import com.epam.rd.context.ApplicationContext;

import java.util.Map;

public class CaptchaValidator {
    public static final int MAX_CAPTCHA_AGE = 1000 * 60 * 2;

    public static boolean validate(String key, String value) {
        Map<String, String> captchaStorage = (Map<String, String>) ApplicationContext.getInstance().getAttribute("captchaStorage");

        // remove all too old values
        long keysForDelete = System.currentTimeMillis() - MAX_CAPTCHA_AGE;
        for (String k : captchaStorage.keySet()) {
            if (Long.parseLong(k) < keysForDelete) {
                captchaStorage.remove(k);
            }
        }

        String captchaValue = captchaStorage.remove(key);
        if (captchaValue == null) {
            return false;
        }

        return captchaValue.equals(value);
    }
}
