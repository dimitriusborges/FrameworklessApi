package borges.dimitrius.controller;

import borges.dimitrius.dao.MultiEntityDao;
import borges.dimitrius.model.entities.Entity;
import borges.dimitrius.model.vo.Vo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.List;

public interface TransferableMultiEntityHandler<T extends Vo> {

    default List<String> fetchAllToTransfer(MultiEntityDao multiEntityDao) throws SQLException{
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

}
