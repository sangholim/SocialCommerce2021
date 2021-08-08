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
            if (criteria.getType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getType(), Product_.type));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStatus(), Product_.status));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), Product_.code));
            }
            if (criteria.getUseClassOption() != null) {
                specification = specification.and(buildSpecification(criteria.getUseClassOption(), Product_.useClassOption));
            }
            if (criteria.getPrice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPrice(), Product_.price));
            }
            if (criteria.getUseDiscountInstant() != null) {
                specification = specification.and(buildSpecification(criteria.getUseDiscountInstant(), Product_.useDiscountInstant));
            }
            if (criteria.getUseInstallment() != null) {
                specification = specification.and(buildSpecification(criteria.getUseInstallment(), Product_.useInstallment));
            }
            if (criteria.getInstallmentMonth() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getInstallmentMonth(), Product_.installmentMonth));
            }
            if (criteria.getUseSellDate() != null) {
                specification = specification.and(buildSpecification(criteria.getUseSellDate(), Product_.useSellDate));
            }
            if (criteria.getSellDateFrom() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSellDateFrom(), Product_.sellDateFrom));
            }
            if (criteria.getSellDateTo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSellDateTo(), Product_.sellDateTo));
            }
            if (criteria.getQuantity() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getQuantity(), Product_.quantity));
            }
            if (criteria.getUseProductOption() != null) {
                specification = specification.and(buildSpecification(criteria.getUseProductOption(), Product_.useProductOption));
            }
            if (criteria.getUseProductInputOption() != null) {
                specification = specification.and(buildSpecification(criteria.getUseProductInputOption(), Product_.useProductInputOption));
            }
            if (criteria.getUseProductAddOption() != null) {
                specification = specification.and(buildSpecification(criteria.getUseProductAddOption(), Product_.useProductAddOption));
            }
            if (criteria.getMinPurchaseAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMinPurchaseAmount(), Product_.minPurchaseAmount));
            }
            if (criteria.getMaxPurchaseAmountPerCount() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getMaxPurchaseAmountPerCount(), Product_.maxPurchaseAmountPerCount));
            }
            if (criteria.getMaxPurchaseAmountPerOne() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getMaxPurchaseAmountPerOne(), Product_.maxPurchaseAmountPerOne));
            }
            if (criteria.getMainImageFileUrl() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMainImageFileUrl(), Product_.mainImageFileUrl));
            }
            if (criteria.getMainVideoFileUrl() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMainVideoFileUrl(), Product_.mainVideoFileUrl));
            }
            if (criteria.getUseProductAnnounce() != null) {
                specification = specification.and(buildSpecification(criteria.getUseProductAnnounce(), Product_.useProductAnnounce));
            }
            if (criteria.getUseProductFaq() != null) {
                specification = specification.and(buildSpecification(criteria.getUseProductFaq(), Product_.useProductFaq));
            }
            if (criteria.getDescriptionFileUrl() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescriptionFileUrl(), Product_.descriptionFileUrl));
            }
            if (criteria.getShippingReleaseType() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getShippingReleaseType(), Product_.shippingReleaseType));
            }
            if (criteria.getShippingStandardStartTime() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(criteria.getShippingStandardStartTime(), Product_.shippingStandardStartTime)
                    );
            }
            if (criteria.getEtcShippingContent() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEtcShippingContent(), Product_.etcShippingContent));
            }
            if (criteria.getSeparateShippingPriceType() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(criteria.getSeparateShippingPriceType(), Product_.separateShippingPriceType)
                    );
            }
            if (criteria.getBundleShippingType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBundleShippingType(), Product_.bundleShippingType));
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
            if (criteria.getExchangeShippingFileType() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getExchangeShippingFileType(), Product_.exchangeShippingFileType));
            }
            if (criteria.getExchangeShippingFileUrl() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getExchangeShippingFileUrl(), Product_.exchangeShippingFileUrl));
            }
            if (criteria.getUseView() != null) {
                specification = specification.and(buildSpecification(criteria.getUseView(), Product_.useView));
            }
            if (criteria.getUseViewReservation() != null) {
                specification = specification.and(buildSpecification(criteria.getUseViewReservation(), Product_.useViewReservation));
            }
            if (criteria.getViewReservationDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getViewReservationDate(), Product_.viewReservationDate));
            }
            if (criteria.getUseProductNotice() != null) {
                specification = specification.and(buildSpecification(criteria.getUseProductNotice(), Product_.useProductNotice));
            }
            if (criteria.getUseProductIllegal() != null) {
                specification = specification.and(buildSpecification(criteria.getUseProductIllegal(), Product_.useProductIllegal));
            }
            if (criteria.getUseProductRecommend() != null) {
                specification = specification.and(buildSpecification(criteria.getUseProductRecommend(), Product_.useProductRecommend));
            }
            if (criteria.getUseProductMapping() != null) {
                specification = specification.and(buildSpecification(criteria.getUseProductMapping(), Product_.useProductMapping));
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
            if (criteria.getProductDiscountId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getProductDiscountId(),
                            root -> root.join(Product_.productDiscounts, JoinType.LEFT).get(ProductDiscount_.id)
                        )
                    );
            }
            if (criteria.getProductMappingId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getProductMappingId(),
                            root -> root.join(Product_.productMappings, JoinType.LEFT).get(ProductMapping_.id)
                        )
                    );
            }
            if (criteria.getProductOptionId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getProductOptionId(),
                            root -> root.join(Product_.productOptions, JoinType.LEFT).get(ProductOption_.id)
                        )
                    );
            }
            if (criteria.getProductAddOptionId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getProductAddOptionId(),
                            root -> root.join(Product_.productAddOptions, JoinType.LEFT).get(ProductAddOption_.id)
                        )
                    );
            }
            if (criteria.getProductInputOptionId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getProductInputOptionId(),
                            root -> root.join(Product_.productInputOptions, JoinType.LEFT).get(ProductInputOption_.id)
                        )
                    );
            }
            if (criteria.getProductFaqId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getProductFaqId(),
                            root -> root.join(Product_.productFaqs, JoinType.LEFT).get(ProductFaq_.id)
                        )
                    );
            }
            if (criteria.getProductAnnounceId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getProductAnnounceId(),
                            root -> root.join(Product_.productAnnounces, JoinType.LEFT).get(ProductAnnounce_.id)
                        )
                    );
            }
            if (criteria.getProductAddImageId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getProductAddImageId(),
                            root -> root.join(Product_.productAddImages, JoinType.LEFT).get(ProductAddImage_.id)
                        )
                    );
            }
            if (criteria.getProductLabelId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getProductLabelId(),
                            root -> root.join(Product_.productLabels, JoinType.LEFT).get(ProductLabel_.id)
                        )
                    );
            }
            if (criteria.getProductTemplateId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getProductTemplateId(),
                            root -> root.join(Product_.productTemplates, JoinType.LEFT).get(ProductTemplate_.id)
                        )
                    );
            }
            if (criteria.getProductCategoryId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getProductCategoryId(),
                            root -> root.join(Product_.productCategories, JoinType.LEFT).get(ProductCategory_.id)
                        )
                    );
            }
            if (criteria.getProductNoticeManageId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getProductNoticeManageId(),
                            root -> root.join(Product_.productNoticeManage, JoinType.LEFT).get(ProductNoticeManage_.id)
                        )
                    );
            }
            if (criteria.getProductClazzAuthorId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getProductClazzAuthorId(),
                            root -> root.join(Product_.productClazzAuthor, JoinType.LEFT).get(ProductClazzAuthor_.id)
                        )
                    );
            }
            if (criteria.getProductStoreId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getProductStoreId(),
                            root -> root.join(Product_.productStore, JoinType.LEFT).get(ProductStore_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
