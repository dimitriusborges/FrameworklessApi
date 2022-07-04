package borges.dimitrius.model.vo;


import borges.dimitrius.model.dto.PatientSymptomDto;
import borges.dimitrius.model.entities.Patient;
import borges.dimitrius.model.entities.PatientSymptom;
import borges.dimitrius.model.entities.Symptom;

public class PatientSymptomVo implements Vo{

    private final PatientSymptom patientSymptom;
    private Symptom symptomType;
    private Patient patient;

    public PatientSymptomVo(PatientSymptom patientSymptom, Symptom symptomType, Patient patient){
        this.patientSymptom = patientSymptom;
        this.symptomType = symptomType;
        this.patient = patient;

    }

    public PatientSymptom getPatientSymptom() {
        return patientSymptom;
    }

    public Symptom getSymptomType() {
        return symptomType;
    }

    public void setSymptomType(Symptom symptomType) {
        this.symptomType = symptomType;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    @Override
    public PatientSymptomDto toDto() {
        return new PatientSymptomDto(patientSymptom.getId().toString(),
                patientSymptom.getReportDate().toString(),
                patient.toDto(), symptomType.toDto());
    }
}
