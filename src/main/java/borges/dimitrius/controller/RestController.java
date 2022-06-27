package borges.dimitrius.controller;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;

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
            default -> response = forbiddenRequest();

        }

        this.sendResponse(exchange, response);

        this.close(exchange);

    }

    protected void sendResponse(HttpExchange exchange, Response response){

        byte[] body = response.getBody().getBytes();

        try {
            exchange.sendResponseHeaders(response.getCode(), body.length > 0 ? body.length : -1);

            if(body.length > 0) {
                OutputStream outputStream = exchange.getResponseBody();
                outputStream.write(body);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    protected void close(HttpExchange exchange){
        exchange.close();
    }

    protected Response get(ExchangeParams params){
        return forbiddenRequest();
    }

    protected Response post(ExchangeParams params){
        return forbiddenRequest();
    }

    protected Response put(ExchangeParams params){
        return forbiddenRequest();
    }

    protected Response delete(ExchangeParams params){
        return forbiddenRequest();
    }

    protected Response forbiddenRequest(){
        return new Response(401, "");
    }

}
