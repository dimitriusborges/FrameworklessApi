package borges.dimitrius.setup;

import borges.dimitrius.dao.StapleDao;
import borges.dimitrius.factory.DbConnectionFactoryTest;
import borges.dimitrius.model.entities.Staple;
import org.junit.jupiter.api.BeforeEach;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class StapleTest {

    protected final Staple defaultStaple = new Staple("Type1A");
    protected StapleDao stapleDao;
    protected Connection connection;

    @BeforeEach
    public void prepareDatabase(){
        try {

            connection = DbConnectionFactoryTest.getConnection();

            this.stapleDao = new StapleDao(connection);

            Statement stmt = connection.createStatement();

            stmt.execute("SET FOREIGN_KEY_CHECKS = 0");

            stmt.execute("TRUNCATE treatment");
            stmt.execute("TRUNCATE staple");
            stmt.execute("INSERT INTO staple (type) values('Type1A')");

            stmt.execute("SET FOREIGN_KEY_CHECKS = 1");

        } catch (SQLException e) {

            e.printStackTrace();
        }
    }
}
