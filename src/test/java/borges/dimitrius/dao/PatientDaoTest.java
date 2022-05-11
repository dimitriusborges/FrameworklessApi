package borges.dimitrius.dao;

import borges.dimitrius.factory.DbConnectionFactoryTest;
import borges.dimitrius.model.Patient;
import org.junit.jupiter.api.*;


import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class PatientDaoTest {

    private final Patient defaultPatient = new Patient(Date.valueOf("1970-01-1"), "Default");

    @BeforeEach
    public void prepareDatabase(){

        try {
            Connection connection = DbConnectionFactoryTest.getConnection();

            Statement stmt = connection.createStatement();

            stmt.execute("TRUNCATE patient");

            stmt.execute("INSERT INTO patient (birthdate, name) values('1970-01-01', 'Default')");

        } catch (SQLException e) {

            e.printStackTrace();
        }
    }

    @Test
    public void insertNewPatient(){
        Patient newPatient = new Patient(Date.valueOf("2022-01-01"), "NewPatient");

        try {
            PatientDao patientDao = new PatientDao(DbConnectionFactoryTest.getConnection());
            patientDao.insert(newPatient);

            List<Patient> patients = patientDao.findAll();

            //waiting at most one row;
            if(patients.size() > 2){
                fail("Was expecting two patient registers (Default + new) at most, " + patients.size() + " were found.");
            }

            Patient patientFromDb = patients.get(1);

            assertEquals(newPatient, patientFromDb);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void findExistingPatient(){

        try {
            PatientDao patientDao = new PatientDao(DbConnectionFactoryTest.getConnection());

            Patient patientFromDb = patientDao.findById(1L);

            assertEquals(defaultPatient, patientFromDb);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void updateExistingPatient(){

        try {
            PatientDao patientDao = new PatientDao(DbConnectionFactoryTest.getConnection());

            Patient patientFromDb = patientDao.findById(1L);

            Patient patientToUpdate = new Patient(patientFromDb.getId(),
                    patientFromDb.getBirthDate(),
                    "NotDefault");

            patientDao.updateById(patientToUpdate);

            Patient updatedPatientFromDb = patientDao.findById(1L);

            assertEquals(patientToUpdate, updatedPatientFromDb);
            assertNotEquals(patientFromDb, updatedPatientFromDb);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void deleteExistingPatient(){

        try {
            PatientDao patientDao = new PatientDao(DbConnectionFactoryTest.getConnection());

            patientDao.deleteById(1L);

            assertNull(patientDao.findById(1L));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}