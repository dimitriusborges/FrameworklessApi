package borges.dimitrius.dao;

import borges.dimitrius.model.entities.Patient;
import borges.dimitrius.setup.PatientTest;
import org.junit.jupiter.api.*;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class PatientDaoTest extends PatientTest {

    @Test
    public void insertNewPatient(){
        Patient newPatient = new Patient(LocalDate.parse("2022-01-01"), "NewPatient");

        try {
            this.patientDao.insert(newPatient);

            List<Patient> patients = this.patientDao.findAll();

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
            Patient patientFromDb = patientDao.findById(1L);

            assertEquals(defaultPatient, patientFromDb);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void updateExistingPatient(){

        try {
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
            patientDao.deleteById(1L);

            assertNull(patientDao.findById(1L));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}