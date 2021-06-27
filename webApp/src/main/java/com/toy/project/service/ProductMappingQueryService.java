package com.toy.project.service;

import com.toy.project.domain.*; // for static metamodels
import com.toy.project.domain.ProductMapping;
import com.toy.project.repository.ProductMappingRepository;
import com.toy.project.service.criteria.ProductMappingCriteria;
import com.toy.project.service.dto.ProductMappingDTO;
import com.toy.project.service.mapper.ProductMappingMapper;
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
 * Service for executing complex queries for {@link ProductMapping} entities in the database.
 * The main input is a {@link ProductMappingCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ProductMappingDTO} or a {@link Page} of {@link ProductMappingDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProductMappingQueryService extends QueryService<ProductMapping> {

    private final Logger log = LoggerFactory.getLogger(ProductMappingQueryService.class);

    private final ProductMappingRepository productMappingRepository;

    private final ProductMappingMapper productMappingMapper;

    public ProductMappingQueryService(ProductMappingRepository productMappingRepository, ProductMappingMapper productMappingMapper) {
        this.productMappingRepository = productMappingRepository;
        this.productMappingMapper = productMappingMapper;
    }

    /**
     * Return a {@link List} of {@link ProductMappingDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ProductMappingDTO> findByCriteria(ProductMappingCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ProductMapping> specification = createSpecification(criteria);
        return productMappingMapper.toDto(productMappingRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ProductMappingDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ProductMappingDTO> findByCriteria(ProductMappingCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ProductMapping> specification = createSpecification(criteria);
        return productMappingRepository.findAll(specification, page).map(productMappingMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProductMappingCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ProductMapping> specification = createSpecification(criteria);
        return productMappingRepository.count(specification);
    }

    /**
     * Function to convert {@link ProductMappingCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ProductMapping> createSpecification(ProductMappingCriteria criteria) {
        Specification<ProductMapping> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ProductMapping_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), ProductMapping_.name));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getType(), ProductMapping_.type));
            }
            if (criteria.getActivated() != null) {
                specification = specification.and(buildSpecification(criteria.getActivated(), ProductMapping_.activated));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), ProductMapping_.createdBy));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), ProductMapping_.createdDate));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), ProductMapping_.lastModifiedBy));
            }
            if (criteria.getLastModifiedDate() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getLastModifiedDate(), ProductMapping_.lastModifiedDate));
            }
            if (criteria.getProductMappingRelId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getProductMappingRelId(),
                            root -> root.join(ProductMapping_.productMappingRels, JoinType.LEFT).get(ProductMappingRel_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
