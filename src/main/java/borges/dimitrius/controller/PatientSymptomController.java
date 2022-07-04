package borges.dimitrius.controller;

import borges.dimitrius.dao.PatientSymptomDao;
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
                response = new Response(404, "");
            }

            return response;
        } catch (SQLException e) {
            e.printStackTrace();
            return new Response(500, "");
        }

    }
}
