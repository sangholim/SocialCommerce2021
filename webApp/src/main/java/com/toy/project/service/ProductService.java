package com.toy.project.service;

import com.toy.project.config.StreamUtils;
import com.toy.project.domain.*;
import com.toy.project.repository.ProductRepository;
import com.toy.project.service.dto.PackageDescriptionImageDTO;
import com.toy.project.service.dto.ProductDTO;
import com.toy.project.service.mapper.PackageDescriptionImageMapper;
import com.toy.project.service.mapper.PackageDescriptionMapper;
import com.toy.project.service.mapper.ProductMapper;
import com.toy.project.web.rest.vm.ProductVM;
import java.time.Instant;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Product}.
 */
@Service
@Transactional
public class ProductService {

    private final Logger log = LoggerFactory.getLogger(ProductService.class);

    private final ProductRepository productRepository;

    private final ProductMapper productMapper;

    private final PackageDescriptionMapper packageDescriptionMapper;

    private final PackageDescriptionImageMapper packageDescriptionImageMapper;

    private final FileService fileService;

    public ProductService(
        ProductRepository productRepository,
        ProductMapper productMapper,
        PackageDescriptionMapper packageDescriptionMapper,
        PackageDescriptionImageMapper packageDescriptionImageMapper,
        FileService fileService
    ) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.packageDescriptionMapper = packageDescriptionMapper;
        this.packageDescriptionImageMapper = packageDescriptionImageMapper;
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
        AtomicInteger atomicInteger = new AtomicInteger();
        String uniqueName = productVM.getCode();
        Long timeMillis = Instant.now().toEpochMilli();
        String fileName = timeMillis + "-main-image.jpg";
        // ${baseDir}/product/code/
        // 메인이미지,
        String mainImagePath = fileService.saveProductFile(uniqueName, fileName, productVM.getMainImageFile());
        productVM.setMainImageFileUrl(mainImagePath);
        // 메인 비디오 TODO 동영상 api 붙이기 (wistia 붙이고 싶긴함)
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
        productVM
            .getProductAddImageList()
            .forEach(
                productAddImageVM -> {
                    String imageFileName = timeMillis + "-add-image-" + atomicInteger.incrementAndGet() + "-.jpg";
                    String addFileName = fileService.saveProductFile(uniqueName, imageFileName, productAddImageVM.getImageFile());
                    productAddImageVM.setImageUrl(addFileName);
                    productAddImageVM.setActivated(true);
                }
            );
        // 상품 선택 옵션 패키지 썸네일을 저장
        atomicInteger.set(0);
        productVM
            .getProductOptionList()
            .forEach(
                productOptionVM -> {
                    String imageFileName = timeMillis + "-option-thumbnail-" + atomicInteger.incrementAndGet() + "-.jpg";
                    String packageThumbnail = fileService.saveProductFile(uniqueName, imageFileName, productOptionVM.getPackageThumbnail());
                    productOptionVM.setPackageThumbnailUrl(packageThumbnail);
                    productOptionVM.setActivated(true);
                }
            );

        CrudRepository crudRepository = productRepository;
        ProductDTO productDTO = productVM.toProductDto();
        Product product = productMapper.toEntity(productDTO);
        product.setActivated(true);
        // manyToOne Entity 저장
        ProductClazzAuthor productClazzAuthor = product.getProductClazzAuthor();
        productClazzAuthor.setActivated(true);
        crudRepository.save(productClazzAuthor);
        product.setProductClazzAuthorId(productClazzAuthor.getId());

        ProductStore productStore = product.getProductStore();
        productStore.setActivated(true);
        crudRepository.save(productStore);
        product.setProductStoreId(productStore.getId());
        product = productRepository.save(product);
        long productId = product.getId();
        // oneToMany
        // 상품 할인 저장
        product.setProductDiscounts(
            product
                .getProductDiscounts()
                .stream()
                .map(
                    productDiscount -> {
                        productDiscount.setProductId(productId);
                        productDiscount.setActivated(true);
                        return (ProductDiscount) crudRepository.save(productDiscount);
                    }
                )
                .collect(Collectors.toSet())
        );

        // 연관, 추천 상품 저장
        product.setProductMappings(
            product
                .getProductMappings()
                .stream()
                .map(
                    productMapping -> {
                        productMapping.setProductId(productId);
                        productMapping.setActivated(true);
                        return (ProductMapping) crudRepository.save(productMapping);
                    }
                )
                .collect(Collectors.toSet())
        );

        // 상품 선택 옵션 저장
        product.setProductOptions(
            product
                .getProductOptions()
                .stream()
                .map(
                    productOption -> {
                        productOption.setProductId(productId);
                        productOption.setActivated(true);
                        return (ProductOption) crudRepository.save(productOption);
                    }
                )
                .collect(Collectors.toSet())
        );

        // 상품 추가 옵션 저장
        product.setProductAddOptions(
            product
                .getProductAddOptions()
                .stream()
                .map(
                    productAddOption -> {
                        productAddOption.setProductId(productId);
                        productAddOption.setActivated(true);
                        return (ProductAddOption) crudRepository.save(productAddOption);
                    }
                )
                .collect(Collectors.toSet())
        );

        // 상품 직접 입력 옵션 저장
        product.setProductInputOptions(
            product
                .getProductInputOptions()
                .stream()
                .map(
                    productInputOption -> {
                        productInputOption.setProductId(productId);
                        productInputOption.setActivated(true);
                        return (ProductInputOption) crudRepository.save(productInputOption);
                    }
                )
                .collect(Collectors.toSet())
        );

        // 상품 자주 묻는 질문 저장
        product.setProductFaqs(
            product
                .getProductFaqs()
                .stream()
                .map(
                    productFaq -> {
                        productFaq.setProductId(productId);
                        productFaq.setActivated(true);
                        return (ProductFaq) crudRepository.save(productFaq);
                    }
                )
                .collect(Collectors.toSet())
        );

        // 상품 고시 저장
        product.setProductAnnounces(
            product
                .getProductAnnounces()
                .stream()
                .map(
                    productAnnounce -> {
                        productAnnounce.setProductId(productId);
                        productAnnounce.setActivated(true);
                        return (ProductAnnounce) crudRepository.save(productAnnounce);
                    }
                )
                .collect(Collectors.toSet())
        );

        // 상품 추가 이미지 저장
        product.setProductAddImages(
            product
                .getProductAddImages()
                .stream()
                .map(
                    productAddImage -> {
                        productAddImage.setProductId(productId);
                        productAddImage.setActivated(true);
                        return (ProductAddImage) crudRepository.save(productAddImage);
                    }
                )
                .collect(Collectors.toSet())
        );

        // 상품 라벨 저장
        product.setProductLabels(
            product
                .getProductLabels()
                .stream()
                .map(
                    productLabel -> {
                        productLabel.setProductId(productId);
                        productLabel.setActivated(true);
                        return (ProductLabel) crudRepository.save(productLabel);
                    }
                )
                .collect(Collectors.toSet())
        );

        // 상품 템플릿 저장
        product.setProductTemplates(
            product
                .getProductTemplates()
                .stream()
                .map(
                    productTemplate -> {
                        productTemplate.setProductId(productId);
                        productTemplate.setActivated(true);
                        return (ProductTemplate) crudRepository.save(productTemplate);
                    }
                )
                .collect(Collectors.toSet())
        );

        // 상품 카테고리 저장
        product.setProductCategories(
            product
                .getProductCategories()
                .stream()
                .map(
                    productCategory -> {
                        productCategory.setProductId(productId);
                        productCategory.setActivated(true);
                        return (ProductCategory) crudRepository.save(productCategory);
                    }
                )
                .collect(Collectors.toSet())
        );

        // 상품 패키지 상세 설명 저장
        product.setPackageDescriptions(
            productDTO
                .getPackageDescriptions()
                .stream()
                .map(
                    packageDescriptionDTO -> {
                        packageDescriptionDTO.setProductId(productId);
                        packageDescriptionDTO.setActivated(true);
                        PackageDescription packageDescription = packageDescriptionMapper.toEntity(packageDescriptionDTO);
                        packageDescription = (PackageDescription) crudRepository.save(packageDescription);
                        long packageDescriptionId = packageDescription.getId();
                        // 저장
                        atomicInteger.set(0);
                        packageDescription.setPackageDescriptionImages(
                            packageDescriptionDTO
                                .getImageFileList()
                                .stream()
                                .map(
                                    multipartFile -> {
                                        String packageDescriptionFileName =
                                            timeMillis +
                                            "-package-description-" +
                                            packageDescriptionId +
                                            "-" +
                                            atomicInteger.incrementAndGet() +
                                            ".jpg";
                                        String addFileName = fileService.saveProductFile(
                                            uniqueName,
                                            packageDescriptionFileName,
                                            multipartFile
                                        );
                                        PackageDescriptionImageDTO packageDescriptionImageDTO = new PackageDescriptionImageDTO();
                                        packageDescriptionImageDTO.setImageUrl(addFileName);
                                        packageDescriptionImageDTO.setActivated(true);
                                        packageDescriptionImageDTO.setPackageDescriptionId(packageDescriptionId);
                                        return (PackageDescriptionImage) crudRepository.save(
                                            packageDescriptionImageMapper.toEntity(packageDescriptionImageDTO)
                                        );
                                    }
                                )
                                .collect(Collectors.toSet())
                        );
                        return packageDescription;
                    }
                )
                .collect(Collectors.toSet())
        );
        return productMapper.toDto(product);
    }

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

    @Transactional(readOnly = true)
    public Page<ProductDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Products");
        return productRepository.findAll(pageable).map(productMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Optional<ProductDTO> findOne(Long id) {
        log.debug("Request to get Product : {}", id);
        return productRepository.findById(id).map(productMapper::toDto);
    }

    public void delete(Long id) {
        log.debug("Request to delete Product : {}", id);
        productRepository.deleteById(id);
    }
}
