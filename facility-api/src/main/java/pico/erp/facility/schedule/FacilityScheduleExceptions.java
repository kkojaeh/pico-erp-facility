package pico.erp.facility.schedule;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public interface FacilityScheduleExceptions {

  @ResponseStatus(code = HttpStatus.NOT_ACCEPTABLE, reason = "facility-schedule.already.exists.exception")
  class AlreadyExistsException extends RuntimeException {

    private static final long serialVersionUID = 1L;
  }

  @ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "facility-schedule.not.found.exception")
  class NotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

  }

  @ResponseStatus(code = HttpStatus.NOT_ACCEPTABLE, reason = "facility-schedule.not.allowed.process.exception")
  class NotAllowedProcessException extends RuntimeException {

    private static final long serialVersionUID = 1L;
  }


}
