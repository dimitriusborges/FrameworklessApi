package borges.dimitrius.controller;

import com.sun.net.httpserver.HttpExchange;

import java.util.Collections;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ExchangeParams {

    private final String context;
    private final String addr;
    private final String arg;
    private final String reqMethod;

    private final HttpExchange exchange;

    public ExchangeParams(HttpExchange exchange){
        this.exchange = exchange;

        this.context = getRequestContext();
        this.addr = getRequestAddr();
        this.arg = getURIArgument();
        this.reqMethod = getRequestMethod();

    }

    private String getRequestContext(){
        return exchange.getHttpContext().getPath().toLowerCase();
    }

    private String getURIArgument(){
        return getRequestAddr().replaceFirst(context, "").replaceFirst("/", "");
    }

    private String getRequestAddr(){
        return exchange.getRequestURI().toString();
    }

    private String getRequestMethod(){
        return exchange.getRequestMethod();
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

    @Override
    public String toString() {
        return "HttpRequestVo{" +
                "context='" + context + '\'' +
                ", addr='" + addr + '\'' +
                ", arg='" + arg + '\'' +
                '}';
    }
}
