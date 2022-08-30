package com.epam.rd.net.socket_controller.util;

import java.util.StringJoiner;

public class HttpResponseHeaders {
    public static String addFirstHeaders200(String answer) {
        StringJoiner stringJoiner = new StringJoiner("\n\r");
        stringJoiner.add("HTTP/1.1 200 OK");
        stringJoiner.add("Content-Type: application/json");
        stringJoiner.add("");
        stringJoiner.add(answer);
        return stringJoiner.toString();
    }

    public static String addFirstHeaders400() {
        StringJoiner stringJoiner = new StringJoiner("\n\r");
        stringJoiner.add("HTTP/1.1 400 bad request");
        return stringJoiner.toString();
    }

    public static String addFirstHeaders404() {
        StringJoiner stringJoiner = new StringJoiner("\n\r");
        stringJoiner.add("HTTP/1.1 404 not found");
        return stringJoiner.toString();
    }
}
