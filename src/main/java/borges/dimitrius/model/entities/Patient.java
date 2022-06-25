package borges.dimitrius.model.entities;

import borges.dimitrius.model.dto.PatientDto;
import borges.dimitrius.model.dto.TransferableEntity;

import java.sql.Date;
import java.util.Objects;

public class Patient extends TransferableEntity {

    private Date birthDate;
    private String name;

    public Patient(Date birthDate, String name) {
        this.birthDate = birthDate;
        this.name = name;
    }

    public Patient(Long id, Date birthDate, String name) {
        this.id = id;
        this.birthDate = birthDate;
        this.name = name;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
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
