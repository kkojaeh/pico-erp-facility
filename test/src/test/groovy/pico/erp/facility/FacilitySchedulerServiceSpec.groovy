package pico.erp.facility

import kkojaeh.spring.boot.component.SpringBootTestComponent
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.Rollback
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional
import pico.erp.facility.process.type.FacilityProcessTypeId
import pico.erp.facility.process.type.FacilityProcessTypeRequests
import pico.erp.facility.process.type.FacilityProcessTypeService
import pico.erp.facility.schedule.FacilityScheduleExceptions
import pico.erp.facility.schedule.FacilityScheduleId
import pico.erp.facility.schedule.FacilityScheduleRequests
import pico.erp.facility.schedule.FacilityScheduleService
import pico.erp.process.ProcessId
import pico.erp.process.type.ProcessTypeId
import pico.erp.shared.ComponentDefinitionServiceLoaderTestComponentSiblingsSupplier
import pico.erp.shared.TestParentApplication
import spock.lang.Specification

import java.time.OffsetDateTime

@SpringBootTest(classes = [FacilityApplication, TestConfig])
@SpringBootTestComponent(parent = TestParentApplication, siblingsSupplier = ComponentDefinitionServiceLoaderTestComponentSiblingsSupplier.class)
@Transactional
@Rollback
@ActiveProfiles("test")
class FacilitySchedulerServiceSpec extends Specification {

  @Autowired
  FacilityScheduleService facilityScheduleService

  @Autowired
  FacilityProcessTypeService facilityProcessTypeService

  @Autowired
  FacilityService facilityService

  def facilityId = FacilityId.from("packaging-1")

  def id = FacilityScheduleId.from("packaging-schedule-1")

  def unknownId = FacilityScheduleId.from("unknown")

  def processId = ProcessId.from("item-3-process-1")

  def processTypeId = ProcessTypeId.from("PO")

  def facilityProcessTypeId = FacilityProcessTypeId.from("packaging-printing-offset")

  def setup() {

    facilityProcessTypeService.create(
      new FacilityProcessTypeRequests.CreateRequest(
      id: facilityProcessTypeId,
      facilityId: facilityId,
      processTypeId: processTypeId,
      speedVariationRate: 0,
      defectiveVariationRate: 0
    ))
    facilityScheduleService.create(
      new FacilityScheduleRequests.CreateRequest(
        id: id,
        facilityId: facilityId,
        begin: OffsetDateTime.parse("2018-08-11T09:00:00+09:00"),
        processId: processId,
        durationMinutes: 60 * 12,
        flexible: false,
      )
    )
  }

  def "존재 - 아이디로 확인"() {
    when:
    def exists = facilityScheduleService.exists(id)

    then:
    exists == true
  }

  def "존재 - 존재하지 않는 아이디로 확인"() {
    when:
    def exists = facilityScheduleService.exists(unknownId)

    then:
    exists == false
  }

  def "조회 - 아이디로 조회"() {
    when:
    def facilitySchedule = facilityScheduleService.get(id)

    then:
    facilitySchedule.facilityId == facilityId
    facilitySchedule.processId == processId
  }

  def "조회 - 존재하지 않는 아이디로 조회"() {
    when:
    facilityScheduleService.get(unknownId)

    then:
    thrown(FacilityScheduleExceptions.NotFoundException)
  }

  def "설비 일정을 생성하면 작업에 지정된 시간에 따라 작업 시간이 계산된다"() {

  }

  def "작업일의 시간이 변경되면 총 일정시간을 기준으로 종료시간이 변경된다"() {

  }

  def "작업시간의 범위를 벗어나면 오류가 발생된다"() {

  }

}
