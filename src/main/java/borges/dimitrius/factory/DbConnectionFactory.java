package borges.dimitrius.factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnectionFactory {

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/rootcanal?useTimezone=true&serverTimezone=UTC",
                "root",
                "root");
    }

}
