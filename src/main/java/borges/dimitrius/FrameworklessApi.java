package borges.dimitrius;

import borges.dimitrius.controller.PatientController;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class FrameworklessApi {

    public static void main(String[] args) {

        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);

            PatientController patientController = PatientController.getInstance();

            patientController.getAllControllerContexts()
                    .forEach(ctx -> server.createContext(ctx, PatientController.getInstance()));

            server.setExecutor(null);
            server.start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
