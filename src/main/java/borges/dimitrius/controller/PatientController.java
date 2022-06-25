package borges.dimitrius.controller;

import borges.dimitrius.dao.PatientDao;
import borges.dimitrius.model.dto.PatientDto;
import borges.dimitrius.model.entities.Patient;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;


public class PatientController extends RestController implements HttpHandler {

    /*
    GET
        /patients -> get all
        /patients/UUID -> get specific
        /patients?arg&arg -> get filtered (Not Implemented)
    POST
        /patients/ -> create new
    PUT
        /patients/UUID -> update existing (Not Implemented)
    DELETE
        /patients/UUID -> delete existing (Not Implemented)
     */

    private final Connection connection;

    public PatientController(Connection connection){
        this.connection = connection;
    }

    @Override
    public String getEndpoint(){
        return RestController.MAIN_ADDRESS + "patients";
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        this.handleRequest(exchange);

    }

    @Override
    public Response get(ExchangeParams params){

        try {
            PatientDao patientDao = new PatientDao(connection);

            Response response;
            String args = params.getArg();

            //FIXME: Arguments will be way more complex than that, when doing a filtered query, for example.
            // Right now we are only dealing with single arg representing an Entity id. I might not implement any further
            if(args.isEmpty()){
                response = fetchAllToResponse(patientDao);
            }
            else{
                response = fetchByIdToResponse(patientDao, args);
            }

            return response;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new Response(400, "");
    }

    @Override
    protected Response post(ExchangeParams params) {

        String reqBody = params.getReqBody();

        if(reqBody.isEmpty()){
            return new Response(400, "");
        }

        Patient patient = (Patient) getEntityFromBody(reqBody, new PatientDto());

        PatientDao patientDao = new PatientDao(connection);

        try {
            patientDao.insert(patient);

            return new Response(200, "");

        } catch (SQLException e) {
            e.printStackTrace();

            return new Response(500, "");
        }

    }
}
