package pico.erp.facility.process.type;

import java.util.Optional;
import java.util.stream.Stream;
import javax.validation.constraints.NotNull;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pico.erp.facility.FacilityId;
import pico.erp.process.type.ProcessTypeId;

@Repository
interface FacilityProcessTypeEntityRepository extends
  CrudRepository<FacilityProcessTypeEntity, FacilityProcessTypeId> {

  @Query("SELECT CASE WHEN COUNT(fpt) > 0 THEN true ELSE false END FROM FacilityProcessType fpt WHERE fpt.facilityId = :facilityId AND fpt.processTypeId = :processTypeId")
  boolean exists(@Param("facilityId") FacilityId facilityId,
    @Param("processTypeId") ProcessTypeId processTypeId);

  @Query("SELECT fpt FROM FacilityProcessType fpt WHERE fpt.facilityId = :facilityId")
  Stream<FacilityProcessTypeEntity> findAllBy(@Param("facilityId") FacilityId facilityId);
}

@Repository
@Transactional
public class FacilityProcessTypeRepositoryJpa implements FacilityProcessTypeRepository {

  @Autowired
  private FacilityProcessTypeEntityRepository repository;

  @Autowired
  private FacilityProcessTypeMapper mapper;

  @Override
  public FacilityProcessType create(@NotNull FacilityProcessType facilityProcessType) {
    val entity = mapper.jpa(facilityProcessType);
    val created = repository.save(entity);
    return mapper.jpa(created);
  }

  @Override
  public void deleteBy(@NotNull FacilityProcessTypeId id) {
    repository.deleteById(id);
  }

  @Override
  public boolean exists(@NotNull FacilityProcessTypeId id) {
    return repository.existsById(id);
  }

  @Override
  public boolean exists(@NotNull FacilityId facilityId, @NotNull ProcessTypeId processTypeId) {
    return repository.exists(facilityId, processTypeId);
  }

  @Override
  public Stream<FacilityProcessType> findAllBy(@NotNull FacilityId facilityId) {
    return repository.findAllBy(facilityId)
      .map(mapper::jpa);
  }

  @Override
  public Optional<FacilityProcessType> findBy(@NotNull FacilityProcessTypeId id) {
    return repository.findById(id)
      .map(mapper::jpa);
  }

  @Override
  public void update(@NotNull FacilityProcessType facilityProcessType) {
    val entity = repository.findById(facilityProcessType.getId()).get();
    mapper.pass(mapper.jpa(facilityProcessType), entity);
    repository.save(entity);
  }

}
