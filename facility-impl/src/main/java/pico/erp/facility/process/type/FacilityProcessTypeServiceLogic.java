package pico.erp.facility.process.type;

import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import pico.erp.audit.AuditService;
import pico.erp.facility.Mapper;
import pico.erp.facility.process.type.data.FacilityProcessTypeData;
import pico.erp.facility.process.type.data.FacilityProcessTypeId;
import pico.erp.shared.Public;
import pico.erp.shared.event.EventPublisher;

@SuppressWarnings("Duplicates")
@Service
@Public
@Transactional
@Validated
public class FacilityProcessTypeServiceLogic implements FacilityProcessTypeService {

  @Autowired
  private FacilityProcessTypeRepository facilityProcessTypeRepository;

  @Autowired
  private EventPublisher eventPublisher;

  @Autowired
  private Mapper mapper;

  @Lazy
  @Autowired
  private AuditService auditService;

  @Override
  public FacilityProcessTypeData create(FacilityProcessTypeRequests.CreateRequest request) {
    val facilityProcessType = new FacilityProcessType();
    val response = facilityProcessType.apply(mapper.map(request));
    if (facilityProcessTypeRepository.exists(request.getFacilityId(), request.getProcessTypeId())) {
      throw new FacilityProcessTypeExceptions.AlreadyExistsException();
    }
    if (facilityProcessTypeRepository.exists(facilityProcessType.getId())) {
      throw new FacilityProcessTypeExceptions.AlreadyExistsException();
    }
    val created = facilityProcessTypeRepository.create(facilityProcessType);
    auditService.commit(created);
    eventPublisher.publishEvents(response.getEvents());
    return mapper.map(created);
  }

  @Override
  public void delete(FacilityProcessTypeRequests.DeleteRequest request) {
    val facilityProcessType = facilityProcessTypeRepository.findBy(request.getId())
      .orElseThrow(FacilityProcessTypeExceptions.NotFoundException::new);
    val response = facilityProcessType.apply(mapper.map(request));
    facilityProcessTypeRepository.deleteBy(facilityProcessType.getId());
    auditService.delete(facilityProcessType);
    eventPublisher.publishEvents(response.getEvents());
  }

  @Override
  public boolean exists(FacilityProcessTypeId id) {
    return facilityProcessTypeRepository.exists(id);
  }

  @Override
  public FacilityProcessTypeData get(FacilityProcessTypeId id) {
    return facilityProcessTypeRepository.findBy(id)
      .map(mapper::map)
      .orElseThrow(FacilityProcessTypeExceptions.NotFoundException::new);
  }

  @Override
  public void update(FacilityProcessTypeRequests.UpdateRequest request) {
    val facility = facilityProcessTypeRepository.findBy(request.getId())
      .orElseThrow(FacilityProcessTypeExceptions.NotFoundException::new);
    val response = facility.apply(mapper.map(request));
    facilityProcessTypeRepository.update(facility);
    auditService.commit(facility);
    eventPublisher.publishEvents(response.getEvents());
  }

}
