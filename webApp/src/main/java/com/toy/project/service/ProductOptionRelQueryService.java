package com.toy.project.service;

import com.toy.project.domain.*; // for static metamodels
import com.toy.project.domain.ProductOptionRel;
import com.toy.project.repository.ProductOptionRelRepository;
import com.toy.project.service.criteria.ProductOptionRelCriteria;
import com.toy.project.service.dto.ProductOptionRelDTO;
import com.toy.project.service.mapper.ProductOptionRelMapper;
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
 * Service for executing complex queries for {@link ProductOptionRel} entities in the database.
 * The main input is a {@link ProductOptionRelCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ProductOptionRelDTO} or a {@link Page} of {@link ProductOptionRelDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProductOptionRelQueryService extends QueryService<ProductOptionRel> {

    private final Logger log = LoggerFactory.getLogger(ProductOptionRelQueryService.class);

    private final ProductOptionRelRepository productOptionRelRepository;

    private final ProductOptionRelMapper productOptionRelMapper;

    public ProductOptionRelQueryService(
        ProductOptionRelRepository productOptionRelRepository,
        ProductOptionRelMapper productOptionRelMapper
    ) {
        this.productOptionRelRepository = productOptionRelRepository;
        this.productOptionRelMapper = productOptionRelMapper;
    }

    /**
     * Return a {@link List} of {@link ProductOptionRelDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ProductOptionRelDTO> findByCriteria(ProductOptionRelCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ProductOptionRel> specification = createSpecification(criteria);
        return productOptionRelMapper.toDto(productOptionRelRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ProductOptionRelDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ProductOptionRelDTO> findByCriteria(ProductOptionRelCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ProductOptionRel> specification = createSpecification(criteria);
        return productOptionRelRepository.findAll(specification, page).map(productOptionRelMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProductOptionRelCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ProductOptionRel> specification = createSpecification(criteria);
        return productOptionRelRepository.count(specification);
    }

    /**
     * Function to convert {@link ProductOptionRelCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ProductOptionRel> createSpecification(ProductOptionRelCriteria criteria) {
        Specification<ProductOptionRel> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ProductOptionRel_.id));
            }
            if (criteria.getActivated() != null) {
                specification = specification.and(buildSpecification(criteria.getActivated(), ProductOptionRel_.activated));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), ProductOptionRel_.createdBy));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), ProductOptionRel_.createdDate));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), ProductOptionRel_.lastModifiedBy));
            }
            if (criteria.getLastModifiedDate() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getLastModifiedDate(), ProductOptionRel_.lastModifiedDate));
            }
            if (criteria.getProductId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getProductId(),
                            root -> root.join(ProductOptionRel_.product, JoinType.LEFT).get(Product_.id)
                        )
                    );
            }
            if (criteria.getProductOptionId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getProductOptionId(),
                            root -> root.join(ProductOptionRel_.productOption, JoinType.LEFT).get(ProductOption_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
