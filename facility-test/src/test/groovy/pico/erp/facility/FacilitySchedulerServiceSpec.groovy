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
import pico.erp.facility.process.type.FacilityProcessTypeRequests
import pico.erp.facility.process.type.FacilityProcessTypeService
import pico.erp.facility.process.type.data.FacilityProcessTypeId
import pico.erp.facility.schedule.FacilityScheduleExceptions
import pico.erp.facility.schedule.FacilityScheduleRequests
import pico.erp.facility.schedule.FacilityScheduleService
import pico.erp.facility.schedule.data.FacilityScheduleId
import pico.erp.process.data.ProcessId
import pico.erp.process.type.data.ProcessTypeId
import pico.erp.shared.IntegrationConfiguration
import pico.erp.work.schedule.category.data.WorkScheduleCategoryId
import spock.lang.Specification

import java.time.OffsetDateTime

@SpringBootTest(classes = [IntegrationConfiguration])
@Transactional
@Rollback
@ActiveProfiles("test")
@Configuration
@ComponentScan("pico.erp.config")
class FacilitySchedulerServiceSpec extends Specification {

  @Autowired
  FacilityScheduleService facilityScheduleService

  @Autowired
  FacilityProcessTypeService facilityProcessTypeService

  @Autowired
  FacilityService facilityService

  def facilityId = FacilityId.from("packaging-11")

  def facilityScheduleId = FacilityScheduleId.from("packaging-schedule-1")

  def processId = ProcessId.from("item-3-process-1")

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
    facilityScheduleService.create(
      new FacilityScheduleRequests.CreateRequest(
        id: facilityScheduleId,
        facilityId: facilityId,
        begin: OffsetDateTime.parse("2018-08-11T09:00:00+09:00"),
        processId: processId,
        durationMinutes: 60 * 12,
        flexible: false,
      )
    )
  }

  def "아이디로 존재하는 설비 일정 확인"() {
    when:
    def exists = facilityScheduleService.exists(facilityScheduleId)

    then:
    exists == true
  }

  def "아이디로 존재하지 않는 설비 일정 확인"() {
    when:
    def exists = facilityScheduleService.exists(FacilityScheduleId.from("unknown"))

    then:
    exists == false
  }

  def "아이디로 존재하는 설비 일정를 조회"() {
    when:
    def facilitySchedule = facilityScheduleService.get(facilityScheduleId)

    then:
    facilitySchedule.facilityId == facilityId
    facilitySchedule.processId == processId
  }

  def "아이디로 존재하지 않는 설비 일정을 조회"() {
    when:
    facilityScheduleService.get(FacilityScheduleId.from("unknown"))

    then:
    thrown(FacilityScheduleExceptions.NotFoundException)
  }

  def "설비 일정을 생성하면 작업에 지정된 시간에 따라 작업 시간이 계산된다"() {
    when:
    facilityScheduleService.get(FacilityScheduleId.from("unknown"))

    then:
    thrown(FacilityScheduleExceptions.NotFoundException)
  }

  def "작업일의 시간이 변경되면 총 일정시간을 기준으로 종료시간이 변경된다"() {
    when:
    facilityScheduleService.get(FacilityScheduleId.from("unknown"))

    then:
    thrown(FacilityScheduleExceptions.NotFoundException)
  }

  def "작업시간의 범위를 벗어나면 오류가 발생된다"() {
    when:
    facilityScheduleService.get(FacilityScheduleId.from("unknown"))

    then:
    thrown(FacilityScheduleExceptions.NotFoundException)
  }

}
