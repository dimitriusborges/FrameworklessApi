package borges.dimitrius.setup;

import borges.dimitrius.dao.PatientDao;
import borges.dimitrius.dao.PatientSymptomDao;
import borges.dimitrius.dao.SymptomDao;
import borges.dimitrius.factory.DbConnectionFactoryTest;
import borges.dimitrius.model.entities.Patient;
import borges.dimitrius.model.entities.PatientSymptom;
import borges.dimitrius.model.entities.Symptom;
import org.junit.jupiter.api.BeforeEach;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

public class PatientSymptomTest {

    protected final PatientSymptom defaultPatientSymptom = new PatientSymptom(1L,
            LocalDate.parse("1970-01-01"),1L);
    protected final Patient defaultPatient = new Patient(LocalDate.parse("1970-01-01"), "Default");
    protected final Symptom defaultSymptomType = new Symptom("DefaultDescription");

    protected PatientSymptomDao patientSymptomDao;
    protected PatientDao patientDao;
    protected SymptomDao symptomTypeDao;
    protected Connection connection;


    @BeforeEach
    public void prepareDatabase(){

        try{
            connection = DbConnectionFactoryTest.getConnection();
            this.patientSymptomDao = new PatientSymptomDao(connection);
            this.patientDao = new PatientDao(connection);
            this.symptomTypeDao = new SymptomDao(connection);

            Statement stmt = connection.createStatement();

            stmt.execute("SET FOREIGN_KEY_CHECKS = 0");

            stmt.execute("TRUNCATE patient");
            stmt.execute("INSERT INTO patient (birthdate, name) values('1970-01-01', 'Default')");
            stmt.execute("INSERT INTO patient (birthdate, name) values('1970-01-01', 'DefaultTwo')");

            stmt.execute("TRUNCATE symptom");
            stmt.execute("INSERT INTO symptom (description) values('DefaultDescription')");
            stmt.execute("INSERT INTO symptom (description) values('AnotherDescription')");

            stmt.execute("TRUNCATE patient_symptom");
            stmt.execute("INSERT INTO patient_symptom (symptom_type, report_date, patient_id) values(1, '1970-01-1', 1)");

            stmt.execute("SET FOREIGN_KEY_CHECKS = 1");

        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
