package com.toy.project.service;

import com.toy.project.domain.*; // for static metamodels
import com.toy.project.domain.ProductOptionPackageRel;
import com.toy.project.repository.ProductOptionPackageRelRepository;
import com.toy.project.service.criteria.ProductOptionPackageRelCriteria;
import com.toy.project.service.dto.ProductOptionPackageRelDTO;
import com.toy.project.service.mapper.ProductOptionPackageRelMapper;
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
 * Service for executing complex queries for {@link ProductOptionPackageRel} entities in the database.
 * The main input is a {@link ProductOptionPackageRelCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ProductOptionPackageRelDTO} or a {@link Page} of {@link ProductOptionPackageRelDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProductOptionPackageRelQueryService extends QueryService<ProductOptionPackageRel> {

    private final Logger log = LoggerFactory.getLogger(ProductOptionPackageRelQueryService.class);

    private final ProductOptionPackageRelRepository productOptionPackageRelRepository;

    private final ProductOptionPackageRelMapper productOptionPackageRelMapper;

    public ProductOptionPackageRelQueryService(
        ProductOptionPackageRelRepository productOptionPackageRelRepository,
        ProductOptionPackageRelMapper productOptionPackageRelMapper
    ) {
        this.productOptionPackageRelRepository = productOptionPackageRelRepository;
        this.productOptionPackageRelMapper = productOptionPackageRelMapper;
    }

    /**
     * Return a {@link List} of {@link ProductOptionPackageRelDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ProductOptionPackageRelDTO> findByCriteria(ProductOptionPackageRelCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ProductOptionPackageRel> specification = createSpecification(criteria);
        return productOptionPackageRelMapper.toDto(productOptionPackageRelRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ProductOptionPackageRelDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ProductOptionPackageRelDTO> findByCriteria(ProductOptionPackageRelCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ProductOptionPackageRel> specification = createSpecification(criteria);
        return productOptionPackageRelRepository.findAll(specification, page).map(productOptionPackageRelMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProductOptionPackageRelCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ProductOptionPackageRel> specification = createSpecification(criteria);
        return productOptionPackageRelRepository.count(specification);
    }

    /**
     * Function to convert {@link ProductOptionPackageRelCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ProductOptionPackageRel> createSpecification(ProductOptionPackageRelCriteria criteria) {
        Specification<ProductOptionPackageRel> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ProductOptionPackageRel_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), ProductOptionPackageRel_.name));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getType(), ProductOptionPackageRel_.type));
            }
            if (criteria.getActivated() != null) {
                specification = specification.and(buildSpecification(criteria.getActivated(), ProductOptionPackageRel_.activated));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), ProductOptionPackageRel_.createdBy));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), ProductOptionPackageRel_.createdDate));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getLastModifiedBy(), ProductOptionPackageRel_.lastModifiedBy));
            }
            if (criteria.getLastModifiedDate() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getLastModifiedDate(), ProductOptionPackageRel_.lastModifiedDate));
            }
            if (criteria.getProductOptionId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getProductOptionId(),
                            root -> root.join(ProductOptionPackageRel_.productOption, JoinType.LEFT).get(ProductOption_.id)
                        )
                    );
            }
            if (criteria.getOptionPackageId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getOptionPackageId(),
                            root -> root.join(ProductOptionPackageRel_.optionPackage, JoinType.LEFT).get(OptionPackage_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
