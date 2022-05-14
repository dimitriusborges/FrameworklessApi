package borges.dimitrius.dao;

import borges.dimitrius.model.entities.Entity;
import borges.dimitrius.model.entities.Staple;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StapleDao extends Dao{

    private enum StapleCols{
        ID("id"),
        TYPE("type");

        private String colName;

        StapleCols(String colName){
            this.colName = colName;
        }

        @Override
        public String toString() {
            return this.colName;
        }
    }

    public StapleDao(Connection connection){
        super(connection, "staple");
    }

    @Override
    protected List<Staple> loadFromResultSet(ResultSet resultSet) throws SQLException {
        List<Staple> staplesList = new ArrayList<>();

        while(resultSet.next()){
            staplesList.add(new Staple(
                    resultSet.getLong(1),
                    resultSet.getString(2)
            ));

        }

        return staplesList;
    }

    @Override
    public List<Staple> findAll() throws SQLException {
        return loadFromResultSet(super.fetchAll());
    }

    @Override
    @SuppressWarnings("unchecked")
    public Staple findById(Long id) throws SQLException {
        return (Staple) this.fetchById(id);
    }

    @Override
    public Map<String, Object> buildValMapping(Entity entity) {
        Staple staple = (Staple) entity;

        Map<String, Object> valMapping = new HashMap<>();

        valMapping.put(StapleCols.TYPE.colName, staple.getType());

        return valMapping;
    }
}
