package pico.erp.facility.core;

import java.util.Optional;
import javax.validation.constraints.NotNull;
import org.springframework.stereotype.Repository;
import pico.erp.facility.data.FacilityId;
import pico.erp.facility.domain.Facility;

@Repository
public interface FacilityRepository {

  Facility create(@NotNull Facility facility);

  void deleteBy(@NotNull FacilityId id);

  boolean exists(@NotNull FacilityId id);

  Optional<Facility> findBy(@NotNull FacilityId id);

  void update(@NotNull Facility facility);

}
