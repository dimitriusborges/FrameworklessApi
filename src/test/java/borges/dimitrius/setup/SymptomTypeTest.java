package borges.dimitrius.setup;

import borges.dimitrius.dao.SymptomTypeDao;
import borges.dimitrius.factory.DbConnectionFactoryTest;
import borges.dimitrius.model.entities.SymptomType;
import org.junit.jupiter.api.BeforeEach;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class SymptomTypeTest {

    protected final SymptomType defaultSymptomType = new SymptomType("DefaultDescription");

    protected SymptomTypeDao symptomTypeDao;

    protected Connection connection;

    @BeforeEach
    public void prepareDatabase(){
        try {
            connection = DbConnectionFactoryTest.getConnection();

            this.symptomTypeDao = new SymptomTypeDao(connection);

            Statement stmt = connection.createStatement();

            stmt.execute("SET FOREIGN_KEY_CHECKS = 0");

            stmt.execute("TRUNCATE patient_symptom");
            stmt.execute("TRUNCATE symptom_type");
            stmt.execute("INSERT INTO symptom_type (description) values('DefaultDescription')");

            stmt.execute("SET FOREIGN_KEY_CHECKS = 1");

        } catch (SQLException e) {

            e.printStackTrace();
        }
    }
}
