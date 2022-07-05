package borges.dimitrius.model.entities;

import borges.dimitrius.model.dto.Dto;
import borges.dimitrius.model.dto.StapleDto;

import java.util.Objects;

public class Staple extends TransferableEntity {

    private String type;

    public Staple(String type){
        this.type = type;
    }

    public Staple(Long id, String type){
        this.id = id;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Staple{" +
                "id=" + id +
                ", type='" + type + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Staple staple = (Staple) o;
        return Objects.equals(getType(), staple.getType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getType());
    }

    @Override
    public Dto toDto() {
        return new StapleDto(this.id.toString(), this.type);
    }

    @Override
    public <T extends Entity> void copyFrom(T entityToCopy) {
        Staple stapleToCopy = (Staple) entityToCopy;

        String type = stapleToCopy.getType();

        if(type != null){
            this.type = type;
        }
    }
}
