package borges.dimitrius.dao;

import borges.dimitrius.model.vo.PatientSymptomVo;
import borges.dimitrius.model.entities.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PatientSymptomDao extends Dao{

    private enum PatientSymptomCols{
        ID("id"),
        SYMPTOM_TYPE("symptom_type"),
        REPORT_DATE("report_date"),
        PATIENT_ID("patient_id");

        private final String colName;

        PatientSymptomCols(String colName){
            this.colName = colName;
        }

        @Override
        public String toString(){
            return this.colName;
        }
    }

    public PatientSymptomDao(Connection connection){
        super(connection, "patient_symptom");
    }

    public PatientSymptomVo transformIntoDto(PatientSymptom patientSymptom) throws SQLException {
        PatientDao patientDao = new PatientDao(this.dbConn);
        Patient patient = patientDao.findById(patientSymptom.getPatientId());

        SymptomTypeDao symptomTypeDao = new SymptomTypeDao(this.dbConn);
        SymptomType symptomType = symptomTypeDao.findById(patientSymptom.getSymptomId());

        return new PatientSymptomVo(patientSymptom, symptomType, patient);

    }

    @Override
    protected List<PatientSymptom> loadFromResultSet(ResultSet resultSet) throws SQLException {
        List<PatientSymptom> patientSymptomList = new ArrayList<>();

        while(resultSet.next()){
            patientSymptomList.add(new PatientSymptom(
                    resultSet.getLong(1),
                    resultSet.getLong(2),
                    resultSet.getDate(3),
                    resultSet.getLong(4)
                    ));
        }

        return patientSymptomList;
    }

    @Override
    public List<PatientSymptom> findAll() throws SQLException {
        return loadFromResultSet(this.fetchAll());
    }

    @Override
    @SuppressWarnings("unchecked")
    public PatientSymptom findById(Long id) throws SQLException {
        return (PatientSymptom) fetchById(id);
    }

    @Override
    public Map<String, Object> buildValMapping(Entity entity) {
        PatientSymptom patientSymptom = (PatientSymptom) entity;

        Map<String, Object> valMapping = new HashMap<>();

        valMapping.put(PatientSymptomCols.SYMPTOM_TYPE.colName, patientSymptom.getSymptomId());
        valMapping.put(PatientSymptomCols.REPORT_DATE.colName, patientSymptom.getReportDate());
        valMapping.put(PatientSymptomCols.PATIENT_ID.colName, patientSymptom.getPatientId());


        return valMapping;
    }
}
