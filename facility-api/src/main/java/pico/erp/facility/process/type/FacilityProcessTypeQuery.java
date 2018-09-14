package pico.erp.facility.process.type;

import javax.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pico.erp.facility.process.type.data.FacilityProcessTypeView;

public interface FacilityProcessTypeQuery {

  Page<FacilityProcessTypeView> retrieve(@NotNull FacilityProcessTypeView.Filter filter, @NotNull Pageable pageable);

}
