package pico.erp.facility.domain;

import java.util.Collection;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.Value;
import pico.erp.facility.data.FacilityCategory;
import pico.erp.facility.data.FacilityId;
import pico.erp.shared.TypeDefinitions;
import pico.erp.shared.event.Event;

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

  }

  @Data
  class UpdateRequest {

    @NotNull
    FacilityCategory category;

    @Size(max = TypeDefinitions.NAME_LENGTH)
    String name;

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
