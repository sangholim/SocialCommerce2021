package com.toy.project.service;

import com.toy.project.domain.*; // for static metamodels
import com.toy.project.domain.ProductCategoryRel;
import com.toy.project.repository.ProductCategoryRelRepository;
import com.toy.project.service.criteria.ProductCategoryRelCriteria;
import com.toy.project.service.dto.ProductCategoryRelDTO;
import com.toy.project.service.mapper.ProductCategoryRelMapper;
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
 * Service for executing complex queries for {@link ProductCategoryRel} entities in the database.
 * The main input is a {@link ProductCategoryRelCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ProductCategoryRelDTO} or a {@link Page} of {@link ProductCategoryRelDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProductCategoryRelQueryService extends QueryService<ProductCategoryRel> {

    private final Logger log = LoggerFactory.getLogger(ProductCategoryRelQueryService.class);

    private final ProductCategoryRelRepository productCategoryRelRepository;

    private final ProductCategoryRelMapper productCategoryRelMapper;

    public ProductCategoryRelQueryService(
        ProductCategoryRelRepository productCategoryRelRepository,
        ProductCategoryRelMapper productCategoryRelMapper
    ) {
        this.productCategoryRelRepository = productCategoryRelRepository;
        this.productCategoryRelMapper = productCategoryRelMapper;
    }

    /**
     * Return a {@link List} of {@link ProductCategoryRelDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ProductCategoryRelDTO> findByCriteria(ProductCategoryRelCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ProductCategoryRel> specification = createSpecification(criteria);
        return productCategoryRelMapper.toDto(productCategoryRelRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ProductCategoryRelDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ProductCategoryRelDTO> findByCriteria(ProductCategoryRelCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ProductCategoryRel> specification = createSpecification(criteria);
        return productCategoryRelRepository.findAll(specification, page).map(productCategoryRelMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProductCategoryRelCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ProductCategoryRel> specification = createSpecification(criteria);
        return productCategoryRelRepository.count(specification);
    }

    /**
     * Function to convert {@link ProductCategoryRelCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ProductCategoryRel> createSpecification(ProductCategoryRelCriteria criteria) {
        Specification<ProductCategoryRel> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ProductCategoryRel_.id));
            }
            if (criteria.getActivated() != null) {
                specification = specification.and(buildSpecification(criteria.getActivated(), ProductCategoryRel_.activated));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), ProductCategoryRel_.createdBy));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), ProductCategoryRel_.createdDate));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getLastModifiedBy(), ProductCategoryRel_.lastModifiedBy));
            }
            if (criteria.getLastModifiedDate() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getLastModifiedDate(), ProductCategoryRel_.lastModifiedDate));
            }
            if (criteria.getProductId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getProductId(),
                            root -> root.join(ProductCategoryRel_.product, JoinType.LEFT).get(Product_.id)
                        )
                    );
            }
            if (criteria.getProductCategoryId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getProductCategoryId(),
                            root -> root.join(ProductCategoryRel_.productCategory, JoinType.LEFT).get(ProductCategory_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
