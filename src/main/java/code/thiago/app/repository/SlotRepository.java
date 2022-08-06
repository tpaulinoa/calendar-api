package code.thiago.app.repository;

import code.thiago.app.dto.SlotDTO;
import code.thiago.app.model.Slot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface SlotRepository extends JpaRepository<Slot, String> {

    @Query(value = "SELECT new code.thiago.app.dto.SlotDTO(s.startDateTime, s.endDateTime) FROM "
                + " slot s "
                + " WHERE "
                + " s.person.id IN (:ids) "
                + " GROUP BY s.startDateTime, s.endDateTime "
                + " HAVING count(*) = :count_participants")
    List<SlotDTO> findAvailableSlots(@Param("ids") Set<String> participantsIds, @Param("count_participants") long countParticipants);
}
