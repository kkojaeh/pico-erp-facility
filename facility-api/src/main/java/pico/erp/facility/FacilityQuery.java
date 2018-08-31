package pico.erp.facility;

import java.util.List;
import javax.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pico.erp.facility.data.FacilityView;
import pico.erp.shared.data.LabeledValuable;

public interface FacilityQuery {

  List<? extends LabeledValuable> asCategoryLabels();

  Page<FacilityView> retrieve(@NotNull FacilityView.Filter filter, Pageable pageable);

}
