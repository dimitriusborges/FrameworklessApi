package borges.dimitrius.model.vo;

import borges.dimitrius.model.dto.Dto;

public interface Vo {

    <E extends Dto> E toDto();
}
