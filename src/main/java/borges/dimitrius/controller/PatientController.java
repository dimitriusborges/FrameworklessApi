package borges.dimitrius.controller;

import borges.dimitrius.model.vo.HttpRequestVo;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;


public class PatientController extends RestController implements HttpHandler {

    public enum PatientCtx implements ControllerCtx {
        REST_GET_ALL(RestController.MAIN_ADDRESS + "patient");

        static{
            Arrays.stream(PatientCtx.values()).forEach(k -> keyValDic.put(k.ctxPath, k));
        }

        private final String ctxPath;

        PatientCtx(String path){
            this.ctxPath = path;
        }

        public String getCtxPath(){
            return this.ctxPath;
        }

        @Override
        public String toString(){
            return this.ctxPath;
        }

    }

    private static final PatientController INSTANCE = new PatientController();

    public static PatientController getInstance(){return PatientController.INSTANCE;}

    private PatientController(){}

    @Override
    public List<String> getAllControllerContexts() {
        return ControllerCtx.getAllCtxs(List.of(PatientCtx.values()));
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        HttpRequestVo requestData = getRequestData(exchange);

        System.out.println(requestData);

        PatientCtx requestCtx = (PatientCtx) ControllerCtx.findByValue(requestData.getContext());

        switch (requestCtx){

            case REST_GET_ALL -> {
                //TODO
            }
        }

        this.sendOk(exchange);
        this.close(exchange);

    }


}
