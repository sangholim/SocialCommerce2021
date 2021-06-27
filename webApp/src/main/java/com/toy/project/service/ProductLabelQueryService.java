package com.toy.project.service;

import com.toy.project.domain.*; // for static metamodels
import com.toy.project.domain.ProductLabel;
import com.toy.project.repository.ProductLabelRepository;
import com.toy.project.service.criteria.ProductLabelCriteria;
import com.toy.project.service.dto.ProductLabelDTO;
import com.toy.project.service.mapper.ProductLabelMapper;
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
 * Service for executing complex queries for {@link ProductLabel} entities in the database.
 * The main input is a {@link ProductLabelCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ProductLabelDTO} or a {@link Page} of {@link ProductLabelDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProductLabelQueryService extends QueryService<ProductLabel> {

    private final Logger log = LoggerFactory.getLogger(ProductLabelQueryService.class);

    private final ProductLabelRepository productLabelRepository;

    private final ProductLabelMapper productLabelMapper;

    public ProductLabelQueryService(ProductLabelRepository productLabelRepository, ProductLabelMapper productLabelMapper) {
        this.productLabelRepository = productLabelRepository;
        this.productLabelMapper = productLabelMapper;
    }

    /**
     * Return a {@link List} of {@link ProductLabelDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ProductLabelDTO> findByCriteria(ProductLabelCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ProductLabel> specification = createSpecification(criteria);
        return productLabelMapper.toDto(productLabelRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ProductLabelDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ProductLabelDTO> findByCriteria(ProductLabelCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ProductLabel> specification = createSpecification(criteria);
        return productLabelRepository.findAll(specification, page).map(productLabelMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProductLabelCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ProductLabel> specification = createSpecification(criteria);
        return productLabelRepository.count(specification);
    }

    /**
     * Function to convert {@link ProductLabelCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ProductLabel> createSpecification(ProductLabelCriteria criteria) {
        Specification<ProductLabel> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ProductLabel_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), ProductLabel_.name));
            }
            if (criteria.getColor() != null) {
                specification = specification.and(buildStringSpecification(criteria.getColor(), ProductLabel_.color));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getType(), ProductLabel_.type));
            }
            if (criteria.getActivated() != null) {
                specification = specification.and(buildSpecification(criteria.getActivated(), ProductLabel_.activated));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), ProductLabel_.createdBy));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), ProductLabel_.createdDate));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), ProductLabel_.lastModifiedBy));
            }
            if (criteria.getLastModifiedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModifiedDate(), ProductLabel_.lastModifiedDate));
            }
            if (criteria.getProductLabelRelId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getProductLabelRelId(),
                            root -> root.join(ProductLabel_.productLabelRels, JoinType.LEFT).get(ProductLabelRel_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
