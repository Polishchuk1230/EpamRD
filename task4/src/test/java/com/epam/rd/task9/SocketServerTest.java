package com.epam.rd.task9;

import com.epam.rd.net.socket_controller.ISocketController;
import com.epam.rd.net.socket_connection.SocketConnectionFactory;
import com.epam.rd.net.socket_server.SocketServer;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SocketServerTest {

    @Test
    public void connectionTest() throws InterruptedException {
        final String TCP_SERVER_ADDRESS = "localhost";
        final int TCP_SERVER_PORT = 3000;

        // Create a fake controller for TCP requests
        ISocketController mockedTCPController = Mockito.mock(ISocketController.class);
        Mockito.when(mockedTCPController.processRequest(Mockito.anyString()))
                .thenReturn("test answer");

        // Replace the real controller with the fake controller in ApplicationContext
        SocketConnectionFactory socketConnectionFactory = new SocketConnectionFactory(mockedTCPController);

        // Create and start the server
        SocketServer tcpServer = new SocketServer(TCP_SERVER_PORT, socketConnectionFactory);
        tcpServer.start();

        List<String> actual = new ArrayList<>();

        // Create a client-side imitation thread
        Runnable runnable = () -> {
            try (Socket socket = new Socket(TCP_SERVER_ADDRESS, TCP_SERVER_PORT);
                 BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())))) {

                out.println("test request");
                out.flush();

                String result = in.readLine();
                actual.add(result);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        };

        // Execute several threads with test requests to the TcpSocketServer
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(runnable);
        executorService.execute(runnable);
        executorService.execute(runnable);

        // Wait for results
        executorService.shutdown();
        Assert.assertTrue(
                executorService.awaitTermination(5, TimeUnit.SECONDS));

        // Assert answers from the server
        List<String> expected = new ArrayList<>(Arrays.asList("test answer", "test answer", "test answer"));
        Assert.assertEquals(expected, actual);

        // Stop the server
        tcpServer.interrupt();
    }
}
