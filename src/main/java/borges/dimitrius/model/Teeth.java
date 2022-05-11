package borges.dimitrius.model;

import java.util.Objects;

public class Teeth extends Entity{


    private Integer toothNum;

    public Integer getToothNum() {
        return toothNum;
    }

    public void setToothNum(Integer toothNum) {
        this.toothNum = toothNum;
    }

    @Override
    public String toString() {
        return "Teeth{" +
                "id=" + id +
                ", toothNum=" + toothNum +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Teeth teeth = (Teeth) o;
        return getToothNum().equals(teeth.getToothNum());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getToothNum());
    }
}
