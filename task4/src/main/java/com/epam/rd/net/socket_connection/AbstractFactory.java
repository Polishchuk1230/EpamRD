package com.epam.rd.net.socket_connection;

import java.net.Socket;

public interface AbstractFactory {

    Thread create(Socket socket);
}
