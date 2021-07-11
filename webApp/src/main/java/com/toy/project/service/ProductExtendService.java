package com.toy.project.service;

import com.toy.project.repository.ProductRepository;
import com.toy.project.service.dto.*;
import com.toy.project.service.mapper.ProductMapper;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class ProductExtendService extends ProductService {

    private final ProductCategoryRelService productCategoryRelService;

    private final ProductClazzRelService productClazzRelService;

    private final ProductLabelRelService productLabelRelService;

    private final ProductMappingRelService productMappingRelService;

    private final ProductNoticeRelService productNoticeRelService;

    private final ProductOptionColorRelService productOptionColorRelService;

    private final ProductOptionDesignRelService productOptionDesignRelService;

    private final ProductOptionPackageRelService productOptionPackageRelService;

    private final ProductOptionRelService productOptionRelService;

    private final ProductShippingRelService productShippingRelService;

    private final ProductStoreRelService productStoreRelService;

    private final ProductTemplateRelService productTemplateRelService;

    private final ProductViewRelService productViewRelService;

    public ProductExtendService(
        ProductRepository productRepository,
        ProductMapper productMapper,
        ProductCategoryRelService productCategoryRelService,
        ProductClazzRelService productClazzRelService,
        ProductLabelRelService productLabelRelService,
        ProductMappingRelService productMappingRelService,
        ProductNoticeRelService productNoticeRelService,
        ProductOptionColorRelService productOptionColorRelService,
        ProductOptionDesignRelService productOptionDesignRelService,
        ProductOptionPackageRelService productOptionPackageRelService,
        ProductOptionRelService productOptionRelService,
        ProductShippingRelService productShippingRelService,
        ProductStoreRelService productStoreRelService,
        ProductTemplateRelService productTemplateRelService,
        ProductViewRelService productViewRelService
    ) {
        super(productRepository, productMapper);
        this.productCategoryRelService = productCategoryRelService;
        this.productClazzRelService = productClazzRelService;
        this.productLabelRelService = productLabelRelService;
        this.productMappingRelService = productMappingRelService;
        this.productNoticeRelService = productNoticeRelService;
        this.productOptionColorRelService = productOptionColorRelService;
        this.productOptionDesignRelService = productOptionDesignRelService;
        this.productOptionPackageRelService = productOptionPackageRelService;
        this.productOptionRelService = productOptionRelService;
        this.productShippingRelService = productShippingRelService;
        this.productStoreRelService = productStoreRelService;
        this.productTemplateRelService = productTemplateRelService;
        this.productViewRelService = productViewRelService;
    }

    public void save(ProductExtendDTO productExtendDTO) {
        // 1. product entity save
        ProductDTO productDTO = super.save(productExtendDTO);
        productDTO.setActivated(true);
        long productId = productDTO.getId();
        // 2. productCategoryRel save
        Set<ProductCategoryRelDTO> productCategoryRelDTOSet = new HashSet<>();
        Set<ProductCategoryDTO> productCategoryDTOs = productExtendDTO.getProductCategories();
        if (!CollectionUtils.isEmpty(productCategoryDTOs)) {
            productCategoryDTOs
                .stream()
                .filter(Objects::nonNull)
                .forEach(
                    productCategoryDTO -> {
                        ProductCategoryRelDTO productCategoryRelDTO = new ProductCategoryRelDTO();
                        productCategoryRelDTO.setActivated(true);
                        productCategoryRelDTO.setProductId(productId);
                        productCategoryRelDTO.setProductCategoryId(productCategoryDTO.getId());
                        productCategoryRelDTOSet.add(productCategoryRelDTO);
                    }
                );
            productCategoryRelService.saveAll(productCategoryRelDTOSet);
        }
        // 3. productOptionRel save
        Set<ProductOptionRelDTO> productOptionRelDTOSet = new HashSet<>();
        Set<ProductOptionDTO> productOptionDTOs = productExtendDTO.getProductOptions();
        if (!CollectionUtils.isEmpty(productOptionDTOs)) {
            productOptionDTOs
                .stream()
                .filter(Objects::nonNull)
                .forEach(
                    productOptionDTO -> {
                        ProductOptionRelDTO productOptionRelDTO = new ProductOptionRelDTO();
                        productOptionRelDTO.setActivated(true);
                        productOptionRelDTO.setProductId(productId);
                        productOptionRelDTO.setProductOptionId(productOptionDTO.getId());
                        productOptionRelDTOSet.add(productOptionRelDTO);
                    }
                );
            productOptionRelService.saveAll(productOptionRelDTOSet);
        }
        // 4. productLabelRel save
        Set<ProductLabelRelDTO> productLabelRelDTOSet = new HashSet<>();
        Set<ProductLabelExtendDTO> productLabelDTOS = productExtendDTO.getProductLabels();
        if (!CollectionUtils.isEmpty(productLabelDTOS)) {
            productLabelDTOS
                .stream()
                .filter(Objects::nonNull)
                .forEach(
                    productLabelDTO -> {
                        ProductLabelRelDTO productLabelRelDTO = new ProductLabelRelDTO();
                        productLabelRelDTO.setActivated(true);
                        productLabelRelDTO.setIsDisplayDate(productLabelDTO.getDisplayDate());
                        productLabelRelDTO.setDisplayDateFrom(productLabelDTO.getDisplayDateFrom());
                        productLabelRelDTO.setDisplayDateTo(productLabelDTO.getDisplayDateTo());
                        productLabelRelDTO.setProductId(productId);
                        productLabelRelDTO.setProductLabelId(productLabelDTO.getId());
                        productLabelRelDTOSet.add(productLabelRelDTO);
                    }
                );
            productLabelRelService.saveAll(productLabelRelDTOSet);
        }
        // 5. productNotices save
        Set<ProductNoticeRelDTO> productNoticeRelDTOSet = new HashSet<>();
        Set<ProductNoticeDTO> productNoticeDTOS = productExtendDTO.getProductNotices();
        if (!CollectionUtils.isEmpty(productNoticeDTOS)) {
            productNoticeDTOS
                .stream()
                .filter(Objects::nonNull)
                .forEach(
                    productNoticeDTO -> {
                        ProductNoticeRelDTO productNoticeRelDTO = new ProductNoticeRelDTO();
                        productNoticeRelDTO.setActivated(true);
                        productNoticeRelDTO.setProductId(productId);
                        productNoticeRelDTO.setProductNoticeId(productNoticeDTO.getId());
                        productNoticeRelDTOSet.add(productNoticeRelDTO);
                    }
                );
            productNoticeRelService.saveAll(productNoticeRelDTOSet);
        }
        // 6. productShippings save
        Set<ProductShippingRelDTO> productShippingDTOSet = new HashSet<>();
        Set<ProductShippingDTO> productShippingDTOS = productExtendDTO.getProductShippings();
        if (!CollectionUtils.isEmpty(productShippingDTOS)) {
            productShippingDTOS
                .stream()
                .filter(Objects::nonNull)
                .forEach(
                    productShippingDTO -> {
                        ProductShippingRelDTO productShippingRelDTO = new ProductShippingRelDTO();
                        productShippingRelDTO.setActivated(true);
                        productShippingRelDTO.setProductId(productId);
                        productShippingRelDTO.setProductShippingId(productShippingDTO.getId());
                        productShippingDTOSet.add(productShippingRelDTO);
                    }
                );
            productShippingRelService.saveAll(productShippingDTOSet);
        }
        // 7. productTemplates save
        Set<ProductTemplateRelDTO> productTemplateRelDTOSet = new HashSet<>();
        Set<ProductTemplateDTO> productTemplateDTOS = productExtendDTO.getProductTemplates();
        if (!CollectionUtils.isEmpty(productTemplateDTOS)) {
            productTemplateDTOS
                .stream()
                .filter(Objects::nonNull)
                .forEach(
                    productTemplateDTO -> {
                        ProductTemplateRelDTO productTemplateRelDTO = new ProductTemplateRelDTO();
                        productTemplateRelDTO.setActivated(true);
                        productTemplateRelDTO.setProductId(productId);
                        productTemplateRelDTO.setProductTemplateId(productTemplateDTO.getId());
                        productTemplateRelDTOSet.add(productTemplateRelDTO);
                    }
                );
            productTemplateRelService.saveAll(productTemplateRelDTOSet);
        }
        // 8. productMappings save
        Set<ProductMappingRelDTO> productMappingRelDTOSet = new HashSet<>();
        Set<ProductMappingDTO> productMappingDTOS = productExtendDTO.getProductMappings();
        if (!CollectionUtils.isEmpty(productMappingDTOS)) {
            productMappingDTOS
                .stream()
                .filter(Objects::nonNull)
                .forEach(
                    productMappingDTO -> {
                        ProductMappingRelDTO productMappingRelDTO = new ProductMappingRelDTO();
                        productMappingRelDTO.setActivated(true);
                        productMappingRelDTO.setProductId(productId);
                        productMappingRelDTO.setProductMappingId(productMappingDTO.getId());
                        productMappingRelDTOSet.add(productMappingRelDTO);
                    }
                );
            productMappingRelService.saveAll(productMappingRelDTOSet);
        }
        // 9. stores save
        Set<ProductStoreRelDTO> productStoreRelDTOSet = new HashSet<>();
        Set<StoreExtendDTO> storeDTOS = productExtendDTO.getStores();
        if (!CollectionUtils.isEmpty(storeDTOS)) {
            storeDTOS
                .stream()
                .filter(Objects::nonNull)
                .forEach(
                    storeDTO -> {
                        ProductStoreRelDTO productStoreRelDTO = new ProductStoreRelDTO();
                        productStoreRelDTO.setActivated(true);
                        productStoreRelDTO.setUseCalculation(storeDTO.getProductUseCalculation());
                        productStoreRelDTO.setCalculation(storeDTO.getProductCalculation());
                        productStoreRelDTO.setCalculationDateFrom(storeDTO.getProductCalculationDateFrom());
                        productStoreRelDTO.setCalculationDateTo(storeDTO.getProductCalculationDateTo());
                        productStoreRelDTO.setProductId(productId);
                        productStoreRelDTO.setStoreId(storeDTO.getId());
                        productStoreRelDTOSet.add(productStoreRelDTO);
                    }
                );
            productStoreRelService.saveAll(productStoreRelDTOSet);
        }
        // 10. productViews save
        Set<ProductViewRelDTO> productViewRelDTOSet = new HashSet<>();
        Set<ProductViewDTO> productViewDTOS = productExtendDTO.getProductViews();
        if (!CollectionUtils.isEmpty(productViewDTOS)) {
            productViewDTOS
                .stream()
                .filter(Objects::nonNull)
                .forEach(
                    productViewDTO -> {
                        ProductViewRelDTO productViewRelDTO = new ProductViewRelDTO();
                        productViewRelDTO.setActivated(true);
                        productViewRelDTO.setProductId(productId);
                        productViewRelDTO.setProductViewId(productViewDTO.getId());
                        productViewRelDTOSet.add(productViewRelDTO);
                    }
                );
            productViewRelService.saveAll(productViewRelDTOSet);
        }
        // 11. clazzs save
        Set<ProductClazzRelDTO> productClazzRelDTOSet = new HashSet<>();
        Set<ClazzExtendDTO> clazzDTOS = productExtendDTO.getClazzs();
        if (!CollectionUtils.isEmpty(clazzDTOS)) {
            clazzDTOS
                .stream()
                .filter(Objects::nonNull)
                .forEach(
                    clazzDTO -> {
                        ProductClazzRelDTO productClazzRelDTO = new ProductClazzRelDTO();
                        productClazzRelDTO.setActivated(true);
                        productClazzRelDTO.setUseCalculation(clazzDTO.getProductUseCalculation());
                        productClazzRelDTO.setCalculation(clazzDTO.getProductCalculation());
                        productClazzRelDTO.setCalculationDateFrom(clazzDTO.getProductCalculationDateFrom());
                        productClazzRelDTO.setCalculationDateTo(clazzDTO.getProductCalculationDateTo());
                        productClazzRelDTO.setProductId(productId);
                        productClazzRelDTO.setClazzId(clazzDTO.getId());
                        productClazzRelDTOSet.add(productClazzRelDTO);
                    }
                );
            productClazzRelService.saveAll(productClazzRelDTOSet);
        }
    }
}
