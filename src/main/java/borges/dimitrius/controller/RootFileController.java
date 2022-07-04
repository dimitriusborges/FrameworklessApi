package borges.dimitrius.controller;

import borges.dimitrius.dao.RootFileDao;
import borges.dimitrius.model.dto.RootFileDto;
import borges.dimitrius.model.entities.RootFile;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class RootFileController extends RestController implements HttpHandler, TransferableEntityHandler {

    /*
    GET
        /rootfile -> get all
        /rootfile/UUID -> get specific
        /rootfile?arg&arg -> get filtered (Not Implemented)
    POST
        /rootfile/ -> create new
    PUT
        /rootfile/UUID -> update existing
    DELETE
        /rootfile/UUID -> delete existing
     */

    private final Connection connection;

    public RootFileController(Connection connection) {
        this.connection = connection;
    }

    @Override
    public String getEndpoint() {
        return RestController.MAIN_ADDRESS + "rootfiles";
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        this.handleRequest(exchange);
    }

    @Override
    public Response get(ExchangeParams params){

        try {
            RootFileDao rootFileDao = new RootFileDao(this.connection);

            Response response;
            String args = params.getArg();

            if(args.isEmpty()){
               List<String> responseBody = fetchAllToTransfer(rootFileDao);
               response = new Response(200, String.valueOf(responseBody));
            }
            else{
                String result = fetchByIdToTransfer(rootFileDao, args);

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
    public Response post(ExchangeParams params){
        String reqBody = params.getReqBody();

        if(reqBody.isEmpty()){
            return new Response(400, "");
        }

        RootFile rootFile = (RootFile) getEntityFromBody(reqBody, new RootFileDto());

        RootFileDao rootFileDao = new RootFileDao(this.connection);

        try{
            rootFileDao.insert(rootFile);

            return new Response(200, "");

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

        RootFile rootFileNewData = (RootFile) this.getEntityFromBody(reqBody, new RootFileDto());

        try {
            RootFileDao rootFileDao = new RootFileDao(this.connection);

            RootFile rootFileToUpdate = (RootFile) fetchById(rootFileDao, params.getArg());

            if(rootFileToUpdate != null){
                rootFileToUpdate.copyFrom(rootFileNewData);

                rootFileDao.updateById(rootFileToUpdate);

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
        else {
            try{
                this.deleteById(new RootFileDao(this.connection), params.getArg());
                return new Response(200, "");
            } catch (SQLException e) {
                e.printStackTrace();

                return new Response(500, "");
            }
        }
    }
}
