package com.epam.rd.net.socket_controller.util;

public class HttpResponse {

    private StatusCodeHeader statusCodeHeader = StatusCodeHeader.CODE_400;

    private TypeContentHeader typeContentHeader = TypeContentHeader.TEXT_HTML;

    private String responseBody = "";

    public HttpResponse() {
    }

    public HttpResponse(StatusCodeHeader statusCodeHeader) {
        this.statusCodeHeader = statusCodeHeader;
    }

    public HttpResponse setTypeContent(TypeContentHeader typeContentHeader) {
        this.typeContentHeader = typeContentHeader;
        return this;
    }

    public HttpResponse setBody(Object responseBody) {
        this.responseBody = "\n" + responseBody;
        return this;
    }

    @Override
    public String toString() {
        return stringValueOf(statusCodeHeader) +
                stringValueOf(typeContentHeader) +
                stringValueOf(responseBody);
    }

    private static String stringValueOf(Object obj) {
        return (obj == null) ? "" : obj.toString();
    }
}
