package borges.dimitrius.model.dto;


import borges.dimitrius.model.entities.Patient;
import borges.dimitrius.model.entities.PatientSymptom;
import borges.dimitrius.model.entities.SymptomType;

public class PatientSymptomDto {

    private final PatientSymptom patientSymptomDecorated;
    private SymptomType symptomType;
    private Patient patient;

    public PatientSymptomDto(PatientSymptom patientSymptom, SymptomType symptomType, Patient patient){
        this.patientSymptomDecorated = patientSymptom;
        this.symptomType = symptomType;
        this.patient = patient;

    }

    public PatientSymptom getPatientSymptom() {
        return patientSymptomDecorated;
    }

    public SymptomType getSymptomType() {
        return symptomType;
    }

    public void setSymptomType(SymptomType symptomType) {
        this.symptomType = symptomType;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }
}
