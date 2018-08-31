package pico.erp.facility.core;

import java.util.Optional;
import java.util.stream.Stream;
import javax.validation.constraints.NotNull;
import pico.erp.facility.data.FacilityCategory;
import pico.erp.facility.data.FacilityCategoryId;

public interface FacilityCategoryRepository {

  Stream<FacilityCategory> findAll();

  Optional<FacilityCategory> findBy(@NotNull FacilityCategoryId id);

}
