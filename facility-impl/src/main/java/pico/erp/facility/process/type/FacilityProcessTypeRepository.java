package pico.erp.facility.process.type;

import java.util.Optional;
import javax.validation.constraints.NotNull;
import org.springframework.stereotype.Repository;
import pico.erp.facility.data.FacilityId;
import pico.erp.facility.process.type.data.FacilityProcessTypeId;
import pico.erp.process.type.data.ProcessTypeId;

@Repository
public interface FacilityProcessTypeRepository {

  FacilityProcessType create(@NotNull FacilityProcessType facilityProcessType);

  void deleteBy(@NotNull FacilityProcessTypeId id);

  boolean exists(@NotNull FacilityProcessTypeId id);

  boolean exists(@NotNull FacilityId facilityId, @NotNull ProcessTypeId processTypeId);

  Optional<FacilityProcessType> findBy(@NotNull FacilityProcessTypeId id);

  void update(@NotNull FacilityProcessType facilityProcessType);

}
