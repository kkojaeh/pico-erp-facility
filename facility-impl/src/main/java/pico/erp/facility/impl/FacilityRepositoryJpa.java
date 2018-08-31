package pico.erp.facility.impl;

import java.util.Optional;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pico.erp.facility.core.FacilityRepository;
import pico.erp.facility.data.FacilityId;
import pico.erp.facility.domain.Facility;
import pico.erp.facility.impl.jpa.FacilityEntity;

@Repository
interface FacilityEntityRepository extends CrudRepository<FacilityEntity, FacilityId> {

}

@Repository
@Transactional
public class FacilityRepositoryJpa implements FacilityRepository {

  @Autowired
  private FacilityEntityRepository repository;

  @Autowired
  private JpaMapper mapper;


  @Override
  public Facility create(Facility facility) {
    val entity = mapper.map(facility);
    val created = repository.save(entity);
    return mapper.map(created);
  }

  @Override
  public void deleteBy(FacilityId id) {
    repository.delete(id);
  }

  @Override
  public boolean exists(FacilityId id) {
    return repository.exists(id);
  }

  @Override
  public Optional<Facility> findBy(FacilityId id) {
    return Optional.ofNullable(repository.findOne(id))
      .map(mapper::map);
  }

  @Override
  public void update(Facility facility) {
    val entity = repository.findOne(facility.getId());
    mapper.pass(mapper.map(facility), entity);
    repository.save(entity);
  }
}
