package borges.dimitrius.dao;

import borges.dimitrius.model.entities.RootFile;
import borges.dimitrius.setup.RootFileTest;
import org.junit.jupiter.api.*;


import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.*;

public class RootFileDaoTest extends RootFileTest {

    @Test
    public void insertNewRootFile(){
        RootFile newRootFile = new RootFile("aNameType", "aBrand");

        try {
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
            RootFile rootFileFromDb = rootFileDao.findById(1L);

            assertEquals(defaultRootFile, rootFileFromDb);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void updateExistingRootFile(){

        try {
            RootFile rootFileFromDb = rootFileDao.findById(1L);

            RootFile rootFileToUpdate = new RootFile(rootFileFromDb.getId(),
                    rootFileFromDb.getTypeName(),
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
            rootFileDao.deleteById(1L);

            assertNull(rootFileDao.findById(1L));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}