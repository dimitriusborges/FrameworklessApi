package borges.dimitrius.model.entities;

import borges.dimitrius.model.dto.Dto;
import borges.dimitrius.model.dto.SymptomTypeDto;
import borges.dimitrius.model.dto.TransferableEntity;

import java.util.Objects;

public class SymptomType extends TransferableEntity {

    private String description;

    public SymptomType(String description){
        this.description = description;
    }

    public SymptomType(Long id, String description) {
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
        SymptomType that = (SymptomType) o;
        return getDescription().equals(that.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDescription());
    }

    @Override
    public Dto toDto() {
        return new SymptomTypeDto(this.id.toString(), this.description);
    }

    @Override
    public <T extends TransferableEntity> void copyFrom(T entityToCopy) {
        SymptomType symptomTypeToCopy = (SymptomType)entityToCopy;

        String description = symptomTypeToCopy.getDescription();

        if(description != null){
            this.description = description;
        }

    }
}
