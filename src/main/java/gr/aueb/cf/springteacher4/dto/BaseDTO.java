package gr.aueb.cf.springteacher4.dto;


import javax.validation.constraints.NotNull;

public abstract class BaseDTO {
    @NotNull
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
