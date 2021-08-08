package com.toy.project.service;

import com.toy.project.domain.ProductAddImage;
import com.toy.project.domain.ProductAnnounce;
import com.toy.project.repository.ProductAddImageRepository;
import com.toy.project.service.dto.ProductAddImageDTO;
import com.toy.project.service.dto.ProductAnnounceDTO;
import com.toy.project.service.mapper.ProductAddImageMapper;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

/**
 * Service Implementation for managing {@link ProductAddImage}.
 */
@Service
@Transactional
public class ProductAddImageService {

    private final Logger log = LoggerFactory.getLogger(ProductAddImageService.class);

    private final ProductAddImageRepository productAddImageRepository;

    private final ProductAddImageMapper productAddImageMapper;

    public ProductAddImageService(ProductAddImageRepository productAddImageRepository, ProductAddImageMapper productAddImageMapper) {
        this.productAddImageRepository = productAddImageRepository;
        this.productAddImageMapper = productAddImageMapper;
    }

    public static Set<ProductAddImage> toEntitySet(Collection<ProductAddImageDTO> productAddImageDTOs, Boolean activated) {
        if (CollectionUtils.isEmpty(productAddImageDTOs)) {
            return null;
        }
        return productAddImageDTOs.stream().map(productAddImageDTO -> toEntity(productAddImageDTO, activated)).collect(Collectors.toSet());
    }

    public static Set<ProductAddImage> toEntitySet(Collection<ProductAddImageDTO> productAddImageDTOs) {
        if (CollectionUtils.isEmpty(productAddImageDTOs)) {
            return null;
        }
        return productAddImageDTOs.stream().map(productAddImageDTO -> toEntity(productAddImageDTO)).collect(Collectors.toSet());
    }

    public static ProductAddImage toEntity(ProductAddImageDTO productAddImageDTO, Boolean activated) {
        ProductAddImage productAddImage = toEntity(productAddImageDTO);
        productAddImage.setActivated(activated);
        return productAddImage;
    }

    public static ProductAddImage toEntity(ProductAddImageDTO productAddImageDTO) {
        ProductAddImage productAddImage = new ProductAddImage();
        productAddImage.setImageUrl(productAddImageDTO.getImageUrl());
        productAddImage.setProductId(productAddImageDTO.getProductId());
        productAddImage.setActivated(productAddImageDTO.getActivated());
        productAddImage.setLastModifiedDate(productAddImageDTO.getLastModifiedDate());
        productAddImage.setLastModifiedBy(productAddImageDTO.getLastModifiedBy());
        productAddImage.setCreatedDate(productAddImageDTO.getCreatedDate());
        productAddImage.setCreatedBy(productAddImageDTO.getCreatedBy());
        return productAddImage;
    }

    public ProductAddImageDTO save(ProductAddImageDTO productAddImageDTO) {
        log.debug("Request to save ProductAddImage : {}", productAddImageDTO);
        ProductAddImage productAddImage = productAddImageMapper.toEntity(productAddImageDTO);
        productAddImage = productAddImageRepository.save(productAddImage);
        return productAddImageMapper.toDto(productAddImage);
    }

    public Optional<ProductAddImageDTO> partialUpdate(ProductAddImageDTO productAddImageDTO) {
        log.debug("Request to partially update ProductAddImage : {}", productAddImageDTO);

        return productAddImageRepository
            .findById(productAddImageDTO.getId())
            .map(
                existingProductAddImage -> {
                    productAddImageMapper.partialUpdate(existingProductAddImage, productAddImageDTO);
                    return existingProductAddImage;
                }
            )
            .map(productAddImageRepository::save)
            .map(productAddImageMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Page<ProductAddImageDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ProductAddImages");
        return productAddImageRepository.findAll(pageable).map(productAddImageMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Optional<ProductAddImageDTO> findOne(Long id) {
        log.debug("Request to get ProductAddImage : {}", id);
        return productAddImageRepository.findById(id).map(productAddImageMapper::toDto);
    }

    public void delete(Long id) {
        log.debug("Request to delete ProductAddImage : {}", id);
        productAddImageRepository.deleteById(id);
    }
}
