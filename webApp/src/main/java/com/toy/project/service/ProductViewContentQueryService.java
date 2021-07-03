package com.toy.project.service;

import com.toy.project.domain.*; // for static metamodels
import com.toy.project.domain.ProductViewContent;
import com.toy.project.repository.ProductViewContentRepository;
import com.toy.project.service.criteria.ProductViewContentCriteria;
import com.toy.project.service.dto.ProductViewContentDTO;
import com.toy.project.service.mapper.ProductViewContentMapper;
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
 * Service for executing complex queries for {@link ProductViewContent} entities in the database.
 * The main input is a {@link ProductViewContentCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ProductViewContentDTO} or a {@link Page} of {@link ProductViewContentDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProductViewContentQueryService extends QueryService<ProductViewContent> {

    private final Logger log = LoggerFactory.getLogger(ProductViewContentQueryService.class);

    private final ProductViewContentRepository productViewContentRepository;

    private final ProductViewContentMapper productViewContentMapper;

    public ProductViewContentQueryService(
        ProductViewContentRepository productViewContentRepository,
        ProductViewContentMapper productViewContentMapper
    ) {
        this.productViewContentRepository = productViewContentRepository;
        this.productViewContentMapper = productViewContentMapper;
    }

    /**
     * Return a {@link List} of {@link ProductViewContentDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ProductViewContentDTO> findByCriteria(ProductViewContentCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ProductViewContent> specification = createSpecification(criteria);
        return productViewContentMapper.toDto(productViewContentRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ProductViewContentDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ProductViewContentDTO> findByCriteria(ProductViewContentCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ProductViewContent> specification = createSpecification(criteria);
        return productViewContentRepository.findAll(specification, page).map(productViewContentMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProductViewContentCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ProductViewContent> specification = createSpecification(criteria);
        return productViewContentRepository.count(specification);
    }

    /**
     * Function to convert {@link ProductViewContentCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ProductViewContent> createSpecification(ProductViewContentCriteria criteria) {
        Specification<ProductViewContent> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ProductViewContent_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), ProductViewContent_.name));
            }
            if (criteria.getIsDetail() != null) {
                specification = specification.and(buildSpecification(criteria.getIsDetail(), ProductViewContent_.isDetail));
            }
            if (criteria.getActivated() != null) {
                specification = specification.and(buildSpecification(criteria.getActivated(), ProductViewContent_.activated));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), ProductViewContent_.createdBy));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), ProductViewContent_.createdDate));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getLastModifiedBy(), ProductViewContent_.lastModifiedBy));
            }
            if (criteria.getLastModifiedDate() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getLastModifiedDate(), ProductViewContent_.lastModifiedDate));
            }
            if (criteria.getProductViewId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getProductViewId(),
                            root -> root.join(ProductViewContent_.productView, JoinType.LEFT).get(ProductView_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
