package com.toy.project.service;

import com.toy.project.config.ApplicationProperties;
import com.toy.project.repository.ProductRepository;
import com.toy.project.service.dto.*;
import com.toy.project.service.mapper.ProductMapper;
import com.toy.project.web.rest.ProductResource;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import java.util.UUID;
import javax.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ProductExtendService extends ProductService {

    private final ProductCategoryRelService productCategoryRelService;

    private final ProductClazzRelService productClazzRelService;

    private final ProductLabelRelService productLabelRelService;

    private final ProductMappingRelService productMappingRelService;

    private final ProductNoticeRelService productNoticeRelService;

    private final ProductOptionRelService productOptionRelService;

    private final ProductShippingRelService productShippingRelService;

    private final ProductStoreRelService productStoreRelService;

    private final ProductTemplateRelService productTemplateRelService;

    private final ProductViewRelService productViewRelService;

    private final ApplicationProperties applicationProperties;

    public static String BASE_PATH = "";

    public ProductExtendService(
        ApplicationProperties applicationProperties,
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
        this.applicationProperties = applicationProperties;
        this.productCategoryRelService = productCategoryRelService;
        this.productClazzRelService = productClazzRelService;
        this.productLabelRelService = productLabelRelService;
        this.productMappingRelService = productMappingRelService;
        this.productNoticeRelService = productNoticeRelService;
        this.productOptionRelService = productOptionRelService;
        this.productShippingRelService = productShippingRelService;
        this.productStoreRelService = productStoreRelService;
        this.productTemplateRelService = productTemplateRelService;
        this.productViewRelService = productViewRelService;
    }

    @PostConstruct
    public void init() throws Exception {
        BASE_PATH = applicationProperties.getResourceDir() + File.separator + ProductResource.ENTITY_NAME;
        // product 폴더 생성
        Path dirPath = Paths.get(BASE_PATH);
        if (!Files.exists(dirPath)) {
            Files.createDirectory(dirPath);
        }
    }

    private boolean save(MultipartFile multipartFile, String dir, String name) {
        File file = new File(dir + File.separator + name);
        try (InputStream is = multipartFile.getInputStream();) {
            Path dirPath = Paths.get(dir);
            if (!Files.exists(dirPath)) {
                Files.createDirectory(dirPath);
            }
            Files.copy(is, file.toPath());
        } catch (Exception e) {
            file.delete();
            e.printStackTrace();
            throw new RuntimeException("fail to upload file. " + e.getMessage());
        }
        return true;
    }

    public ProductDTO save(ProductExtendDTO productExtendDTO) {
        // 파일 저장후, URL 전체 대시 파일 이름만 저장
        // String basePath = applicationProperties.getResourceDir() + File.separator + ProductResource.ENTITY_NAME + File.separator;
        String name = productExtendDTO.getName() + "-" + productExtendDTO.getCode();
        String identifier = UUID.randomUUID().toString();
        String basePath = BASE_PATH + File.separator + identifier;
        // 메인 이미지 파일 저장
        if (save(productExtendDTO.getMainImageFile(), basePath, name + "-main-image")) {
            productExtendDTO.setMainImageFileUrl(identifier + File.separator + name + "-main-image");
        }
        // 추가 이미지 파일 저장
        if (save(productExtendDTO.getAddImageFile(), basePath, name + "-add-image")) {
            productExtendDTO.setAddImageFileUrl(identifier + File.separator + name + "-add-image");
        }
        // 메인 비디오 파일 저장
        if (save(productExtendDTO.getMainVideoFile(), basePath, name + "-main-video")) {
            productExtendDTO.setMainVideoFileUrl(identifier + File.separator + name + "-main-video");
        }
        // 상품 설명 파일 저장
        if (save(productExtendDTO.getDescriptionFile(), basePath, name + "-description")) {
            productExtendDTO.setDescriptionFileUrl(identifier + File.separator + name + "-description");
        }
        // 교환, 환불, 배송 파일 저장
        if (save(productExtendDTO.getExchangeShippingFile(), basePath, name + "-exchange-shipping")) {
            productExtendDTO.setExchangeShippingFileUrl(identifier + File.separator + name + "-exchange-shipping");
        }
        // 1. product entity save
        ProductDTO productDTO = super.save(productExtendDTO);
        productDTO.setActivated(true);
        long productId = productDTO.getId();
        // 2. productCategoryRel save
        Set<ProductCategoryRelDTO> productCategoryRelDTOSet = ProductCategoryRelDTO.toSet(
            productId,
            true,
            productExtendDTO.getProductCategories()
        );
        productCategoryRelService.saveAll(productCategoryRelDTOSet);
        // 3. productOptionRel save
        Set<ProductOptionRelDTO> productOptionRelDTOSet = ProductOptionRelDTO.toSet(productId, true, productExtendDTO.getProductOptions());
        productOptionRelService.saveAll(productOptionRelDTOSet);
        // 4. productLabelRel save
        Set<ProductLabelRelDTO> productLabelRelDTOSet = ProductLabelRelDTO.toSet(productId, true, productExtendDTO.getProductLabels());
        productLabelRelService.saveAll(productLabelRelDTOSet);
        // 5. productNotices save
        Set<ProductNoticeRelDTO> productNoticeRelDTOSet = ProductNoticeRelDTO.toSet(productId, true, productExtendDTO.getProductNotices());
        productNoticeRelService.saveAll(productNoticeRelDTOSet);
        // 6. productShippings save
        Set<ProductShippingRelDTO> productShippingDTOSet = ProductShippingRelDTO.toSet(
            productId,
            true,
            productExtendDTO.getProductShippings()
        );
        productShippingRelService.saveAll(productShippingDTOSet);
        // 7. productTemplates save
        Set<ProductTemplateRelDTO> productTemplateRelDTOSet = ProductTemplateRelDTO.toSet(
            productId,
            true,
            productExtendDTO.getProductTemplates()
        );
        productTemplateRelService.saveAll(productTemplateRelDTOSet);
        // 8. productMappings save
        Set<ProductMappingRelDTO> productMappingRelDTOSet = ProductMappingRelDTO.toSet(
            productId,
            true,
            productExtendDTO.getProductMappings()
        );
        productMappingRelService.saveAll(productMappingRelDTOSet);
        // 9. stores save
        Set<ProductStoreRelDTO> productStoreRelDTOSet = ProductStoreRelDTO.toSet(productId, true, productExtendDTO.getStores());
        productStoreRelService.saveAll(productStoreRelDTOSet);
        // 10. productViews save
        Set<ProductViewRelDTO> productViewRelDTOSet = ProductViewRelDTO.toSet(productId, true, productExtendDTO.getProductViews());
        productViewRelService.saveAll(productViewRelDTOSet);
        // 11. clazzs save
        Set<ProductClazzRelDTO> productClazzRelDTOSet = ProductClazzRelDTO.toSet(productId, true, productExtendDTO.getClazzs());
        productClazzRelService.saveAll(productClazzRelDTOSet);

        return productDTO;
    }
}
