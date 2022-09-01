package com.epam.rd.net.socket_controller.util;

public class HttpResponseHeaders {
    public static String CONTENT_TYPE_JSON = "Content-Type: application/json\n\r";
    public static String CONTENT_TYPE_TEXT_HTML = "Content-Type: text/html; charset=UTF-8\n\r";

    public static String RESPONSE_STATUS_CODE_200 = "HTTP/1.1 200 OK\n\r";
    public static String RESPONSE_STATUS_CODE_400 = "HTTP/1.1 400 bad request\n\r";
    public static String RESPONSE_STATUS_CODE_404 = "HTTP/1.1 404 not found\n\r";
}
