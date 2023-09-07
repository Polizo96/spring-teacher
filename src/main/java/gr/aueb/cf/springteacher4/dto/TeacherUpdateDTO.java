package gr.aueb.cf.springteacher4.dto;

public class TeacherUpdateDTO extends BaseDTO {
    private String firstname;
    private String lastname;

    public TeacherUpdateDTO() {}

    public TeacherUpdateDTO(String firstname, String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
}
