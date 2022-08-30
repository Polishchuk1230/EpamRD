package com.epam.rd.net.socket_server;

import com.epam.rd.net.socket_connection.AbstractFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.SocketTimeoutException;

public class SocketServer extends Thread {
    private static final Logger logger = LogManager.getLogger(SocketServer.class);
    private final int port;
    private AbstractFactory socketConnectionFactory;

    public SocketServer(int port, AbstractFactory socketConnectionFactory) {
        this.port = port;
        this.socketConnectionFactory = socketConnectionFactory;
    }

    @Override
    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            // check isInterrupted every 5 seconds
            serverSocket.setSoTimeout(5000);

            while (true) {
                try {
                    // create and start a thread which processes a request
                    Thread socketConnectionThread = socketConnectionFactory.create(serverSocket.accept());
                    socketConnectionThread.start();
                }
                // stop server if interrupt() was invoked
                catch (SocketTimeoutException e) {
                    if (isInterrupted()) break;
                }
            }
        } catch (IOException e) {
            logger.info(e.getMessage());
        }
    }

}
