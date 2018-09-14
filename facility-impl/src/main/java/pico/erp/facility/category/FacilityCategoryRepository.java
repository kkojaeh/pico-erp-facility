package pico.erp.facility.category;

import java.util.Optional;
import java.util.stream.Stream;
import javax.validation.constraints.NotNull;
import pico.erp.facility.category.data.FacilityCategory;
import pico.erp.facility.category.data.FacilityCategoryId;

public interface FacilityCategoryRepository {

  Stream<FacilityCategory> findAll();

  Optional<FacilityCategory> findBy(@NotNull FacilityCategoryId id);

}
