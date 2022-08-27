package com.epam.rd.net.socket_connection;

import com.epam.rd.controller.IController;

import java.io.*;
import java.net.Socket;

public class SocketConnectionFactory implements AbstractFactory {

    private IController controller;

    public SocketConnectionFactory(IController controller) {
        this.controller = controller;
    }

    @Override
    public Thread create(Socket socket) {
        Runnable runnable = () -> {
            try {
                try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                     PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())))) {

                    String request = in.readLine();
                    if (request == null) {
                        System.out.println("InputStream was timed out");
                        return;
                    }

                    // process a request
                    String answer = controller.processRequest(request);

                    // send an answer
                    out.println(answer);
                    out.flush();

                } finally {
                    System.out.println("resources are closed");
                    socket.close();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        };

        return new Thread(runnable);
    }
}
