package pico.erp.facility.jpa;

import java.util.Optional;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pico.erp.facility.schedule.FacilitySchedule;
import pico.erp.facility.schedule.FacilityScheduleRepository;
import pico.erp.facility.schedule.data.FacilityScheduleId;

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
  private JpaMapper mapper;


  @Override
  public FacilitySchedule create(FacilitySchedule facilitySchedule) {
    val entity = mapper.map(facilitySchedule);
    val created = repository.save(entity);
    return mapper.map(created);
  }

  @Override
  public void deleteBy(FacilityScheduleId id) {
    repository.delete(id);
  }

  @Override
  public boolean exists(FacilityScheduleId id) {
    return repository.exists(id);
  }

  @Override
  public Optional<FacilitySchedule> findBy(FacilityScheduleId id) {
    return Optional.ofNullable(repository.findOne(id))
      .map(mapper::map);
  }

  @Override
  public void update(FacilitySchedule facilitySchedule) {
    val entity = repository.findOne(facilitySchedule.getId());
    mapper.pass(mapper.map(facilitySchedule), entity);
    repository.save(entity);
  }
}
