package pico.erp.facility.process;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import pico.erp.facility.data.FacilityCategory;
import pico.erp.facility.data.FacilityCategoryId;
import pico.erp.facility.data.FacilityData;
import pico.erp.facility.data.FacilityId;

public interface FacilityProcessTypeService {

  FacilityData create(@Valid FacilityProcessTypeRequests.CreateRequest request);

  void delete(@Valid FacilityProcessTypeRequests.DeleteRequest request);

  boolean exists(@NotNull FacilityId id);

  FacilityData get(@NotNull FacilityId id);

  void update(@Valid FacilityProcessTypeRequests.UpdateRequest request);

  FacilityCategory get(@NotNull FacilityCategoryId id);


}
