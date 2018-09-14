package pico.erp.facility

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.test.annotation.Rollback
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional
import pico.erp.facility.category.data.FacilityCategoryId
import pico.erp.facility.data.FacilityId
import pico.erp.shared.IntegrationConfiguration
import spock.lang.Specification

@SpringBootTest(classes = [IntegrationConfiguration])
@Transactional
@Rollback
@ActiveProfiles("test")
@Configuration
@ComponentScan("pico.erp.config")
class FacilityServiceSpec extends Specification {

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

  def "아이디로 존재하는 작업일 확인"() {
    when:
    def exists = facilityService.exists(facilityId)

    then:
    exists == true
  }

  def "아이디로 존재하지 않는 작업일 확인"() {
    when:
    def exists = facilityService.exists(FacilityId.from("packaging-00"))

    then:
    exists == false
  }

  def "아이디로 존재하는 작업일를 조회"() {
    when:
    def workDay = facilityService.get(facilityId)

    then:
    workDay.name == "포장 11 라인"
    workDay.categoryId == FacilityCategoryId.from("packaging")
  }

  def "아이디로 존재하지 않는 작업일을 조회"() {
    when:
    facilityService.get(FacilityId.from("packaging-00"))

    then:
    thrown(FacilityExceptions.NotFoundException)
  }

}
