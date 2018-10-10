package pico.erp.facility;

import java.util.Collection;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.Value;
import pico.erp.facility.category.FacilityCategory;
import pico.erp.shared.TypeDefinitions;
import pico.erp.shared.event.Event;
import pico.erp.work.schedule.category.WorkScheduleCategory;

public interface FacilityMessages {

  @Data
  class CreateRequest {

    @Valid
    @NotNull
    FacilityId id;

    @NotNull
    FacilityCategory category;

    @Size(max = TypeDefinitions.NAME_LENGTH)
    String name;

    WorkScheduleCategory workScheduleCategory;

  }

  @Data
  class UpdateRequest {

    @NotNull
    FacilityCategory category;

    @Size(max = TypeDefinitions.NAME_LENGTH)
    String name;

    WorkScheduleCategory workScheduleCategory;

  }

  @Data
  class DeleteRequest {

  }

  @Value
  class CreateResponse {

    Collection<Event> events;

  }

  @Value
  class UpdateResponse {

    Collection<Event> events;

  }

  @Value
  class DeleteResponse {

    Collection<Event> events;

  }
}
