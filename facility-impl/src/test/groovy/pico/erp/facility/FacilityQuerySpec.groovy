package pico.erp.facility

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.domain.PageRequest
import org.springframework.test.annotation.Rollback
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional
import pico.erp.facility.data.FacilityCategoryId
import pico.erp.facility.data.FacilityId
import pico.erp.facility.data.FacilityView
import pico.erp.shared.IntegrationConfiguration
import spock.lang.Specification

@SpringBootTest(classes = [IntegrationConfiguration])
@Transactional
@Rollback
@ActiveProfiles("test")
@Configuration
@ComponentScan("pico.erp.config")
class FacilityQuerySpec extends Specification {

  @Autowired
  FacilityQuery facilityQuery


  @Autowired
  FacilityService facilityService

  def facilityId = FacilityId.from("packaging-11")

  def setup() {
    facilityService.create(new FacilityRequests.CreateRequest(
      id: facilityId,
      name: "포장 11 라인",
      categoryId: FacilityCategoryId.from("packaging")
    ))
  }

  def "카테고리로 검색"() {
    when:
    def result = facilityQuery.retrieve(new FacilityView.Filter(
      categoryId: FacilityCategoryId.from("packaging")
    ), new PageRequest(0, 10))

    then:
    result.totalElements == 3
  }

}
