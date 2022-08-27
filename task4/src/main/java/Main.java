import com.epam.rd.Application;
import com.epam.rd.net.socket_controller.impl.HttpController;
import com.epam.rd.net.socket_controller.impl.TcpController;
import com.epam.rd.net.socket_connection.SocketConnectionFactory;
import com.epam.rd.net.socket_server.SocketServer;

/**
 * Provides reading commands from the console
 */
public class Main {
    public static void main(String[] args) {
        // Start TCP-server
        SocketServer tcpSocketServer = new SocketServer(3000, new SocketConnectionFactory(new TcpController()));
        tcpSocketServer.setDaemon(true);
        tcpSocketServer.start();

        // Start HTTP-server
        SocketServer httpSocketServer = new SocketServer(8080, new SocketConnectionFactory(new HttpController()));
        httpSocketServer.setDaemon(true);
        httpSocketServer.start();

        // Start main application
        Application.start();
    }
}
