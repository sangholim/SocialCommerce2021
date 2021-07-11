package com.toy.project.service;

import com.toy.project.domain.*; // for static metamodels
import com.toy.project.domain.ProductStoreRel;
import com.toy.project.repository.ProductStoreRelRepository;
import com.toy.project.service.criteria.ProductStoreRelCriteria;
import com.toy.project.service.dto.ProductStoreRelDTO;
import com.toy.project.service.mapper.ProductStoreRelMapper;
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
 * Service for executing complex queries for {@link ProductStoreRel} entities in the database.
 * The main input is a {@link ProductStoreRelCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ProductStoreRelDTO} or a {@link Page} of {@link ProductStoreRelDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProductStoreRelQueryService extends QueryService<ProductStoreRel> {

    private final Logger log = LoggerFactory.getLogger(ProductStoreRelQueryService.class);

    private final ProductStoreRelRepository productStoreRelRepository;

    private final ProductStoreRelMapper productStoreRelMapper;

    public ProductStoreRelQueryService(ProductStoreRelRepository productStoreRelRepository, ProductStoreRelMapper productStoreRelMapper) {
        this.productStoreRelRepository = productStoreRelRepository;
        this.productStoreRelMapper = productStoreRelMapper;
    }

    /**
     * Return a {@link List} of {@link ProductStoreRelDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ProductStoreRelDTO> findByCriteria(ProductStoreRelCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ProductStoreRel> specification = createSpecification(criteria);
        return productStoreRelMapper.toDto(productStoreRelRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ProductStoreRelDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ProductStoreRelDTO> findByCriteria(ProductStoreRelCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ProductStoreRel> specification = createSpecification(criteria);
        return productStoreRelRepository.findAll(specification, page).map(productStoreRelMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProductStoreRelCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ProductStoreRel> specification = createSpecification(criteria);
        return productStoreRelRepository.count(specification);
    }

    /**
     * Function to convert {@link ProductStoreRelCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ProductStoreRel> createSpecification(ProductStoreRelCriteria criteria) {
        Specification<ProductStoreRel> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ProductStoreRel_.id));
            }
            if (criteria.getUseCalculation() != null) {
                specification = specification.and(buildSpecification(criteria.getUseCalculation(), ProductStoreRel_.useCalculation));
            }
            if (criteria.getCalculation() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCalculation(), ProductStoreRel_.calculation));
            }
            if (criteria.getCalculationDateFrom() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getCalculationDateFrom(), ProductStoreRel_.calculationDateFrom));
            }
            if (criteria.getCalculationDateTo() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getCalculationDateTo(), ProductStoreRel_.calculationDateTo));
            }
            if (criteria.getActivated() != null) {
                specification = specification.and(buildSpecification(criteria.getActivated(), ProductStoreRel_.activated));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), ProductStoreRel_.createdBy));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), ProductStoreRel_.createdDate));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), ProductStoreRel_.lastModifiedBy));
            }
            if (criteria.getLastModifiedDate() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getLastModifiedDate(), ProductStoreRel_.lastModifiedDate));
            }
            if (criteria.getProductId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getProductId(),
                            root -> root.join(ProductStoreRel_.product, JoinType.LEFT).get(Product_.id)
                        )
                    );
            }
            if (criteria.getStoreId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getStoreId(), root -> root.join(ProductStoreRel_.store, JoinType.LEFT).get(Store_.id))
                    );
            }
        }
        return specification;
    }
}
