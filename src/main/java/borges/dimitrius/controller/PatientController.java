package borges.dimitrius.controller;

import borges.dimitrius.dao.Dao;
import borges.dimitrius.dao.PatientDao;
import borges.dimitrius.model.dto.SharableEntity;
import borges.dimitrius.model.entities.Entity;
import borges.dimitrius.model.entities.Patient;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;


public class PatientController extends RestController implements HttpHandler {

    /*
    GET
        /patients -> get all
        /patients/UUID -> get specific
        /patients?arg&arg -> get filtered
    POST
        /patients/ -> create new
    PUT
        /patients/UUID -> update existing
    DELETE
        /patients/UUID -> delete existing
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
                response = fetchAll(patientDao);
            }
            else{
                response = fethById(patientDao, args);
            }

            return response;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new Response();
    }




}
