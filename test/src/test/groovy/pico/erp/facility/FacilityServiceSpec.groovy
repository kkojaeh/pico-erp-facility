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

  def id = FacilityId.from("packaging-11")

  def unknownId = FacilityId.from("unknown")

  def name = "포장 11 라인"

  def categoryId = FacilityCategoryId.from("packaging")

  def workScheduleCategoryId = WorkScheduleCategoryId.from("global")

  def setup() {
    facilityService.create(new FacilityRequests.CreateRequest(
      id: id,
      name: name,
      categoryId: categoryId,
      workScheduleCategoryId: workScheduleCategoryId
    ))
  }

  def "존재 - 아이디로 확인"() {
    when:
    def exists = facilityService.exists(id)

    then:
    exists == true
  }

  def "존재 - 존재하지 않는 아이디로 확인"() {
    when:
    def exists = facilityService.exists(unknownId)

    then:
    exists == false
  }

  def "조회 - 아이디로 조회"() {
    when:
    def facility = facilityService.get(id)

    then:
    facility.id == id
    facility.name == name
    facility.categoryId == categoryId
    facility.workScheduleCategoryId == workScheduleCategoryId
  }

  def "조회 - 존재하지 않는 아이디로 조회"() {
    when:
    facilityService.get(unknownId)

    then:
    thrown(FacilityExceptions.NotFoundException)
  }

}
