package com.toy.project.service;

import com.toy.project.domain.*; // for static metamodels
import com.toy.project.domain.ProductShipping;
import com.toy.project.repository.ProductShippingRepository;
import com.toy.project.service.criteria.ProductShippingCriteria;
import com.toy.project.service.dto.ProductShippingDTO;
import com.toy.project.service.mapper.ProductShippingMapper;
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
 * Service for executing complex queries for {@link ProductShipping} entities in the database.
 * The main input is a {@link ProductShippingCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ProductShippingDTO} or a {@link Page} of {@link ProductShippingDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProductShippingQueryService extends QueryService<ProductShipping> {

    private final Logger log = LoggerFactory.getLogger(ProductShippingQueryService.class);

    private final ProductShippingRepository productShippingRepository;

    private final ProductShippingMapper productShippingMapper;

    public ProductShippingQueryService(ProductShippingRepository productShippingRepository, ProductShippingMapper productShippingMapper) {
        this.productShippingRepository = productShippingRepository;
        this.productShippingMapper = productShippingMapper;
    }

    /**
     * Return a {@link List} of {@link ProductShippingDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ProductShippingDTO> findByCriteria(ProductShippingCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ProductShipping> specification = createSpecification(criteria);
        return productShippingMapper.toDto(productShippingRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ProductShippingDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ProductShippingDTO> findByCriteria(ProductShippingCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ProductShipping> specification = createSpecification(criteria);
        return productShippingRepository.findAll(specification, page).map(productShippingMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProductShippingCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ProductShipping> specification = createSpecification(criteria);
        return productShippingRepository.count(specification);
    }

    /**
     * Function to convert {@link ProductShippingCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ProductShipping> createSpecification(ProductShippingCriteria criteria) {
        Specification<ProductShipping> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ProductShipping_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), ProductShipping_.name));
            }
            if (criteria.getIsGroup() != null) {
                specification = specification.and(buildSpecification(criteria.getIsGroup(), ProductShipping_.isGroup));
            }
            if (criteria.getDefaultShippingPrice() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getDefaultShippingPrice(), ProductShipping_.defaultShippingPrice));
            }
            if (criteria.getFreeShippingPrice() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getFreeShippingPrice(), ProductShipping_.freeShippingPrice));
            }
            if (criteria.getJejuShippingPrice() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getJejuShippingPrice(), ProductShipping_.jejuShippingPrice));
            }
            if (criteria.getDifficultShippingPrice() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(criteria.getDifficultShippingPrice(), ProductShipping_.difficultShippingPrice)
                    );
            }
            if (criteria.getRefundShippingPrice() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getRefundShippingPrice(), ProductShipping_.refundShippingPrice));
            }
            if (criteria.getExchangeShippingPrice() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getExchangeShippingPrice(), ProductShipping_.exchangeShippingPrice));
            }
            if (criteria.getActivated() != null) {
                specification = specification.and(buildSpecification(criteria.getActivated(), ProductShipping_.activated));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), ProductShipping_.createdBy));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), ProductShipping_.createdDate));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), ProductShipping_.lastModifiedBy));
            }
            if (criteria.getLastModifiedDate() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getLastModifiedDate(), ProductShipping_.lastModifiedDate));
            }
            if (criteria.getProductShippingRelId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getProductShippingRelId(),
                            root -> root.join(ProductShipping_.productShippingRels, JoinType.LEFT).get(ProductShippingRel_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
