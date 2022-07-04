package borges.dimitrius.dao;

import borges.dimitrius.model.entities.Symptom;
import borges.dimitrius.setup.SymptomTest;
import org.junit.jupiter.api.*;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SymptomDaoTest extends SymptomTest {

    @Test
    public void insertNewSymptomType(){
        Symptom newSymptomType = new Symptom("SomeDescription");

        try {
            symptomTypeDao.insert(newSymptomType);

            List<Symptom> symptomTypesList = symptomTypeDao.findAll();

            if(symptomTypesList.size() > 2){
                fail("Was expecting two Symptom Types registers (Default + new) at most, "
                        + symptomTypesList.size()
                        + " were found.");
            }

            Symptom symptomTypeFromDb = symptomTypesList.get(1);

            assertEquals(newSymptomType, symptomTypeFromDb);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void findExistingSymptomType(){
        try{
            Symptom symptomTypeFromDb = symptomTypeDao.findById(1L);

            assertEquals(defaultSymptomType, symptomTypeFromDb);

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Test
    public void updateExistingSymptomType(){
        try {
            Symptom symptomTypeFromDb = symptomTypeDao.findById(1L);

            Symptom symptomTypeToUpdate = new Symptom(symptomTypeFromDb.getId(),
                    "AnotherDescription");

            symptomTypeDao.updateById(symptomTypeToUpdate);

            Symptom symptomTypeUpdated = symptomTypeDao.findById(1L);

            assertEquals(symptomTypeToUpdate, symptomTypeUpdated);
            assertNotEquals(symptomTypeFromDb, symptomTypeUpdated);

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Test
    public void deleteExistingSymptomType(){
        try{
            symptomTypeDao.deleteById(1L);

            assertNull(symptomTypeDao.findById(1L));

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

}