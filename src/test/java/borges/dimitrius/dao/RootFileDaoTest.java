package borges.dimitrius.dao;

import borges.dimitrius.factory.DbConnectionFactoryTest;
import borges.dimitrius.model.RootFile;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.*;

public class RootFileDaoTest {

    private final RootFile defaultRootFile = new RootFile("NameType", "Brand");

    @BeforeEach
    public void prepareDatabase(){
        try {
            Connection connection = DbConnectionFactoryTest.getConnection();

            Statement stmt = connection.createStatement();

            stmt.execute("TRUNCATE rootfile");

            stmt.execute("INSERT INTO rootfile (name_type, brand) values('NameType', 'Brand')");

        } catch (SQLException e) {

            e.printStackTrace();
        }
    }

    @Test
    public void insertNewRootFile(){
        RootFile newRootFile = new RootFile("aNameType", "aBrand");

        try {
            RootFileDao rootFileDao = new RootFileDao(DbConnectionFactoryTest.getConnection());
            rootFileDao.insert(newRootFile);

            List<RootFile> rootFiles = rootFileDao.findAll();

            if(rootFiles.size() > 2){
                fail("Was expecting two Root File registers (Default + new) at most, " + rootFiles.size() + " were found.");
            }

            RootFile rootFileFromDb = rootFiles.get(1);

            assertEquals(newRootFile, rootFileFromDb);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void findExistingRootFile(){

        try {
            RootFileDao rootFileDao = new RootFileDao(DbConnectionFactoryTest.getConnection());

            RootFile rootFileFromDb = rootFileDao.findById(1L);

            assertEquals(defaultRootFile, rootFileFromDb);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void updateExistingRootFile(){

        try {
            RootFileDao rootFileDao = new RootFileDao(DbConnectionFactoryTest.getConnection());

            RootFile rootFileFromDb = rootFileDao.findById(1L);

            RootFile rootFileToUpdate = new RootFile(rootFileFromDb.getId(),
                    rootFileFromDb.getNameType(),
                    "AnotherBrand");


            rootFileDao.updateById(rootFileToUpdate);

            RootFile updatedRootFile = rootFileDao.findById(1L);

            assertEquals(rootFileToUpdate, updatedRootFile);
            assertNotEquals(rootFileFromDb, updatedRootFile);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void deleteExistingRootFile(){

        try {
            RootFileDao rootFileDao = new RootFileDao(DbConnectionFactoryTest.getConnection());

            rootFileDao.deleteById(1L);

            assertNull(rootFileDao.findById(1L));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}