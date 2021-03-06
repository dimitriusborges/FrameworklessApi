package borges.dimitrius.model.entities;

import borges.dimitrius.model.dto.PatientDto;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Objects;

public class Patient extends TransferableEntity {

    private LocalDate birthDate;
    private String name;

    public Patient(LocalDate birthDate, String name) {
        this.birthDate = birthDate;
        this.name = name;
    }

    public Patient(Long id, LocalDate birthDate, String name) {
        this.id = id;
        this.birthDate = birthDate;
        this.name = name;
    }

    @Override
    public <T extends Entity> void copyFrom(T entityToCopy){

        Patient patientToCopy = (Patient) entityToCopy;

        LocalDate birthDate = patientToCopy.getBirthDate();
        if(birthDate != null){
            this.birthDate = birthDate;
        }

        String name = patientToCopy.getName();

        if(name != null && !name.isEmpty()){
            this.name = name;
        }
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Pacient{" +
                "id=" + id +
                ", birthDate=" + birthDate +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Patient patient = (Patient) o;
        return getBirthDate().equals(patient.getBirthDate()) && getName().equals(patient.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getBirthDate(), getName());
    }

    @Override
    public PatientDto toDto(){
        return new PatientDto(this.id.toString(), this.birthDate.toString(), this.name);
    }
}
