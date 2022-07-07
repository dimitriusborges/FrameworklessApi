package borges.dimitrius.model.dto;

import borges.dimitrius.model.entities.Entity;
import borges.dimitrius.model.entities.Treatment;
import com.mysql.cj.log.Log;

import java.util.Date;

public class TreatmentDto implements Dto{

    private String id;
    private String procedureDate;
    private String tooth;
    private String canal1;
    private String canal2;
    private String canal3;
    private String canal4;
    private String canal5;
    private String observation;
    private PatientDto patient;
    private RootFileDto rootFile;
    private StapleDto staple;



    public TreatmentDto() {
    }

    public TreatmentDto(String id, String procedureDate, PatientDto patient, String tooth,
                        String canal1, String canal2, String canal3, String canal4, String canal5,
                        RootFileDto rootFile, StapleDto staple, String observation) {
        this.id = id;
        this.procedureDate = procedureDate;

        this.tooth = tooth;
        this.canal1 = canal1;
        this.canal2 = canal2;
        this.canal3 = canal3;
        this.canal4 = canal4;
        this.canal5 = canal5;
        this.observation = observation;
        this.patient = patient;
        this.rootFile = rootFile;
        this.staple = staple;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProcedureDate() {
        return procedureDate;
    }

    public void setProcedureDate(String procedureDate) {
        this.procedureDate = procedureDate;
    }

    public String getTooth() {
        return tooth;
    }

    public void setTooth(String tooth) {
        this.tooth = tooth;
    }

    public String getCanal1() {
        return canal1;
    }

    public void setCanal1(String canal1) {
        this.canal1 = canal1;
    }

    public String getCanal2() {
        return canal2;
    }

    public void setCanal2(String canal2) {
        this.canal2 = canal2;
    }

    public String getCanal3() {
        return canal3;
    }

    public void setCanal3(String canal3) {
        this.canal3 = canal3;
    }

    public String getCanal4() {
        return canal4;
    }

    public void setCanal4(String canal4) {
        this.canal4 = canal4;
    }

    public String getCanal5() {
        return canal5;
    }

    public void setCanal5(String canal5) {
        this.canal5 = canal5;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public PatientDto getPatient() {
        return patient;
    }

    public void setPatient(PatientDto patient) {
        this.patient = patient;
    }

    public RootFileDto getRootFile() {
        return rootFile;
    }

    public void setRootFile(RootFileDto rootFile) {
        this.rootFile = rootFile;
    }

    public StapleDto getStaple() {
        return staple;
    }

    public void setStaple(StapleDto staple) {
        this.staple = staple;
    }

    @Override
    public Treatment toEntity() {
        return new Treatment(this.id != null ? Long.parseLong(this.id) : 0,
                java.sql.Date.valueOf(this.procedureDate),
                Long.parseLong(this.patient.getId()),
                Integer.parseInt(this.tooth),
                Integer.parseInt(this.canal1),
                Integer.parseInt(this.canal2),
                Integer.parseInt(this.canal3),
                Integer.parseInt(this.canal4),
                Integer.parseInt(this.canal5),
                Long.parseLong(this.rootFile.getId()),
                Long.parseLong(this.staple.getId()),
                this.observation
                );
    }

}
