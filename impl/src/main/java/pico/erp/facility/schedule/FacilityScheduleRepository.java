package pico.erp.facility.schedule;

import java.util.Optional;
import javax.validation.constraints.NotNull;
import org.springframework.stereotype.Repository;

@Repository
public interface FacilityScheduleRepository {

  FacilitySchedule create(@NotNull FacilitySchedule facilitySchedule);

  void deleteBy(@NotNull FacilityScheduleId id);

  boolean exists(@NotNull FacilityScheduleId id);

  Optional<FacilitySchedule> findBy(@NotNull FacilityScheduleId id);

  void update(@NotNull FacilitySchedule facilitySchedule);

}
