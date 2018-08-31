package pico.erp.facility.impl;

import static org.springframework.util.StringUtils.isEmpty;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import pico.erp.facility.FacilityQuery;
import pico.erp.facility.core.FacilityCategoryRepository;
import pico.erp.facility.data.FacilityView;
import pico.erp.facility.impl.jpa.QFacilityEntity;
import pico.erp.shared.ExtendedLabeledValue;
import pico.erp.shared.Public;
import pico.erp.shared.data.LabeledValuable;
import pico.erp.shared.jpa.QueryDslJpaSupport;

@Service
@Public
@Transactional(readOnly = true)
@Validated
public class FacilityQueryJpa implements FacilityQuery {

  private final QFacilityEntity facility = QFacilityEntity.facilityEntity;

  @PersistenceContext
  private EntityManager entityManager;

  @Autowired
  private QueryDslJpaSupport queryDslJpaSupport;

  @Autowired
  private FacilityCategoryRepository facilityCategoryRepository;


  @Override
  public List<? extends LabeledValuable> asCategoryLabels() {
    return facilityCategoryRepository.findAll()
      .map(category -> ExtendedLabeledValue.builder()
        .value(category.getId().getValue())
        .label(category.getName())
        .build()
      ).collect(Collectors.toList());
  }

  @Override
  public Page<FacilityView> retrieve(FacilityView.Filter filter, Pageable pageable) {
    val query = new JPAQuery<FacilityView>(entityManager);
    val select = Projections.bean(FacilityView.class,
      facility.id,
      facility.name,
      facility.categoryId,
      facility.createdBy,
      facility.createdDate,
      facility.lastModifiedBy,
      facility.lastModifiedDate
    );

    query.select(select);

    val builder = new BooleanBuilder();

    if (!isEmpty(filter.getName())) {
      builder.and(facility.name
        .likeIgnoreCase(queryDslJpaSupport.toLikeKeyword("%", filter.getName(), "%")));
    }
    if (filter.getCategoryId() != null) {
      builder.and(facility.categoryId.eq(filter.getCategoryId()));
    }
    query.from(facility);
    query.where(builder);

    return queryDslJpaSupport.paging(query, pageable, select);
  }
}
