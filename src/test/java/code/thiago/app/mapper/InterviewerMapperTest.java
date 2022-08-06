package code.thiago.app.mapper;

import code.thiago.app.dto.CandidateDTO;
import code.thiago.app.model.Candidate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
class InterviewerMapperTest {

    @Autowired
    CandidateMapper mapper;

    @Test
    void mapDTOToEntity_ReturnsAnEntity_When_ANotNullDTOIsGiven() {
        CandidateDTO dto = new CandidateDTO();
        dto.setName("Alice");
        dto.setEmail("alice@example.com");
        dto.setPhone("1234567890");
        dto.setId("123");
        dto.setLinkedinProfile("linkedin.com/alice");

        Candidate entity = mapper.mapDTOToEntity(dto);

        assertEquals(dto.getName(), entity.getName());
        assertEquals(dto.getEmail(), entity.getEmail());
        assertEquals(dto.getPhone(), entity.getPhone());
        assertEquals(dto.getLinkedinProfile(), entity.getLinkedinProfile());
        assertEquals(dto.getId(), entity.getId());
    }

    @Test
    void mapDTOToEntity_ReturnsNull_When_ANullDTOIsGiven() {
        Candidate entity = mapper.mapDTOToEntity(null);
        assertNull(entity);
    }

    @Test
    void mapEntityToDTO_ReturnsADTO_When_ANotNullEntityIsGiven() {
        Candidate entity = new Candidate();
        entity.setName("Alice");
        entity.setEmail("alice@example.com");
        entity.setPhone("1234567890");
        entity.setId("123");
        entity.setLinkedinProfile("linkedin.com/alice");

        CandidateDTO dto = mapper.mapEntityToDTO(entity);

        assertEquals(entity.getName(), dto.getName());
        assertEquals(entity.getEmail(), dto.getEmail());
        assertEquals(entity.getPhone(), dto.getPhone());
        assertEquals(entity.getLinkedinProfile(), dto.getLinkedinProfile());
        assertEquals(entity.getId(), dto.getId());
    }

    @Test
    void mapEntityToDTO_ReturnsNull_When_ANullEntityIsGiven() {
        CandidateDTO dto = mapper.mapEntityToDTO(null);
        assertNull(dto);
    }
}
