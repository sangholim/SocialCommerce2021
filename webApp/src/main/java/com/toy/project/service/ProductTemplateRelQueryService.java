package com.toy.project.service;

import com.toy.project.domain.*; // for static metamodels
import com.toy.project.domain.ProductTemplateRel;
import com.toy.project.repository.ProductTemplateRelRepository;
import com.toy.project.service.criteria.ProductTemplateRelCriteria;
import com.toy.project.service.dto.ProductTemplateRelDTO;
import com.toy.project.service.mapper.ProductTemplateRelMapper;
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
 * Service for executing complex queries for {@link ProductTemplateRel} entities in the database.
 * The main input is a {@link ProductTemplateRelCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ProductTemplateRelDTO} or a {@link Page} of {@link ProductTemplateRelDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProductTemplateRelQueryService extends QueryService<ProductTemplateRel> {

    private final Logger log = LoggerFactory.getLogger(ProductTemplateRelQueryService.class);

    private final ProductTemplateRelRepository productTemplateRelRepository;

    private final ProductTemplateRelMapper productTemplateRelMapper;

    public ProductTemplateRelQueryService(
        ProductTemplateRelRepository productTemplateRelRepository,
        ProductTemplateRelMapper productTemplateRelMapper
    ) {
        this.productTemplateRelRepository = productTemplateRelRepository;
        this.productTemplateRelMapper = productTemplateRelMapper;
    }

    /**
     * Return a {@link List} of {@link ProductTemplateRelDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ProductTemplateRelDTO> findByCriteria(ProductTemplateRelCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ProductTemplateRel> specification = createSpecification(criteria);
        return productTemplateRelMapper.toDto(productTemplateRelRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ProductTemplateRelDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ProductTemplateRelDTO> findByCriteria(ProductTemplateRelCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ProductTemplateRel> specification = createSpecification(criteria);
        return productTemplateRelRepository.findAll(specification, page).map(productTemplateRelMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProductTemplateRelCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ProductTemplateRel> specification = createSpecification(criteria);
        return productTemplateRelRepository.count(specification);
    }

    /**
     * Function to convert {@link ProductTemplateRelCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ProductTemplateRel> createSpecification(ProductTemplateRelCriteria criteria) {
        Specification<ProductTemplateRel> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ProductTemplateRel_.id));
            }
            if (criteria.getActivated() != null) {
                specification = specification.and(buildSpecification(criteria.getActivated(), ProductTemplateRel_.activated));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), ProductTemplateRel_.createdBy));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), ProductTemplateRel_.createdDate));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getLastModifiedBy(), ProductTemplateRel_.lastModifiedBy));
            }
            if (criteria.getLastModifiedDate() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getLastModifiedDate(), ProductTemplateRel_.lastModifiedDate));
            }
            if (criteria.getProductId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getProductId(),
                            root -> root.join(ProductTemplateRel_.product, JoinType.LEFT).get(Product_.id)
                        )
                    );
            }
            if (criteria.getProductTemplateId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getProductTemplateId(),
                            root -> root.join(ProductTemplateRel_.productTemplate, JoinType.LEFT).get(ProductTemplate_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
