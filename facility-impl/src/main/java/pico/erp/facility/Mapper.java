package pico.erp.facility;

import java.util.Optional;
import java.util.stream.Collectors;
import lombok.val;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import pico.erp.facility.category.FacilityCategoryRepository;
import pico.erp.facility.category.data.FacilityCategory;
import pico.erp.facility.category.data.FacilityCategoryId;
import pico.erp.facility.data.FacilityData;
import pico.erp.facility.data.FacilityId;
import pico.erp.facility.process.type.FacilityProcessType;
import pico.erp.facility.process.type.FacilityProcessTypeMessages;
import pico.erp.facility.process.type.FacilityProcessTypeRepository;
import pico.erp.facility.process.type.FacilityProcessTypeRequests;
import pico.erp.facility.process.type.data.FacilityProcessTypeData;
import pico.erp.facility.schedule.FacilitySchedule;
import pico.erp.facility.schedule.FacilityScheduleMessages;
import pico.erp.facility.schedule.FacilityScheduleRepository;
import pico.erp.facility.schedule.FacilityScheduleRequests;
import pico.erp.facility.schedule.data.FacilityScheduleData;
import pico.erp.process.ProcessData;
import pico.erp.process.ProcessId;
import pico.erp.process.ProcessService;
import pico.erp.process.type.ProcessTypeData;
import pico.erp.process.type.ProcessTypeId;
import pico.erp.process.type.ProcessTypeService;
import pico.erp.work.schedule.WorkScheduleRequests;
import pico.erp.work.schedule.WorkScheduleService;
import pico.erp.work.schedule.category.WorkScheduleCategory;
import pico.erp.work.schedule.category.WorkScheduleCategoryId;

@org.mapstruct.Mapper
public abstract class Mapper {

  @Autowired
  private FacilityCategoryRepository facilityCategoryRepository;

  @Lazy
  @Autowired
  private FacilityRepository facilityRepository;

  @Lazy
  @Autowired
  private FacilityScheduleRepository facilityScheduleRepository;

  @Lazy
  @Autowired
  private FacilityProcessTypeRepository facilityProcessTypeRepository;

  @Lazy
  @Autowired
  private ProcessTypeService processTypeService;

  @Lazy
  @Autowired
  private WorkScheduleService workScheduleService;

  @Lazy
  @Autowired
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

  protected FacilityCategory map(FacilityCategoryId categoryId) {
    return Optional.ofNullable(categoryId)
      .map(id -> facilityCategoryRepository.findBy(categoryId)
        .orElseThrow(FacilityExceptions.CategoryNotFoundException::new)
      )
      .orElse(null);
  }

  protected Facility map(FacilityId facilityId) {
    return Optional.ofNullable(facilityId)
      .map(id -> facilityRepository.findBy(facilityId)
        .orElseThrow(FacilityExceptions.NotFoundException::new)
      )
      .orElse(null);
  }

  protected ProcessTypeData map(ProcessTypeId processTypeId) {
    return Optional.ofNullable(processTypeId)
      .map(processTypeService::get)
      .orElse(null);
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

  @Mappings({
    @Mapping(target = "categoryId", source = "category.id"),
    @Mapping(target = "workScheduleCategoryId", source = "workScheduleCategory.id")
  })
  public abstract FacilityData map(Facility facility);

  protected WorkScheduleCategory map(WorkScheduleCategoryId workScheduleCategoryId) {
    return Optional.ofNullable(workScheduleCategoryId)
      .map(workScheduleService::get)
      .orElse(null);
  }

  @Mappings({
    @Mapping(target = "facilityId", source = "facility.id"),
    @Mapping(target = "processTypeId", source = "processTypeData.id")
  })
  public abstract FacilityProcessTypeData map(FacilityProcessType facilityProcessType);

  protected ProcessData map(ProcessId processId) {
    return Optional.ofNullable(processId)
      .map(processService::get)
      .orElse(null);
  }

  @Mappings({
    @Mapping(target = "facilityId", source = "facility.id"),
    @Mapping(target = "processId", source = "process.id")
  })
  public abstract FacilityScheduleData map(FacilitySchedule facilitySchedule);

  public abstract FacilityMessages.DeleteRequest map(FacilityRequests.DeleteRequest request);

  @Mappings({
    @Mapping(target = "facility", source = "facilityId"),
    @Mapping(target = "processTypeData", source = "processTypeId")
  })
  public abstract FacilityProcessTypeMessages.CreateRequest map(
    FacilityProcessTypeRequests.CreateRequest request);

  @Mappings({
  })
  public abstract FacilityProcessTypeMessages.UpdateRequest map(
    FacilityProcessTypeRequests.UpdateRequest request);

  public abstract FacilityProcessTypeMessages.DeleteRequest map(
    FacilityProcessTypeRequests.DeleteRequest request);

  @Mappings({
    @Mapping(target = "category", source = "categoryId"),
    @Mapping(target = "workScheduleCategory", source = "workScheduleCategoryId")
  })
  public abstract FacilityMessages.CreateRequest map(FacilityRequests.CreateRequest request);

  @Mappings({
    @Mapping(target = "category", source = "categoryId"),
    @Mapping(target = "workScheduleCategory", source = "workScheduleCategoryId")
  })
  public abstract FacilityMessages.UpdateRequest map(FacilityRequests.UpdateRequest request);

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

}
