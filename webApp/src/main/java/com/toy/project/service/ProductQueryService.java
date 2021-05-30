package com.toy.project.service;

import com.toy.project.domain.*; // for static metamodels
import com.toy.project.domain.Product;
import com.toy.project.repository.ProductRepository;
import com.toy.project.service.criteria.ProductCriteria;
import com.toy.project.service.dto.ProductDTO;
import com.toy.project.service.mapper.ProductMapper;
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
 * Service for executing complex queries for {@link Product} entities in the database.
 * The main input is a {@link ProductCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ProductDTO} or a {@link Page} of {@link ProductDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProductQueryService extends QueryService<Product> {

    private final Logger log = LoggerFactory.getLogger(ProductQueryService.class);

    private final ProductRepository productRepository;

    private final ProductMapper productMapper;

    public ProductQueryService(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    /**
     * Return a {@link List} of {@link ProductDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ProductDTO> findByCriteria(ProductCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Product> specification = createSpecification(criteria);
        return productMapper.toDto(productRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ProductDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ProductDTO> findByCriteria(ProductCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Product> specification = createSpecification(criteria);
        return productRepository.findAll(specification, page).map(productMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProductCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Product> specification = createSpecification(criteria);
        return productRepository.count(specification);
    }

    /**
     * Function to convert {@link ProductCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Product> createSpecification(ProductCriteria criteria) {
        Specification<Product> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Product_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Product_.name));
            }
            if (criteria.getDifficulty() != null) {
                specification = specification.and(buildSpecification(criteria.getDifficulty(), Product_.difficulty));
            }
            if (criteria.getThumbnail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getThumbnail(), Product_.thumbnail));
            }
            if (criteria.getOwner() != null) {
                specification = specification.and(buildStringSpecification(criteria.getOwner(), Product_.owner));
            }
            if (criteria.getRegdate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRegdate(), Product_.regdate));
            }
            if (criteria.getPriceRegular() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPriceRegular(), Product_.priceRegular));
            }
            if (criteria.getIsUseDiscount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getIsUseDiscount(), Product_.isUseDiscount));
            }
            if (criteria.getDiscountUnit() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDiscountUnit(), Product_.discountUnit));
            }
            if (criteria.getDiscountValue() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDiscountValue(), Product_.discountValue));
            }
            if (criteria.getDiscountStartdate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDiscountStartdate(), Product_.discountStartdate));
            }
            if (criteria.getDiscountInterval() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDiscountInterval(), Product_.discountInterval));
            }
            if (criteria.getVideo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getVideo(), Product_.video));
            }
            if (criteria.getStartdate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStartdate(), Product_.startdate));
            }
            if (criteria.getPrepareResource() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPrepareResource(), Product_.prepareResource));
            }
            if (criteria.getIntroduceResource() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIntroduceResource(), Product_.introduceResource));
            }
            if (criteria.getShippingResource() != null) {
                specification = specification.and(buildStringSpecification(criteria.getShippingResource(), Product_.shippingResource));
            }
            if (criteria.getRefundResource() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRefundResource(), Product_.refundResource));
            }
            if (criteria.getChangeResource() != null) {
                specification = specification.and(buildStringSpecification(criteria.getChangeResource(), Product_.changeResource));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), Product_.code));
            }
            if (criteria.getInstallmentMonth() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getInstallmentMonth(), Product_.installmentMonth));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildSpecification(criteria.getType(), Product_.type));
            }
            if (criteria.getNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNumber(), Product_.number));
            }
            if (criteria.getPopularCount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPopularCount(), Product_.popularCount));
            }
            if (criteria.getReviewCount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getReviewCount(), Product_.reviewCount));
            }
        }
        return specification;
    }
}
