package com.toy.project.service;

import com.toy.project.domain.*; // for static metamodels
import com.toy.project.domain.ProductClazzRel;
import com.toy.project.repository.ProductClazzRelRepository;
import com.toy.project.service.criteria.ProductClazzRelCriteria;
import com.toy.project.service.dto.ProductClazzRelDTO;
import com.toy.project.service.mapper.ProductClazzRelMapper;
import java.util.List;
import javax.persistence.criteria.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link ProductClazzRel} entities in the database.
 * The main input is a {@link ProductClazzRelCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ProductClazzRelDTO} or a {@link Page} of {@link ProductClazzRelDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProductClazzRelQueryService extends QueryService<ProductClazzRel> {

    private final Logger log = LoggerFactory.getLogger(ProductClazzRelQueryService.class);

    private final ProductClazzRelRepository productClazzRelRepository;

    private final ProductClazzRelMapper productClazzRelMapper;

    public ProductClazzRelQueryService(ProductClazzRelRepository productClazzRelRepository, ProductClazzRelMapper productClazzRelMapper) {
        this.productClazzRelRepository = productClazzRelRepository;
        this.productClazzRelMapper = productClazzRelMapper;
    }

    /**
     * Return a {@link List} of {@link ProductClazzRelDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ProductClazzRelDTO> findByCriteria(ProductClazzRelCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ProductClazzRel> specification = createSpecification(criteria);
        return productClazzRelMapper.toDto(productClazzRelRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ProductClazzRelDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ProductClazzRelDTO> findByCriteria(ProductClazzRelCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ProductClazzRel> specification = createSpecification(criteria);
        return productClazzRelRepository.findAll(specification, page).map(productClazzRelMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProductClazzRelCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ProductClazzRel> specification = createSpecification(criteria);
        return productClazzRelRepository.count(specification);
    }

    /**
     * Function to convert {@link ProductClazzRelCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ProductClazzRel> createSpecification(ProductClazzRelCriteria criteria) {
        Specification<ProductClazzRel> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ProductClazzRel_.id));
            }
            if (criteria.getUseCalculation() != null) {
                specification = specification.and(buildSpecification(criteria.getUseCalculation(), ProductClazzRel_.useCalculation));
            }
            if (criteria.getCalculation() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCalculation(), ProductClazzRel_.calculation));
            }
            if (criteria.getCalculationDateFrom() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getCalculationDateFrom(), ProductClazzRel_.calculationDateFrom));
            }
            if (criteria.getCalculationDateTo() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getCalculationDateTo(), ProductClazzRel_.calculationDateTo));
            }
            if (criteria.getActivated() != null) {
                specification = specification.and(buildSpecification(criteria.getActivated(), ProductClazzRel_.activated));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), ProductClazzRel_.createdBy));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), ProductClazzRel_.createdDate));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), ProductClazzRel_.lastModifiedBy));
            }
            if (criteria.getLastModifiedDate() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getLastModifiedDate(), ProductClazzRel_.lastModifiedDate));
            }
            if (criteria.getProductId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getProductId(),
                            root -> root.join(ProductClazzRel_.product, JoinType.LEFT).get(Product_.id)
                        )
                    );
            }
            if (criteria.getClazzId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getClazzId(), root -> root.join(ProductClazzRel_.clazz, JoinType.LEFT).get(Clazz_.id))
                    );
            }
        }
        return specification;
    }
}
