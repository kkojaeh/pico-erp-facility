package pico.erp.facility.process.type;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import pico.erp.audit.annotation.Audit;
import pico.erp.facility.Facility;
import pico.erp.process.type.ProcessTypeData;

@Builder(toBuilder = true)
@Getter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode(of = "id")
@Audit(alias = "facility-process-type")
public class FacilityProcessType implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  FacilityProcessTypeId id;

  Facility facility;

  ProcessTypeData processType;

  BigDecimal speedVariationRate;

  BigDecimal defectiveVariationRate;

  public FacilityProcessTypeMessages.CreateResponse apply(
    FacilityProcessTypeMessages.CreateRequest request) {
    id = request.getId();
    facility = request.getFacility();
    processType = request.getProcessType();
    speedVariationRate = request.getSpeedVariationRate();
    defectiveVariationRate = request.getDefectiveVariationRate();
    return new FacilityProcessTypeMessages.CreateResponse(
      Arrays.asList(new FacilityProcessTypeEvents.CreatedEvent(this.id)));
  }

  public FacilityProcessTypeMessages.UpdateResponse apply(
    FacilityProcessTypeMessages.UpdateRequest request) {
    speedVariationRate = request.getSpeedVariationRate();
    defectiveVariationRate = request.getDefectiveVariationRate();
    return new FacilityProcessTypeMessages.UpdateResponse(
      Arrays.asList(new FacilityProcessTypeEvents.UpdatedEvent(this.id)));
  }

  public FacilityProcessTypeMessages.DeleteResponse apply(
    FacilityProcessTypeMessages.DeleteRequest request) {
    return new FacilityProcessTypeMessages.DeleteResponse(
      Arrays.asList(new FacilityProcessTypeEvents.DeletedEvent(this.id)));
  }

}
