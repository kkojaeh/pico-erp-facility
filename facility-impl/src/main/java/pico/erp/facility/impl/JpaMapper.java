package pico.erp.facility.impl;

import java.util.Optional;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;
import pico.erp.facility.FacilityExceptions;
import pico.erp.facility.core.FacilityCategoryRepository;
import pico.erp.facility.data.FacilityCategory;
import pico.erp.facility.data.FacilityCategoryId;
import pico.erp.facility.domain.Facility;
import pico.erp.facility.impl.jpa.FacilityEntity;

@Mapper
public abstract class JpaMapper {

  @Autowired
  private FacilityCategoryRepository facilityCategoryRepository;

  protected FacilityCategory map(FacilityCategoryId categoryId) {
    return Optional.ofNullable(categoryId)
      .map(id -> facilityCategoryRepository.findBy(categoryId)
        .orElseThrow(FacilityExceptions.CategoryNotFoundException::new)
      )
      .orElse(null);
  }

  protected Facility map(FacilityEntity entity) {
    return Facility.builder()
      .id(entity.getId())
      .category(map(entity.getCategoryId()))
      .name(entity.getName())
      .build();
  }


  @Mappings({
    @Mapping(target = "categoryId", source = "category.id"),
    @Mapping(target = "createdBy", ignore = true),
    @Mapping(target = "createdDate", ignore = true),
    @Mapping(target = "lastModifiedBy", ignore = true),
    @Mapping(target = "lastModifiedDate", ignore = true)
  })
  public abstract FacilityEntity map(Facility facility);

  public abstract void pass(FacilityEntity from, @MappingTarget FacilityEntity to);

}
