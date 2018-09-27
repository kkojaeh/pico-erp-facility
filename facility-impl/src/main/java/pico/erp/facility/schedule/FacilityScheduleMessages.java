package pico.erp.facility.schedule;

import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.Set;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.Value;
import pico.erp.facility.Facility;
import pico.erp.facility.process.type.FacilityProcessType;
import pico.erp.facility.schedule.data.FacilityScheduleId;
import pico.erp.process.data.ProcessData;
import pico.erp.shared.event.Event;

public interface FacilityScheduleMessages {

  @Data
  class CreateRequest {

    @Valid
    @NotNull
    FacilityScheduleId id;

    @NotNull
    Facility facility;

    @NotNull
    ProcessData process;

    boolean flexible;

    OffsetDateTime begin;

    OffsetDateTime end;

    long durationMinutes;

    @NotNull
    Set<FacilityProcessType> facilityProcessTypes;

  }

  @Data
  class UpdateRequest {

    boolean flexible;

    OffsetDateTime begin;

    OffsetDateTime end;

    long durationMinutes;

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
