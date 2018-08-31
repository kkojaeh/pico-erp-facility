package pico.erp.facility;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import pico.erp.shared.data.Menu;
import pico.erp.shared.data.MenuCategory;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum MENU implements Menu {

  FACILITY_MANAGEMENT("/work-day", "fas fa-building", MenuCategory.SETTINGS);

  String url;

  String icon;

  MenuCategory category;

  public String getId() {
    return name();
  }

}
