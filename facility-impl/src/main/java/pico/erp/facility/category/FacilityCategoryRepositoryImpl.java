package pico.erp.facility.category;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;

@Repository
public class FacilityCategoryRepositoryImpl implements FacilityCategoryRepository {

  @Lazy
  @Autowired
  List<FacilityCategory> facilityCategories;

  @Override
  public Stream<FacilityCategory> findAll() {
    return facilityCategories.stream();
  }

  @Override
  public Optional<FacilityCategory> findBy(@NotNull FacilityCategoryId id) {
    return facilityCategories.stream()
      .filter(category -> category.getId().equals(id))
      .findFirst();
  }
}
