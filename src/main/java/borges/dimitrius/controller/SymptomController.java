package borges.dimitrius.controller;

import borges.dimitrius.dao.SymptomDao;
import borges.dimitrius.model.dto.SymptomDto;
import borges.dimitrius.model.entities.Symptom;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class SymptomController extends RestController implements HttpHandler, TransferableEntityHandler {

    private final Connection connection;

    public SymptomController(Connection connection) {
        this.connection = connection;
    }

    @Override
    public String getEndpoint() {
        return RestController.MAIN_ADDRESS + "symptoms";
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        this.handleRequest(exchange);

    }

    @Override
    protected Response get(ExchangeParams params) {
        try{
            SymptomDao symptomTypeDao = new SymptomDao(this.connection);
            Response response;
            String args = params.getArg();

            if(args.isEmpty()){
                List<String> responseBody = fetchAllToTransfer(symptomTypeDao);
                response = new Response(200, String.valueOf(responseBody));
            }
            else{
                String result = fetchByIdToTransfer(symptomTypeDao, args);

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
            return new Response(400,"");
        }
        Symptom symptomType = (Symptom) getEntityFromBody(reqBody, new SymptomDto());

        SymptomDao symptomTypeDao = new SymptomDao(connection);

        try{
            symptomTypeDao.insert(symptomType);
            return new Response(200, "");
        } catch (SQLException e) {
            e.printStackTrace();
            return new Response(500, "");
        }
    }

    @Override
    protected Response put(ExchangeParams params) {
        String arg = params.getArg();

        if(arg.isEmpty()){
            return new Response(204, "");
        }

        String reqBody = params.getReqBody();

        if(reqBody.isEmpty()){
            return new Response(204, "");
        }

        Symptom symptomTypeNewData = (Symptom) this.getEntityFromBody(reqBody, new SymptomDto());

        try{
            SymptomDao symptomTypeDao = new SymptomDao(connection);

            Symptom symptomTypeToUpdate = (Symptom) fetchById(symptomTypeDao, arg);

            if(symptomTypeToUpdate != null){
                symptomTypeToUpdate.copyFrom(symptomTypeNewData);

                symptomTypeDao.updateById(symptomTypeToUpdate);

                return new Response(200, "");
            }
            else{
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
            try{
                this.deleteById(new SymptomDao(connection), arg);
                return new Response(200, "");
            } catch (SQLException e) {
                e.printStackTrace();
                return new Response(500, "");
            }
        }
    }
}
