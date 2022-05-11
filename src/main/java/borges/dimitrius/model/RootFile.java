package borges.dimitrius.model;

import java.util.Objects;

public class RootFile extends Entity{

    private String nameType;
    private String brand;

    public RootFile(Long id, String nameType, String brand) {
        this.id = id;
        this.nameType = nameType;
        this.brand = brand;
    }

    public RootFile(String nameType, String brand){
        this.nameType = nameType;
        this.brand = brand;
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
    public String toString() {
        return "RootFile{" +
                "id='" + id + '\'' +
                "nameType='" + nameType + '\'' +
                ", brand='" + brand + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RootFile rootFile = (RootFile) o;
        return getNameType().equals(rootFile.getNameType()) && getBrand().equals(rootFile.getBrand());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNameType(), getBrand());
    }
}
