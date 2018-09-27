package pico.erp.facility.process.type;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import pico.erp.facility.process.type.data.FacilityProcessTypeData;
import pico.erp.facility.process.type.data.FacilityProcessTypeId;

public interface FacilityProcessTypeService {

  FacilityProcessTypeData create(@Valid FacilityProcessTypeRequests.CreateRequest request);

  void delete(@Valid FacilityProcessTypeRequests.DeleteRequest request);

  boolean exists(@NotNull FacilityProcessTypeId id);

  FacilityProcessTypeData get(@NotNull FacilityProcessTypeId id);

  void update(@Valid FacilityProcessTypeRequests.UpdateRequest request);

}