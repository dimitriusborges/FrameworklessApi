package borges.dimitrius.model.dto;


public interface Dto {

    <E extends TransferableEntity> E toEntity();
}
