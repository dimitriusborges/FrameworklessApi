package borges.dimitrius.controller;

import borges.dimitrius.model.dto.SymptomDto;
import borges.dimitrius.model.entities.Symptom;
import borges.dimitrius.setup.SymptomTest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Type;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SymptomControllerTest extends SymptomTest {

    private final Symptom defaultSymptomType2 = new Symptom("Symptom2");
    private SymptomController symptomTypeController;

    @Override
    @BeforeEach
    public void prepareDatabase() {
        super.prepareDatabase();

        try {

            Statement stmt = connection.createStatement();
            this.symptomTypeController = new SymptomController(this.connection);

            stmt.execute("SET FOREIGN_KEY_CHECKS = 0");

            stmt.execute("INSERT INTO symptom (description) values('Symptom2')");

            stmt.execute("SET FOREIGN_KEY_CHECKS = 1");

        } catch (SQLException e) {

            e.printStackTrace();
        }

    }

    @Test
    public void getSingleSymptomType(){
        ExchangeParams exchangeParamsMock = new ExchangeParams(this.symptomTypeController.getEndpoint(),
                this.symptomTypeController.getEndpoint(), "1", "get", "", null);

        Response response = this.symptomTypeController.get(exchangeParamsMock);
        assertEquals(response.getCode(), 200);

        Gson gson = new Gson();

        SymptomDto symptomTypeReceived = gson.fromJson(response.getBody(), SymptomDto.class);

        assertEquals(symptomTypeReceived.toEntity(), defaultSymptomType);

    }


    @Test
    public void getSymptomTypeList(){
        ExchangeParams exchangeParamsMock = new ExchangeParams(this.symptomTypeController.getEndpoint(),
                this.symptomTypeController.getEndpoint(), "", "get", "", null);

        Response response = this.symptomTypeController.get(exchangeParamsMock);
        assertEquals(response.getCode(), 200);

        Type symptomTypeDtoList = new TypeToken<List<SymptomDto>>() {}.getType();
        Gson gson = new Gson();
        List<SymptomDto> receivedSyptomType = gson.fromJson(response.getBody(), symptomTypeDtoList);

        if(receivedSyptomType.size() != 2){
            fail("Was expecting two symptoms registers, but " + receivedSyptomType.size() + " were found.");
        }

        assertEquals(receivedSyptomType.get(0).toEntity(), defaultSymptomType);
        assertEquals(receivedSyptomType.get(1).toEntity(), defaultSymptomType2);
    }


    @Test
    public void inserNewSymptomType(){
        ExchangeParams exchangeParamsMock = new ExchangeParams(this.symptomTypeController.getEndpoint(),
                this.symptomTypeController.getEndpoint(), "", "post",
                "{\"description\": \"New Description\"}", null);

        Symptom expectedSyptomType = new Symptom(3L, "New Description");

        Response response = symptomTypeController.post(exchangeParamsMock);
        assertEquals(response.getCode(), 200);

        try{
            Symptom symptomTypeInserted = this.symptomTypeDao.findById(3L);
            assertEquals(symptomTypeInserted, expectedSyptomType);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void updateExistingSymptomType(){
        ExchangeParams exchangeParamsMock = new ExchangeParams(this.symptomTypeController.getEndpoint(),
                this.symptomTypeController.getEndpoint(), "1", "put",
                "{\"description\": \"New Description\"}", null);

        Symptom updatingSymptomType = new Symptom(1L, "New Description");

        Response response = symptomTypeController.put(exchangeParamsMock);
        assertEquals(response.getCode(), 200);

        try{
            Symptom updatedSymptomType = this.symptomTypeDao.findById(1L);
            assertEquals(updatedSymptomType, updatingSymptomType);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void deleteExistingSymptomType(){
        ExchangeParams exchangeParamsMock = new ExchangeParams(this.symptomTypeController.getEndpoint(),
                this.symptomTypeController.getEndpoint(), "2", "delete", "", null);

        Response response = symptomTypeController.delete(exchangeParamsMock);
        assertEquals(response.getCode(), 200);

        try {
            Symptom deletedSymptomType = this.symptomTypeDao.findById(2L);
            assertNull(deletedSymptomType);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}