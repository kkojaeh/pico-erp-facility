package pico.erp.facility;

import javax.persistence.Id;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import pico.erp.shared.data.Role;

public final class FacilityApi {

  @RequiredArgsConstructor
  public enum Roles implements Role {

    FACILITY_MANAGER,

    FACILITY_ACCESSOR;


    @Id
    @Getter
    private final String id = name();

  }
}
