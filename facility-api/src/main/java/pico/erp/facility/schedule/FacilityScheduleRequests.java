package pico.erp.facility.schedule;

import java.time.OffsetDateTime;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pico.erp.facility.data.FacilityId;
import pico.erp.facility.schedule.data.FacilityScheduleId;
import pico.erp.process.ProcessId;

public interface FacilityScheduleRequests {

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  class CreateRequest {

    @Valid
    @NotNull
    FacilityScheduleId id;

    FacilityId facilityId;

    ProcessId processId;

    boolean flexible;

    OffsetDateTime begin;

    long durationMinutes;

  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  class UpdateRequest {

    @Valid
    @NotNull
    FacilityScheduleId id;

    boolean flexible;

    OffsetDateTime begin;

    long durationMinutes;

  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  class DeleteRequest {

    @Valid
    @NotNull
    FacilityScheduleId id;
  }
}
