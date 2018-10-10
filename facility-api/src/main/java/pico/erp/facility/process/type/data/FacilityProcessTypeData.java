package pico.erp.facility.process.type.data;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;
import pico.erp.facility.data.FacilityId;
import pico.erp.process.type.ProcessTypeId;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode(of = "id")
public class FacilityProcessTypeData implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  FacilityProcessTypeId id;

  FacilityId facilityId;

  ProcessTypeId processTypeId;

  BigDecimal speedVariationRate;

  BigDecimal defectiveVariationRate;

}
