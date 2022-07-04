package borges.dimitrius.model.entities;

import borges.dimitrius.model.dto.Dto;

public abstract class TransferableEntity extends Entity {

    public abstract Dto toDto();

    public abstract <T extends  TransferableEntity> void copyFrom(T entityToCopy);
}
