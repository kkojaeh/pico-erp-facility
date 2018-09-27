package pico.erp.facility.process.type;

import java.math.BigDecimal;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pico.erp.facility.data.FacilityId;
import pico.erp.facility.process.type.data.FacilityProcessTypeId;
import pico.erp.process.type.data.ProcessTypeId;

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
    BigDecimal speedVariationRate;

    @NotNull
    BigDecimal defectiveVariationRate;

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
    BigDecimal speedVariationRate;

    @NotNull
    BigDecimal defectiveVariationRate;

  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  class DeleteRequest {

    @Valid
    @NotNull
    FacilityProcessTypeId id;
  }
}
