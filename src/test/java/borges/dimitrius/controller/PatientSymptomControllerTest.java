package borges.dimitrius.controller;

import borges.dimitrius.factory.DbConnectionFactoryTest;
import borges.dimitrius.model.dto.PatientSymptomDto;
import borges.dimitrius.model.entities.PatientSymptom;
import borges.dimitrius.setup.PatientSymptomTest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Type;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PatientSymptomControllerTest extends PatientSymptomTest {

    private PatientSymptomController patientSymptomController;

    protected final PatientSymptom defaultPatientSymptom2 = new PatientSymptom(2L,
            LocalDate.parse("1970-02-02"),2L);

    @Override
    @BeforeEach
    public void prepareDatabase() {
        super.prepareDatabase();

        try{
            this.patientSymptomController = new PatientSymptomController(this.connection);


            Statement stmt = connection.createStatement();

            stmt.execute("SET FOREIGN_KEY_CHECKS = 0");

            stmt.execute("INSERT INTO patient_symptom (symptom_type, report_date, patient_id) values(2, '1970-02-02', 2)");

            stmt.execute("SET FOREIGN_KEY_CHECKS = 1");

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void getSinglePatientSymptoms(){
        ExchangeParams exchangeParamsMock = new ExchangeParams(this.patientSymptomController.getEndpoint(),
                this.patientSymptomController.getEndpoint(), "1", "get", "", null);

        Response response = this.patientSymptomController.get(exchangeParamsMock);
        assertEquals(response.getCode(), 200);

        Gson gson = new Gson();

        PatientSymptomDto patientSymptomReceived = gson.fromJson(response.getBody(), PatientSymptomDto.class);

        assertEquals(patientSymptomReceived.toEntity(), this.defaultPatientSymptom);
    }

    @Test
    public void getListPatientSymptoms(){
        ExchangeParams exchangeParamsMock = new ExchangeParams(this.patientSymptomController.getEndpoint(),
                this.patientSymptomController.getEndpoint(), "", "get", "", null);

        Response response = this.patientSymptomController.get(exchangeParamsMock);
        assertEquals(response.getCode(), 200);

        Type patientSymptomDto = new TypeToken<List<PatientSymptomDto>>() {}.getType();
        Gson gson = new Gson();

        List<PatientSymptomDto> receivedPatientSymptoms = gson.fromJson(response.getBody(), patientSymptomDto);

        if(receivedPatientSymptoms.size() != 2){
            fail("Was expecting two patient reported symptoms registers, but " + receivedPatientSymptoms.size() + " were found.");
        }

        assertEquals(receivedPatientSymptoms.get(0).toEntity(), this.defaultPatientSymptom);
        assertEquals(receivedPatientSymptoms.get(1).toEntity(), this.defaultPatientSymptom2);

    }

    @Test
    public void insertNewPatientSymptom(){
        ExchangeParams exchangeParamsMock = new ExchangeParams(this.patientSymptomController.getEndpoint(),
                this.patientSymptomController.getEndpoint(), "", "post",
                "{\"reportDate\": \"1970-01-01\",\"patient\": {\"id\": \"1\"},\"symptom\": {\"id\": \"2\"}}",
                null);

        PatientSymptom patientSymptomExpected = new PatientSymptom(3L, 2L, LocalDate.parse("1970-01-01"), 1L);

        Response response = this.patientSymptomController.post(exchangeParamsMock);
        assertEquals(response.getCode(), 201);

        try {
            PatientSymptom patientSymptomInserted = this.patientSymptomDao.findById(3L);
            assertEquals(patientSymptomInserted, patientSymptomExpected);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void updateExistingPatientSymptom(){
        ExchangeParams exchangeParamsMock = new ExchangeParams(this.patientSymptomController.getEndpoint(),
                this.patientSymptomController.getEndpoint(), "1", "put",
                "{\"reportDate\": \"1970-02-02\",\"patient\": {\"id\": \"2\"},\"symptom\": {\"id\": \"1\"}}",
                null);

        PatientSymptom patientSymptomUpdating = new PatientSymptom(1L, 1L, LocalDate.parse("1970-02-02"), 2L);

        Response response = this.patientSymptomController.put(exchangeParamsMock);
        assertEquals(response.getCode(), 200);


        try {
            PatientSymptom patientSymptomUpdated = this.patientSymptomDao.findById(1L);
            assertEquals(patientSymptomUpdating, patientSymptomUpdated);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void deleteExistingPatientSymptom(){
        ExchangeParams exchangeParamsMock = new ExchangeParams(this.patientSymptomController.getEndpoint(),
                this.patientSymptomController.getEndpoint(), "1", "delete", "", null);

        Response response = this.patientSymptomController.delete(exchangeParamsMock);
        assertEquals(response.getCode(), 200);

        try{
            PatientSymptom deletedPatientSymptom = this.patientSymptomDao.findById(1L);
            assertNull(deletedPatientSymptom);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}