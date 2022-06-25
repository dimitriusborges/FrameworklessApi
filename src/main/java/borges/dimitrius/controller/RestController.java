package borges.dimitrius.controller;

import borges.dimitrius.dao.Dao;
import borges.dimitrius.model.dto.Dto;
import borges.dimitrius.model.dto.TransferableEntity;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.List;

public abstract class RestController {

    protected static final String MAIN_ADDRESS = "/canal_api/";

    public abstract String getEndpoint();

    public void handleRequest(HttpExchange exchange) throws IOException {

        ExchangeParams params = new ExchangeParams(exchange);

        Response response;

        switch (params.getReqMethod().toLowerCase()){
            case "get" -> response = get(params);
            case "post" -> response = post(params);
            case "put" -> response = put(params);
            case "delete" -> response = delete(params);
            default -> response = forbidden();

        }

        this.sendResponse(exchange, response);

        this.close(exchange);

    }

    protected void sendResponse(HttpExchange exchange, Response response){

        byte[] body = response.getBody().getBytes();

        try {
            exchange.sendResponseHeaders(response.getCode(), body.length);

            OutputStream outputStream = exchange.getResponseBody();
            outputStream.write(body);

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    protected void close(HttpExchange exchange){
        exchange.close();
    }

    protected Response get(ExchangeParams params){
        return forbidden();
    }

    protected Response post(ExchangeParams params){
        return forbidden();
    }

    protected Response put(ExchangeParams params){
        return forbidden();
    }

    protected Response delete(ExchangeParams params){
        return forbidden();
    }

    private Response forbidden(){
        return new Response(401, "");
    }

    protected Response fetchAllToResponse(Dao entityDao) throws SQLException {
        Gson gson = new Gson();
        Response response = new Response();

        List<TransferableEntity> allRegs = (List<TransferableEntity>) entityDao.findAll();

        if(allRegs.isEmpty()){
            response.setCode(204);
            response.setBody("");
        }
        else{
            List<String> dtoList = allRegs.stream().map( reg -> gson.toJson(reg.toDto())).toList();

            response.setCode(200);
            response.setBody(String.valueOf(dtoList));
        }

        return response;
    }

    public Response fetchByIdToResponse(Dao entityDao, String args) throws SQLException {
        Gson gson = new Gson();
        Response response = new Response();

        TransferableEntity entity = entityDao.findById(Long.parseLong(args));

        if(entity == null){
            response.setCode(204);
            response.setBody("");
        }
        else{
            response.setCode(200);
            response.setBody(gson.toJson(entity.toDto()));
        }

        return response;
    }

    public TransferableEntity getEntityFromBody(String reqBody, Dto dtoTransformer){
        Gson gson = new Gson();

        dtoTransformer = gson.fromJson(reqBody, dtoTransformer.getClass());

        return dtoTransformer.toEntity();
    }




}
