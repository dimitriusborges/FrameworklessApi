package borges.dimitrius.controller;

import borges.dimitrius.model.dto.PatientDto;
import borges.dimitrius.model.entities.Patient;
import borges.dimitrius.setup.PatientTest;
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

class PatientControllerTest extends PatientTest {

    private final Patient defaultPatient2 = new Patient(LocalDate.parse("1971-02-02"), "Default2");
    private PatientController patientController;

    @Override
    @BeforeEach
    public void preparedatabase() {
        super.preparedatabase();

        patientController = new PatientController(this.connection);

        try {

            Statement stmt = connection.createStatement();
            stmt.execute("SET FOREIGN_KEY_CHECKS = 0");

            stmt.execute("INSERT INTO patient (birthdate, name) values('1971-02-02', 'Default2')");

            stmt.execute("SET FOREIGN_KEY_CHECKS = 1");

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void getSinglePatient(){

        ExchangeParams exchangeParamsMock = new ExchangeParams(this.patientController.getEndpoint(),
                this.patientController.getEndpoint(), "1", "get", "", null);

        Response response = this.patientController.get(exchangeParamsMock);
        assertEquals(response.getCode(), 200);

        Gson gson = new Gson();

        PatientDto patientReceived = gson.fromJson(response.getBody(), PatientDto.class);

        assertEquals(patientReceived.toEntity(), this.defaultPatient);
    }


    @Test
    public void getPatientList(){

        ExchangeParams exchangeParamsMock = new ExchangeParams(this.patientController.getEndpoint(),
                this.patientController.getEndpoint(), "", "get", "", null);


        Response response = this.patientController.get(exchangeParamsMock);
        assertEquals(response.getCode(), 200);

        Type patientDtoList = new TypeToken<List<PatientDto>>() {}.getType();
        Gson gson = new Gson();
        List<PatientDto> receivedPatients = gson.fromJson(response.getBody(), patientDtoList);


        if(receivedPatients.size() != 2){
            fail("Was expecting two patient registers, but " + receivedPatients.size() + " were found.");
        }

        assertEquals(receivedPatients.get(0).toEntity(), this.defaultPatient);
        assertEquals(receivedPatients.get(1).toEntity(), this.defaultPatient2);

    }

    @Test
    public void insertNewPatient(){

        ExchangeParams exchangeParamsMock = new ExchangeParams(this.patientController.getEndpoint(),
                this.patientController.getEndpoint(), "", "post",
                "{\"name\": \"New Patient\",\"birthdate\": \"1972-03-03\"\n}", null);

        Patient expectedPatient = new Patient(3L, LocalDate.parse("1972-03-03"), "New Patient");

        Response response = patientController.post(exchangeParamsMock);
        assertEquals(response.getCode(), 201);

        try {
            Patient patientInserted = this.patientDao.findById(3L);
            assertEquals(patientInserted, expectedPatient);

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    @Test
    public void updateExistingPatient(){
        ExchangeParams exchangeParamsMock = new ExchangeParams(this.patientController.getEndpoint(),
                this.patientController.getEndpoint(), "1", "put",
                "{\"name\": \"Updated Patient\",\"birthdate\": \"1972-03-03\"\n}", null);

        Patient updatingPatient = new Patient(1L, LocalDate.parse("1972-03-03"), "Updated Patient");

        Response response = this.patientController.put(exchangeParamsMock);
        assertEquals(response.getCode(), 200);


        try {
            Patient updatedPatient = this.patientDao.findById(1L);
            assertEquals(updatedPatient, updatingPatient);
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }


    @Test
    public void deleteExistingPatient(){
        ExchangeParams exchangeParamsMock = new ExchangeParams(this.patientController.getEndpoint(),
                this.patientController.getEndpoint(), "2", "delete","", null);

        Response response = this.patientController.delete(exchangeParamsMock);
        assertEquals(response.getCode(), 200);

        try {
            Patient deletedPatient = this.patientDao.findById(2L);
            assertNull(deletedPatient);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}