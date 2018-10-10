package pico.erp.facility;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pico.erp.facility.category.data.FacilityCategoryId;
import pico.erp.facility.data.FacilityId;
import pico.erp.shared.TypeDefinitions;
import pico.erp.work.schedule.category.WorkScheduleCategoryId;

public interface FacilityRequests {

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  class CreateRequest {

    @Valid
    @NotNull
    FacilityId id;

    @Valid
    @NotNull
    FacilityCategoryId categoryId;

    @Size(max = TypeDefinitions.NAME_LENGTH)
    String name;

    @Valid
    @NotNull
    WorkScheduleCategoryId workScheduleCategoryId;

  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  class UpdateRequest {

    @Valid
    @NotNull
    FacilityId id;

    @Valid
    @NotNull
    FacilityCategoryId categoryId;

    @Size(max = TypeDefinitions.NAME_LENGTH)
    String name;

    @Valid
    @NotNull
    WorkScheduleCategoryId workScheduleCategoryId;

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
