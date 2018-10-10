package pico.erp.facility.schedule;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.Arrays;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.val;
import pico.erp.audit.annotation.Audit;
import pico.erp.facility.Facility;
import pico.erp.facility.schedule.data.FacilityScheduleId;
import pico.erp.process.ProcessData;

@Builder(toBuilder = true)
@Getter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode(of = "id")
@Audit(alias = "facility-schedule")
public class FacilitySchedule implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  FacilityScheduleId id;

  Facility facility;

  ProcessData process;

  boolean flexible;

  OffsetDateTime begin;

  OffsetDateTime end;

  long durationMinutes;

  public FacilityScheduleMessages.CreateResponse apply(
    FacilityScheduleMessages.CreateRequest request) {
    id = request.getId();
    facility = request.getFacility();
    process = request.getProcess();
    flexible = request.isFlexible();
    begin = request.getBegin();
    end = request.getEnd();
    durationMinutes = request.getDurationMinutes();
    val allowedProcess = request.getFacilityProcessTypes().stream()
      .map(facilityProcessTypes -> facilityProcessTypes.getProcessTypeData())
      .filter(processType -> processType.getId().equals(process.getTypeId()))
      .count() > 0;

    if (!allowedProcess) {
      throw new FacilityScheduleExceptions.NotAllowedProcessException();
    }
    return new FacilityScheduleMessages.CreateResponse(
      Arrays.asList(new FacilityScheduleEvents.CreatedEvent(this.id)));
  }

  public FacilityScheduleMessages.UpdateResponse apply(
    FacilityScheduleMessages.UpdateRequest request) {
    flexible = request.isFlexible();
    begin = request.getBegin();
    end = request.getEnd();
    durationMinutes = request.getDurationMinutes();
    return new FacilityScheduleMessages.UpdateResponse(
      Arrays.asList(new FacilityScheduleEvents.UpdatedEvent(this.id)));
  }

  public FacilityScheduleMessages.DeleteResponse apply(
    FacilityScheduleMessages.DeleteRequest request) {
    return new FacilityScheduleMessages.DeleteResponse(
      Arrays.asList(new FacilityScheduleEvents.DeletedEvent(this.id)));
  }

}
