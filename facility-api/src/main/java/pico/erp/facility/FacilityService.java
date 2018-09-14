package pico.erp.facility;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import pico.erp.facility.category.data.FacilityCategory;
import pico.erp.facility.category.data.FacilityCategoryId;
import pico.erp.facility.data.FacilityData;
import pico.erp.facility.data.FacilityId;

public interface FacilityService {

  FacilityData create(@Valid FacilityRequests.CreateRequest request);

  void delete(@Valid FacilityRequests.DeleteRequest request);

  boolean exists(@NotNull FacilityId id);

  FacilityData get(@NotNull FacilityId id);

  void update(@Valid FacilityRequests.UpdateRequest request);

  FacilityCategory get(@NotNull FacilityCategoryId id);


}
