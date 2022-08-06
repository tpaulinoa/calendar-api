package code.thiago.app.repository;

import code.thiago.app.model.Candidate;
import org.springframework.stereotype.Repository;

@Repository
public interface CandidateRepository extends PersonRepository<Candidate> {

}
