package pico.erp.facility.process.data;

import java.time.OffsetDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pico.erp.facility.data.FacilityCategoryId;
import pico.erp.facility.data.FacilityId;
import pico.erp.shared.data.Auditor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class FacilityProcessTypeView {

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
