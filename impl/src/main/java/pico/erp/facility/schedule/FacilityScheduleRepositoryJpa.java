package pico.erp.facility.schedule;

import java.util.Optional;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
interface FacilityScheduleEntityRepository extends
  CrudRepository<FacilityScheduleEntity, FacilityScheduleId> {

}

@Repository
@Transactional
public class FacilityScheduleRepositoryJpa implements FacilityScheduleRepository {

  @Autowired
  private FacilityScheduleEntityRepository repository;

  @Autowired
  private FacilityScheduleMapper mapper;


  @Override
  public FacilitySchedule create(FacilitySchedule facilitySchedule) {
    val entity = mapper.jpa(facilitySchedule);
    val created = repository.save(entity);
    return mapper.jpa(created);
  }

  @Override
  public void deleteBy(FacilityScheduleId id) {
    repository.deleteById(id);
  }

  @Override
  public boolean exists(FacilityScheduleId id) {
    return repository.existsById(id);
  }

  @Override
  public Optional<FacilitySchedule> findBy(FacilityScheduleId id) {
    return repository.findById(id)
      .map(mapper::jpa);
  }

  @Override
  public void update(FacilitySchedule facilitySchedule) {
    val entity = repository.findById(facilitySchedule.getId()).get();
    mapper.pass(mapper.jpa(facilitySchedule), entity);
    repository.save(entity);
  }
}
