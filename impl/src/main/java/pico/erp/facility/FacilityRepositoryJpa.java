package pico.erp.facility;

import java.util.Optional;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
interface FacilityEntityRepository extends CrudRepository<FacilityEntity, FacilityId> {

}

@Repository
@Transactional
public class FacilityRepositoryJpa implements FacilityRepository {

  @Autowired
  private FacilityEntityRepository repository;

  @Autowired
  private FacilityMapper mapper;


  @Override
  public Facility create(Facility facility) {
    val entity = mapper.jpa(facility);
    val created = repository.save(entity);
    return mapper.jpa(created);
  }

  @Override
  public void deleteBy(FacilityId id) {
    repository.deleteById(id);
  }

  @Override
  public boolean exists(FacilityId id) {
    return repository.existsById(id);
  }

  @Override
  public Optional<Facility> findBy(FacilityId id) {
    return repository.findById(id)
      .map(mapper::jpa);
  }

  @Override
  public void update(Facility facility) {
    val entity = repository.findById(facility.getId()).get();
    mapper.pass(mapper.jpa(facility), entity);
    repository.save(entity);
  }
}
