package pico.erp.facility.schedule;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pico.erp.facility.schedule.data.FacilityScheduleId;
import pico.erp.shared.event.Event;

public interface FacilityScheduleEvents {

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  class CreatedEvent implements Event {

    public final static String CHANNEL = "event.facility-schedule.created";

    private FacilityScheduleId facilityScheduleId;

    public String channel() {
      return CHANNEL;
    }

  }

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  class DeletedEvent implements Event {

    public final static String CHANNEL = "event.facility-schedule.deleted";

    private FacilityScheduleId facilityScheduleId;

    public String channel() {
      return CHANNEL;
    }


  }

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  class UpdatedEvent implements Event {

    public final static String CHANNEL = "event.facility-schedule.updated";

    private FacilityScheduleId facilityScheduleId;

    public String channel() {
      return CHANNEL;
    }

  }

}
