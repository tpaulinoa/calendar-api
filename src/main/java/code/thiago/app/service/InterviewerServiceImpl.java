package code.thiago.app.service;

import code.thiago.app.dto.InterviewerDTO;
import code.thiago.app.exceptions.PersonNotFoundException;
import code.thiago.app.interfaces.InterviewerService;
import code.thiago.app.mapper.InterviewerMapper;
import code.thiago.app.model.Interviewer;
import code.thiago.app.repository.InterviewerRepository;
import code.thiago.app.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InterviewerServiceImpl implements InterviewerService {

    private final InterviewerRepository repository;
    private final InterviewerMapper mapper;

    @Autowired
    public InterviewerServiceImpl(InterviewerRepository repository, InterviewerMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public InterviewerDTO save(InterviewerDTO interviewerDTO) {
        Interviewer interviewer = repository.save(mapper.mapDTOToEntity(interviewerDTO));
        return mapper.mapEntityToDTO(interviewer);
    }

    @Override
    public InterviewerDTO findById(String id) {
        return repository.findById(id)
                .map(mapper::mapEntityToDTO)
                .orElseThrow(() -> new PersonNotFoundException(id));
    }

    @Override
    public List<InterviewerDTO> findAll() {
        return repository.findAll()
                .stream().map(mapper::mapEntityToDTO)
                .collect(Collectors.toList());
    }
}
