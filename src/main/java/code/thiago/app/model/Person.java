package code.thiago.app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Person extends GenericEntity {

    @NotBlank(message = "Name is required")
    private String name;
    @NotBlank(message = "E-mail is required")
    private String email;
    private String phone;

    @JsonIgnore
    @OneToMany(mappedBy = "person", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Slot> slots;
}
