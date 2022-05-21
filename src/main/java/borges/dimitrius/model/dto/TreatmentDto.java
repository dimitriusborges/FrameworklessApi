package borges.dimitrius.model.dto;

import borges.dimitrius.model.entities.Patient;
import borges.dimitrius.model.entities.Treatment;
import borges.dimitrius.model.entities.RootFile;
import borges.dimitrius.model.entities.Staple;

public class TreatmentDto {

    private final Treatment treatment;
    private Patient patient;
    private RootFile rootFile;
    private Staple staple;


    public TreatmentDto(Treatment procedure, Patient patient, RootFile rootFile, Staple staple) {
        this.treatment = procedure;
        this.patient = patient;
        this.rootFile = rootFile;
        this.staple = staple;
    }

    public Treatment getTreatment() {
        return treatment;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public RootFile getRootFile() {
        return rootFile;
    }

    public void setRootFile(RootFile rootFile) {
        this.rootFile = rootFile;
    }

    public Staple getStaple() {
        return staple;
    }

    public void setStaple(Staple staple) {
        this.staple = staple;
    }
}
