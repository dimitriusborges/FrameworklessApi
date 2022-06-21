package borges.dimitrius.model.dto;



public class PatientDto implements Dto{

    private String id;
    private String birthdate;
    private String name;

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
}
