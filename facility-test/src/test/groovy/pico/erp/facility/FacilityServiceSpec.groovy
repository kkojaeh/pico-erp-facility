package pico.erp.facility

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.test.annotation.Rollback
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional
import pico.erp.facility.category.FacilityCategoryId
import pico.erp.shared.IntegrationConfiguration
import pico.erp.work.schedule.category.WorkScheduleCategoryId
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
      categoryId: FacilityCategoryId.from("packaging"),
      workScheduleCategoryId: WorkScheduleCategoryId.from("global")
    ))
  }

  def "아이디로 존재하는 설비 확인"() {
    when:
    def exists = facilityService.exists(facilityId)

    then:
    exists == true
  }

  def "아이디로 존재하지 않는 설비 확인"() {
    when:
    def exists = facilityService.exists(FacilityId.from("packaging-00"))

    then:
    exists == false
  }

  def "아이디로 존재하는 설비를 조회"() {
    when:
    def facility = facilityService.get(facilityId)

    then:
    facility.name == "포장 11 라인"
    facility.categoryId == FacilityCategoryId.from("packaging")
  }

  def "아이디로 존재하지 않는 설비을 조회"() {
    when:
    facilityService.get(FacilityId.from("packaging-00"))

    then:
    thrown(FacilityExceptions.NotFoundException)
  }

}
