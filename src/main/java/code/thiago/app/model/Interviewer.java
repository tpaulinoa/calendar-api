package code.thiago.app.model;

import lombok.Data;

import javax.persistence.Entity;

@Data
@Entity(name = "interviewer")
public class Interviewer extends Person {

}
