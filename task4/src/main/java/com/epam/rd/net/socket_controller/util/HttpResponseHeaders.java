package com.epam.rd.net.socket_controller.util;

import java.util.StringJoiner;

public class HttpResponseHeaders {
    public static String addFirstHeaders200(String answer) {
        StringJoiner stringJoiner = new StringJoiner("\n\r");
        stringJoiner.add("HTTP/1.1 200 OK");
        stringJoiner.add(answer);
        return stringJoiner.toString();
    }

    public static String addFirstHeaders400() {
        return "HTTP/1.1 400 bad request";
    }

    public static String addFirstHeaders404() {
        return "HTTP/1.1 404 not found";
    }
}
