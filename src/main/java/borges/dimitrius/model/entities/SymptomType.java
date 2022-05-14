package borges.dimitrius.model.entities;

import java.util.Objects;

public class SymptomType extends Entity {

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
}
