package com.toy.project.service;

import com.toy.project.config.ApplicationProperties;
import com.toy.project.domain.*;
import com.toy.project.repository.ProductRepository;
import com.toy.project.service.dto.ProductDTO;
import com.toy.project.service.mapper.ProductMapper;
import com.toy.project.web.rest.vm.ProductAddImageVM;
import com.toy.project.web.rest.vm.ProductOptionVM;
import com.toy.project.web.rest.vm.ProductVM;
import io.swagger.annotations.ApiModelProperty;
import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

/**
 * Service Implementation for managing {@link Product}.
 */
@Service
@Transactional
public class ProductService {

    private final Logger log = LoggerFactory.getLogger(ProductService.class);

    private final ProductRepository productRepository;

    private final ProductMapper productMapper;

    private final FileService fileService;

    public ProductService(ProductRepository productRepository, ProductMapper productMapper, FileService fileService) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.fileService = fileService;
    }

    public ProductDTO save(ProductDTO productDTO) {
        Product product = productMapper.toEntity(productDTO);
        product = productRepository.save(product);
        return productMapper.toDto(product);
    }

    public ProductDTO save(ProductVM productVM) {
        log.debug("Request to save Product : {}", productVM);
        // ProductVM -> ProductDto 만들기
        String uniqueName = productVM.getCode();
        Long timeMillis = Instant.now().toEpochMilli();
        String fileName = timeMillis + "-main-image.jpg";
        // ${baseDir}/product/code/
        // 메인이미지,
        String mainImagePath = fileService.saveProductFile(uniqueName, fileName, productVM.getMainImageFile());
        productVM.setMainImageFileUrl(mainImagePath);
        // 메인 비디오 TODO 동영상 api 붙이기
        fileName = timeMillis + "-main-video.jpg";
        String mainVideoPath = fileService.saveProductFile(uniqueName, fileName, productVM.getMainVideoFile());
        productVM.setMainVideoFileUrl(mainVideoPath);
        // 상세설명,
        fileName = timeMillis + "-description.jpg";
        String descriptionFilePath = fileService.saveProductFile(uniqueName, fileName, productVM.getDescriptionFile());
        productVM.setDescriptionFileUrl(descriptionFilePath);
        // 교환/환불/안내 파일 저장후 파일경로 설정
        fileName = timeMillis + "-exchange-shipping.jpg";
        String exchangeShippingFilePath = fileService.saveProductFile(uniqueName, fileName, productVM.getExchangeShippingFile());
        productVM.setExchangeShippingFileUrl(exchangeShippingFilePath);
        // 상품 추가 이미지
        List<ProductAddImageVM> productAddImageList = productVM.getProductAddImageList();
        if (!CollectionUtils.isEmpty(productAddImageList)) {
            for (int i = 0; i < productAddImageList.size(); i++) {
                fileName = timeMillis + "-add-image-" + i + "-.jpg";
                ProductAddImageVM productAddImage = productAddImageList.get(i);
                String addFileName = fileService.saveProductFile(uniqueName, fileName, productAddImage.getImageFile());
                productAddImage.setImageUrl(addFileName);
                productAddImage.setActivated(true);
            }
        }
        // 상품 선택 옵션 패키지 썸네일
        List<ProductOptionVM> productOptionList = productVM.getProductOptionList();
        if (!CollectionUtils.isEmpty(productOptionList)) {
            for (int i = 0; i < productOptionList.size(); i++) {
                ProductOptionVM productOptionVM = productOptionList.get(i);
                fileName = timeMillis + "-option-thumbnail-" + i + "-.jpg";
                String packageThumbnail = fileService.saveProductFile(uniqueName, fileName, productOptionVM.getPackageThumbnail());
                productOptionVM.setPackageThumbnailUrl(packageThumbnail);
                productOptionVM.setActivated(true);
            }
        }

        ProductDTO productDTO = productVM.toProductDto();
        Product product = productMapper.toEntity(productDTO);
        product.setActivated(true);
        // manyToOne Entity 저장
        ProductClazzAuthor productClazzAuthor = product.getProductClazzAuthor();
        productClazzAuthor.setActivated(true);
        ((CrudRepository) productRepository).save(productClazzAuthor);
        product.setProductClazzAuthorId(productClazzAuthor.getId());

        ProductStore productStore = product.getProductStore();
        productStore.setActivated(true);
        ((CrudRepository) productRepository).save(productStore);
        product.setProductStoreId(productStore.getId());
        product = productRepository.save(product);
        long productId = product.getId();
        // oneToMany 저장
        if (!CollectionUtils.isEmpty(product.getProductDiscounts())) {
            for (ProductDiscount productDiscount : product.getProductDiscounts()) {
                productDiscount.setProductId(productId);
                productDiscount.setActivated(true);
            }
            ((CrudRepository) productRepository).saveAll(product.getProductDiscounts());
        }

        if (!CollectionUtils.isEmpty(product.getProductMappings())) {
            for (ProductMapping productMapping : product.getProductMappings()) {
                productMapping.setProductId(productId);
                productMapping.setActivated(true);
            }
            ((CrudRepository) productRepository).saveAll(product.getProductMappings());
        }

        if (!CollectionUtils.isEmpty(product.getProductOptions())) {
            for (ProductOption productOption : product.getProductOptions()) {
                productOption.setProductId(productId);
                productOption.setActivated(true);
            }
            ((CrudRepository) productRepository).saveAll(product.getProductOptions());
        }

        if (!CollectionUtils.isEmpty(product.getProductAddOptions())) {
            for (ProductAddOption productAddOption : product.getProductAddOptions()) {
                productAddOption.setProductId(productId);
                productAddOption.setActivated(true);
            }
            ((CrudRepository) productRepository).saveAll(product.getProductAddOptions());
        }

        if (!CollectionUtils.isEmpty(product.getProductInputOptions())) {
            for (ProductInputOption productInputOption : product.getProductInputOptions()) {
                productInputOption.setProductId(productId);
                productInputOption.setActivated(true);
            }
            ((CrudRepository) productRepository).saveAll(product.getProductInputOptions());
        }

        if (!CollectionUtils.isEmpty(product.getProductFaqs())) {
            for (ProductFaq productFaq : product.getProductFaqs()) {
                productFaq.setProductId(productId);
                productFaq.setActivated(true);
            }
            ((CrudRepository) productRepository).saveAll(product.getProductFaqs());
        }

        if (!CollectionUtils.isEmpty(product.getProductAnnounces())) {
            for (ProductAnnounce productAnnounce : product.getProductAnnounces()) {
                productAnnounce.setProductId(productId);
                productAnnounce.setActivated(true);
            }
            ((CrudRepository) productRepository).saveAll(product.getProductAnnounces());
        }

        if (!CollectionUtils.isEmpty(product.getProductAddImages())) {
            for (ProductAddImage productAddImage : product.getProductAddImages()) {
                productAddImage.setProductId(productId);
                productAddImage.setActivated(true);
            }
            ((CrudRepository) productRepository).saveAll(product.getProductAddImages());
        }

        if (!CollectionUtils.isEmpty(product.getProductLabels())) {
            for (ProductLabel productLabel : product.getProductLabels()) {
                productLabel.setProductId(productId);
                productLabel.setActivated(true);
            }
            ((CrudRepository) productRepository).saveAll(product.getProductLabels());
        }

        if (!CollectionUtils.isEmpty(product.getProductTemplates())) {
            for (ProductTemplate productTemplate : product.getProductTemplates()) {
                productTemplate.setProductId(productId);
                productTemplate.setActivated(true);
            }
            ((CrudRepository) productRepository).saveAll(product.getProductTemplates());
        }

        if (!CollectionUtils.isEmpty(product.getProductCategories())) {
            for (ProductCategory productCategory : product.getProductCategories()) {
                productCategory.setProductId(productId);
                productCategory.setActivated(true);
            }
            ((CrudRepository) productRepository).saveAll(product.getProductCategories());
        }
        product = productRepository.save(product);
        return productMapper.toDto(product);
    }

    /**
     * Partially update a product.
     *
     * @param productDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ProductDTO> partialUpdate(ProductDTO productDTO) {
        log.debug("Request to partially update Product : {}", productDTO);

        return productRepository
            .findById(productDTO.getId())
            .map(
                existingProduct -> {
                    productMapper.partialUpdate(existingProduct, productDTO);
                    return existingProduct;
                }
            )
            .map(productRepository::save)
            .map(productMapper::toDto);
    }

    /**
     * Get all the products.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ProductDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Products");
        return productRepository.findAll(pageable).map(productMapper::toDto);
    }

    /**
     * Get one product by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ProductDTO> findOne(Long id) {
        log.debug("Request to get Product : {}", id);
        return productRepository.findById(id).map(productMapper::toDto);
    }

    /**
     * Delete the product by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Product : {}", id);
        productRepository.deleteById(id);
    }
}
