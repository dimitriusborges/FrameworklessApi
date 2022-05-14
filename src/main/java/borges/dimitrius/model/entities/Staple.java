package borges.dimitrius.model.entities;

import java.util.Objects;

public class Staple extends Entity{

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
}
