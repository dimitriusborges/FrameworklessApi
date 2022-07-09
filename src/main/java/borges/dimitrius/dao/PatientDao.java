package borges.dimitrius.dao;

import borges.dimitrius.model.entities.Entity;
import borges.dimitrius.model.entities.Patient;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class PatientDao extends Dao{

    private enum PatientCols {
        ID("id"),
        BIRTHDATE("birthdate"),
        NAME("name");

        private final String colName;

        PatientCols(String colName) {
            this.colName = colName;
        }

        @Override
        public String toString(){
            return this.colName;
        }
    }

    public PatientDao(Connection connection) {
        super(connection, "patient");
    }

    @Override
    protected List<Patient> loadFromResultSet(ResultSet resultSet) throws SQLException {

        List<Patient> pacientList = new ArrayList<>();

        while (resultSet.next()){

            pacientList.add(new Patient(
                    resultSet.getLong(1),
                    resultSet.getDate(2).toLocalDate(),
                    resultSet.getString(3)
            ));
        }

        return pacientList;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Patient findById(Long id) throws SQLException {
        return (Patient) this.fetchById(id);
    }

    @Override
    public List<Patient> findAll() throws SQLException{

        return loadFromResultSet(this.fetchAll());
    }

    @Override
    public Map<String, Object> buildValMapping(Entity entity) {
        Patient pacient = (Patient) entity;
        Map<String, Object> valMapping = new HashMap<>();

        valMapping.put(PatientCols.BIRTHDATE.colName, pacient.getBirthDate());
        valMapping.put(PatientCols.NAME.colName, pacient.getName());

        return valMapping;
    }




}
