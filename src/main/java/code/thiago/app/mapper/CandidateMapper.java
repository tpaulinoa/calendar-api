package code.thiago.app.mapper;

import code.thiago.app.dto.CandidateDTO;
import code.thiago.app.model.Candidate;
import org.springframework.stereotype.Component;

@Component
public class CandidateMapper {

    public Candidate mapDTOToEntity(CandidateDTO dto) {
        if (dto == null) return null;

        Candidate entity = new Candidate();
        entity.setPhone(dto.getPhone());
        entity.setName(dto.getName());
        entity.setEmail(dto.getEmail());
        entity.setId(dto.getId());
        entity.setLinkedinProfile(dto.getLinkedinProfile());
        return entity;
    }

    public CandidateDTO mapEntityToDTO(Candidate entity) {
        if (entity == null) return null;

        CandidateDTO dto = new CandidateDTO();
        dto.setPhone(entity.getPhone());
        dto.setName(entity.getName());
        dto.setEmail(entity.getEmail());
        dto.setId(entity.getId());
        dto.setLinkedinProfile(entity.getLinkedinProfile());
        return dto;
    }
}
