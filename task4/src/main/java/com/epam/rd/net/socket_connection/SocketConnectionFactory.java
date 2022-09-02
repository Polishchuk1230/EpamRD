package com.epam.rd.net.socket_connection;

import com.epam.rd.net.exception.CustomException;
import com.epam.rd.net.socket_controller.ISocketController;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.IOException;
import java.net.Socket;

public class SocketConnectionFactory implements AbstractFactory {

    private ISocketController controller;

    public SocketConnectionFactory(ISocketController controller) {
        this.controller = controller;
    }

    @Override
    public Thread create(Socket socket) {
        Runnable runnable = () -> {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())))) {

                String request = in.readLine();
                if (request == null) {
                    return;
                }

                // process a request
                String answer = controller.processRequest(request);

                // send an answer
                out.println(answer);
                out.flush();
            } catch (IOException e) {
                throw new CustomException(e);
            }
        };

        return new Thread(runnable);
    }
}
