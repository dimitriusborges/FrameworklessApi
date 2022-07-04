package borges.dimitrius.setup;

import borges.dimitrius.dao.SymptomDao;
import borges.dimitrius.factory.DbConnectionFactoryTest;
import borges.dimitrius.model.entities.Symptom;
import org.junit.jupiter.api.BeforeEach;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class SymptomTest {

    protected final Symptom defaultSymptomType = new Symptom("DefaultDescription");

    protected SymptomDao symptomTypeDao;

    protected Connection connection;

    @BeforeEach
    public void prepareDatabase(){
        try {
            connection = DbConnectionFactoryTest.getConnection();

            this.symptomTypeDao = new SymptomDao(connection);

            Statement stmt = connection.createStatement();

            stmt.execute("SET FOREIGN_KEY_CHECKS = 0");

            stmt.execute("TRUNCATE patient_symptom");
            stmt.execute("TRUNCATE symptom");
            stmt.execute("INSERT INTO symptom (description) values('DefaultDescription')");

            stmt.execute("SET FOREIGN_KEY_CHECKS = 1");

        } catch (SQLException e) {

            e.printStackTrace();
        }
    }
}
