package pico.erp.facility;

import javax.persistence.Id;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import pico.erp.shared.data.Role;

@RequiredArgsConstructor
public enum FacilityRoles implements Role {

  FACILITY_MANAGER,

  FACILITY_ACCESSOR;


  @Id
  @Getter
  private final String id = name();

}
