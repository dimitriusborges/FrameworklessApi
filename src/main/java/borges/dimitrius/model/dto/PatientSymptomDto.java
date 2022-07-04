package borges.dimitrius.model.dto;

import borges.dimitrius.model.entities.PatientSymptom;

import java.sql.Date;

public class PatientSymptomDto implements Dto{

    private String id;
    private String reportDate;
    private PatientDto patient;
    private SymptomDto symptom;

    public PatientSymptomDto(String id, String reportDate, PatientDto patientDto, SymptomDto symptomTypeDto) {
        this.id = id;
        this.reportDate = reportDate;
        this.patient = patientDto;
        this.symptom = symptomTypeDto;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getReportDate() {
        return reportDate;
    }

    public void setReportDate(String reportDate) {
        this.reportDate = reportDate;
    }

    public PatientDto getPatient() {
        return patient;
    }

     public void setPatient(PatientDto patient) {
            this.patient = patient;
        }

    public SymptomDto getSymptom() {
        return symptom;
    }

    public void setSymptom(SymptomDto symptom) {
        this.symptom = symptom;
    }

    @Override
    public PatientSymptom toEntity() {

        return new PatientSymptom(this.id != null ? Long.parseLong(this.id): 0,
                this.symptom.toEntity().getId(),
                Date.valueOf(this.reportDate),
                this.patient.toEntity().getId());
    }

    @Override
    public String toString() {
        return "PatientSymptomDto{" +
                "id='" + id + '\'' +
                ", reportDate='" + reportDate + '\'' +
                ", patientDto=" + patient.toString() +
                ", symptomTypeDto=" + symptom.toString() +
                '}';
    }
}
