package borges.dimitrius.controller;

import borges.dimitrius.model.dto.RootFileDto;
import borges.dimitrius.model.entities.RootFile;
import borges.dimitrius.setup.RootFileTest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Type;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RootFileControllerTest extends RootFileTest {

    private final RootFile defaultRootFile2 = new RootFile("AnotherName", "AnotherBrand");
    private RootFileController rootFileController;

    @Override
    @BeforeEach
    public void prepareDatabase() {
        super.prepareDatabase();

        rootFileController = new RootFileController(this.connection);


        try {

            Statement stmt = this.connection.createStatement();
            stmt.execute("SET FOREIGN_KEY_CHECKS = 0");

            stmt.execute("INSERT INTO rootfile (type_name, brand) values('AnotherName', 'AnotherBrand')");

            stmt.execute("SET FOREIGN_KEY_CHECKS = 1");

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void getSingleRootFile(){
        ExchangeParams exchangeParamsMock = new ExchangeParams(this.rootFileController.getEndpoint(),
                this.rootFileController.getEndpoint(), "1", "get", "", null);

        Response response = this.rootFileController.get(exchangeParamsMock);
        assertEquals(response.getCode(), 200);

        Gson gson = new Gson();

        RootFileDto rootFileReceived = gson.fromJson(response.getBody(), RootFileDto.class);

        assertEquals(rootFileReceived.toEntity(), this.defaultRootFile);
    }

    @Test
    public void getListRootFile(){
        ExchangeParams exchangeParamsMock = new ExchangeParams(this.rootFileController.getEndpoint(),
                this.rootFileController.getEndpoint(), "", "get", "", null);

        Response response = this.rootFileController.get(exchangeParamsMock);
        assertEquals(response.getCode(), 200);

        Type rootFileDtoList = new TypeToken<List<RootFileDto>>() {}.getType();
        Gson gson = new Gson();

        List<RootFileDto> receivedRootFiles = gson.fromJson(response.getBody(), rootFileDtoList);

        if(receivedRootFiles.size() != 2){
            fail("Was expecting two Root Files registers, but " + receivedRootFiles.size() + " were found.");
        }

        assertEquals(receivedRootFiles.get(0).toEntity(), this.defaultRootFile);
        assertEquals(receivedRootFiles.get(1).toEntity(), this.defaultRootFile2);

    }

    @Test
    public void insertNewRootFile(){
        ExchangeParams exchangeParamsMock = new ExchangeParams(this.rootFileController.getEndpoint(),
                this.rootFileController.getEndpoint(), "", "get",
                "{\"nameType\": \"New RootFile\",\"brand\": \"New Brand\"}", null);

        RootFile expectedRootFile = new RootFile("New RootFile", "New Brand");

        Response response = this.rootFileController.post(exchangeParamsMock);
        assertEquals(response.getCode(), 200);

        try{
            RootFile rootFileInserted = this.rootFileDao.findById(3L);
            assertEquals(rootFileInserted, expectedRootFile);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void updateExistingRootFile(){
        ExchangeParams exchangeParamsMock = new ExchangeParams(this.rootFileController.getEndpoint(),
                this.rootFileController.getEndpoint(), "1", "get",
                "{\"nameType\": \"New RootFile\",\"brand\": \"New Brand\"}", null);

        RootFile updatingRootFile = new RootFile(1L, "New RootFile", "New Brand");

        Response response = this.rootFileController.put(exchangeParamsMock);
        assertEquals(response.getCode(), 200);

        try {
            RootFile updatedRootFile = this.rootFileDao.findById(1L);
            assertEquals(updatingRootFile, updatedRootFile);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void deleteExistingRootRile(){
        ExchangeParams exchangeParamsMock = new ExchangeParams(this.rootFileController.getEndpoint(),
                this.rootFileController.getEndpoint(), "2", "get",
                "", null);

        Response response = this.rootFileController.delete(exchangeParamsMock);
        assertEquals(response.getCode(), 200);

        try{
            RootFile rootFile = this.rootFileDao.findById(2L);
            assertNull(rootFile);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}