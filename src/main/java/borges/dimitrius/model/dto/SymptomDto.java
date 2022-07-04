package borges.dimitrius.model.dto;

import borges.dimitrius.model.entities.Symptom;

public class SymptomDto implements Dto{

    private String id;
    private String description;

    public SymptomDto() {
    }

    public SymptomDto(String id, String description) {
        this.id = id;
        this.description = description;
    }

    @Override
    public Symptom toEntity() {

        return new Symptom(id != null ? Long.parseLong(this.id): 0, this.description);
    }
}
