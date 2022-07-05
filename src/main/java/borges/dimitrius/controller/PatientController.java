package borges.dimitrius.controller;

import borges.dimitrius.dao.PatientDao;
import borges.dimitrius.model.dto.PatientDto;
import borges.dimitrius.model.entities.Patient;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;


public class PatientController extends RestController implements HttpHandler, TransferableEntityHandler {

    /*
    GET
        /patients -> get all
        /patients/UUID -> get specific
        /patients?arg&arg -> get filtered (Not Implemented)
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
            PatientDao patientDao = new PatientDao(this.connection);

            Response response;
            String args = params.getArg();

            //FIXME: Arguments will be way more complex than that, when doing a filtered query, for example.
            // Right now we are only dealing with single arg representing an Entity id. I might not implement any further
            if(args.isEmpty()){
                List<String> responseBody = fetchAllToTransfer(patientDao);
                response = new Response(200, String.valueOf(responseBody));

            }
            else{
                String result = fetchByIdToTransfer(patientDao, args);

                if(result.isEmpty()){
                    response = new Response(204, "");
                }
                else{
                    response = new Response(200, result);
                }
            }

            return response;

        } catch (SQLException e) {
            e.printStackTrace();
            return new Response(500, "");
        }

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

            return new Response(201, "");

        } catch (SQLException e) {
            e.printStackTrace();

            return new Response(500, "");
        }

    }

    @Override
    protected Response put(ExchangeParams params){

        if(params.getArg().isEmpty()){
            return new Response(400, "");
        }

        String reqBody = params.getReqBody();

        if(reqBody.isEmpty()){
            return new Response(400, "");
        }

        Patient patientNewData = (Patient) this.getEntityFromBody(reqBody, new PatientDto());

        try {

            PatientDao patientDao = new PatientDao(connection);

            Patient patientToUpdate = (Patient) fetchById(patientDao, params.getArg());

            if(patientToUpdate != null){

                patientToUpdate.copyFrom(patientNewData);

                patientDao.updateById(patientToUpdate);

                return new Response(200, "");
            }
            else{
                return new Response(204, "");
            }

        } catch (SQLException e) {
            e.printStackTrace();

            return  new Response(500, "");
        }
    }

    @Override
    protected Response delete(ExchangeParams params) {
        if(params.getArg().isEmpty()){
            return new Response(400, "");
        }
        else{
            try {
                this.deleteById(new PatientDao(connection), params.getArg());
                return  new Response(200, "");
            } catch (SQLException e) {
                e.printStackTrace();

                return new Response(500, "");
            }
        }
    }
}
