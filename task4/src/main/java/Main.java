import com.epam.rd.Application;
import com.epam.rd.net.socket_controller.impl.HttpController;
import com.epam.rd.net.socket_controller.impl.TcpController;

/**
 * Provides reading commands from the console
 */
public class Main {
    public static void main(String[] args) {
        Application.startSocketServer(8080, new HttpController());
        Application.startSocketServer(3000, new TcpController());

        // Start main application
        Application.start();
    }
}
