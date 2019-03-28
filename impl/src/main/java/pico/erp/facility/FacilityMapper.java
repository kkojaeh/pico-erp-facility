package pico.erp.facility;

import java.util.Optional;
import kkojaeh.spring.boot.component.Take;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import pico.erp.facility.category.FacilityCategory;
import pico.erp.facility.category.FacilityCategoryId;
import pico.erp.facility.category.FacilityCategoryMapper;
import pico.erp.facility.process.type.FacilityProcessTypeRepository;
import pico.erp.facility.schedule.FacilityScheduleRepository;
import pico.erp.process.ProcessData;
import pico.erp.process.ProcessId;
import pico.erp.process.ProcessService;
import pico.erp.work.schedule.WorkScheduleService;
import pico.erp.work.schedule.category.WorkScheduleCategory;
import pico.erp.work.schedule.category.WorkScheduleCategoryId;

@org.mapstruct.Mapper
public abstract class FacilityMapper {

  @Lazy
  @Autowired
  protected FacilityCategoryMapper categoryMapper;

  @Lazy
  @Autowired
  private FacilityRepository facilityRepository;

  @Lazy
  @Autowired
  private FacilityScheduleRepository facilityScheduleRepository;

  @Lazy
  @Autowired
  private FacilityProcessTypeRepository facilityProcessTypeRepository;

  @Take
  private WorkScheduleService workScheduleService;

  @Take
  private ProcessService processService;

  public Facility jpa(FacilityEntity entity) {
    return Facility.builder()
      .id(entity.getId())
      .category(map(entity.getCategoryId()))
      .name(entity.getName())
      .workScheduleCategory(map(entity.getWorkScheduleCategoryId()))
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
  public abstract FacilityEntity jpa(Facility facility);

  public Facility map(FacilityId facilityId) {
    return Optional.ofNullable(facilityId)
      .map(id -> facilityRepository.findBy(facilityId)
        .orElseThrow(FacilityExceptions.NotFoundException::new)
      )
      .orElse(null);
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

  protected ProcessData map(ProcessId processId) {
    return Optional.ofNullable(processId)
      .map(processService::get)
      .orElse(null);
  }

  public abstract FacilityMessages.DeleteRequest map(FacilityRequests.DeleteRequest request);

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

  protected FacilityCategory map(FacilityCategoryId categoryId) {
    return categoryMapper.map(categoryId);
  }

  public abstract void pass(FacilityEntity from, @MappingTarget FacilityEntity to);

}
