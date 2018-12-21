package pico.erp.facility.process.type;

import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import pico.erp.facility.FacilityId;

public interface FacilityProcessTypeService {

  FacilityProcessTypeData create(@Valid FacilityProcessTypeRequests.CreateRequest request);

  void delete(@Valid FacilityProcessTypeRequests.DeleteRequest request);

  boolean exists(@NotNull FacilityProcessTypeId id);

  FacilityProcessTypeData get(@NotNull FacilityProcessTypeId id);

  void update(@Valid FacilityProcessTypeRequests.UpdateRequest request);

  List<FacilityProcessTypeData> getAll(@NotNull FacilityId facilityId);

}
