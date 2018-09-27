package pico.erp.facility.schedule;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import pico.erp.facility.schedule.data.FacilityScheduleData;
import pico.erp.facility.schedule.data.FacilityScheduleId;

public interface FacilityScheduleService {

  FacilityScheduleData create(@Valid FacilityScheduleRequests.CreateRequest request);

  void delete(@Valid FacilityScheduleRequests.DeleteRequest request);

  boolean exists(@NotNull FacilityScheduleId id);

  FacilityScheduleData get(@NotNull FacilityScheduleId id);

  void update(@Valid FacilityScheduleRequests.UpdateRequest request);

}
