package code.thiago.app.model;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@Data
@MappedSuperclass
public abstract class GenericEntity {

    @Id
    @GeneratedValue( generator = "uuid" )
    @GenericGenerator( name = "uuid", strategy = "org.hibernate.id.UUIDGenerator" )
    private String id;
}
