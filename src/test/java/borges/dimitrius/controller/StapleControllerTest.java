package borges.dimitrius.controller;

import borges.dimitrius.model.dto.StapleDto;
import borges.dimitrius.model.entities.Staple;
import borges.dimitrius.setup.StapleTest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Type;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StapleControllerTest extends StapleTest {

    private final Staple defaultStaple2 = new Staple("StapleType");
    private StapleController stapleController;

    @Override
    @BeforeEach
    public void prepareDatabase() {
        super.prepareDatabase();

        this.stapleController = new StapleController(this.connection);
        try {

            Statement stmt = this.connection.createStatement();
            stmt.execute("SET FOREIGN_KEY_CHECKS = 0");

            stmt.execute("INSERT INTO staple (type) values('StapleType')");

            stmt.execute("SET FOREIGN_KEY_CHECKS = 1");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getSingleStaple(){
        ExchangeParams exchangeParamsMock = new ExchangeParams(this.stapleController.getEndpoint(),
                this.stapleController.getEndpoint(), "1", "get", "", null);

        Response response = this.stapleController.get(exchangeParamsMock);
        assertEquals(response.getCode(), 200);

        Gson gson = new Gson();

        StapleDto stapleReceived = gson.fromJson(response.getBody(), StapleDto.class);

        assertEquals(stapleReceived.toEntity(), this.defaultStaple);
    }

    @Test
    public void getStapleList(){
        ExchangeParams exchangeParamsMock = new ExchangeParams(this.stapleController.getEndpoint(),
                this.stapleController.getEndpoint(), "", "get", "", null);

        Response response = this.stapleController.get(exchangeParamsMock);
        assertEquals(response.getCode(), 200);

        Type stapleDtoList = new TypeToken<List<StapleDto>>() {}.getType();
        Gson gson = new Gson();

        List<StapleDto> receivedStaples = gson.fromJson(response.getBody(), stapleDtoList);

        if(receivedStaples.size() != 2){
            fail("Was expecting two staples registers, but " + receivedStaples.size() + " were found.");
        }

        assertEquals(receivedStaples.get(0).toEntity(), this.defaultStaple);
        assertEquals(receivedStaples.get(1).toEntity(), this.defaultStaple2);

    }

    @Test
    public void insertNewStaple(){

        ExchangeParams exchangeParamsMock = new ExchangeParams(this.stapleController.getEndpoint(),
                this.stapleController.getEndpoint(), "", "post",
                "{\"type\": \"NewType\"\n}", null);

        Staple expectedStaple = new Staple(3L,"NewType");

        Response response = stapleController.post(exchangeParamsMock);
        assertEquals(response.getCode(),200);

        try{
            Staple insertedStaple = this.stapleDao.findById(3L);

            assertEquals(expectedStaple, insertedStaple);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void updateExistingStaple(){
        ExchangeParams exchangeParamsMock = new ExchangeParams(this.stapleController.getEndpoint(),
                this.stapleController.getEndpoint(), "1", "post",
                "{\"type\": \"Updated\"}", null);

        Staple updatingStaple = new Staple(1L, "Updated");

        Response response = this.stapleController.put(exchangeParamsMock);
        assertEquals(response.getCode(), 200);

        try {
            Staple updatedStaple = this.stapleDao.findById(1L);
            assertEquals(updatedStaple, updatingStaple);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void deleteExistingStaple(){
        ExchangeParams exchangeParamsMock = new ExchangeParams(this.stapleController.getEndpoint(),
                this.stapleController.getEndpoint(), "2", "post","", null);

        Response response = this.stapleController.delete(exchangeParamsMock);
        assertEquals(response.getCode(), 200);

        try{
            Staple deletesStaple = this.stapleDao.findById(2L);
            assertNull(deletesStaple);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }




}