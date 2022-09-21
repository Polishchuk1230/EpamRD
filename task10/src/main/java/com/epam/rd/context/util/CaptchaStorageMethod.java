package com.epam.rd.context.util;

public enum CaptchaStorageMethod {
    COOKIE("cookie"),
    HIDDEN_TAG("hiddenTag"),
    SESSION("session");

    private String value;

    CaptchaStorageMethod(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
