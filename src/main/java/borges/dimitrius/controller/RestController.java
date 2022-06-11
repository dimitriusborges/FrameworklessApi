package borges.dimitrius.controller;

import borges.dimitrius.model.vo.HttpRequestVo;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public abstract class RestController {

    protected static final String MAIN_ADDRESS = "/canal_api/";


    protected Map<String, String> mapParams(String rawParams){

        if(rawParams == null || rawParams.isEmpty()){
            return Collections.emptyMap();
        }

        return Pattern.compile("&").splitAsStream(rawParams).
                map(pair -> pair.split("=")).
                collect(Collectors.toMap(pair -> pair[0], pair -> pair[1]));

    }


    protected HttpRequestVo getRequestData(HttpExchange exchange){

        String context = this.getRequestContext(exchange);
        return new HttpRequestVo(
                context,
                this.getRequestAddr(exchange),
                this.getURIArgument(exchange, context));

    }

    private String getRequestContext(HttpExchange exchange){
        return exchange.getHttpContext().getPath().toLowerCase();
    }

    private String getURIArgument(HttpExchange exchange, String context){
        return getRequestAddr(exchange).replaceFirst(context, "").replaceFirst("/", "");
    }

    private String getRequestAddr(HttpExchange exchange){
        return exchange.getRequestURI().toString();
    }

    protected void sendOk(HttpExchange exchange) throws IOException {
        exchange.sendResponseHeaders(200, 0);
    }

    protected void close(HttpExchange exchange){
        exchange.close();
    }

    public abstract List<String> getAllControllerContexts();
}
