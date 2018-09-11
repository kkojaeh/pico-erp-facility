package pico.erp.facility.process.data;

import java.io.Serializable;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;
import pico.erp.facility.data.FacilityCategoryId;
import pico.erp.facility.data.FacilityId;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode(of = "id")
public class FacilityProcessTypeData implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  FacilityId id;

  String name;

  FacilityCategoryId categoryId;

}
