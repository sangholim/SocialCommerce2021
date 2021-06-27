package com.toy.project.service;

import com.toy.project.domain.*; // for static metamodels
import com.toy.project.domain.ProductLabelRel;
import com.toy.project.repository.ProductLabelRelRepository;
import com.toy.project.service.criteria.ProductLabelRelCriteria;
import com.toy.project.service.dto.ProductLabelRelDTO;
import com.toy.project.service.mapper.ProductLabelRelMapper;
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
 * Service for executing complex queries for {@link ProductLabelRel} entities in the database.
 * The main input is a {@link ProductLabelRelCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ProductLabelRelDTO} or a {@link Page} of {@link ProductLabelRelDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProductLabelRelQueryService extends QueryService<ProductLabelRel> {

    private final Logger log = LoggerFactory.getLogger(ProductLabelRelQueryService.class);

    private final ProductLabelRelRepository productLabelRelRepository;

    private final ProductLabelRelMapper productLabelRelMapper;

    public ProductLabelRelQueryService(ProductLabelRelRepository productLabelRelRepository, ProductLabelRelMapper productLabelRelMapper) {
        this.productLabelRelRepository = productLabelRelRepository;
        this.productLabelRelMapper = productLabelRelMapper;
    }

    /**
     * Return a {@link List} of {@link ProductLabelRelDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ProductLabelRelDTO> findByCriteria(ProductLabelRelCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ProductLabelRel> specification = createSpecification(criteria);
        return productLabelRelMapper.toDto(productLabelRelRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ProductLabelRelDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ProductLabelRelDTO> findByCriteria(ProductLabelRelCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ProductLabelRel> specification = createSpecification(criteria);
        return productLabelRelRepository.findAll(specification, page).map(productLabelRelMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProductLabelRelCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ProductLabelRel> specification = createSpecification(criteria);
        return productLabelRelRepository.count(specification);
    }

    /**
     * Function to convert {@link ProductLabelRelCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ProductLabelRel> createSpecification(ProductLabelRelCriteria criteria) {
        Specification<ProductLabelRel> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ProductLabelRel_.id));
            }
            if (criteria.getIsDisplayDate() != null) {
                specification = specification.and(buildSpecification(criteria.getIsDisplayDate(), ProductLabelRel_.isDisplayDate));
            }
            if (criteria.getDisplayDateFrom() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDisplayDateFrom(), ProductLabelRel_.displayDateFrom));
            }
            if (criteria.getDisplayDateTo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDisplayDateTo(), ProductLabelRel_.displayDateTo));
            }
            if (criteria.getActivated() != null) {
                specification = specification.and(buildSpecification(criteria.getActivated(), ProductLabelRel_.activated));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), ProductLabelRel_.createdBy));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), ProductLabelRel_.createdDate));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), ProductLabelRel_.lastModifiedBy));
            }
            if (criteria.getLastModifiedDate() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getLastModifiedDate(), ProductLabelRel_.lastModifiedDate));
            }
            if (criteria.getProductId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getProductId(),
                            root -> root.join(ProductLabelRel_.product, JoinType.LEFT).get(Product_.id)
                        )
                    );
            }
            if (criteria.getProductLabelId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getProductLabelId(),
                            root -> root.join(ProductLabelRel_.productLabel, JoinType.LEFT).get(ProductLabel_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
