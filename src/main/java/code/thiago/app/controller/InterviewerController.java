package code.thiago.app.controller;

import code.thiago.app.dto.InterviewerDTO;
import code.thiago.app.interfaces.PersonService;
import code.thiago.app.mapper.InterviewerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/interviewer", produces = MediaType.APPLICATION_JSON_VALUE)
public class InterviewerController {

    private final PersonService<InterviewerDTO> service;

    @Autowired
    public InterviewerController(PersonService<InterviewerDTO> service, InterviewerMapper interviewerMapper) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<InterviewerDTO> save(@RequestBody InterviewerDTO interviewer) {
        InterviewerDTO savedInterviewer = service.save(interviewer);
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(savedInterviewer);
    }

    @GetMapping
    public ResponseEntity<List<InterviewerDTO>> save() {
        List<InterviewerDTO> allInterviewers = service.findAll();
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(allInterviewers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InterviewerDTO> findById(@PathVariable String id) {
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(service.findById(id));
    }
}
