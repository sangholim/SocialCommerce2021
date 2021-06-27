package com.toy.project.service;

import com.toy.project.domain.*; // for static metamodels
import com.toy.project.domain.ProductNoticeRel;
import com.toy.project.repository.ProductNoticeRelRepository;
import com.toy.project.service.criteria.ProductNoticeRelCriteria;
import com.toy.project.service.dto.ProductNoticeRelDTO;
import com.toy.project.service.mapper.ProductNoticeRelMapper;
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
 * Service for executing complex queries for {@link ProductNoticeRel} entities in the database.
 * The main input is a {@link ProductNoticeRelCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ProductNoticeRelDTO} or a {@link Page} of {@link ProductNoticeRelDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProductNoticeRelQueryService extends QueryService<ProductNoticeRel> {

    private final Logger log = LoggerFactory.getLogger(ProductNoticeRelQueryService.class);

    private final ProductNoticeRelRepository productNoticeRelRepository;

    private final ProductNoticeRelMapper productNoticeRelMapper;

    public ProductNoticeRelQueryService(
        ProductNoticeRelRepository productNoticeRelRepository,
        ProductNoticeRelMapper productNoticeRelMapper
    ) {
        this.productNoticeRelRepository = productNoticeRelRepository;
        this.productNoticeRelMapper = productNoticeRelMapper;
    }

    /**
     * Return a {@link List} of {@link ProductNoticeRelDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ProductNoticeRelDTO> findByCriteria(ProductNoticeRelCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ProductNoticeRel> specification = createSpecification(criteria);
        return productNoticeRelMapper.toDto(productNoticeRelRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ProductNoticeRelDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ProductNoticeRelDTO> findByCriteria(ProductNoticeRelCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ProductNoticeRel> specification = createSpecification(criteria);
        return productNoticeRelRepository.findAll(specification, page).map(productNoticeRelMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProductNoticeRelCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ProductNoticeRel> specification = createSpecification(criteria);
        return productNoticeRelRepository.count(specification);
    }

    /**
     * Function to convert {@link ProductNoticeRelCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ProductNoticeRel> createSpecification(ProductNoticeRelCriteria criteria) {
        Specification<ProductNoticeRel> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ProductNoticeRel_.id));
            }
            if (criteria.getActivated() != null) {
                specification = specification.and(buildSpecification(criteria.getActivated(), ProductNoticeRel_.activated));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), ProductNoticeRel_.createdBy));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), ProductNoticeRel_.createdDate));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), ProductNoticeRel_.lastModifiedBy));
            }
            if (criteria.getLastModifiedDate() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getLastModifiedDate(), ProductNoticeRel_.lastModifiedDate));
            }
            if (criteria.getProductId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getProductId(),
                            root -> root.join(ProductNoticeRel_.product, JoinType.LEFT).get(Product_.id)
                        )
                    );
            }
            if (criteria.getProductNoticeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getProductNoticeId(),
                            root -> root.join(ProductNoticeRel_.productNotice, JoinType.LEFT).get(ProductNotice_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
