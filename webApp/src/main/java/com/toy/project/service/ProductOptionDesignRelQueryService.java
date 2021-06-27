package com.toy.project.service;

import com.toy.project.domain.*; // for static metamodels
import com.toy.project.domain.ProductOptionDesignRel;
import com.toy.project.repository.ProductOptionDesignRelRepository;
import com.toy.project.service.criteria.ProductOptionDesignRelCriteria;
import com.toy.project.service.dto.ProductOptionDesignRelDTO;
import com.toy.project.service.mapper.ProductOptionDesignRelMapper;
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
 * Service for executing complex queries for {@link ProductOptionDesignRel} entities in the database.
 * The main input is a {@link ProductOptionDesignRelCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ProductOptionDesignRelDTO} or a {@link Page} of {@link ProductOptionDesignRelDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProductOptionDesignRelQueryService extends QueryService<ProductOptionDesignRel> {

    private final Logger log = LoggerFactory.getLogger(ProductOptionDesignRelQueryService.class);

    private final ProductOptionDesignRelRepository productOptionDesignRelRepository;

    private final ProductOptionDesignRelMapper productOptionDesignRelMapper;

    public ProductOptionDesignRelQueryService(
        ProductOptionDesignRelRepository productOptionDesignRelRepository,
        ProductOptionDesignRelMapper productOptionDesignRelMapper
    ) {
        this.productOptionDesignRelRepository = productOptionDesignRelRepository;
        this.productOptionDesignRelMapper = productOptionDesignRelMapper;
    }

    /**
     * Return a {@link List} of {@link ProductOptionDesignRelDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ProductOptionDesignRelDTO> findByCriteria(ProductOptionDesignRelCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ProductOptionDesignRel> specification = createSpecification(criteria);
        return productOptionDesignRelMapper.toDto(productOptionDesignRelRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ProductOptionDesignRelDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ProductOptionDesignRelDTO> findByCriteria(ProductOptionDesignRelCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ProductOptionDesignRel> specification = createSpecification(criteria);
        return productOptionDesignRelRepository.findAll(specification, page).map(productOptionDesignRelMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProductOptionDesignRelCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ProductOptionDesignRel> specification = createSpecification(criteria);
        return productOptionDesignRelRepository.count(specification);
    }

    /**
     * Function to convert {@link ProductOptionDesignRelCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ProductOptionDesignRel> createSpecification(ProductOptionDesignRelCriteria criteria) {
        Specification<ProductOptionDesignRel> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ProductOptionDesignRel_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), ProductOptionDesignRel_.name));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getType(), ProductOptionDesignRel_.type));
            }
            if (criteria.getActivated() != null) {
                specification = specification.and(buildSpecification(criteria.getActivated(), ProductOptionDesignRel_.activated));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), ProductOptionDesignRel_.createdBy));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), ProductOptionDesignRel_.createdDate));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getLastModifiedBy(), ProductOptionDesignRel_.lastModifiedBy));
            }
            if (criteria.getLastModifiedDate() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getLastModifiedDate(), ProductOptionDesignRel_.lastModifiedDate));
            }
            if (criteria.getProductOptionId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getProductOptionId(),
                            root -> root.join(ProductOptionDesignRel_.productOption, JoinType.LEFT).get(ProductOption_.id)
                        )
                    );
            }
            if (criteria.getOptionDesignId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getOptionDesignId(),
                            root -> root.join(ProductOptionDesignRel_.optionDesign, JoinType.LEFT).get(OptionDesign_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
