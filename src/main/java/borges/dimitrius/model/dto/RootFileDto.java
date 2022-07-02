package borges.dimitrius.model.dto;

import borges.dimitrius.model.entities.RootFile;

public class RootFileDto implements Dto{

    private String id;
    private String nameType;
    private String brand;

    public RootFileDto(){}

    public RootFileDto(String id, String nameType, String brand) {
        this.id = id;
        this.nameType = nameType;
        this.brand = brand;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    @Override
    public RootFile toEntity(){
        return new RootFile(this.nameType, this.brand);
    }

    @Override
    public String toString() {
        return "RootFileDto{" +
                "id='" + id + '\'' +
                ", nameType='" + nameType + '\'' +
                ", brand='" + brand + '\'' +
                '}';
    }
}
