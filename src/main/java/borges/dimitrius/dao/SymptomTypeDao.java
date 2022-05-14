package borges.dimitrius.dao;

import borges.dimitrius.model.entities.Entity;
import borges.dimitrius.model.entities.SymptomType;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SymptomTypeDao extends Dao{

    private enum SymptomTypeCols {
        ID("id"),
        DESCRIPTION("description");

        private final String colName;

        SymptomTypeCols(String colName){
            this.colName = colName;
        }

        @Override
        public String toString() {
            return this.colName;
        }
    }

    public SymptomTypeDao(Connection connection){
        super(connection, "symptom_type");
    }

    @Override
    protected List<SymptomType> loadFromResultSet(ResultSet resultSet) throws SQLException {
        List<SymptomType> symptomTypesList = new ArrayList<>();

        while(resultSet.next()){
            symptomTypesList.add( new SymptomType(
               resultSet.getLong(1),
               resultSet.getString(2)
            ));
        }

        return symptomTypesList;
    }

    @Override
    public List<SymptomType> findAll() throws SQLException {
        return loadFromResultSet(super.fetchAll());
    }

    @Override
    @SuppressWarnings("unchecked")
    public SymptomType findById(Long id) throws SQLException {
        return (SymptomType) this.fetchById(id);
    }

    @Override
    public Map<String, Object> buildValMapping(Entity entity) {
        SymptomType symptomType = (SymptomType) entity;

        Map<String, Object> valMapping = new HashMap<>();

        valMapping.put(SymptomTypeCols.DESCRIPTION.colName, symptomType.getDescription());

        return valMapping;
    }
}
