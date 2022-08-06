package code.thiago.app.service;

import code.thiago.app.dto.CandidateDTO;
import code.thiago.app.exceptions.PersonNotFoundException;
import code.thiago.app.interfaces.CandidateService;
import code.thiago.app.mapper.CandidateMapper;
import code.thiago.app.model.Candidate;
import code.thiago.app.repository.CandidateRepository;
import code.thiago.app.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CandidateServiceImpl implements CandidateService {

    private final CandidateRepository repository;
    private final CandidateMapper mapper;

    @Autowired
    public CandidateServiceImpl(CandidateRepository repository, CandidateMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public CandidateDTO save(CandidateDTO candidateDTO) {
        Candidate candidate = repository.save(mapper.mapDTOToEntity(candidateDTO));
        return mapper.mapEntityToDTO(candidate);
    }

    @Override
    public CandidateDTO findById(String id) {
        return repository.findById(id)
                .map(mapper::mapEntityToDTO)
                .orElseThrow(() -> new PersonNotFoundException(id));
    }

    @Override
    public List<CandidateDTO> findAll() {
        return repository.findAll()
                .stream().map(mapper::mapEntityToDTO)
                .collect(Collectors.toList());
    }
}
