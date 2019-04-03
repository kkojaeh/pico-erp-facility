package pico.erp.config;

import kkojaeh.spring.boot.component.ComponentBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pico.erp.facility.category.FacilityCategory;
import pico.erp.facility.category.FacilityCategory.FacilityCategoryImpl;
import pico.erp.facility.category.FacilityCategoryId;

@Configuration
public class FacilityConfiguration {

  @Bean
  @ComponentBean
  public FacilityCategory packagingFacilityCategory() {
    return FacilityCategoryImpl.builder()
      .id(FacilityCategoryId.from("packaging"))
      .name("포장")
      .build();
  }

  @Bean
  @ComponentBean
  public FacilityCategory pressMoldingFacilityCategory() {
    return FacilityCategoryImpl.builder()
      .id(FacilityCategoryId.from("press-molding"))
      .name("압착 성형")
      .build();
  }

}
