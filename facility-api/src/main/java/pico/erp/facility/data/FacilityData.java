package pico.erp.facility.data;

import java.io.Serializable;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;
import pico.erp.facility.category.data.FacilityCategoryId;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode(of = "id")
public class FacilityData implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  FacilityId id;

  String name;

  FacilityCategoryId categoryId;

}
