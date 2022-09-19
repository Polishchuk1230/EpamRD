package com.epam.rd.context.util;

public enum CaptchaStoreMethod {
    COOKIE("cookie"),
    HIDDEN_TAG("hiddenTag"),
    SESSION("session");

    private String value;

    CaptchaStoreMethod(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
