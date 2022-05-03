package borges.dimitrius.model;

public class RootFile {

    private Long id;
    private String nameType;
    private String brand;


    public Long getId() {
        return id;
    }

    public String getNameType() {
        return nameType;
    }

    public void setNameType(String nameType) {
        this.nameType = nameType;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }
}
