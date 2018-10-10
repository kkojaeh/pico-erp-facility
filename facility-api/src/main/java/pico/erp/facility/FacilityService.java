package pico.erp.facility;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import pico.erp.facility.category.FacilityCategory;
import pico.erp.facility.category.FacilityCategoryId;

public interface FacilityService {

  FacilityData create(@Valid FacilityRequests.CreateRequest request);

  void delete(@Valid FacilityRequests.DeleteRequest request);

  boolean exists(@NotNull FacilityId id);

  FacilityData get(@NotNull FacilityId id);

  void update(@Valid FacilityRequests.UpdateRequest request);

  FacilityCategory get(@NotNull FacilityCategoryId id);


}
