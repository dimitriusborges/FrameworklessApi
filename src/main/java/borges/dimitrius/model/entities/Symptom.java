package borges.dimitrius.model.entities;

import borges.dimitrius.model.dto.SymptomDto;

import java.util.Objects;

public class Symptom extends TransferableEntity {

    private String description;

    public Symptom(String description){
        this.description = description;
    }

    public Symptom(Long id, String description) {
        this.id = id;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "SymptomType{" +
                "id=" + id +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Symptom that = (Symptom) o;
        return getDescription().equals(that.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDescription());
    }

    @Override
    public SymptomDto toDto() {
        return new SymptomDto(this.id.toString(), this.description);
    }

    @Override
    public <T extends Entity> void copyFrom(T entityToCopy) {
        Symptom symptomTypeToCopy = (Symptom)entityToCopy;

        String description = symptomTypeToCopy.getDescription();

        if(description != null){
            this.description = description;
        }

    }
}
