package com.toy.project.service;

import com.toy.project.domain.ProductDiscount;
import com.toy.project.domain.ProductMapping;
import com.toy.project.repository.ProductMappingRepository;
import com.toy.project.service.dto.ProductDTO;
import com.toy.project.service.dto.ProductDiscountDTO;
import com.toy.project.service.dto.ProductMappingDTO;
import com.toy.project.service.dto.ProductMappingManageDTO;
import com.toy.project.service.mapper.ProductMappingMapper;
import java.time.Instant;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.constraints.Size;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

/**
 * Service Implementation for managing {@link ProductMapping}.
 */
@Service
@Transactional
public class ProductMappingService {

    private final Logger log = LoggerFactory.getLogger(ProductMappingService.class);

    private final ProductMappingRepository productMappingRepository;

    private final ProductMappingMapper productMappingMapper;

    public ProductMappingService(ProductMappingRepository productMappingRepository, ProductMappingMapper productMappingMapper) {
        this.productMappingRepository = productMappingRepository;
        this.productMappingMapper = productMappingMapper;
    }

    public ProductMappingDTO save(ProductMappingDTO productMappingDTO) {
        log.debug("Request to save ProductMapping : {}", productMappingDTO);
        ProductMapping productMapping = productMappingMapper.toEntity(productMappingDTO);
        productMapping = productMappingRepository.save(productMapping);
        return productMappingMapper.toDto(productMapping);
    }

    public Optional<ProductMappingDTO> partialUpdate(ProductMappingDTO productMappingDTO) {
        log.debug("Request to partially update ProductMapping : {}", productMappingDTO);

        return productMappingRepository
            .findById(productMappingDTO.getId())
            .map(
                existingProductMapping -> {
                    productMappingMapper.partialUpdate(existingProductMapping, productMappingDTO);
                    return existingProductMapping;
                }
            )
            .map(productMappingRepository::save)
            .map(productMappingMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Page<ProductMappingDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ProductMappings");
        return productMappingRepository.findAll(pageable).map(productMappingMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Optional<ProductMappingDTO> findOne(Long id) {
        log.debug("Request to get ProductMapping : {}", id);
        return productMappingRepository.findById(id).map(productMappingMapper::toDto);
    }

    public void delete(Long id) {
        log.debug("Request to delete ProductMapping : {}", id);
        productMappingRepository.deleteById(id);
    }
}
