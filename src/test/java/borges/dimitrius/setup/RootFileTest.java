package borges.dimitrius.setup;

import borges.dimitrius.dao.RootFileDao;
import borges.dimitrius.factory.DbConnectionFactoryTest;
import borges.dimitrius.model.entities.RootFile;
import org.junit.jupiter.api.BeforeEach;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class RootFileTest {

    protected final RootFile defaultRootFile = new RootFile("NameType", "Brand");
    protected Connection connection;
    protected RootFileDao rootFileDao;

    @BeforeEach
    public void prepareDatabase(){
        try {
            this.connection = DbConnectionFactoryTest.getConnection();

            this.rootFileDao = new RootFileDao(this.connection);

            Statement stmt = this.connection.createStatement();

            stmt.execute("SET FOREIGN_KEY_CHECKS = 0");

            stmt.execute("TRUNCATE treatment");

            stmt.execute("TRUNCATE rootfile");
            stmt.execute("INSERT INTO rootfile (type_name, brand) values('NameType', 'Brand')");

            stmt.execute("SET FOREIGN_KEY_CHECKS = 1");

        } catch (SQLException e) {

            e.printStackTrace();
        }
    }
}
