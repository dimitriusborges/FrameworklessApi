package borges.dimitrius.dao;

import borges.dimitrius.factory.DbConnectionFactoryTest;
import borges.dimitrius.model.entities.Entity;
import borges.dimitrius.model.entities.Staple;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StapleDaoTest {

    private final Staple defaultStaple = new Staple("Type1A");
    private StapleDao stapleDao;

    @BeforeEach
    public void prepareDatabase(){
        try {

            Connection connection = DbConnectionFactoryTest.getConnection();

            this.stapleDao = new StapleDao(connection);

            Statement stmt = connection.createStatement();

            stmt.execute("SET FOREIGN_KEY_CHECKS = 0");

            stmt.execute("TRUNCATE treatment");
            stmt.execute("TRUNCATE staple");
            stmt.execute("INSERT INTO staple (type) values('Type1A')");

            stmt.execute("SET FOREIGN_KEY_CHECKS = 1");

        } catch (SQLException e) {

            e.printStackTrace();
        }
    }

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