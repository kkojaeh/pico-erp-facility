package pico.erp.facility.schedule;

import java.time.LocalDateTime;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pico.erp.facility.FacilityId;
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

    LocalDateTime begin;

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

    LocalDateTime begin;

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
