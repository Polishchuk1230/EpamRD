package com.epam.rd.net.socket_controller.http_response;

public enum TypeContentHeader {
    JSON("Content-Type: application/json\n\r"),
    TEXT_HTML("Content-Type: text/html; charset=UTF-8\n\r");

    private final String typeHeader;

    TypeContentHeader(String typeHeader) {
        this.typeHeader = typeHeader;
    }

    @Override
    public String toString() {
        return typeHeader;
    }
}
