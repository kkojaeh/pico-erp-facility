package pico.erp.facility.core;

import java.util.Optional;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;
import pico.erp.facility.FacilityExceptions;
import pico.erp.facility.FacilityRequests;
import pico.erp.facility.data.FacilityCategory;
import pico.erp.facility.data.FacilityCategoryId;
import pico.erp.facility.data.FacilityData;
import pico.erp.facility.domain.Facility;
import pico.erp.facility.domain.FacilityMessages;

@org.mapstruct.Mapper
public abstract class Mapper {

  @Autowired
  private FacilityCategoryRepository facilityCategoryRepository;

  protected FacilityCategory map(FacilityCategoryId categoryId) {
    return Optional.ofNullable(categoryId)
      .map(id -> facilityCategoryRepository.findBy(categoryId)
        .orElseThrow(FacilityExceptions.CategoryNotFoundException::new)
      )
      .orElse(null);
  }

  @Mappings({
    @Mapping(target = "categoryId", source = "category.id")
  })
  public abstract FacilityData map(Facility facility);

  @Mappings({
    @Mapping(target = "category", source = "categoryId")
  })
  public abstract FacilityMessages.CreateRequest map(FacilityRequests.CreateRequest request);

  @Mappings({
    @Mapping(target = "category", source = "categoryId")
  })
  public abstract FacilityMessages.UpdateRequest map(FacilityRequests.UpdateRequest request);

  public abstract FacilityMessages.DeleteRequest map(FacilityRequests.DeleteRequest request);

}
