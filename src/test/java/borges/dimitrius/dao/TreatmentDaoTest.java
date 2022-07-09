package borges.dimitrius.dao;

import static org.junit.jupiter.api.Assertions.*;

import borges.dimitrius.factory.DbConnectionFactoryTest;
import borges.dimitrius.model.vo.TreatmentVo;
import borges.dimitrius.model.entities.Patient;
import borges.dimitrius.model.entities.Treatment;
import borges.dimitrius.model.entities.RootFile;
import borges.dimitrius.model.entities.Staple;
import borges.dimitrius.setup.TreatmentTest;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.List;

class TreatmentDaoTest extends TreatmentTest {


    @Test
    public void insertNewTreatment(){
        Treatment newTreatment = new Treatment(1L, LocalDate.parse("1970-01-01"), 1L, 15, 0, 0, 0, 0, 0, 1L, 1L, "Observation");

        try{
            treatmentDao.insert(newTreatment);

            List<Treatment> treatmentsList = treatmentDao.findAll();

            if(treatmentsList.size() > 2){
                fail("Was expecting two Treatment registers (Default + new) at most, " + treatmentsList.size() + " were found.");
            }

            Treatment treatmentFromDb = treatmentsList.get(1);

            assertEquals(treatmentFromDb, newTreatment);

            TreatmentVo treatmentDto = treatmentDao.transformIntoVo(treatmentFromDb);

            assertEquals(treatmentDto.getTreatment(), treatmentFromDb);
            assertEquals(treatmentDto.getPatient(), patientDao.findById(1L));
            assertEquals(treatmentDto.getRootFile(), rootFileDao.findById(1L));
            assertEquals(treatmentDto.getStaple(), stapleDao.findById(1L));


        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Test
    public void findExistingTreatment(){
        try{
            Treatment treatmentFromDb = treatmentDao.findById(1L);

            assertEquals(treatmentFromDb, defaultTreatment);

            TreatmentVo treatmentDto = treatmentDao.transformIntoVo(treatmentFromDb);
            assertEquals(treatmentDto.getTreatment(), treatmentFromDb);
            assertEquals(treatmentDto.getPatient(), patientDao.findById(1L));
            assertEquals(treatmentDto.getRootFile(), rootFileDao.findById(1L));
            assertEquals(treatmentDto.getStaple(), stapleDao.findById(1L));

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Test
    public void updateExistingTreatment(){
        try {
            Treatment treatmentFromDb = treatmentDao.findById(1L);

            Treatment treatmentToUpDate = new Treatment(1L, LocalDate.parse("1970-01-01"), 2L, 15, 0, 0, 0, 0, 0, 2L, 2L, "Default");

            treatmentDao.updateById(treatmentToUpDate);

            Treatment updatedTreatmentFromDb = treatmentDao.findById(1L);

            assertEquals(updatedTreatmentFromDb, treatmentToUpDate);
            assertNotEquals(updatedTreatmentFromDb, treatmentFromDb);

            TreatmentVo treatmentDto = treatmentDao.transformIntoVo(updatedTreatmentFromDb);

            assertEquals(treatmentDto.getTreatment(), updatedTreatmentFromDb);
            assertEquals(treatmentDto.getPatient(), patientDao.findById(2L));
            assertEquals(treatmentDto.getRootFile(), rootFileDao.findById(2L));
            assertEquals(treatmentDto.getStaple(), stapleDao.findById(2L));


        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Test
    public void deleteExistingTreatment(){
        try{
            treatmentDao.deleteById(1L);

            assertNull(treatmentDao.findById(1L));
            assertNotNull(patientDao.findById(1L));
            assertNotNull(stapleDao.findById(1L));
            assertNotNull(rootFileDao.findById(1L));
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}