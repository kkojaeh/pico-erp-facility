package pico.erp.facility;

import kkojaeh.spring.boot.component.ComponentBean;
import kkojaeh.spring.boot.component.SpringBootComponent;
import kkojaeh.spring.boot.component.SpringBootComponentBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import pico.erp.ComponentDefinition;
import pico.erp.facility.FacilityApi.Roles;
import pico.erp.shared.SharedConfiguration;
import pico.erp.shared.data.Role;

@Slf4j
@SpringBootComponent("facility")
@EntityScan
@EnableAspectJAutoProxy
@EnableTransactionManagement
@EnableJpaRepositories
@EnableJpaAuditing(auditorAwareRef = "auditorAware", dateTimeProviderRef = "dateTimeProvider")
@SpringBootApplication
@Import(value = {
  SharedConfiguration.class
})
public class FacilityApplication implements ComponentDefinition {

  public static void main(String[] args) {
    new SpringBootComponentBuilder()
      .component(FacilityApplication.class)
      .run(args);
  }

  @Bean
  @ComponentBean(host = false)
  public Role facilityAccessorRole() {
    return Roles.FACILITY_ACCESSOR;
  }

  @Bean
  @ComponentBean(host = false)
  public Role facilityManagerRole() {
    return Roles.FACILITY_MANAGER;
  }

  @Override
  public Class<?> getComponentClass() {
    return FacilityApplication.class;
  }
}
