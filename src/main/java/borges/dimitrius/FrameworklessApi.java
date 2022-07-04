package borges.dimitrius;

import borges.dimitrius.controller.PatientController;
import borges.dimitrius.controller.RootFileController;
import borges.dimitrius.controller.StapleController;
import borges.dimitrius.controller.SymptomTypeController;
import borges.dimitrius.factory.DbConnectionFactory;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.sql.SQLException;

public class FrameworklessApi {

    public static void main(String[] args) {

        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);

            PatientController patientController = new PatientController(DbConnectionFactory.getConnection());
            RootFileController rootFileController = new RootFileController(DbConnectionFactory.getConnection());
            StapleController stapleController = new StapleController(DbConnectionFactory.getConnection());
            SymptomTypeController symptomTypeController = new SymptomTypeController(DbConnectionFactory.getConnection());

            server.createContext(patientController.getEndpoint(), patientController);
            server.createContext(rootFileController.getEndpoint(), rootFileController);
            server.createContext(stapleController.getEndpoint(), stapleController);
            server.createContext(symptomTypeController.getEndpoint(), symptomTypeController);

            server.setExecutor(null);
            server.start();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
