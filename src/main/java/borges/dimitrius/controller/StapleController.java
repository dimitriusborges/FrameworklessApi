package borges.dimitrius.controller;

import borges.dimitrius.dao.StapleDao;
import borges.dimitrius.model.dto.StapleDto;
import borges.dimitrius.model.entities.Staple;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class StapleController extends RestController implements HttpHandler, TransferableEntityHandler {

      /*
    GET
        /staples -> get all
        /staples/UUID -> get specific
        /staples?arg&arg -> get filtered (Not Implemented)
    POST
        /staples/ -> create new
    PUT
        /staples/UUID -> update existing
    DELETE
        /staples/UUID -> delete existing
     */


    private final Connection connection;

    public StapleController(Connection connection){this.connection = connection;}

    @Override
    public String getEndpoint() {
        return RestController.MAIN_ADDRESS + "staples";
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        this.handleRequest(exchange);
    }

    @Override
    protected Response get(ExchangeParams params) {

        try{
            StapleDao stapleDao = new StapleDao(this.connection);

            Response response;
            String args = params.getArg();

            if(args.isEmpty()){
                List<String> responseBody = fetchAllToTransfer(stapleDao);

                if(responseBody.isEmpty()){
                    response = new Response(204, "");
                }
                else {
                    response  = new Response(200, String.valueOf(responseBody));
                }
            }
            else{
                String result = fetchByIdToTransfer(stapleDao, args);

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

        Staple staple = (Staple) getEntityFromBody(reqBody, new StapleDto());

        StapleDao stapleDao = new StapleDao(this.connection);

        try{
            stapleDao.insert(staple);

            return new Response(200, "");

        } catch (SQLException e) {
            e.printStackTrace();

            return new Response(500, "");
        }
    }


    @Override
    protected Response put(ExchangeParams params) {
        if(params.getArg().isEmpty()){
            return new Response(204, "");
        }

        String reqBody = params.getReqBody();

        if(reqBody.isEmpty()){
            return new Response(400, "");
        }

        Staple stapleNewData = (Staple) this.getEntityFromBody(reqBody, new StapleDto());

        try{
            StapleDao stapleDao = new StapleDao(this.connection);

            Staple stapleToUpdate = (Staple) fetchById(stapleDao, params.getArg());

            if(stapleToUpdate != null){
                stapleToUpdate.copyFrom(stapleNewData);

                stapleDao.updateById(stapleToUpdate);

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
        if(params.getArg().isEmpty()){
            return new Response(400, "");
        }
        else{
            try{
                this.deleteById(new StapleDao(this.connection), params.getArg());
                return new Response(200, "");
            } catch (SQLException e) {
                e.printStackTrace();

                return new Response(500, "");
            }
        }
    }
}
