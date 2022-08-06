package code.thiago.app.repository;

import code.thiago.app.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Set;

@Repository
public interface PersonRepository<T extends Person> extends JpaRepository<T, String> {

    long countByIdIn(Set<String> ids);
}
