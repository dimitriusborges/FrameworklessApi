package borges.dimitrius.model.entities;

import java.util.Date;
import java.util.Objects;

public class PatientSymptom extends Entity{

    private Long symptomId;
    private Date reportDate;
    private Long patientId;


    public PatientSymptom(Long id, Long symptomId, Date reportDate, Long patientId){
        this.id = id;
        this.symptomId = symptomId;
        this.reportDate = reportDate;
        this.patientId = patientId;
    }

    public PatientSymptom(Long symptomId, Date reportDate, Long patientId){
        this.symptomId = symptomId;
        this.reportDate = reportDate;
        this.patientId = patientId;
    }

    public Long getSymptomId() {
        return symptomId;
    }

    public void setSymptomId(Long symptomId) {
        this.symptomId = symptomId;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public Date getReportDate() {
        return reportDate;
    }

    public void setReportDate(Date reportDate) {
        this.reportDate = reportDate;
    }

    @Override
    public String toString() {
        return "PatientSymptom{" +
                "id=" + id +
                ", symptom=" + symptomId +
                ", reportDate=" + reportDate +
                ", patient=" + patientId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PatientSymptom that = (PatientSymptom) o;
        return getSymptomId().equals(that.getSymptomId()) && getReportDate().equals(that.getReportDate()) && getPatientId().equals(that.getPatientId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSymptomId(), getReportDate(), getPatientId());
    }
}
