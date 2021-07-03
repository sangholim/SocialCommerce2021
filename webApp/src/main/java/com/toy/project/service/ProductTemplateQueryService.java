package com.toy.project.service;

import com.toy.project.domain.*; // for static metamodels
import com.toy.project.domain.ProductTemplate;
import com.toy.project.repository.ProductTemplateRepository;
import com.toy.project.service.criteria.ProductTemplateCriteria;
import com.toy.project.service.dto.ProductTemplateDTO;
import com.toy.project.service.mapper.ProductTemplateMapper;
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
 * Service for executing complex queries for {@link ProductTemplate} entities in the database.
 * The main input is a {@link ProductTemplateCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ProductTemplateDTO} or a {@link Page} of {@link ProductTemplateDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProductTemplateQueryService extends QueryService<ProductTemplate> {

    private final Logger log = LoggerFactory.getLogger(ProductTemplateQueryService.class);

    private final ProductTemplateRepository productTemplateRepository;

    private final ProductTemplateMapper productTemplateMapper;

    public ProductTemplateQueryService(ProductTemplateRepository productTemplateRepository, ProductTemplateMapper productTemplateMapper) {
        this.productTemplateRepository = productTemplateRepository;
        this.productTemplateMapper = productTemplateMapper;
    }

    /**
     * Return a {@link List} of {@link ProductTemplateDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ProductTemplateDTO> findByCriteria(ProductTemplateCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ProductTemplate> specification = createSpecification(criteria);
        return productTemplateMapper.toDto(productTemplateRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ProductTemplateDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ProductTemplateDTO> findByCriteria(ProductTemplateCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ProductTemplate> specification = createSpecification(criteria);
        return productTemplateRepository.findAll(specification, page).map(productTemplateMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProductTemplateCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ProductTemplate> specification = createSpecification(criteria);
        return productTemplateRepository.count(specification);
    }

    /**
     * Function to convert {@link ProductTemplateCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ProductTemplate> createSpecification(ProductTemplateCriteria criteria) {
        Specification<ProductTemplate> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ProductTemplate_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), ProductTemplate_.name));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getType(), ProductTemplate_.type));
            }
            if (criteria.getContentFileUrl() != null) {
                specification = specification.and(buildStringSpecification(criteria.getContentFileUrl(), ProductTemplate_.contentFileUrl));
            }
            if (criteria.getActivated() != null) {
                specification = specification.and(buildSpecification(criteria.getActivated(), ProductTemplate_.activated));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), ProductTemplate_.createdBy));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), ProductTemplate_.createdDate));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), ProductTemplate_.lastModifiedBy));
            }
            if (criteria.getLastModifiedDate() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getLastModifiedDate(), ProductTemplate_.lastModifiedDate));
            }
            if (criteria.getProductTemplateRelId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getProductTemplateRelId(),
                            root -> root.join(ProductTemplate_.productTemplateRels, JoinType.LEFT).get(ProductTemplateRel_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
