package borges.dimitrius.dao;

import borges.dimitrius.model.vo.TreatmentVo;
import borges.dimitrius.model.entities.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TreatmentDao extends MultiEntityDao {



    private enum TreatmentCols {
        ID("id"),
        PROCEDURE_DATE("treatment_date"),
        PATIENT_ID("patient_id"),
        TOOTH("tooth"),
        CANAL1("canal1"),
        CANAL2("canal2"),
        CANAL3("canal3"),
        CANAL4("canal4"),
        CANAL5("canal5"),
        FILE_ID("file_id"),
        STAPLE_ID("staple_id"),
        OBSERVATION("observation");

        private final String colName;

        TreatmentCols(String colName){
            this.colName = colName;
        }

        @Override
        public String toString() {
            return this.colName;
        }
    }

    public TreatmentDao(Connection connection){
        super(connection, "treatment");
    }

    @Override
    protected List<Treatment> loadFromResultSet(ResultSet resultSet) throws SQLException {
        List<Treatment> procedures = new ArrayList<>();

        while(resultSet.next()){
            procedures.add(new Treatment(
                    resultSet.getLong(1),
                    resultSet.getDate(2).toLocalDate(),
                    resultSet.getLong(3),
                    resultSet.getInt(4),
                    resultSet.getInt(5),
                    resultSet.getInt(6),
                    resultSet.getInt(7),
                    resultSet.getInt(8),
                    resultSet.getInt(9),
                    resultSet.getLong(10),
                    resultSet.getLong(11),
                    resultSet.getString(12)
            ));
        }

        return procedures;
    }

    @Override
    public List<Treatment> findAll() throws SQLException {
        return loadFromResultSet(this.fetchAll());
    }

    @Override
    @SuppressWarnings("unchecked")
    public Treatment findById(Long id) throws SQLException {
        return (Treatment) fetchById(id);
    }

    @Override
    public Map<String, Object> buildValMapping(Entity entity) {
        Treatment treatment = (Treatment) entity;

        Map<String, Object> valMapping = new HashMap<>();

        valMapping.put(TreatmentCols.PROCEDURE_DATE.colName, treatment.getProcedureDate());
        valMapping.put(TreatmentCols.PATIENT_ID.colName, treatment.getPatientId());
        valMapping.put(TreatmentCols.TOOTH.colName, treatment.getTooth());
        valMapping.put(TreatmentCols.CANAL1.colName, treatment.getCanal1());
        valMapping.put(TreatmentCols.CANAL2.colName, treatment.getCanal2());
        valMapping.put(TreatmentCols.CANAL3.colName, treatment.getCanal3());
        valMapping.put(TreatmentCols.CANAL4.colName, treatment.getCanal4());
        valMapping.put(TreatmentCols.CANAL5.colName, treatment.getCanal5());
        valMapping.put(TreatmentCols.FILE_ID.colName, treatment.getRootFileId());
        valMapping.put(TreatmentCols.STAPLE_ID.colName, treatment.getStapleId());
        valMapping.put(TreatmentCols.OBSERVATION.colName, treatment.getObservation());

        return valMapping;
    }

    @Override
    public TreatmentVo transformIntoVo(Entity entity) throws SQLException {
        Treatment treatment = (Treatment) entity;

        PatientDao patientDao = new PatientDao(this.dbConn);
        Patient patient = patientDao.findById(treatment.getPatientId());

        RootFileDao rootFileDao = new RootFileDao(this.dbConn);
        RootFile rootFile = rootFileDao.findById(treatment.getRootFileId());

        StapleDao stapleDao = new StapleDao(this.dbConn);
        Staple staple = stapleDao.findById(treatment.getStapleId());

        return new TreatmentVo(treatment, patient, rootFile, staple);

    }
}
