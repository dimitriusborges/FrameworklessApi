package borges.dimitrius.controller;

import borges.dimitrius.dao.Dao;
import borges.dimitrius.model.dto.Dto;
import borges.dimitrius.model.entities.TransferableEntity;
import com.google.gson.Gson;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface TransferableEntityHandler {


    default List<String> fetchAllToTransfer(Dao entityDao) throws SQLException {

        Gson gson = new Gson();

        List<TransferableEntity> allRegs = (List<TransferableEntity>) entityDao.findAll();

        if(allRegs.isEmpty()){
            return new ArrayList<>();
        }
        else{
            return allRegs.stream().map( reg -> gson.toJson(reg.toDto())).toList();
        }
    }

    default String fetchByIdToTransfer(Dao entityDao, String id) throws SQLException {
        Gson gson = new Gson();

        TransferableEntity entity = fetchById(entityDao, id);

        if(entity == null){
            return "";
        }
        else{
            return gson.toJson(entity.toDto());
        }
    }

    default TransferableEntity fetchById(Dao entityDao, String id) throws SQLException {
        return entityDao.findById(Long.parseLong(id));
    }

    default void deleteById(Dao entityDao, String id) throws SQLException {
        entityDao.deleteById(Long.parseLong(id));
    }

    default TransferableEntity getEntityFromBody(String reqBody, Dto dtoTransformer){
        Gson gson = new Gson();

        dtoTransformer = gson.fromJson(reqBody, dtoTransformer.getClass());

        return dtoTransformer.toEntity();
    }

}

