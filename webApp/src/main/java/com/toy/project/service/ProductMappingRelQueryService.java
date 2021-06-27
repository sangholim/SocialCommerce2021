package com.toy.project.service;

import com.toy.project.domain.*; // for static metamodels
import com.toy.project.domain.ProductMappingRel;
import com.toy.project.repository.ProductMappingRelRepository;
import com.toy.project.service.criteria.ProductMappingRelCriteria;
import com.toy.project.service.dto.ProductMappingRelDTO;
import com.toy.project.service.mapper.ProductMappingRelMapper;
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
 * Service for executing complex queries for {@link ProductMappingRel} entities in the database.
 * The main input is a {@link ProductMappingRelCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ProductMappingRelDTO} or a {@link Page} of {@link ProductMappingRelDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProductMappingRelQueryService extends QueryService<ProductMappingRel> {

    private final Logger log = LoggerFactory.getLogger(ProductMappingRelQueryService.class);

    private final ProductMappingRelRepository productMappingRelRepository;

    private final ProductMappingRelMapper productMappingRelMapper;

    public ProductMappingRelQueryService(
        ProductMappingRelRepository productMappingRelRepository,
        ProductMappingRelMapper productMappingRelMapper
    ) {
        this.productMappingRelRepository = productMappingRelRepository;
        this.productMappingRelMapper = productMappingRelMapper;
    }

    /**
     * Return a {@link List} of {@link ProductMappingRelDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ProductMappingRelDTO> findByCriteria(ProductMappingRelCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ProductMappingRel> specification = createSpecification(criteria);
        return productMappingRelMapper.toDto(productMappingRelRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ProductMappingRelDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ProductMappingRelDTO> findByCriteria(ProductMappingRelCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ProductMappingRel> specification = createSpecification(criteria);
        return productMappingRelRepository.findAll(specification, page).map(productMappingRelMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProductMappingRelCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ProductMappingRel> specification = createSpecification(criteria);
        return productMappingRelRepository.count(specification);
    }

    /**
     * Function to convert {@link ProductMappingRelCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ProductMappingRel> createSpecification(ProductMappingRelCriteria criteria) {
        Specification<ProductMappingRel> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ProductMappingRel_.id));
            }
            if (criteria.getActivated() != null) {
                specification = specification.and(buildSpecification(criteria.getActivated(), ProductMappingRel_.activated));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), ProductMappingRel_.createdBy));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), ProductMappingRel_.createdDate));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getLastModifiedBy(), ProductMappingRel_.lastModifiedBy));
            }
            if (criteria.getLastModifiedDate() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getLastModifiedDate(), ProductMappingRel_.lastModifiedDate));
            }
            if (criteria.getProductId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getProductId(),
                            root -> root.join(ProductMappingRel_.product, JoinType.LEFT).get(Product_.id)
                        )
                    );
            }
            if (criteria.getProductMappingId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getProductMappingId(),
                            root -> root.join(ProductMappingRel_.productMapping, JoinType.LEFT).get(ProductMapping_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
