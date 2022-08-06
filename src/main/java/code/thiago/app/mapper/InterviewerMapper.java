package code.thiago.app.mapper;

import code.thiago.app.dto.InterviewerDTO;
import code.thiago.app.model.Interviewer;
import org.springframework.stereotype.Component;

@Component
public class InterviewerMapper {

    public Interviewer mapDTOToEntity(InterviewerDTO dto) {
        if (dto == null) return null;

        Interviewer entity = new Interviewer();
        entity.setPhone(dto.getPhone());
        entity.setName(dto.getName());
        entity.setEmail(dto.getEmail());
        entity.setId(dto.getId());
        return entity;
    }

    public InterviewerDTO mapEntityToDTO(Interviewer entity) {
        if (entity == null) return null;

        InterviewerDTO dto = new InterviewerDTO();
        dto.setPhone(entity.getPhone());
        dto.setName(entity.getName());
        dto.setEmail(entity.getEmail());
        dto.setId(entity.getId());
        return dto;
    }
}
