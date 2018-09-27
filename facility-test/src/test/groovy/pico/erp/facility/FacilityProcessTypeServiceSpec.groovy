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
import pico.erp.facility.process.type.FacilityProcessTypeExceptions
import pico.erp.facility.process.type.FacilityProcessTypeRequests
import pico.erp.facility.process.type.FacilityProcessTypeService
import pico.erp.facility.process.type.data.FacilityProcessTypeId
import pico.erp.process.type.data.ProcessTypeId
import pico.erp.shared.IntegrationConfiguration
import pico.erp.work.schedule.category.data.WorkScheduleCategoryId
import spock.lang.Specification

@SpringBootTest(classes = [IntegrationConfiguration])
@Transactional
@Rollback
@ActiveProfiles("test")
@Configuration
@ComponentScan("pico.erp.config")
class FacilityProcessTypeServiceSpec extends Specification {

  @Autowired
  FacilityProcessTypeService facilityProcessTypeService

  @Autowired
  FacilityService facilityService

  def facilityId = FacilityId.from("packaging-11")

  def processTypeId = ProcessTypeId.from("printing-offset")

  def facilityProcessTypeId = FacilityProcessTypeId.from("packaging-printing-offset")

  def setup() {
    facilityService.create(new FacilityRequests.CreateRequest(
      id: facilityId,
      name: "포장 11 라인",
      categoryId: FacilityCategoryId.from("packaging"),
      workScheduleCategoryId: WorkScheduleCategoryId.from("global")
    ))
    facilityProcessTypeService.create(new FacilityProcessTypeRequests.CreateRequest(
      id: facilityProcessTypeId,
      facilityId: facilityId,
      processTypeId: processTypeId,
      speedVariationRate: 0,
      defectiveVariationRate: 0
    ))
  }

  def "설비 아이디로 전체 설비공정유형 확인"() {
    when:
    def all = facilityProcessTypeService.getAll(facilityId)

    then:
    all.size() == 1
  }

  def "아이디로 존재하는 설비공정유형 확인"() {
    when:
    def exists = facilityProcessTypeService.exists(facilityProcessTypeId)

    then:
    exists == true
  }

  def "아이디로 존재하지 않는 설비공정유형 확인"() {
    when:
    def exists = facilityProcessTypeService.exists(FacilityProcessTypeId.from("없음"))

    then:
    exists == false
  }

  def "아이디로 존재하는 설비공정유형를 조회"() {
    when:
    def one = facilityProcessTypeService.get(facilityProcessTypeId)

    then:
    one.facilityId == facilityId
    one.processTypeId == processTypeId
  }

  def "아이디로 존재하지 않는 설비공정유형을 조회"() {
    when:
    facilityProcessTypeService.get(FacilityProcessTypeId.from("packaging-00"))

    then:
    thrown(FacilityProcessTypeExceptions.NotFoundException)
  }

  def "동일 설비 동일 공정유형은 생성 불가"() {
    when:
    facilityProcessTypeService.create(new FacilityProcessTypeRequests.CreateRequest(
      id: facilityProcessTypeId,
      facilityId: facilityId,
      processTypeId: processTypeId,
      speedVariationRate: 0,
      defectiveVariationRate: 0
    ))

    then:
    thrown(FacilityProcessTypeExceptions.AlreadyExistsException)
  }

}
