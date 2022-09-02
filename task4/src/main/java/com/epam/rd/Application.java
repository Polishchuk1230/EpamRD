package com.epam.rd;

import com.epam.rd.context.ApplicationContext;
import com.epam.rd.controller.Controller;
import com.epam.rd.controller.IController;
import com.epam.rd.net.socket_connection.SocketConnectionFactory;
import com.epam.rd.net.socket_controller.ISocketController;
import com.epam.rd.net.socket_server.SocketServer;

import java.util.Scanner;

public class Application {
    public static void start() {
        Scanner sc = new Scanner(System.in);

        // choose random\handle way to fill parameters of new products
        System.out.println("Do you prefer to fill product parameters randomly? Type: true. Handle way otherwise.");
        boolean randomInput = sc.nextLine().equals("true");

        IController controller = new Controller(randomInput);
        // demonstrate all possible console commands to a user
        System.out.println(controller.getFullInfo());
        // start processing commands
        while ((boolean) ApplicationContext.getInstance().find("running") && sc.hasNextLine()) {
            System.out.println(controller.processRequest(sc.nextLine()));
        }
        sc.close();
    }

    public static void startSocketServer(int port, ISocketController controller) {
        SocketServer tcpSocketServer = new SocketServer(port, new SocketConnectionFactory(controller));
        tcpSocketServer.setDaemon(true);
        tcpSocketServer.start();
    }
}
