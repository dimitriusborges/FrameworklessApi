package borges.dimitrius.controller;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ExchangeParams {

    private final String context;
    private final String addr;
    private final String arg;
    private final String reqMethod;
    private final String reqBody;

    private final HttpExchange exchange;

    public ExchangeParams(HttpExchange exchange){
        this.exchange = exchange;

        this.context = this.extractRequestContext();
        this.addr = this.extractRequestAddr();
        this.arg = this.extractURIArgument();
        this.reqMethod = this.extractRequestMethod();
        this.reqBody = this.extractReqBody();

    }

    private String extractRequestContext(){
        return exchange.getHttpContext().getPath().toLowerCase();
    }

    private String extractURIArgument(){
        return extractRequestAddr().replaceFirst(context, "").replaceFirst("/", "");
    }

    private String extractRequestAddr(){
        return exchange.getRequestURI().toString();
    }

    private String extractRequestMethod(){
        return exchange.getRequestMethod();
    }

    private String extractReqBody(){
        try {
            return new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }

    protected Map<String, String> mapParams(String rawParams){

        if(rawParams == null || rawParams.isEmpty()){
            return Collections.emptyMap();
        }

        return Pattern.compile("&").splitAsStream(rawParams).
                map(pair -> pair.split("=")).
                collect(Collectors.toMap(pair -> pair[0], pair -> pair[1]));

    }

    public String getContext() {
        return context;
    }

    public String getAddr() {
        return addr;
    }

    public String getArg() {
        return arg;
    }

    public String getReqMethod() {
        return reqMethod;
    }

    public String getReqBody() {
        return reqBody;
    }

    @Override
    public String toString() {
        return "HttpRequestVo{" +
                "context='" + context + '\'' +
                ", addr='" + addr + '\'' +
                ", arg='" + arg + '\'' +
                '}';
    }
}
