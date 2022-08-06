package code.thiago.app.controller;

import code.thiago.app.dto.CandidateDTO;
import code.thiago.app.interfaces.CandidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/candidate", produces = MediaType.APPLICATION_JSON_VALUE)
public class CandidateController {

    private final CandidateService service;

    @Autowired
    public CandidateController(CandidateService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<CandidateDTO> save(@RequestBody CandidateDTO candidate) {
        CandidateDTO savedCandidate = service.save(candidate);
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(savedCandidate);
    }

    @GetMapping
    public ResponseEntity<List<CandidateDTO>> save() {
        List<CandidateDTO> allCandidates = service.findAll();
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(allCandidates);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CandidateDTO> findById(@PathVariable String id) {
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(service.findById(id));
    }
}
