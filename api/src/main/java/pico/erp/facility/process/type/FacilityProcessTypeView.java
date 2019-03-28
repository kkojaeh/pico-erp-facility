package pico.erp.facility.process.type;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pico.erp.facility.FacilityId;
import pico.erp.facility.category.FacilityCategoryId;
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

  LocalDateTime createdDate;

  Auditor lastModifiedBy;

  LocalDateTime lastModifiedDate;

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class Filter {

    FacilityCategoryId categoryId;

    String name;

  }

}
