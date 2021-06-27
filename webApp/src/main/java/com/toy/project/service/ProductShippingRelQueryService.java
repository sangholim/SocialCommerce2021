package com.toy.project.service;

import com.toy.project.domain.*; // for static metamodels
import com.toy.project.domain.ProductShippingRel;
import com.toy.project.repository.ProductShippingRelRepository;
import com.toy.project.service.criteria.ProductShippingRelCriteria;
import com.toy.project.service.dto.ProductShippingRelDTO;
import com.toy.project.service.mapper.ProductShippingRelMapper;
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
 * Service for executing complex queries for {@link ProductShippingRel} entities in the database.
 * The main input is a {@link ProductShippingRelCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ProductShippingRelDTO} or a {@link Page} of {@link ProductShippingRelDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProductShippingRelQueryService extends QueryService<ProductShippingRel> {

    private final Logger log = LoggerFactory.getLogger(ProductShippingRelQueryService.class);

    private final ProductShippingRelRepository productShippingRelRepository;

    private final ProductShippingRelMapper productShippingRelMapper;

    public ProductShippingRelQueryService(
        ProductShippingRelRepository productShippingRelRepository,
        ProductShippingRelMapper productShippingRelMapper
    ) {
        this.productShippingRelRepository = productShippingRelRepository;
        this.productShippingRelMapper = productShippingRelMapper;
    }

    /**
     * Return a {@link List} of {@link ProductShippingRelDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ProductShippingRelDTO> findByCriteria(ProductShippingRelCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ProductShippingRel> specification = createSpecification(criteria);
        return productShippingRelMapper.toDto(productShippingRelRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ProductShippingRelDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ProductShippingRelDTO> findByCriteria(ProductShippingRelCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ProductShippingRel> specification = createSpecification(criteria);
        return productShippingRelRepository.findAll(specification, page).map(productShippingRelMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProductShippingRelCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ProductShippingRel> specification = createSpecification(criteria);
        return productShippingRelRepository.count(specification);
    }

    /**
     * Function to convert {@link ProductShippingRelCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ProductShippingRel> createSpecification(ProductShippingRelCriteria criteria) {
        Specification<ProductShippingRel> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ProductShippingRel_.id));
            }
            if (criteria.getActivated() != null) {
                specification = specification.and(buildSpecification(criteria.getActivated(), ProductShippingRel_.activated));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), ProductShippingRel_.createdBy));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), ProductShippingRel_.createdDate));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getLastModifiedBy(), ProductShippingRel_.lastModifiedBy));
            }
            if (criteria.getLastModifiedDate() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getLastModifiedDate(), ProductShippingRel_.lastModifiedDate));
            }
            if (criteria.getProductId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getProductId(),
                            root -> root.join(ProductShippingRel_.product, JoinType.LEFT).get(Product_.id)
                        )
                    );
            }
            if (criteria.getProductShippingId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getProductShippingId(),
                            root -> root.join(ProductShippingRel_.productShipping, JoinType.LEFT).get(ProductShipping_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
