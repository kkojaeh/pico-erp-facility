package pico.erp.facility;

import java.io.Serializable;
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
import pico.erp.facility.category.data.FacilityCategory;
import pico.erp.facility.data.FacilityId;

@Builder(toBuilder = true)
@Getter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode(of = "id")
@Audit(alias = "facility")
public class Facility implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  FacilityId id;

  FacilityCategory category;

  String name;

  public FacilityMessages.CreateResponse apply(FacilityMessages.CreateRequest request) {
    id = request.getId();
    category = request.getCategory();
    name = request.getName();
    return new FacilityMessages.CreateResponse(
      Arrays.asList(new FacilityEvents.CreatedEvent(this.id)));
  }

  public FacilityMessages.UpdateResponse apply(FacilityMessages.UpdateRequest request) {
    category = request.getCategory();
    name = request.getName();
    return new FacilityMessages.UpdateResponse(
      Arrays.asList(new FacilityEvents.UpdatedEvent(this.id)));
  }

  public FacilityMessages.DeleteResponse apply(FacilityMessages.DeleteRequest request) {
    return new FacilityMessages.DeleteResponse(
      Arrays.asList(new FacilityEvents.DeletedEvent(this.id)));
  }

}
