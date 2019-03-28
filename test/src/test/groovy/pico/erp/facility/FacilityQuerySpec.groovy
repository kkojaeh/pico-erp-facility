package pico.erp.facility

import kkojaeh.spring.boot.component.SpringBootTestComponent
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.PageRequest
import org.springframework.test.annotation.Rollback
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional
import pico.erp.company.CompanyApplication
import pico.erp.facility.category.FacilityCategoryId
import pico.erp.item.ItemApplication
import pico.erp.process.ProcessApplication
import pico.erp.shared.TestParentApplication
import pico.erp.work.schedule.WorkScheduleApplication
import spock.lang.Specification

@SpringBootTest(classes = [FacilityApplication, TestConfig])
@SpringBootTestComponent(parent = TestParentApplication, siblings = [ItemApplication, ProcessApplication, CompanyApplication, WorkScheduleApplication])
@Transactional
@Rollback
@ActiveProfiles("test")
class FacilityQuerySpec extends Specification {

  @Autowired
  FacilityQuery facilityQuery

  def "카테고리로 검색"() {
    when:
    def result = facilityQuery.retrieve(new FacilityView.Filter(
      categoryId: FacilityCategoryId.from("packaging")
    ), new PageRequest(0, 10))

    then:
    result.totalElements == 2
  }

  def "라벨 검색"() {
    when:
    def result = facilityQuery.asLabels("포장", 10)

    then:
    result.size() == 2
  }

}
