package pico.erp.facility.process;

import java.util.List;
import javax.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pico.erp.facility.data.FacilityView;
import pico.erp.facility.process.data.FacilityProcessTypeView;
import pico.erp.shared.data.LabeledValuable;

public interface FacilityProcessTypeQuery {

  Page<FacilityProcessTypeView> retrieve(@NotNull FacilityProcessTypeView.Filter filter, @NotNull Pageable pageable);

}
