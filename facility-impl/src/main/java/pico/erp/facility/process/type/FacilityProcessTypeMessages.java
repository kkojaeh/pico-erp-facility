package pico.erp.facility.process.type;

import java.math.BigDecimal;
import java.util.Collection;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.Value;
import pico.erp.facility.Facility;
import pico.erp.facility.process.type.data.FacilityProcessTypeId;
import pico.erp.process.type.data.ProcessTypeData;
import pico.erp.shared.event.Event;

public interface FacilityProcessTypeMessages {

  @Data
  class CreateRequest {

    @Valid
    @NotNull
    FacilityProcessTypeId id;

    @NotNull
    Facility facility;

    @NotNull
    ProcessTypeData processTypeData;

    @NotNull
    BigDecimal speedVariationRate;

    @NotNull
    BigDecimal defectiveVariationRate;

  }

  @Data
  class UpdateRequest {

    @NotNull
    BigDecimal speedVariationRate;

    @NotNull
    BigDecimal defectiveVariationRate;

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
