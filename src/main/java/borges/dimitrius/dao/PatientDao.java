package borges.dimitrius.dao;

import borges.dimitrius.model.Entity;
import borges.dimitrius.model.Patient;

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

        private final String name;

        PatientCols(String name) {
            this.name = name;
        }

        @Override
        public String toString(){
            return this.name;
        }
    }

    public PatientDao(Connection connection) {
        super(connection, "pacient");
    }

    @Override
    public List<Patient> findAll() throws SQLException{

        ResultSet resultSet = super.fetchAll();

        List<Patient> pacientList = new ArrayList<>();

        while (resultSet.next()){

            pacientList.add(new Patient(
                    resultSet.getLong(1),
                    resultSet.getDate(2),
                    resultSet.getString(3)
            ));
        }

        return pacientList;
    }

    @Override
    public <E extends Entity> void insert(E entity) throws SQLException {
        super.insert(this.buildValMapping(entity));
    }

    @Override
    public <E extends Entity> void updateById(E entity, Long id) throws SQLException {
        //TODO: the condition building doesn't feel right
        super.update(this.buildValMapping(entity),
                "WHERE id = '" + id + "'");
    }

    @Override
    public <E extends Entity> Map<String, Object> buildValMapping(E entity) {
        Patient pacient = (Patient) entity;
        Map<String, Object> valMapping = new HashMap<>();

        valMapping.put(PatientCols.BIRTHDATE.toString(), pacient.getBirthDate());
        valMapping.put(PatientCols.NAME.toString(), pacient.getName());

        return valMapping;
    }
}
