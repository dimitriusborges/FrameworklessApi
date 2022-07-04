package borges.dimitrius.dao;

import borges.dimitrius.model.entities.Entity;
import borges.dimitrius.model.vo.Vo;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class MultiEntityDao extends Dao{

    public MultiEntityDao(Connection connection, String tableName) {
        super(connection, tableName);
    }

    public abstract <E extends Vo> E transformIntoVo(Entity entity) throws SQLException;
}
