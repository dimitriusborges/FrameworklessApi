package borges.dimitrius.dao;

import borges.dimitrius.factory.DbConnectionFactoryTest;
import borges.dimitrius.model.entities.SymptomType;
import borges.dimitrius.setup.SymptomTypeTest;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SymptomTypeDaoTest extends SymptomTypeTest {

    @Test
    public void insertNewSymptomType(){
        SymptomType newSymptomType = new SymptomType("SomeDescription");

        try {
            symptomTypeDao.insert(newSymptomType);

            List<SymptomType> symptomTypesList = symptomTypeDao.findAll();

            if(symptomTypesList.size() > 2){
                fail("Was expecting two Symptom Types registers (Default + new) at most, "
                        + symptomTypesList.size()
                        + " were found.");
            }

            SymptomType symptomTypeFromDb = symptomTypesList.get(1);

            assertEquals(newSymptomType, symptomTypeFromDb);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void findExistingSymptomType(){
        try{
            SymptomType symptomTypeFromDb = symptomTypeDao.findById(1L);

            assertEquals(defaultSymptomType, symptomTypeFromDb);

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Test
    public void updateExistingSymptomType(){
        try {
            SymptomType symptomTypeFromDb = symptomTypeDao.findById(1L);

            SymptomType symptomTypeToUpdate = new SymptomType(symptomTypeFromDb.getId(),
                    "AnotherDescription");

            symptomTypeDao.updateById(symptomTypeToUpdate);

            SymptomType symptomTypeUpdated = symptomTypeDao.findById(1L);

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