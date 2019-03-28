package pico.erp.facility.schedule;

import java.util.Optional;
import java.util.stream.Collectors;
import kkojaeh.spring.boot.component.Take;
import lombok.val;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import pico.erp.facility.Facility;
import pico.erp.facility.FacilityExceptions;
import pico.erp.facility.FacilityId;
import pico.erp.facility.FacilityMapper;
import pico.erp.facility.process.type.FacilityProcessTypeRepository;
import pico.erp.process.ProcessData;
import pico.erp.process.ProcessId;
import pico.erp.process.ProcessService;
import pico.erp.work.schedule.WorkScheduleRequests;
import pico.erp.work.schedule.WorkScheduleService;

@Mapper
public abstract class FacilityScheduleMapper {

  @Lazy
  @Autowired
  protected FacilityMapper facilityMapper;

  @Take
  private WorkScheduleService workScheduleService;

  @Lazy
  @Autowired
  private FacilityScheduleRepository facilityScheduleRepository;

  @Lazy
  @Autowired
  private FacilityProcessTypeRepository facilityProcessTypeRepository;

  @Take
  private ProcessService processService;

  @AfterMapping
  protected void afterMapping(FacilityScheduleRequests.CreateRequest from,
    @MappingTarget FacilityScheduleMessages.CreateRequest to) {
    to.setFacilityProcessTypes(
      facilityProcessTypeRepository.findAllBy(from.getFacilityId())
        .collect(Collectors.toSet())
    );
    to.setEnd(
      workScheduleService.calculateEnd(
        WorkScheduleRequests.CalculateEndRequest.builder()
          .begin(to.getBegin())
          .categoryId(to.getFacility().getWorkScheduleCategory().getId())
          .durationMinutes(to.getDurationMinutes())
          .build()
      )
    );
  }

  @AfterMapping
  protected void afterMapping(FacilityScheduleRequests.UpdateRequest from,
    @MappingTarget FacilityScheduleMessages.UpdateRequest to) {
    val facilitySchedule = facilityScheduleRepository.findBy(from.getId())
      .orElseThrow(FacilityExceptions.NotFoundException::new);

    to.setEnd(
      workScheduleService.calculateEnd(
        WorkScheduleRequests.CalculateEndRequest.builder()
          .begin(to.getBegin())
          .categoryId(facilitySchedule.getFacility().getWorkScheduleCategory().getId())
          .durationMinutes(to.getDurationMinutes())
          .build()
      )
    );
  }

  public FacilitySchedule jpa(FacilityScheduleEntity entity) {
    return FacilitySchedule.builder()
      .id(entity.getId())
      .facility(map(entity.getFacilityId()))
      .process(map(entity.getProcessId()))
      .begin(entity.getBegin())
      .end(entity.getEnd())
      .durationMinutes(entity.getDurationMinutes())
      .flexible(entity.isFlexible())
      .build();
  }

  @Mappings({
    @Mapping(target = "facilityId", source = "facility.id"),
    @Mapping(target = "processId", source = "process.id"),
    @Mapping(target = "createdBy", ignore = true),
    @Mapping(target = "createdDate", ignore = true),
    @Mapping(target = "lastModifiedBy", ignore = true),
    @Mapping(target = "lastModifiedDate", ignore = true)
  })
  public abstract FacilityScheduleEntity jpa(FacilitySchedule facility);

  @Mappings({
    @Mapping(target = "facility", source = "facilityId"),
    @Mapping(target = "process", source = "processId"),
    @Mapping(target = "facilityProcessTypes", ignore = true),
    @Mapping(target = "end", ignore = true)
  })
  public abstract FacilityScheduleMessages.CreateRequest map(
    FacilityScheduleRequests.CreateRequest request);

  @Mappings({
    @Mapping(target = "end", ignore = true)
  })
  public abstract FacilityScheduleMessages.UpdateRequest map(
    FacilityScheduleRequests.UpdateRequest request);

  public abstract FacilityScheduleMessages.DeleteRequest map(
    FacilityScheduleRequests.DeleteRequest request);

  @Mappings({
    @Mapping(target = "facilityId", source = "facility.id"),
    @Mapping(target = "processId", source = "process.id")
  })
  public abstract FacilityScheduleData map(FacilitySchedule facilitySchedule);

  protected ProcessData map(ProcessId processId) {
    return Optional.ofNullable(processId)
      .map(processService::get)
      .orElse(null);
  }

  protected Facility map(FacilityId facilityId) {
    return facilityMapper.map(facilityId);
  }

  public abstract void pass(FacilityScheduleEntity from,
    @MappingTarget FacilityScheduleEntity to);

}
