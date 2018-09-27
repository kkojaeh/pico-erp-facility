package pico.erp.facility.jpa;

import java.util.Optional;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import pico.erp.facility.Facility;
import pico.erp.facility.FacilityExceptions;
import pico.erp.facility.FacilityRepository;
import pico.erp.facility.category.FacilityCategoryRepository;
import pico.erp.facility.category.data.FacilityCategory;
import pico.erp.facility.category.data.FacilityCategoryId;
import pico.erp.facility.data.FacilityId;
import pico.erp.facility.process.type.FacilityProcessType;
import pico.erp.facility.schedule.FacilitySchedule;
import pico.erp.process.ProcessService;
import pico.erp.process.data.ProcessData;
import pico.erp.process.data.ProcessId;
import pico.erp.process.type.ProcessTypeService;
import pico.erp.process.type.data.ProcessTypeData;
import pico.erp.process.type.data.ProcessTypeId;
import pico.erp.work.schedule.WorkScheduleService;
import pico.erp.work.schedule.category.data.WorkScheduleCategory;
import pico.erp.work.schedule.category.data.WorkScheduleCategoryId;

@Mapper
public abstract class JpaMapper {

  @Autowired
  private FacilityCategoryRepository facilityCategoryRepository;

  @Lazy
  @Autowired
  private ProcessTypeService processTypeService;

  @Lazy
  @Autowired
  private ProcessService processService;

  @Lazy
  @Autowired
  private FacilityRepository facilityRepository;

  @Lazy
  @Autowired
  private WorkScheduleService workScheduleService;

  protected WorkScheduleCategory map(WorkScheduleCategoryId workScheduleCategoryId) {
    return Optional.ofNullable(workScheduleCategoryId)
      .map(workScheduleService::get)
      .orElse(null);
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

  protected ProcessData map(ProcessId processId) {
    return Optional.ofNullable(processId)
      .map(processService::get)
      .orElse(null);
  }

  public Facility map(FacilityEntity entity) {
    return Facility.builder()
      .id(entity.getId())
      .category(map(entity.getCategoryId()))
      .name(entity.getName())
      .workScheduleCategory(map(entity.getWorkScheduleCategoryId()))
      .build();
  }

  public FacilityProcessType map(FacilityProcessTypeEntity entity) {
    return FacilityProcessType.builder()
      .id(entity.getId())
      .facility(map(entity.getFacility()))
      .processTypeData(map(entity.getProcessTypeId()))
      .speedVariationPrate(entity.getSpeedVariationPrate())
      .defectiveVariationPrate(entity.getDefectiveVariationPrate())
      .build();
  }

  public FacilitySchedule map(FacilityScheduleEntity entity) {
    return FacilitySchedule.builder()
      .id(entity.getId())
      .facility(map(entity.getFacility()))
      .process(map(entity.getProcessId()))
      .begin(entity.getBegin())
      .end(entity.getEnd())
      .durationMinutes(entity.getDurationMinutes())
      .flexible(entity.isFlexible())
      .build();
  }


  @Mappings({
    @Mapping(target = "categoryId", source = "category.id"),
    @Mapping(target = "workScheduleCategoryId", source = "workScheduleCategory.id"),
    @Mapping(target = "createdBy", ignore = true),
    @Mapping(target = "createdDate", ignore = true),
    @Mapping(target = "lastModifiedBy", ignore = true),
    @Mapping(target = "lastModifiedDate", ignore = true)
  })
  public abstract FacilityEntity map(Facility facility);

  @Mappings({
    @Mapping(target = "facility", source = "facility.id"),
    @Mapping(target = "processTypeId", source = "processTypeData.id"),
    @Mapping(target = "createdBy", ignore = true),
    @Mapping(target = "createdDate", ignore = true),
    @Mapping(target = "lastModifiedBy", ignore = true),
    @Mapping(target = "lastModifiedDate", ignore = true)
  })
  public abstract FacilityProcessTypeEntity map(FacilityProcessType facilityProcessType);

  @Mappings({
    @Mapping(target = "facility", source = "facility.id"),
    @Mapping(target = "processId", source = "process.id"),
    @Mapping(target = "createdBy", ignore = true),
    @Mapping(target = "createdDate", ignore = true),
    @Mapping(target = "lastModifiedBy", ignore = true),
    @Mapping(target = "lastModifiedDate", ignore = true)
  })
  public abstract FacilityScheduleEntity map(FacilitySchedule facility);

  public abstract void pass(FacilityEntity from, @MappingTarget FacilityEntity to);

  public abstract void pass(FacilityProcessTypeEntity from,
    @MappingTarget FacilityProcessTypeEntity to);

  public abstract void pass(FacilityScheduleEntity from,
    @MappingTarget FacilityScheduleEntity to);

}
