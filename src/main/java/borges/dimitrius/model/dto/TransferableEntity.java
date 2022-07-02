package borges.dimitrius.model.dto;

import borges.dimitrius.model.entities.Entity;
import borges.dimitrius.model.entities.Patient;

public abstract class TransferableEntity extends Entity {

    public abstract Dto toDto();

    public abstract <T extends  TransferableEntity> void copyFrom(T entityToCopy);
}
