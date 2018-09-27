package pico.erp.facility.schedule.data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;
import pico.erp.facility.data.FacilityId;
import pico.erp.process.data.ProcessId;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode(of = "id")
public class FacilityScheduleData implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  FacilityScheduleId id;

  FacilityId facilityId;

  ProcessId processId;

  OffsetDateTime begin;

  OffsetDateTime end;

  BigDecimal durationHours;

  boolean flexible;


}
