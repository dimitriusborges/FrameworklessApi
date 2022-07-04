package borges.dimitrius.dao;

import borges.dimitrius.model.entities.Entity;
import borges.dimitrius.model.entities.Symptom;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SymptomDao extends Dao{

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

    public SymptomDao(Connection connection){
        super(connection, "symptom");
    }

    @Override
    protected List<Symptom> loadFromResultSet(ResultSet resultSet) throws SQLException {
        List<Symptom> symptomTypesList = new ArrayList<>();

        while(resultSet.next()){
            symptomTypesList.add( new Symptom(
               resultSet.getLong(1),
               resultSet.getString(2)
            ));
        }

        return symptomTypesList;
    }

    @Override
    public List<Symptom> findAll() throws SQLException {
        return loadFromResultSet(super.fetchAll());
    }

    @Override
    @SuppressWarnings("unchecked")
    public Symptom findById(Long id) throws SQLException {
        return (Symptom) this.fetchById(id);
    }

    @Override
    public Map<String, Object> buildValMapping(Entity entity) {
        Symptom symptomType = (Symptom) entity;

        Map<String, Object> valMapping = new HashMap<>();

        valMapping.put(SymptomTypeCols.DESCRIPTION.colName, symptomType.getDescription());

        return valMapping;
    }
}
