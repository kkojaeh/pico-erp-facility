package pico.erp.facility;

import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import pico.erp.audit.AuditService;
import pico.erp.facility.category.FacilityCategory;
import pico.erp.facility.category.FacilityCategoryId;
import pico.erp.facility.category.FacilityCategoryRepository;
import pico.erp.shared.Public;
import pico.erp.shared.event.EventPublisher;

@SuppressWarnings("Duplicates")
@Service
@Public
@Transactional
@Validated
public class FacilityServiceLogic implements FacilityService {

  @Autowired
  private FacilityRepository facilityRepository;

  @Autowired
  private EventPublisher eventPublisher;

  @Autowired
  private FacilityMapper mapper;

  @Lazy
  @Autowired
  private AuditService auditService;

  @Autowired
  private FacilityCategoryRepository facilityCategoryRepository;

  @Override
  public FacilityData create(FacilityRequests.CreateRequest request) {
    val facility = new Facility();
    val response = facility.apply(mapper.map(request));
    if (facilityRepository.exists(facility.getId())) {
      throw new FacilityExceptions.AlreadyExistsException();
    }
    val created = facilityRepository.create(facility);
    auditService.commit(created);
    eventPublisher.publishEvents(response.getEvents());
    return mapper.map(created);
  }

  @Override
  public void delete(FacilityRequests.DeleteRequest request) {
    val facility = facilityRepository.findBy(request.getId())
      .orElseThrow(FacilityExceptions.NotFoundException::new);
    val response = facility.apply(mapper.map(request));
    facilityRepository.deleteBy(facility.getId());
    auditService.delete(facility);
    eventPublisher.publishEvents(response.getEvents());
  }

  @Override
  public boolean exists(FacilityId id) {
    return facilityRepository.exists(id);
  }

  @Override
  public FacilityData get(FacilityId id) {
    return facilityRepository.findBy(id)
      .map(mapper::map)
      .orElseThrow(FacilityExceptions.NotFoundException::new);
  }

  @Override
  public void update(FacilityRequests.UpdateRequest request) {
    val facility = facilityRepository.findBy(request.getId())
      .orElseThrow(FacilityExceptions.NotFoundException::new);
    val response = facility.apply(mapper.map(request));
    facilityRepository.update(facility);
    auditService.commit(facility);
    eventPublisher.publishEvents(response.getEvents());
  }

  @Override
  public FacilityCategory get(FacilityCategoryId id) {
    return facilityCategoryRepository.findBy(id)
      .orElseThrow(FacilityExceptions.CategoryNotFoundException::new);
  }

}
