package borges.dimitrius.model.dto;


import borges.dimitrius.model.entities.Entity;

public interface Dto {

    <E extends Entity> E toEntity();
}
