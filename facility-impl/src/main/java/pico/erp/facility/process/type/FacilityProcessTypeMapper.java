package pico.erp.facility.process.type;

import java.util.Optional;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import pico.erp.facility.Facility;
import pico.erp.facility.FacilityEntity;
import pico.erp.facility.FacilityId;
import pico.erp.facility.FacilityMapper;
import pico.erp.process.type.ProcessTypeData;
import pico.erp.process.type.ProcessTypeId;
import pico.erp.process.type.ProcessTypeService;

@Mapper
public abstract class FacilityProcessTypeMapper {

  @Lazy
  @Autowired
  protected FacilityMapper facilityMapper;

  @Lazy
  @Autowired
  private ProcessTypeService processTypeService;

  public FacilityProcessType jpa(FacilityProcessTypeEntity entity) {
    return FacilityProcessType.builder()
      .id(entity.getId())
      .facility(facilityMapper.jpa(entity.getFacility()))
      .processTypeData(map(entity.getProcessTypeId()))
      .speedVariationRate(entity.getSpeedVariationRate())
      .defectiveVariationRate(entity.getDefectiveVariationRate())
      .build();
  }

  @Mappings({
    @Mapping(target = "facility", source = "facility.id"),
    @Mapping(target = "processTypeId", source = "processTypeData.id"),
    @Mapping(target = "createdBy", ignore = true),
    @Mapping(target = "createdDate", ignore = true),
    @Mapping(target = "lastModifiedBy", ignore = true),
    @Mapping(target = "lastModifiedDate", ignore = true)
  })
  public abstract FacilityProcessTypeEntity jpa(FacilityProcessType facilityProcessType);

  protected FacilityEntity jpa(FacilityId facilityId) {
    return facilityMapper.jpa(facilityId);
  }

  protected ProcessTypeData map(ProcessTypeId processTypeId) {
    return Optional.ofNullable(processTypeId)
      .map(processTypeService::get)
      .orElse(null);
  }

  @Mappings({
    @Mapping(target = "facilityId", source = "facility.id"),
    @Mapping(target = "processTypeId", source = "processTypeData.id")
  })
  public abstract FacilityProcessTypeData map(FacilityProcessType facilityProcessType);

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

  protected Facility map(FacilityId facilityId) {
    return facilityMapper.map(facilityId);
  }

  public abstract void pass(FacilityProcessTypeEntity from,
    @MappingTarget FacilityProcessTypeEntity to);


}
