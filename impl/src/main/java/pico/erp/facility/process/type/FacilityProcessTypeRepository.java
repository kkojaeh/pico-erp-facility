package pico.erp.facility.process.type;

import java.util.Optional;
import java.util.stream.Stream;
import javax.validation.constraints.NotNull;
import org.springframework.stereotype.Repository;
import pico.erp.facility.FacilityId;
import pico.erp.process.type.ProcessTypeId;

@Repository
public interface FacilityProcessTypeRepository {

  FacilityProcessType create(@NotNull FacilityProcessType facilityProcessType);

  void deleteBy(@NotNull FacilityProcessTypeId id);

  boolean exists(@NotNull FacilityProcessTypeId id);

  boolean exists(@NotNull FacilityId facilityId, @NotNull ProcessTypeId processTypeId);

  Optional<FacilityProcessType> findBy(@NotNull FacilityProcessTypeId id);

  Stream<FacilityProcessType> findAllBy(@NotNull FacilityId facilityId);

  void update(@NotNull FacilityProcessType facilityProcessType);

}
