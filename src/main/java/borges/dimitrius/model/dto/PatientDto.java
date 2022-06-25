package borges.dimitrius.model.dto;


import borges.dimitrius.model.entities.Patient;

import java.sql.Date;
import java.text.ParseException;

public class PatientDto implements Dto{

    private String id;
    private String birthdate;
    private String name;

    public PatientDto(){}

    public PatientDto(String id, String birthdate, String name){
        this.id = id;
        this.birthdate = birthdate;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Patient toEntity() {
        return new Patient(Date.valueOf(this.birthdate), this.name);
    }

    @Override
    public String toString() {
        return "PatientDto{" +
                "id='" + id + '\'' +
                ", birthdate='" + birthdate + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
