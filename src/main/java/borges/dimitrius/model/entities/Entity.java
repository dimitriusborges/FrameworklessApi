package borges.dimitrius.model.entities;

public abstract class Entity {

    protected Long id;

    public Long getId() {
        return id;
    }

    public abstract <T extends  Entity> void copyFrom(T entityToCopy);
}
