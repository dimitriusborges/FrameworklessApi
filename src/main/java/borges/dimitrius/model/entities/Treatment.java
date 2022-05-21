package borges.dimitrius.model.entities;

import java.util.Date;
import java.util.Objects;

public class Treatment extends Entity{

    private Date procedureDate;
    private Long patientId;
    private int tooth;
    private int canal1;
    private int canal2;
    private int canal3;
    private int canal4;
    private int canal5;
    private Long fileId;
    private Long stapleId;
    private String observation;

    public Treatment(Long id, Date procedureDate, Long patientId, int tooth, Long fileId, Long stapleId, String observation) {
        this.id = id;
        this.procedureDate = procedureDate;
        this.patientId = patientId;
        this.tooth = tooth;
        this.fileId = fileId;
        this.stapleId = stapleId;
        this.observation = observation;
    }

    public Treatment(Long id, Date procedureDate, Long patientId, int tooth,
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
        this.fileId = fileId;
        this.stapleId = stapleId;
        this.observation = observation;
    }

    public Treatment(Date procedureDate, Long patientId, int tooth, Long fileId, Long stapleId, String observation) {
        this.procedureDate = procedureDate;
        this.patientId = patientId;
        this.tooth = tooth;
        this.fileId = fileId;
        this.stapleId = stapleId;
        this.observation = observation;
    }

    public Date getProcedureDate() {
        return procedureDate;
    }

    public void setProcedureDate(Date procedureDate) {
        this.procedureDate = procedureDate;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public int getTooth() {
        return tooth;
    }

    public void setTooth(int tooth) {
        this.tooth = tooth;
    }

    public int getCanal1() {
        return canal1;
    }

    public void setCanal1(int canal1) {
        this.canal1 = canal1;
    }

    public int getCanal2() {
        return canal2;
    }

    public void setCanal2(int canal2) {
        this.canal2 = canal2;
    }

    public int getCanal3() {
        return canal3;
    }

    public void setCanal3(int canal3) {
        this.canal3 = canal3;
    }

    public int getCanal4() {
        return canal4;
    }

    public void setCanal4(int canal4) {
        this.canal4 = canal4;
    }

    public int getCanal5() {
        return canal5;
    }

    public void setCanal5(int canal5) {
        this.canal5 = canal5;
    }

    public Long getFileId() {
        return fileId;
    }

    public void setFileId(Long fileId) {
        this.fileId = fileId;
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
                ", fileId=" + fileId +
                ", stapleId=" + stapleId +
                ", observation='" + observation + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Treatment procedure = (Treatment) o;
        return getTooth() == procedure.getTooth() && getCanal1() == procedure.getCanal1() && getCanal2() == procedure.getCanal2() && getCanal3() == procedure.getCanal3() && getCanal4() == procedure.getCanal4() && getCanal5() == procedure.getCanal5() && getProcedureDate().equals(procedure.getProcedureDate()) && getPatientId().equals(procedure.getPatientId()) && getFileId().equals(procedure.getFileId()) && getStapleId().equals(procedure.getStapleId()) && getObservation().equals(procedure.getObservation());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getProcedureDate(), getPatientId(), getTooth(), getCanal1(), getCanal2(), getCanal3(), getCanal4(), getCanal5(), getFileId(), getStapleId(), getObservation());
    }
}
