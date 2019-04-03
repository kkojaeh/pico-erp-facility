package pico.erp.facility

import kkojaeh.spring.boot.component.SpringBootTestComponent
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.Rollback
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional
import pico.erp.facility.process.type.FacilityProcessTypeExceptions
import pico.erp.facility.process.type.FacilityProcessTypeId
import pico.erp.facility.process.type.FacilityProcessTypeRequests
import pico.erp.facility.process.type.FacilityProcessTypeService
import pico.erp.process.type.ProcessTypeId
import pico.erp.shared.ComponentDefinitionServiceLoaderTestComponentSiblingsSupplier
import pico.erp.shared.TestParentApplication
import spock.lang.Specification

@SpringBootTest(classes = [FacilityApplication, TestConfig])
@SpringBootTestComponent(parent = TestParentApplication, siblingsSupplier = ComponentDefinitionServiceLoaderTestComponentSiblingsSupplier.class)
@Transactional
@Rollback
@ActiveProfiles("test")
class FacilityProcessTypeServiceSpec extends Specification {

  @Autowired
  FacilityProcessTypeService facilityProcessTypeService

  @Autowired
  FacilityService facilityService

  def facilityId = FacilityId.from("packaging-1")

  def processTypeId = ProcessTypeId.from("PO")

  def id = FacilityProcessTypeId.from("packaging-printing-offset")

  def id2 = FacilityProcessTypeId.from("packaging-printing-offset-2")

  def unknownId = FacilityProcessTypeId.from("unknown")

  def setup() {
    facilityProcessTypeService.create(new FacilityProcessTypeRequests.CreateRequest(
      id: id,
      facilityId: facilityId,
      processTypeId: processTypeId,
      speedVariationRate: 0,
      defectiveVariationRate: 0
    ))
  }

  def "조회 - 설비 아이디로 확인"() {
    when:
    def all = facilityProcessTypeService.getAll(facilityId)

    then:
    all.size() == 1
  }

  def "존재 - 아이디로 확인"() {
    when:
    def exists = facilityProcessTypeService.exists(id)

    then:
    exists == true
  }

  def "존재 - 존재하지 않는 아이디로 확인"() {
    when:
    def exists = facilityProcessTypeService.exists(unknownId)

    then:
    exists == false
  }

  def "조회 - 아이디로 조회"() {
    when:
    def one = facilityProcessTypeService.get(id)

    then:
    one.facilityId == facilityId
    one.processTypeId == processTypeId
    one.speedVariationRate == 0
    one.defectiveVariationRate == 0
  }

  def "조회 - 존재하지 않는 아이디로 조회"() {
    when:
    facilityProcessTypeService.get(unknownId)

    then:
    thrown(FacilityProcessTypeExceptions.NotFoundException)
  }

  def "동일 설비 동일 공정유형은 생성 불가"() {
    when:
    facilityProcessTypeService.create(new FacilityProcessTypeRequests.CreateRequest(
      id: id2,
      facilityId: facilityId,
      processTypeId: processTypeId,
      speedVariationRate: 0,
      defectiveVariationRate: 0
    ))

    then:
    thrown(FacilityProcessTypeExceptions.AlreadyExistsException)
  }

}
