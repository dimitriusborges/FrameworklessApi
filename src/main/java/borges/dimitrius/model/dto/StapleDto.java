package borges.dimitrius.model.dto;

import borges.dimitrius.model.entities.Staple;

public class StapleDto implements Dto{

    private String id;
    private String type;


    public StapleDto() {
    }

    public StapleDto(String id, String type) {
        this.id = id;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public Staple toEntity() {
        return new Staple(this.id != null? Long.parseLong(this.id): 0, this.type);
    }

    @Override
    public String toString() {
        return "StableDto{" +
                "id='" + id + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
