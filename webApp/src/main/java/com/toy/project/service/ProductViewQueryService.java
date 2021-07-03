package com.toy.project.service;

import com.toy.project.domain.*; // for static metamodels
import com.toy.project.domain.ProductView;
import com.toy.project.repository.ProductViewRepository;
import com.toy.project.service.criteria.ProductViewCriteria;
import com.toy.project.service.dto.ProductViewDTO;
import com.toy.project.service.mapper.ProductViewMapper;
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
 * Service for executing complex queries for {@link ProductView} entities in the database.
 * The main input is a {@link ProductViewCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ProductViewDTO} or a {@link Page} of {@link ProductViewDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProductViewQueryService extends QueryService<ProductView> {

    private final Logger log = LoggerFactory.getLogger(ProductViewQueryService.class);

    private final ProductViewRepository productViewRepository;

    private final ProductViewMapper productViewMapper;

    public ProductViewQueryService(ProductViewRepository productViewRepository, ProductViewMapper productViewMapper) {
        this.productViewRepository = productViewRepository;
        this.productViewMapper = productViewMapper;
    }

    /**
     * Return a {@link List} of {@link ProductViewDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ProductViewDTO> findByCriteria(ProductViewCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ProductView> specification = createSpecification(criteria);
        return productViewMapper.toDto(productViewRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ProductViewDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ProductViewDTO> findByCriteria(ProductViewCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ProductView> specification = createSpecification(criteria);
        return productViewRepository.findAll(specification, page).map(productViewMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProductViewCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ProductView> specification = createSpecification(criteria);
        return productViewRepository.count(specification);
    }

    /**
     * Function to convert {@link ProductViewCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ProductView> createSpecification(ProductViewCriteria criteria) {
        Specification<ProductView> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ProductView_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), ProductView_.name));
            }
            if (criteria.getActivated() != null) {
                specification = specification.and(buildSpecification(criteria.getActivated(), ProductView_.activated));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), ProductView_.createdBy));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), ProductView_.createdDate));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), ProductView_.lastModifiedBy));
            }
            if (criteria.getLastModifiedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModifiedDate(), ProductView_.lastModifiedDate));
            }
            if (criteria.getProductViewRelId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getProductViewRelId(),
                            root -> root.join(ProductView_.productViewRels, JoinType.LEFT).get(ProductViewRel_.id)
                        )
                    );
            }
            if (criteria.getProductViewContentId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getProductViewContentId(),
                            root -> root.join(ProductView_.productViewContents, JoinType.LEFT).get(ProductViewContent_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
