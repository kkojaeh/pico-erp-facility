package pico.erp.facility.process;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pico.erp.facility.data.FacilityId;
import pico.erp.facility.process.data.FacilityProcessTypeId;
import pico.erp.shared.event.Event;

public interface FacilityProcessTypeEvents {

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  class CreatedEvent implements Event {

    public final static String CHANNEL = "event.facility-process-type.created";

    private FacilityProcessTypeId facilityProcessTypeId;

    public String channel() {
      return CHANNEL;
    }

  }

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  class DeletedEvent implements Event {

    public final static String CHANNEL = "event.facility-process-type.deleted";

    private FacilityProcessTypeId facilityProcessTypeId;

    public String channel() {
      return CHANNEL;
    }


  }

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  class UpdatedEvent implements Event {

    public final static String CHANNEL = "event.facility-process-type.updated";

    private FacilityProcessTypeId facilityProcessTypeId;

    public String channel() {
      return CHANNEL;
    }

  }

}
