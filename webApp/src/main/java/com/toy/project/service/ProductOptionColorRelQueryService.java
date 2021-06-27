package com.toy.project.service;

import com.toy.project.domain.*; // for static metamodels
import com.toy.project.domain.ProductOptionColorRel;
import com.toy.project.repository.ProductOptionColorRelRepository;
import com.toy.project.service.criteria.ProductOptionColorRelCriteria;
import com.toy.project.service.dto.ProductOptionColorRelDTO;
import com.toy.project.service.mapper.ProductOptionColorRelMapper;
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
 * Service for executing complex queries for {@link ProductOptionColorRel} entities in the database.
 * The main input is a {@link ProductOptionColorRelCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ProductOptionColorRelDTO} or a {@link Page} of {@link ProductOptionColorRelDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProductOptionColorRelQueryService extends QueryService<ProductOptionColorRel> {

    private final Logger log = LoggerFactory.getLogger(ProductOptionColorRelQueryService.class);

    private final ProductOptionColorRelRepository productOptionColorRelRepository;

    private final ProductOptionColorRelMapper productOptionColorRelMapper;

    public ProductOptionColorRelQueryService(
        ProductOptionColorRelRepository productOptionColorRelRepository,
        ProductOptionColorRelMapper productOptionColorRelMapper
    ) {
        this.productOptionColorRelRepository = productOptionColorRelRepository;
        this.productOptionColorRelMapper = productOptionColorRelMapper;
    }

    /**
     * Return a {@link List} of {@link ProductOptionColorRelDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ProductOptionColorRelDTO> findByCriteria(ProductOptionColorRelCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ProductOptionColorRel> specification = createSpecification(criteria);
        return productOptionColorRelMapper.toDto(productOptionColorRelRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ProductOptionColorRelDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ProductOptionColorRelDTO> findByCriteria(ProductOptionColorRelCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ProductOptionColorRel> specification = createSpecification(criteria);
        return productOptionColorRelRepository.findAll(specification, page).map(productOptionColorRelMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProductOptionColorRelCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ProductOptionColorRel> specification = createSpecification(criteria);
        return productOptionColorRelRepository.count(specification);
    }

    /**
     * Function to convert {@link ProductOptionColorRelCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ProductOptionColorRel> createSpecification(ProductOptionColorRelCriteria criteria) {
        Specification<ProductOptionColorRel> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ProductOptionColorRel_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), ProductOptionColorRel_.name));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getType(), ProductOptionColorRel_.type));
            }
            if (criteria.getActivated() != null) {
                specification = specification.and(buildSpecification(criteria.getActivated(), ProductOptionColorRel_.activated));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), ProductOptionColorRel_.createdBy));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), ProductOptionColorRel_.createdDate));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getLastModifiedBy(), ProductOptionColorRel_.lastModifiedBy));
            }
            if (criteria.getLastModifiedDate() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getLastModifiedDate(), ProductOptionColorRel_.lastModifiedDate));
            }
            if (criteria.getProductOptionId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getProductOptionId(),
                            root -> root.join(ProductOptionColorRel_.productOption, JoinType.LEFT).get(ProductOption_.id)
                        )
                    );
            }
            if (criteria.getOptionColorId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getOptionColorId(),
                            root -> root.join(ProductOptionColorRel_.optionColor, JoinType.LEFT).get(OptionColor_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
