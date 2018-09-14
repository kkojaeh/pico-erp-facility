package pico.erp.facility.category.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public interface FacilityCategory {

  FacilityCategoryId getId();

  String getName();

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  class FacilityCategoryImpl implements FacilityCategory{

    FacilityCategoryId id;

    String name;
  }

}
