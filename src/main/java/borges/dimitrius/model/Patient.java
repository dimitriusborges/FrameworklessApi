package borges.dimitrius.model;

import java.util.Date;

public class Patient extends Entity{

    private Date birthDate;
    private String name;

    public Patient(Date birthDate, String name) {
        this.birthDate = birthDate;
        this.name = name;
    }

    public Patient(Long id, Date birthDate, String name) {
        this.id = id;
        this.birthDate = birthDate;
        this.name = name;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Pacient{" +
                "id=" + id +
                ", birthDate=" + birthDate +
                ", name='" + name + '\'' +
                '}';
    }
}
