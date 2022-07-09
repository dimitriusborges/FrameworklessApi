package borges.dimitrius.setup;

import borges.dimitrius.dao.PatientDao;
import borges.dimitrius.dao.RootFileDao;
import borges.dimitrius.dao.StapleDao;
import borges.dimitrius.dao.TreatmentDao;
import borges.dimitrius.factory.DbConnectionFactoryTest;
import borges.dimitrius.model.entities.Patient;
import borges.dimitrius.model.entities.RootFile;
import borges.dimitrius.model.entities.Staple;
import borges.dimitrius.model.entities.Treatment;
import org.junit.jupiter.api.BeforeEach;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

public class TreatmentTest {

    protected final Treatment defaultTreatment = new Treatment(1L, LocalDate.parse("1970-01-01"),
            1L, 10, 0, 0, 0, 0, 0, 1L, 1L, "No canal measure");
    protected final Patient defaultPatient = new Patient(LocalDate.parse("1970-01-01"), "Default");
    protected final RootFile defaultRootFile = new RootFile("NameType", "Brand");
    protected final Staple defaultStaple = new Staple("Type1A");

    protected TreatmentDao treatmentDao;
    protected PatientDao patientDao;
    protected RootFileDao rootFileDao;
    protected StapleDao stapleDao;

    protected Connection connection;

    @BeforeEach
    public void prepareDatabase(){
        try{
            connection = DbConnectionFactoryTest.getConnection();

            this.treatmentDao = new TreatmentDao(connection);
            this.patientDao = new PatientDao(connection);
            this.rootFileDao = new RootFileDao(connection);
            this.stapleDao = new StapleDao(connection);

            Statement stmt = connection.createStatement();

            stmt.execute("SET FOREIGN_KEY_CHECKS = 0");

            stmt.execute("TRUNCATE patient");
            stmt.execute("INSERT INTO patient (birthdate, name) values('1970-01-01', 'Default')");
            stmt.execute("INSERT INTO patient (birthdate, name) values('1970-01-01', 'DefaultTwo')");


            stmt.execute("TRUNCATE rootfile");
            stmt.execute("INSERT INTO rootfile (type_name, brand) values('NameType', 'Brand')");
            stmt.execute("INSERT INTO rootfile (type_name, brand) values('NameType', 'Brand2')");

            stmt.execute("TRUNCATE staple");
            stmt.execute("INSERT INTO staple (type) values('Type1A')");
            stmt.execute("INSERT INTO staple (type) values('Type1B')");


            stmt.execute("TRUNCATE treatment");
            stmt.execute("INSERT INTO treatment (treatment_date, patient_id, tooth, file_id, staple_id, observation) " +
                    "values ('1970-01-01', 1, 10, 1, 1, 'No canal measure')");


            stmt.execute("SET FOREIGN_KEY_CHECKS = 1");

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

}
