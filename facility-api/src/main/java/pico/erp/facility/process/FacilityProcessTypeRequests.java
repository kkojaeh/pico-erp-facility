package pico.erp.facility.process;

import java.math.BigDecimal;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pico.erp.facility.data.FacilityCategoryId;
import pico.erp.facility.data.FacilityId;
import pico.erp.facility.process.data.FacilityProcessTypeId;
import pico.erp.process.data.ProcessTypeId;
import pico.erp.shared.TypeDefinitions;

public interface FacilityProcessTypeRequests {

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  class CreateRequest {

    @Valid
    @NotNull
    FacilityProcessTypeId id;

    @Valid
    @NotNull
    FacilityId facilityId;

    @Valid
    @NotNull
    ProcessTypeId processTypeId;

    @NotNull
    BigDecimal speedVariationPrate;

    @NotNull
    BigDecimal defectiveVariationPrate;

  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  class UpdateRequest {

    @Valid
    @NotNull
    FacilityProcessTypeId id;

    @NotNull
    BigDecimal speedVariationPrate;

    @NotNull
    BigDecimal defectiveVariationPrate;

  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  class DeleteRequest {

    @Valid
    @NotNull
    FacilityId id;
  }
}
