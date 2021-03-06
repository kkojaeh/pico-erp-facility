package pico.erp.facility;

import java.time.OffsetDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pico.erp.facility.category.FacilityCategoryId;
import pico.erp.shared.data.Auditor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class FacilityView {

  FacilityId id;

  String name;

  FacilityCategoryId categoryId;

  Auditor createdBy;

  OffsetDateTime createdDate;

  Auditor lastModifiedBy;

  OffsetDateTime lastModifiedDate;

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class Filter {

    FacilityCategoryId categoryId;

    String name;

  }

}
