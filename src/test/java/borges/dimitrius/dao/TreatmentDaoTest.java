package borges.dimitrius.dao;

import static org.junit.jupiter.api.Assertions.*;

import borges.dimitrius.factory.DbConnectionFactoryTest;
import borges.dimitrius.model.vo.TreatmentVo;
import borges.dimitrius.model.entities.Patient;
import borges.dimitrius.model.entities.Treatment;
import borges.dimitrius.model.entities.RootFile;
import borges.dimitrius.model.entities.Staple;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

class TreatmentDaoTest {

    private final Treatment defaultTreatment = new Treatment(Date.valueOf("1970-01-1"),
            1L, 10, 1L, 1L, "No canal measure");
    private final Patient defaultPatient = new Patient(Date.valueOf("1970-01-1"), "Default");
    private final RootFile defaultRootFile = new RootFile("NameType", "Brand");
    private final Staple defaultStaple = new Staple("Type1A");

    private TreatmentDao treatmentDao;
    private PatientDao patientDao;
    private RootFileDao rootFileDao;
    private StapleDao stapleDao;

    @BeforeEach
    public void prepareDatabase(){
        try{
            Connection connection = DbConnectionFactoryTest.getConnection();

            this.treatmentDao = new TreatmentDao(connection);
            this.patientDao = new PatientDao(connection);
            this.rootFileDao = new RootFileDao(connection);
            this.stapleDao = new StapleDao(connection);

            Statement stmt = connection.createStatement();

            stmt.execute("SET FOREIGN_KEY_CHECKS = 0");

            stmt.execute("TRUNCATE patient");
            stmt.execute("INSERT INTO patient (birthdate, name) values('1970-01-01', 'Default')");
            stmt.execute("INSERT INTO patient (birthdate, name) values('1970-01-01', 'DefaultTwo')");


            stmt.execute("TRUNCATE rootfile");
            stmt.execute("INSERT INTO rootfile (type_name, brand) values('NameType', 'Brand')");
            stmt.execute("INSERT INTO rootfile (type_name, brand) values('NameType', 'Brand2')");

            stmt.execute("TRUNCATE staple");
            stmt.execute("INSERT INTO staple (type) values('Type1A')");
            stmt.execute("INSERT INTO staple (type) values('Type1B')");


            stmt.execute("TRUNCATE treatment");
            stmt.execute("INSERT INTO treatment (treatment_date, patient_id, tooth, file_id, staple_id, observation) " +
                    "values ('1970-01-01', 1, 10, 1, 1, 'No canal measure')");


            stmt.execute("SET FOREIGN_KEY_CHECKS = 1");

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Test
    public void insertNewTreatment(){
        Treatment newTreatment = new Treatment(Date.valueOf("1970-01-1"), 1L, 15, 1L, 1L, "Observation");

        try{
            treatmentDao.insert(newTreatment);

            List<Treatment> treatmentsList = treatmentDao.findAll();

            if(treatmentsList.size() > 2){
                fail("Was expecting two Treatment registers (Default + new) at most, " + treatmentsList.size() + " were found.");
            }

            Treatment treatmentFromDb = treatmentsList.get(1);

            assertEquals(treatmentFromDb, newTreatment);

            TreatmentVo treatmentDto = treatmentDao.transformIntoDto(treatmentFromDb);

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

            TreatmentVo treatmentDto = treatmentDao.transformIntoDto(treatmentFromDb);
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

            Treatment treatmentToUpDate = new Treatment(treatmentFromDb.getFileId(),
                    Date.valueOf("1970-01-31"), 2L, 15, 2L, 2L, "Default");

            treatmentDao.updateById(treatmentToUpDate);

            Treatment updatedTreatmentFromDb = treatmentDao.findById(1L);

            assertEquals(updatedTreatmentFromDb, treatmentToUpDate);
            assertNotEquals(updatedTreatmentFromDb, treatmentFromDb);

            TreatmentVo treatmentDto = treatmentDao.transformIntoDto(updatedTreatmentFromDb);

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