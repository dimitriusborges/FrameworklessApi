package borges.dimitrius.controller;

import borges.dimitrius.dao.Dao;
import borges.dimitrius.dao.MultiEntityDao;
import borges.dimitrius.model.entities.Entity;
import borges.dimitrius.model.vo.Vo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.List;

public interface TransferableMultiEntityHandler<T extends Vo> extends TransferableEntityHandler{

    @Override
    default <D extends Dao> List<String> fetchAllToTransfer(D entityDao) throws SQLException {
        MultiEntityDao multiEntityDao = (MultiEntityDao) entityDao;
        Gson gson = new Gson();

        List<Entity> allRegs = (List<Entity>) multiEntityDao.findAll();


        List<T> allRegsVo = allRegs.stream().map(e -> {
            try {
                return (T) multiEntityDao.transformIntoVo(e);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return null;
        }).toList();

        Type tType = new TypeToken<T>() {}.getType();

        return allRegsVo.stream().map(reg -> gson.toJson(reg.toDto(), tType)).toList();
    }

    @Override
    default String fetchByIdToTransfer(Dao entityDao, String id) throws SQLException {
        Entity entity = this.fetchById(entityDao, id);
        Gson gson = new Gson();

        if(entity == null){
            return "";
        }

        MultiEntityDao multiEntityDao = (MultiEntityDao) entityDao;

        T multiEntity = multiEntityDao.transformIntoVo(entity);

        Type tType = new TypeToken<T>() {}.getType();
        return gson.toJson(multiEntity.toDto(), tType);

    }
}
