package borges.dimitrius.model.vo;

import borges.dimitrius.model.dto.Dto;
import borges.dimitrius.model.dto.TreatmentDto;
import borges.dimitrius.model.entities.Patient;
import borges.dimitrius.model.entities.Treatment;
import borges.dimitrius.model.entities.RootFile;
import borges.dimitrius.model.entities.Staple;

public class TreatmentVo implements Vo{

    private final Treatment treatment;
    private Patient patient;
    private RootFile rootFile;
    private Staple staple;


    public TreatmentVo(Treatment procedure, Patient patient, RootFile rootFile, Staple staple) {
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

    //FIXME it doesn't look nice
    @Override
    public TreatmentDto toDto() {
        return new TreatmentDto(treatment.getId().toString(),
                treatment.getProcedureDate().toString(),
                patient.toDto(),
                String.valueOf(treatment.getTooth()),
                String.valueOf(treatment.getCanal1()),String.valueOf(treatment.getCanal2()),String.valueOf(treatment.getCanal3()),String.valueOf(treatment.getCanal4()),String.valueOf(treatment.getCanal5()),
                rootFile.toDto(), staple.toDto(), treatment.getObservation());
    }
}
