package pico.erp.facility.schedule;

import javax.validation.constraints.NotNull;
import kkojaeh.spring.boot.component.Give;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import pico.erp.facility.schedule.FacilityScheduleRequests.CreateRequest;
import pico.erp.facility.schedule.FacilityScheduleRequests.DeleteRequest;
import pico.erp.facility.schedule.FacilityScheduleRequests.UpdateRequest;
import pico.erp.shared.event.EventPublisher;

@SuppressWarnings("Duplicates")
@Service
@Give
@Transactional
@Validated
public class FacilityScheduleServiceLogic implements FacilityScheduleService {

  @Autowired
  private FacilityScheduleMapper mapper;

  @Autowired
  private FacilityScheduleRepository facilityScheduleRepository;

  @Autowired
  private EventPublisher eventPublisher;

  @Override
  public FacilityScheduleData create(CreateRequest request) {
    val facilitySchedule = new FacilitySchedule();
    val response = facilitySchedule.apply(mapper.map(request));
    if (facilityScheduleRepository.exists(facilitySchedule.getId())) {
      throw new FacilityScheduleExceptions.AlreadyExistsException();
    }
    val created = facilityScheduleRepository.create(facilitySchedule);
    eventPublisher.publishEvents(response.getEvents());
    return mapper.map(created);
  }

  @Override
  public void delete(DeleteRequest request) {
    val facilitySchedule = facilityScheduleRepository.findBy(request.getId())
      .orElseThrow(FacilityScheduleExceptions.NotFoundException::new);
    val response = facilitySchedule.apply(mapper.map(request));
    facilityScheduleRepository.deleteBy(facilitySchedule.getId());
    eventPublisher.publishEvents(response.getEvents());
  }

  @Override
  public boolean exists(@NotNull FacilityScheduleId id) {
    return facilityScheduleRepository.exists(id);
  }

  @Override
  public FacilityScheduleData get(@NotNull FacilityScheduleId id) {
    return facilityScheduleRepository.findBy(id)
      .map(mapper::map)
      .orElseThrow(FacilityScheduleExceptions.NotFoundException::new);
  }

  @Override
  public void update(UpdateRequest request) {
    val facilitySchedule = facilityScheduleRepository.findBy(request.getId())
      .orElseThrow(FacilityScheduleExceptions.NotFoundException::new);
    val response = facilitySchedule.apply(mapper.map(request));
    facilityScheduleRepository.update(facilitySchedule);
    eventPublisher.publishEvents(response.getEvents());
  }
}
