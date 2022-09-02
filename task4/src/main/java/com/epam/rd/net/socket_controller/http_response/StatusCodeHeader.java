package com.epam.rd.net.socket_controller.http_response;

public enum StatusCodeHeader {
    CODE_400("HTTP/1.1 400 bad request\n\r"),
    CODE_404("HTTP/1.1 404 not found\n\r"),
    CODE_200("HTTP/1.1 200 OK\n\r");

    private final String codeHeader;

    StatusCodeHeader(String codeHeader) {
        this.codeHeader = codeHeader;
    }

    @Override
    public String toString() {
        return codeHeader;
    }
}
