package borges.dimitrius.controller;

import borges.dimitrius.dao.SymptomTypeDao;
import borges.dimitrius.model.dto.SymptomTypeDto;
import borges.dimitrius.model.entities.SymptomType;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class SymptomTypeController extends RestController implements HttpHandler, TransferableEntityHandler {

    private final Connection connection;

    public SymptomTypeController(Connection connection) {
        this.connection = connection;
    }

    @Override
    public String getEndpoint() {
        return RestController.MAIN_ADDRESS + "symptomtypes";
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        this.handleRequest(exchange);

    }

    @Override
    protected Response get(ExchangeParams params) {
        try{
            SymptomTypeDao symptomTypeDao = new SymptomTypeDao(this.connection);
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
        SymptomType symptomType = (SymptomType) getEntityFromBody(reqBody, new SymptomTypeDto());

        SymptomTypeDao symptomTypeDao = new SymptomTypeDao(connection);

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

        SymptomType symptomTypeNewData = (SymptomType) this.getEntityFromBody(reqBody, new SymptomTypeDto());

        try{
            SymptomTypeDao symptomTypeDao = new SymptomTypeDao(connection);

            SymptomType symptomTypeToUpdate = (SymptomType) fetchById(symptomTypeDao, arg);

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
                this.deleteById(new SymptomTypeDao(connection), arg);
                return new Response(200, "");
            } catch (SQLException e) {
                e.printStackTrace();
                return new Response(500, "");
            }
        }
    }
}
