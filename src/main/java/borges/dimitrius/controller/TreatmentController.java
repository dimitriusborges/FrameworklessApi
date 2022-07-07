package borges.dimitrius.controller;

import borges.dimitrius.dao.TreatmentDao;
import borges.dimitrius.model.dto.TreatmentDto;
import borges.dimitrius.model.entities.Treatment;
import borges.dimitrius.model.vo.TreatmentVo;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class TreatmentController extends RestController implements HttpHandler, TransferableMultiEntityHandler<TreatmentVo> {

    private final Connection connection;

    public TreatmentController(Connection connection) {
        this.connection = connection;
    }

    @Override
    public String getEndpoint() {
        return RestController.MAIN_ADDRESS + "treatments";
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        this.handleRequest(exchange);
    }

    @Override
    protected Response get(ExchangeParams params) {
        try{
            TreatmentDao treatmentDao = new TreatmentDao(this.connection);
            Response response;

            String args = params.getArg();

            if(args.isEmpty()){
                List<String> responseBody = fetchAllToTransfer(treatmentDao);
                response = new Response(200, String.valueOf(responseBody));
            }
            else{
                String result = this.fetchByIdToTransfer(treatmentDao, args);

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

        Treatment treatment = (Treatment) getEntityFromBody(reqBody, new TreatmentDto());

        TreatmentDao treatmentDao = new TreatmentDao(this.connection);

        try{
            treatmentDao.insert(treatment);
            return new Response(201, "");
        } catch (SQLException e) {
            e.printStackTrace();

            return new Response(500, "");
        }
    }

    @Override
    protected Response put(ExchangeParams params) {
        String arg = params.getArg();

        if(arg.isEmpty()){
            return new Response(400, "");
        }

        String reqBody = params.getReqBody();

        if(reqBody.isEmpty()){
            return new Response(400, "");
        }

        Treatment treatmentNewData = (Treatment) this.getEntityFromBody(reqBody, new TreatmentDto());

        try{
            TreatmentDao treatmentDao = new TreatmentDao(this.connection);

            Treatment treatmentToUpdate = (Treatment) fetchById(treatmentDao, arg);

            if(treatmentToUpdate != null){
                treatmentToUpdate.copyFrom(treatmentNewData);

                treatmentDao.updateById(treatmentToUpdate);

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
                this.deleteById(new TreatmentDao(connection), arg);
                return new Response(200, "");
            } catch (SQLException e) {
                e.printStackTrace();
                return new Response(500, "");
            }
        }
    }
}
