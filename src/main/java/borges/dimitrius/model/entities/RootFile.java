package borges.dimitrius.model.entities;

import borges.dimitrius.model.dto.Dto;
import borges.dimitrius.model.dto.RootFileDto;

import java.util.Objects;

public class RootFile extends TransferableEntity {

    private String typeName;
    private String brand;

    public RootFile(Long id, String nameType, String brand) {
        this.id = id;
        this.typeName = nameType;
        this.brand = brand;
    }

    public RootFile(String nameType, String brand){
        this.typeName = nameType;
        this.brand = brand;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    @Override
    public String toString() {
        return "RootFile{" +
                "id='" + id + '\'' +
                "nameType='" + typeName + '\'' +
                ", brand='" + brand + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RootFile rootFile = (RootFile) o;
        return getTypeName().equals(rootFile.getTypeName()) && getBrand().equals(rootFile.getBrand());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTypeName(), getBrand());
    }

    @Override
    public Dto toDto() {
        return new RootFileDto(this.id.toString(), this.typeName, this.brand);
    }

    @Override
    public <T extends TransferableEntity> void copyFrom(T entityToCopy) {
        RootFile rootFileToCopy = (RootFile) entityToCopy;

        String typeName = rootFileToCopy.getTypeName();

        if(typeName != null){
            this.typeName = typeName;
        }

        String brand = rootFileToCopy.getBrand();

        if (brand != null){
            this.brand = brand;
        }
    }
}
