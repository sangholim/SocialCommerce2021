package com.toy.project.service;

import com.toy.project.domain.*; // for static metamodels
import com.toy.project.domain.ProductViewRel;
import com.toy.project.repository.ProductViewRelRepository;
import com.toy.project.service.criteria.ProductViewRelCriteria;
import com.toy.project.service.dto.ProductViewRelDTO;
import com.toy.project.service.mapper.ProductViewRelMapper;
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
 * Service for executing complex queries for {@link ProductViewRel} entities in the database.
 * The main input is a {@link ProductViewRelCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ProductViewRelDTO} or a {@link Page} of {@link ProductViewRelDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProductViewRelQueryService extends QueryService<ProductViewRel> {

    private final Logger log = LoggerFactory.getLogger(ProductViewRelQueryService.class);

    private final ProductViewRelRepository productViewRelRepository;

    private final ProductViewRelMapper productViewRelMapper;

    public ProductViewRelQueryService(ProductViewRelRepository productViewRelRepository, ProductViewRelMapper productViewRelMapper) {
        this.productViewRelRepository = productViewRelRepository;
        this.productViewRelMapper = productViewRelMapper;
    }

    /**
     * Return a {@link List} of {@link ProductViewRelDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ProductViewRelDTO> findByCriteria(ProductViewRelCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ProductViewRel> specification = createSpecification(criteria);
        return productViewRelMapper.toDto(productViewRelRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ProductViewRelDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ProductViewRelDTO> findByCriteria(ProductViewRelCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ProductViewRel> specification = createSpecification(criteria);
        return productViewRelRepository.findAll(specification, page).map(productViewRelMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProductViewRelCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ProductViewRel> specification = createSpecification(criteria);
        return productViewRelRepository.count(specification);
    }

    /**
     * Function to convert {@link ProductViewRelCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ProductViewRel> createSpecification(ProductViewRelCriteria criteria) {
        Specification<ProductViewRel> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ProductViewRel_.id));
            }
            if (criteria.getActivated() != null) {
                specification = specification.and(buildSpecification(criteria.getActivated(), ProductViewRel_.activated));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), ProductViewRel_.createdBy));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), ProductViewRel_.createdDate));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), ProductViewRel_.lastModifiedBy));
            }
            if (criteria.getLastModifiedDate() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getLastModifiedDate(), ProductViewRel_.lastModifiedDate));
            }
            if (criteria.getProductId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getProductId(),
                            root -> root.join(ProductViewRel_.product, JoinType.LEFT).get(Product_.id)
                        )
                    );
            }
            if (criteria.getProductViewId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getProductViewId(),
                            root -> root.join(ProductViewRel_.productView, JoinType.LEFT).get(ProductView_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
