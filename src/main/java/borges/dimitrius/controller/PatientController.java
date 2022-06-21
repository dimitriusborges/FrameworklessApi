package borges.dimitrius.controller;

import borges.dimitrius.dao.PatientDao;
import borges.dimitrius.model.entities.Patient;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
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
            Gson gson = new Gson();

            String arg = params.getArg();

            Response response = new Response();

            //FIXME: Arguments will be way more complex than that, but I might not implement any further
            if(arg.isEmpty()){

                List<Patient> allPatients = patientDao.findAll();

                if(allPatients.isEmpty()){
                    response.setCode(204);
                    response.setBody("");
                }
                else{
                    List<String> patientsDto = allPatients.stream()
                            .map(patient -> gson.toJson(patient.toDto())).toList();

                    response.setCode(200);
                    response.setBody(String.valueOf(patientsDto));
                }

            }

            else{
                Patient patient = patientDao.findById(Long.parseLong(arg));

                if(patient == null){
                    response.setCode(204);
                    response.setBody("");
                }
                else {
                    response.setCode(200);
                    response.setBody(gson.toJson(patient.toDto()));
                }


            }

            return response;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new Response();
    }




}
