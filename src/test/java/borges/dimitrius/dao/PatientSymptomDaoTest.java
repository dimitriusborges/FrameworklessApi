package borges.dimitrius.dao;

import borges.dimitrius.factory.DbConnectionFactoryTest;
import borges.dimitrius.model.entities.Patient;
import borges.dimitrius.model.entities.PatientSymptom;
import borges.dimitrius.model.vo.PatientSymptomVo;
import borges.dimitrius.model.entities.Symptom;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PatientSymptomDaoTest {

    private final PatientSymptom defaultPatientSymptom = new PatientSymptom(1L,
            Date.valueOf("1970-01-1"),
            1L);
    private final Patient defaultPatient = new Patient(Date.valueOf("1970-01-1"), "Default");
    private final Symptom defaultSymptomType = new Symptom("DefaultDescription");

    private PatientSymptomDao patientSymptomDao;
    private PatientDao patientDao;
    private SymptomDao symptomTypeDao;



    @BeforeEach
    public void prepareDatabase(){

        try{
            Connection connection = DbConnectionFactoryTest.getConnection();
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

    @Test
    public void insertNewPatientSymptom(){
        PatientSymptom newPatientSymptom = new PatientSymptom(1L, Date.valueOf("1970-1-31"), 1L);

        try{
            patientSymptomDao.insert(newPatientSymptom);
            List<PatientSymptom> patientSymptomsList = patientSymptomDao.findAll();

            if(patientSymptomsList.size() > 2){
                fail("Was expecting two Patient Symptom registers (Default + new) at most, " + patientSymptomsList.size() + " were found.");
            }

            PatientSymptom patientSymptomFromDb = patientSymptomsList.get(1);

            assertEquals(newPatientSymptom, patientSymptomFromDb);

            PatientSymptomVo patientSymptomVo = patientSymptomDao.transformIntoVo(patientSymptomFromDb);

            assertEquals(patientSymptomVo.getPatientSymptom(), patientSymptomFromDb);
            assertEquals(patientSymptomVo.getPatient(), patientDao.findById(1L));
            assertEquals(patientSymptomVo.getSymptomType(), symptomTypeDao.findById(1L));

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Test
    public void findExistingPatientSymptom(){
        try{
            PatientSymptom patientSymptomFromDb = patientSymptomDao.findById(1L);

            assertEquals(patientSymptomFromDb, defaultPatientSymptom);

            PatientSymptomVo patientSymptomVo = patientSymptomDao.transformIntoVo(patientSymptomFromDb);
            assertEquals(patientSymptomVo.getPatientSymptom(), patientSymptomFromDb);
            assertEquals(patientSymptomVo.getPatient(), patientDao.findById(1L));
            assertEquals(patientSymptomVo.getSymptomType(), symptomTypeDao.findById(1L));


        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Test
    public void updateExistingPatientSymptom(){
        try{
            PatientSymptom patientSymptomFromDb = patientSymptomDao.findById(1L);

            PatientSymptom patientSymptomToUpdate = new PatientSymptom(patientSymptomFromDb.getPatientId(),
                    2L, Date.valueOf("1970-12-31"), 2L);

            patientSymptomDao.updateById(patientSymptomToUpdate);

            PatientSymptom updatedPatientSymptomFromDb = patientSymptomDao.findById(1L);

            assertEquals(patientSymptomToUpdate, updatedPatientSymptomFromDb);
            assertNotEquals(patientSymptomFromDb, updatedPatientSymptomFromDb);

            PatientSymptomVo patientSymptomVo = patientSymptomDao.transformIntoVo(updatedPatientSymptomFromDb);

            assertEquals(patientSymptomVo.getPatientSymptom(), updatedPatientSymptomFromDb);
            assertEquals(patientSymptomVo.getPatient(), patientDao.findById(2L));
            assertEquals(patientSymptomVo.getSymptomType(), symptomTypeDao.findById(2L));



        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Test
    public void deleteExistingPatientSymptom(){
        try{
            patientSymptomDao.deleteById(1L);

            assertNull(patientSymptomDao.findById(1L));
            assertNotNull(patientDao.findById(1L));
            assertNotNull(symptomTypeDao.findById(1L));

        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}