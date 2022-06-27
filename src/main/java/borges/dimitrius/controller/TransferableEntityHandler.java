package borges.dimitrius.controller;

import borges.dimitrius.dao.Dao;
import borges.dimitrius.model.dto.Dto;
import borges.dimitrius.model.dto.TransferableEntity;
import com.google.gson.Gson;

import java.sql.SQLException;
import java.util.List;

public interface TransferableEntityHandler {


    default Response fetchAllToResponse(Dao entityDao) throws SQLException {

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

    default Response fetchByIdToResponse(Dao entityDao, String args) throws SQLException {
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

    default Response deleteByIdToResponse(Dao entityDao, String id) throws SQLException {
        Response response = new Response();

        entityDao.deleteById(Long.parseLong(id));
        response.setCode(200);
        response.setBody("");


        return response;
    }

    default TransferableEntity getEntityFromBody(String reqBody, Dto dtoTransformer){
        Gson gson = new Gson();

        dtoTransformer = gson.fromJson(reqBody, dtoTransformer.getClass());

        return dtoTransformer.toEntity();
    }

}

