package com.epam.rd;

import com.epam.rd.context.ApplicationContext;
import com.epam.rd.controller.Controller;
import com.epam.rd.controller.IController;
import com.epam.rd.net.socket_connection.SocketConnectionFactory;
import com.epam.rd.net.socket_controller.ISocketController;
import com.epam.rd.net.socket_server.SocketServer;
import com.epam.rd.pojo.GamingChair;
import com.epam.rd.pojo.RockingChair;
import com.epam.rd.util.Reflection;

import java.util.Scanner;

public class Application {
    public static void start() {
        Scanner sc = new Scanner(System.in);

        // choose random\handle way to fill parameters of new products
        System.out.println("Do you prefer to fill product parameters randomly? Type: true. Handle way otherwise.");
        boolean randomInput = sc.nextLine().equals("true");
        if (randomInput) {
            System.out.println("You have chosen random way. Use:\nproduct add -t PRODUCT_TYPE");
        } else {
            System.out.println("You have chosen handle way. Use the following templates:" +
                    "\nproduct add -t GamingChair --parameters locale=true, " + Reflection.getTypedFieldsAsString(GamingChair.class, "id") +
                    "\nproduct add -t RockingChair --parameters locale=true, " + Reflection.getTypedFieldsAsString(RockingChair.class, "id"));
        }

        // start processing commands
        IController controller = new Controller(randomInput);
        System.out.println(controller.getFullInfo());
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
