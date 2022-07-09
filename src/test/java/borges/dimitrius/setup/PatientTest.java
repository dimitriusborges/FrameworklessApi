package borges.dimitrius.setup;

import borges.dimitrius.dao.PatientDao;
import borges.dimitrius.factory.DbConnectionFactoryTest;
import borges.dimitrius.model.entities.Patient;
import org.junit.jupiter.api.BeforeEach;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

public class PatientTest {

    protected final Patient defaultPatient = new Patient(LocalDate.parse("1970-01-01"), "Default");
    protected PatientDao patientDao;
    protected Connection connection;

    @BeforeEach
    public void preparedatabase(){

        try {
            connection = DbConnectionFactoryTest.getConnection();
            this.patientDao = new PatientDao(connection);

            Statement stmt = connection.createStatement();

            stmt.execute("SET FOREIGN_KEY_CHECKS = 0");

            stmt.execute("TRUNCATE treatment");
            stmt.execute("TRUNCATE patient_symptom");

            stmt.execute("TRUNCATE patient");
            stmt.execute("INSERT INTO patient (birthdate, name) values('1970-01-01', 'Default')");


            stmt.execute("SET FOREIGN_KEY_CHECKS = 1");

        } catch (SQLException e) {

            e.printStackTrace();
        }
    }
}
