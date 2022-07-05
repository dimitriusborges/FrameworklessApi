package borges.dimitrius.controller;

import borges.dimitrius.dao.PatientSymptomDao;
import borges.dimitrius.model.dto.PatientSymptomDto;
import borges.dimitrius.model.entities.PatientSymptom;
import borges.dimitrius.model.vo.PatientSymptomVo;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class PatientSymptomController extends RestController implements HttpHandler, TransferableMultiEntityHandler<PatientSymptomVo> {

    private final Connection connection;


    public PatientSymptomController(Connection connection){
        this.connection = connection;

    }
    @Override
    public String getEndpoint() {
        return RestController.MAIN_ADDRESS + "patientsymptoms";
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        this.handleRequest(exchange);
    }

    @Override
    protected Response get(ExchangeParams params) {
        try {
            PatientSymptomDao patientSymptomDao = new PatientSymptomDao(this.connection);
            Response  response;
            String args = params.getArg();

            if(args.isEmpty()){
                List<String> responseBody = fetchAllToTransfer(patientSymptomDao);
                response = new Response(200, String.valueOf(responseBody));
            }
            else{
                String result = fetchByIdToTransfer(patientSymptomDao, args);

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

        PatientSymptom patientSymptom = (PatientSymptom) getEntityFromBody(reqBody, new PatientSymptomDto());

        PatientSymptomDao patientSymptomDao = new PatientSymptomDao(connection);

        try{
            patientSymptomDao.insert(patientSymptom);

            return new Response(201, "");

        } catch (SQLException e) {
            e.printStackTrace();

            return new Response(500, "");
        }
    }

    @Override
    protected Response put(ExchangeParams params) {
        if(params.getArg().isEmpty()){
            return new Response(400, "");
        }

        String reqBody = params.getReqBody();

        if(reqBody.isEmpty()){
            return new Response(400, "");
        }

        PatientSymptom patientSymptomNewData = (PatientSymptom) this.getEntityFromBody(reqBody, new PatientSymptomDto());

        try{
            PatientSymptomDao patientSymptomDao = new PatientSymptomDao(this.connection);

            PatientSymptom patientSymptomToUpdate = (PatientSymptom) this.fetchById(patientSymptomDao, params.getArg());

            if(patientSymptomToUpdate != null){
                patientSymptomToUpdate.copyFrom(patientSymptomNewData);

                patientSymptomDao.updateById(patientSymptomToUpdate);

                return new Response(200, "");
            }
            else {
                return new Response(204, "");
            }
        } catch (SQLException e) {
            e.printStackTrace();

            return new Response(500, "");
        }
    }

    @Override
    protected Response delete(ExchangeParams params) {
        String arg = params.getArg();

        if(arg.isEmpty()){
            return new Response(400, "");
        }
        else{
            try {
                this.deleteById(new PatientSymptomDao(connection), arg);
                return new Response(200, "");
            } catch (SQLException e) {
                e.printStackTrace();
                return new Response(500, "");
            }
        }
    }
}
