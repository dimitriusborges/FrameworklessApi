package borges.dimitrius.model.dto;

import borges.dimitrius.model.entities.SymptomType;

public class SymptomTypeDto implements Dto{

    private String id;
    private String description;

    public SymptomTypeDto() {
    }

    public SymptomTypeDto(String id, String description) {
        this.id = id;
        this.description = description;
    }

    @Override
    public SymptomType toEntity() {

        return new SymptomType(id != null ? Long.parseLong(this.id): 0, this.description);
    }
}
