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
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), Product_.code));
            }
            if (criteria.getCalculation() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCalculation(), Product_.calculation));
            }
            if (criteria.getCalculationDateFrom() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCalculationDateFrom(), Product_.calculationDateFrom));
            }
            if (criteria.getCalculationDateTo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCalculationDateTo(), Product_.calculationDateTo));
            }
            if (criteria.getPrice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPrice(), Product_.price));
            }
            if (criteria.getAllPriceUnit() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAllPriceUnit(), Product_.allPriceUnit));
            }
            if (criteria.getDiscount() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDiscount(), Product_.discount));
            }
            if (criteria.getDiscountPrice() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDiscountPrice(), Product_.discountPrice));
            }
            if (criteria.getDiscountUnit() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDiscountUnit(), Product_.discountUnit));
            }
            if (criteria.getDiscountDateFrom() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDiscountDateFrom(), Product_.discountDateFrom));
            }
            if (criteria.getDiscountDateTo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDiscountDateTo(), Product_.discountDateTo));
            }
            if (criteria.getIsInstallment() != null) {
                specification = specification.and(buildSpecification(criteria.getIsInstallment(), Product_.isInstallment));
            }
            if (criteria.getInstallmentMonth() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getInstallmentMonth(), Product_.installmentMonth));
            }
            if (criteria.getIsSell() != null) {
                specification = specification.and(buildSpecification(criteria.getIsSell(), Product_.isSell));
            }
            if (criteria.getSellDateFrom() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSellDateFrom(), Product_.sellDateFrom));
            }
            if (criteria.getSellDateTo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSellDateTo(), Product_.sellDateTo));
            }
            if (criteria.getMinPurchaseAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMinPurchaseAmount(), Product_.minPurchaseAmount));
            }
            if (criteria.getManPurchaseAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getManPurchaseAmount(), Product_.manPurchaseAmount));
            }
            if (criteria.getMainImageFileUrl() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMainImageFileUrl(), Product_.mainImageFileUrl));
            }
            if (criteria.getMainVideoFileUrl() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMainVideoFileUrl(), Product_.mainVideoFileUrl));
            }
            if (criteria.getDescriptionFileUrl() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescriptionFileUrl(), Product_.descriptionFileUrl));
            }
            if (criteria.getShippingType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getShippingType(), Product_.shippingType));
            }
            if (criteria.getSeparateShippingPriceType() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(criteria.getSeparateShippingPriceType(), Product_.separateShippingPriceType)
                    );
            }
            if (criteria.getDefaultShippingPrice() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getDefaultShippingPrice(), Product_.defaultShippingPrice));
            }
            if (criteria.getFreeShippingPrice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFreeShippingPrice(), Product_.freeShippingPrice));
            }
            if (criteria.getJejuShippingPrice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getJejuShippingPrice(), Product_.jejuShippingPrice));
            }
            if (criteria.getDifficultShippingPrice() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getDifficultShippingPrice(), Product_.difficultShippingPrice));
            }
            if (criteria.getRefundShippingPrice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRefundShippingPrice(), Product_.refundShippingPrice));
            }
            if (criteria.getExchangeShippingPrice() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getExchangeShippingPrice(), Product_.exchangeShippingPrice));
            }
            if (criteria.getExchangeShippingFileUrl() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getExchangeShippingFileUrl(), Product_.exchangeShippingFileUrl));
            }
            if (criteria.getIsView() != null) {
                specification = specification.and(buildSpecification(criteria.getIsView(), Product_.isView));
            }
            if (criteria.getViewReservationDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getViewReservationDate(), Product_.viewReservationDate));
            }
            if (criteria.getActivated() != null) {
                specification = specification.and(buildSpecification(criteria.getActivated(), Product_.activated));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), Product_.createdBy));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), Product_.createdDate));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), Product_.lastModifiedBy));
            }
            if (criteria.getLastModifiedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModifiedDate(), Product_.lastModifiedDate));
            }
            if (criteria.getProductCategoryRelId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getProductCategoryRelId(),
                            root -> root.join(Product_.productCategoryRels, JoinType.LEFT).get(ProductCategoryRel_.id)
                        )
                    );
            }
            if (criteria.getProductLabelRelId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getProductLabelRelId(),
                            root -> root.join(Product_.productLabelRels, JoinType.LEFT).get(ProductLabelRel_.id)
                        )
                    );
            }
            if (criteria.getProductMappingRelId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getProductMappingRelId(),
                            root -> root.join(Product_.productMappingRels, JoinType.LEFT).get(ProductMappingRel_.id)
                        )
                    );
            }
            if (criteria.getProductViewRelId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getProductViewRelId(),
                            root -> root.join(Product_.productViewRels, JoinType.LEFT).get(ProductViewRel_.id)
                        )
                    );
            }
            if (criteria.getProductNoticeRelId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getProductNoticeRelId(),
                            root -> root.join(Product_.productNoticeRels, JoinType.LEFT).get(ProductNoticeRel_.id)
                        )
                    );
            }
            if (criteria.getProductShippingRelId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getProductShippingRelId(),
                            root -> root.join(Product_.productShippingRels, JoinType.LEFT).get(ProductShippingRel_.id)
                        )
                    );
            }
            if (criteria.getProductTemplateRelId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getProductTemplateRelId(),
                            root -> root.join(Product_.productTemplateRels, JoinType.LEFT).get(ProductTemplateRel_.id)
                        )
                    );
            }
            if (criteria.getProductOptionRelId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getProductOptionRelId(),
                            root -> root.join(Product_.productOptionRels, JoinType.LEFT).get(ProductOptionRel_.id)
                        )
                    );
            }
            if (criteria.getProductClazzRelId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getProductClazzRelId(),
                            root -> root.join(Product_.productClazzRels, JoinType.LEFT).get(ProductClazzRel_.id)
                        )
                    );
            }
            if (criteria.getProductStoreRelId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getProductStoreRelId(),
                            root -> root.join(Product_.productStoreRels, JoinType.LEFT).get(ProductStoreRel_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
