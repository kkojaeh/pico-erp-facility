package pico.erp.facility;

import java.util.Optional;
import org.mapstruct.Mapping;
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
import pico.erp.facility.process.type.FacilityProcessTypeRequests;
import pico.erp.facility.process.type.data.FacilityProcessTypeData;
import pico.erp.process.type.ProcessTypeService;
import pico.erp.process.type.data.ProcessTypeData;
import pico.erp.process.type.data.ProcessTypeId;

@org.mapstruct.Mapper
public abstract class Mapper {

  @Autowired
  private FacilityCategoryRepository facilityCategoryRepository;

  @Lazy
  @Autowired
  private FacilityRepository facilityRepository;

  @Lazy
  @Autowired
  private ProcessTypeService processTypeService;

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

  @Mappings({
    @Mapping(target = "categoryId", source = "category.id")
  })
  public abstract FacilityData map(Facility facility);

  @Mappings({
    @Mapping(target = "facilityId", source = "facility.id"),
    @Mapping(target = "processTypeId", source = "processTypeData.id")
  })
  public abstract FacilityProcessTypeData map(FacilityProcessType facilityProcessType);

  @Mappings({
    @Mapping(target = "category", source = "categoryId")
  })
  public abstract FacilityMessages.CreateRequest map(FacilityRequests.CreateRequest request);

  @Mappings({
    @Mapping(target = "category", source = "categoryId")
  })
  public abstract FacilityMessages.UpdateRequest map(FacilityRequests.UpdateRequest request);

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

}
