package pico.erp.facility.category;

import java.util.Optional;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import pico.erp.facility.FacilityExceptions;

@Mapper
public abstract class FacilityCategoryMapper {

  @Autowired
  private FacilityCategoryRepository facilityCategoryRepository;

  public FacilityCategory map(FacilityCategoryId categoryId) {
    return Optional.ofNullable(categoryId)
      .map(id -> facilityCategoryRepository.findBy(categoryId)
        .orElseThrow(FacilityExceptions.CategoryNotFoundException::new)
      )
      .orElse(null);
  }
}
