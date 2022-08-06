package code.thiago.app.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;

@Data
@Entity(name = "candidate")
public class Candidate extends Person {

    @Column(name = "linkedin_profile")
    private String linkedinProfile;
}
