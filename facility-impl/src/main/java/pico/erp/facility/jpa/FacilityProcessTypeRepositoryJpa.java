package pico.erp.facility.jpa;

import java.util.Optional;
import javax.validation.constraints.NotNull;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pico.erp.facility.data.FacilityId;
import pico.erp.facility.process.type.FacilityProcessType;
import pico.erp.facility.process.type.FacilityProcessTypeRepository;
import pico.erp.facility.process.type.data.FacilityProcessTypeId;
import pico.erp.process.type.data.ProcessTypeId;

@Repository
interface FacilityProcessTypeEntityRepository extends
  CrudRepository<FacilityProcessTypeEntity, FacilityProcessTypeId> {

  @Query("SELECT CASE WHEN COUNT(fpt) > 0 THEN true ELSE false END FROM FacilityProcessType fpt WHERE fpt.facility.id = :facilityId AND fpt.processTypeId = :processTypeId")
  boolean exists(@Param("facilityId") FacilityId facilityId,
    @Param("processTypeId") ProcessTypeId processTypeId);
}

@Repository
@Transactional
public class FacilityProcessTypeRepositoryJpa implements FacilityProcessTypeRepository {

  @Autowired
  private FacilityProcessTypeEntityRepository repository;

  @Autowired
  private JpaMapper mapper;

  @Override
  public FacilityProcessType create(@NotNull FacilityProcessType facilityProcessType) {
    val entity = mapper.map(facilityProcessType);
    val created = repository.save(entity);
    return mapper.map(created);
  }

  @Override
  public void deleteBy(@NotNull FacilityProcessTypeId id) {
    repository.delete(id);
  }

  @Override
  public boolean exists(@NotNull FacilityProcessTypeId id) {
    return repository.exists(id);
  }

  @Override
  public boolean exists(@NotNull FacilityId facilityId, @NotNull ProcessTypeId processTypeId) {
    return false;
  }

  @Override
  public Optional<FacilityProcessType> findBy(@NotNull FacilityProcessTypeId id) {
    return Optional.ofNullable(repository.findOne(id))
      .map(mapper::map);
  }

  @Override
  public void update(@NotNull FacilityProcessType facilityProcessType) {
    val entity = repository.findOne(facilityProcessType.getId());
    mapper.pass(mapper.map(facilityProcessType), entity);
    repository.save(entity);
  }

}
