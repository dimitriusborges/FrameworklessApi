package borges.dimitrius.dao;

import borges.dimitrius.factory.DbConnectionFactoryTest;
import borges.dimitrius.model.entities.Entity;
import borges.dimitrius.model.entities.Staple;
import borges.dimitrius.setup.StapleTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StapleDaoTest extends StapleTest {

    @Test
    public void insertNewStaple(){
        Staple newStaple = new Staple("Type2A");

        try{
            stapleDao.insert(newStaple);

            List<Staple> staplesList = stapleDao.findAll();

            if(staplesList.size() > 2){
                fail("Was expecting two Staple registers (Default + new) at most, "
                        + staplesList.size()
                        + " were found.");
            }

            Staple stapleFromDb = staplesList.get(1);

            assertEquals(newStaple, stapleFromDb);

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Test
    public void findExistingStaple(){
        try{
            Staple stapleFromDb = stapleDao.findById(1L);

            assertEquals(defaultStaple, stapleFromDb);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Test
    public void updateExistingStaple(){
        try{
            Staple stapleFromDb = (Staple) stapleDao.fetchById(1L);

            Staple stapleToUpdate = new Staple(stapleFromDb.getId(), "Type3A");

            stapleDao.updateById(stapleToUpdate);

            Staple stapleUpdated = stapleDao.findById(1L);

            assertEquals(stapleToUpdate, stapleUpdated);
            assertNotEquals(stapleFromDb, stapleUpdated);

        }catch (SQLException e){
            e.printStackTrace();
        }

    }


    @Test
    public void deleteExistingStaple(){
        try{
            stapleDao.deleteById(1L);

            assertNull(stapleDao.findById(1L));
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

}