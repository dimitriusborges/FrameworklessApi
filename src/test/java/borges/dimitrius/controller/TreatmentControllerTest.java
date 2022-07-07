package borges.dimitrius.controller;

import borges.dimitrius.model.dto.TreatmentDto;
import borges.dimitrius.model.entities.Treatment;
import borges.dimitrius.setup.TreatmentTest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Type;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TreatmentControllerTest extends TreatmentTest {

    protected final Treatment defaultTreatment2 = new Treatment(2L, Date.valueOf("1970-02-2"),
            2L, 20, 0, 0, 0, 0, 0, 2L, 2L, "Some observation");

    private TreatmentController treatmentController;


    @Override
    @BeforeEach
    public void prepareDatabase() {
        super.prepareDatabase();

        try{
            this.treatmentController = new TreatmentController(this.connection);

            Statement stmt = connection.createStatement();

            stmt.execute("SET FOREIGN_KEY_CHECKS = 0");

            stmt.execute("INSERT INTO treatment (treatment_date, patient_id, tooth, file_id, staple_id, observation) " +
                    "values ('1970-02-02', 2, 20, 2, 2, 'Some observation')");

            stmt.execute("SET FOREIGN_KEY_CHECKS = 1");

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Test
    public void getSingleTreatment(){
        ExchangeParams exchangeParamsMock = new ExchangeParams(this.treatmentController.getEndpoint(),
                this.treatmentController.getEndpoint(), "1", "get", "", null);

        Response response = this.treatmentController.get(exchangeParamsMock);
        assertEquals(response.getCode(), 200);

        Gson gson = new Gson();

        TreatmentDto treatmentReceived = gson.fromJson(response.getBody(), TreatmentDto.class);

        assertEquals(treatmentReceived.toEntity(), this.defaultTreatment);
    }

    @Test
    public void getListTreatments(){
        ExchangeParams exchangeParamsMock = new ExchangeParams(this.treatmentController.getEndpoint(),
                this.treatmentController.getEndpoint(), "", "get", "", null);

        Response response = this.treatmentController.get(exchangeParamsMock);
        assertEquals(response.getCode(), 200);

        Type treatmentDto = new TypeToken<List<TreatmentDto>>() {}.getType();
        Gson gson = new Gson();

        List<TreatmentDto> receivedTreatments = gson.fromJson(response.getBody(), treatmentDto);

        if(receivedTreatments.size() != 2){
            fail("Was expecting two treatments registers, but " + receivedTreatments.size() + " were found.");
        }

        assertEquals(receivedTreatments.get(0).toEntity(), this.defaultTreatment);
        assertEquals(receivedTreatments.get(1).toEntity(), this.defaultTreatment2);
    }

    @Test
    public void insertNewTreatment(){
        ExchangeParams exchangeParamsMock = new ExchangeParams(this.treatmentController.getEndpoint(),
                this.treatmentController.getEndpoint(), "", "post",
                "{\"procedureDate\":\"2022-07-07\",\"tooth\":\"32\",\"canal1\":\"1\",\"canal2\":\"2\",\"canal3\":\"3\",\"canal4\":\"4\",\"canal5\":\"5\",\"observation\":\"No observations\",\"patient\":{\"id\":\"2\",\"birthdate\":\"1992-09-22\",\"name\":\"Monique Ferraz\"},\"rootFile\":{\"id\":\"2\",\"nameType\":\"C-PILOT\",\"brand\":\"EASY\"},\"staple\":{\"id\":\"2\",\"type\":\"Staple\"}}",
                null);

        Treatment treatmentExpected = new Treatment(
                3L, Date.valueOf("2022-07-07"),
                2L, 32, 1, 2, 3, 4, 5, 2L, 2L, "No observations");

        Response response = this.treatmentController.post(exchangeParamsMock);
        assertEquals(response.getCode(), 201);

        try{
            Treatment treatmentInserted = this.treatmentDao.findById(3L);
            assertEquals(treatmentExpected, treatmentInserted);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void updateExistingTreatment(){
        ExchangeParams exchangeParamsMock = new ExchangeParams(this.treatmentController.getEndpoint(),
                this.treatmentController.getEndpoint(), "1", "put",
                "{\"procedureDate\":\"2022-07-07\",\"tooth\":\"10\",\"canal1\":\"1\",\"canal2\":\"2\",\"canal3\":\"3\",\"canal4\":\"4\",\"canal5\":\"5\",\"observation\":\"No observations\",\"patient\":{\"id\":\"2\",\"birthdate\":\"1992-09-22\",\"name\":\"Monique Ferraz\"},\"rootFile\":{\"id\":\"2\",\"nameType\":\"C-PILOT\",\"brand\":\"EASY\"},\"staple\":{\"id\":\"2\",\"type\":\"Staple\"}}",
                null);

        Treatment treatmentUpdating = new Treatment(
                1L, Date.valueOf("2022-07-07"),
                2L, 10, 1, 2, 3, 4, 5, 2L, 2L, "No observations");

        Response response = this.treatmentController.put(exchangeParamsMock);
        assertEquals(response.getCode(), 200);

        try{
            Treatment treatmentUpdated = this.treatmentDao.findById(1L);
            assertEquals(treatmentUpdating, treatmentUpdated);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void deleteExistingTreatment(){
        ExchangeParams exchangeParamsMock = new ExchangeParams(this.treatmentController.getEndpoint(),
                this.treatmentController.getEndpoint(), "2", "delete", "", null);


        Response response = this.treatmentController.delete(exchangeParamsMock);
        assertEquals(response.getCode(), 200);

        try{
            Treatment treatmentDeleted = this.treatmentDao.findById(2L);
            assertNull(treatmentDeleted);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}