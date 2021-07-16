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
        Set<ProductCategoryRelDTO> productCategoryRelDTOSet = productCategoryRelService.toProductCategoryRelDTOSet(
            productId,
            true,
            productExtendDTO.getProductCategories()
        );
        productCategoryRelService.saveAll(productCategoryRelDTOSet);
        // 3. productOptionRel save
        Set<ProductOptionRelDTO> productOptionRelDTOSet = productOptionRelService.toProductOptionRelDTOSet(
            productId,
            true,
            productExtendDTO.getProductOptions()
        );
        productOptionRelService.saveAll(productOptionRelDTOSet);
        // 4. productLabelRel save
        Set<ProductLabelRelDTO> productLabelRelDTOSet = productLabelRelService.toProductLabelRelDTOSet(
            productId,
            true,
            productExtendDTO.getProductLabels()
        );
        productLabelRelService.saveAll(productLabelRelDTOSet);
        // 5. productNotices save
        Set<ProductNoticeRelDTO> productNoticeRelDTOSet = productNoticeRelService.toProductNoticeRelDTOS(
            productId,
            true,
            productExtendDTO.getProductNotices()
        );
        productNoticeRelService.saveAll(productNoticeRelDTOSet);
        // 6. productShippings save
        Set<ProductShippingRelDTO> productShippingDTOSet = productShippingRelService.toProductShippingRelDTOSet(
            productId,
            true,
            productExtendDTO.getProductShippings()
        );
        productShippingRelService.saveAll(productShippingDTOSet);
        // 7. productTemplates save
        Set<ProductTemplateRelDTO> productTemplateRelDTOSet = productTemplateRelService.toProductTemplateRelDTOSet(
            productId,
            true,
            productExtendDTO.getProductTemplates()
        );
        productTemplateRelService.saveAll(productTemplateRelDTOSet);
        // 8. productMappings save
        Set<ProductMappingRelDTO> productMappingRelDTOSet = productMappingRelService.toProductMappingRelDTOSet(
            productId,
            true,
            productExtendDTO.getProductMappings()
        );
        productMappingRelService.saveAll(productMappingRelDTOSet);
        // 9. stores save
        Set<ProductStoreRelDTO> productStoreRelDTOSet = productStoreRelService.toProductStoreRelDTOSet(
            productId,
            true,
            productExtendDTO.getStores()
        );
        productStoreRelService.saveAll(productStoreRelDTOSet);
        // 10. productViews save
        Set<ProductViewRelDTO> productViewRelDTOSet = productViewRelService.toProductViewRelDTOSet(
            productId,
            true,
            productExtendDTO.getProductViews()
        );
        productViewRelService.saveAll(productViewRelDTOSet);
        // 11. clazzs save
        Set<ProductClazzRelDTO> productClazzRelDTOSet = productClazzRelService.toProductClazzRelDTOSet(
            productId,
            true,
            productExtendDTO.getClazzs()
        );
        productClazzRelService.saveAll(productClazzRelDTOSet);
    }
}
