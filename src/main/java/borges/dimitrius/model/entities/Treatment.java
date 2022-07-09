package borges.dimitrius.model.entities;

import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

public class Treatment extends Entity{

    private LocalDate procedureDate;
    private Long patientId;
    private Integer tooth;
    private Integer canal1;
    private Integer canal2;
    private Integer canal3;
    private Integer canal4;
    private Integer canal5;
    private Long rootFileId;
    private Long stapleId;
    private String observation;

    public Treatment(Long id, LocalDate procedureDate, Long patientId, int tooth, Long fileId, Long stapleId, String observation) {
        this.id = id;
        this.procedureDate = procedureDate;
        this.patientId = patientId;
        this.tooth = tooth;
        this.rootFileId = fileId;
        this.stapleId = stapleId;
        this.observation = observation;
    }

    public Treatment(Long id, LocalDate procedureDate, Long patientId, int tooth,
                     int canal1,
                     int canal2,
                     int canal3,
                     int canal4,
                     int canal5,
                     Long fileId, Long stapleId, String observation) {
        this.id = id;
        this.procedureDate = procedureDate;
        this.patientId = patientId;
        this.tooth = tooth;
        this.canal1 = canal1;
        this.canal2 = canal2;
        this.canal3 = canal3;
        this.canal4 = canal4;
        this.canal5 = canal5;
        this.rootFileId = fileId;
        this.stapleId = stapleId;
        this.observation = observation;
    }

    public Treatment(LocalDate procedureDate, Long patientId, int tooth, Long fileId, Long stapleId, String observation) {
        this.procedureDate = procedureDate;
        this.patientId = patientId;
        this.tooth = tooth;
        this.rootFileId = fileId;
        this.stapleId = stapleId;
        this.observation = observation;
    }

    public LocalDate getProcedureDate() {
        return procedureDate;
    }

    public void setProcedureDate(LocalDate procedureDate) {
        this.procedureDate = procedureDate;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public Integer getTooth() {
        return tooth;
    }

    public void setTooth(Integer tooth) {
        this.tooth = tooth;
    }

    public Integer getCanal1() {
        return canal1;
    }

    public void setCanal1(Integer canal1) {
        this.canal1 = canal1;
    }

    public Integer getCanal2() {
        return canal2;
    }

    public void setCanal2(Integer canal2) {
        this.canal2 = canal2;
    }

    public Integer getCanal3() {
        return canal3;
    }

    public void setCanal3(Integer canal3) {
        this.canal3 = canal3;
    }

    public Integer getCanal4() {
        return canal4;
    }

    public void setCanal4(Integer canal4) {
        this.canal4 = canal4;
    }

    public Integer getCanal5() {
        return canal5;
    }

    public void setCanal5(Integer canal5) {
        this.canal5 = canal5;
    }

    public Long getRootFileId() {
        return rootFileId;
    }

    public void setRootFileId(Long rootFileId) {
        this.rootFileId = rootFileId;
    }

    public Long getStapleId() {
        return stapleId;
    }

    public void setStapleId(Long stapleId) {
        this.stapleId = stapleId;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }


    @Override
    public String toString() {
        return "Procedure{" +
                "id=" + id +
                ", procedureDate=" + procedureDate +
                ", patientId=" + patientId +
                ", tooth=" + tooth +
                ", canal1=" + canal1 +
                ", canal2=" + canal2 +
                ", canal3=" + canal3 +
                ", canal4=" + canal4 +
                ", canal5=" + canal5 +
                ", fileId=" + rootFileId +
                ", stapleId=" + stapleId +
                ", observation='" + observation + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Treatment procedure = (Treatment) o;
        return getTooth() == procedure.getTooth() && getCanal1() == procedure.getCanal1() && getCanal2() == procedure.getCanal2() && getCanal3() == procedure.getCanal3() && getCanal4() == procedure.getCanal4() && getCanal5() == procedure.getCanal5() && getProcedureDate().equals(procedure.getProcedureDate()) && getPatientId().equals(procedure.getPatientId()) && getRootFileId().equals(procedure.getRootFileId()) && getStapleId().equals(procedure.getStapleId()) && getObservation().equals(procedure.getObservation());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getProcedureDate(), getPatientId(), getTooth(), getCanal1(), getCanal2(), getCanal3(), getCanal4(), getCanal5(), getRootFileId(), getStapleId(), getObservation());
    }

    @Override
    public <T extends Entity> void copyFrom(T entityToCopy) {

        Treatment treatmentToCopy = (Treatment) entityToCopy;


        Integer tooth = treatmentToCopy.getTooth();
        this.tooth = tooth != null ? tooth : this.tooth;

        LocalDate procedureDate = treatmentToCopy.getProcedureDate();
        this.procedureDate = procedureDate != null? procedureDate : this.procedureDate;

        Integer canal1 = treatmentToCopy.getCanal1();
        this.canal1 = canal1 != null ? canal1 : this.canal1;

        Integer canal2 = treatmentToCopy.getCanal2();
        this.canal2 = canal2 != null ? canal2 : this.canal1;

        Integer canal3 = treatmentToCopy.getCanal3();
        this.canal3 = canal3 != null ? canal3 : this.canal1;

        Integer canal4 = treatmentToCopy.getCanal4();
        this.canal4 = canal4 != null ? canal4 : this.canal1;

        Integer canal5 = treatmentToCopy.getCanal5();
        this.canal5 = canal5 != null ? canal5 : this.canal1;

        String observation = treatmentToCopy.getObservation();
        this.observation = observation != null ? observation : this.observation;

        Long patientId = treatmentToCopy.getPatientId();
        this.patientId = patientId != null ? patientId : this.patientId;

        Long stapleId = treatmentToCopy.getStapleId();
        this.stapleId = stapleId != null ? stapleId : this.stapleId;

        Long rootFileId = treatmentToCopy.getRootFileId();
        this.rootFileId = rootFileId != null ? rootFileId: this.rootFileId;

    }
}
