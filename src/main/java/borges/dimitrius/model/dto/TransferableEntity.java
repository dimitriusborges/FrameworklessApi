package borges.dimitrius.model.dto;

import borges.dimitrius.model.entities.Entity;

public abstract class TransferableEntity extends Entity {

    public abstract Dto toDto();
}
