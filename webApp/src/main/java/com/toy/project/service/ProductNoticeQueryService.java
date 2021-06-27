package com.toy.project.service;

import com.toy.project.domain.*; // for static metamodels
import com.toy.project.domain.ProductNotice;
import com.toy.project.repository.ProductNoticeRepository;
import com.toy.project.service.criteria.ProductNoticeCriteria;
import com.toy.project.service.dto.ProductNoticeDTO;
import com.toy.project.service.mapper.ProductNoticeMapper;
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
 * Service for executing complex queries for {@link ProductNotice} entities in the database.
 * The main input is a {@link ProductNoticeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ProductNoticeDTO} or a {@link Page} of {@link ProductNoticeDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProductNoticeQueryService extends QueryService<ProductNotice> {

    private final Logger log = LoggerFactory.getLogger(ProductNoticeQueryService.class);

    private final ProductNoticeRepository productNoticeRepository;

    private final ProductNoticeMapper productNoticeMapper;

    public ProductNoticeQueryService(ProductNoticeRepository productNoticeRepository, ProductNoticeMapper productNoticeMapper) {
        this.productNoticeRepository = productNoticeRepository;
        this.productNoticeMapper = productNoticeMapper;
    }

    /**
     * Return a {@link List} of {@link ProductNoticeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ProductNoticeDTO> findByCriteria(ProductNoticeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ProductNotice> specification = createSpecification(criteria);
        return productNoticeMapper.toDto(productNoticeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ProductNoticeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ProductNoticeDTO> findByCriteria(ProductNoticeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ProductNotice> specification = createSpecification(criteria);
        return productNoticeRepository.findAll(specification, page).map(productNoticeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProductNoticeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ProductNotice> specification = createSpecification(criteria);
        return productNoticeRepository.count(specification);
    }

    /**
     * Function to convert {@link ProductNoticeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ProductNotice> createSpecification(ProductNoticeCriteria criteria) {
        Specification<ProductNotice> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ProductNotice_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), ProductNotice_.name));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getType(), ProductNotice_.type));
            }
            if (criteria.getActivated() != null) {
                specification = specification.and(buildSpecification(criteria.getActivated(), ProductNotice_.activated));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), ProductNotice_.createdBy));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), ProductNotice_.createdDate));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), ProductNotice_.lastModifiedBy));
            }
            if (criteria.getLastModifiedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModifiedDate(), ProductNotice_.lastModifiedDate));
            }
            if (criteria.getProductNoticeRelId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getProductNoticeRelId(),
                            root -> root.join(ProductNotice_.productNoticeRels, JoinType.LEFT).get(ProductNoticeRel_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
