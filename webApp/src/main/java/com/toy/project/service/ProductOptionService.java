package com.toy.project.service;

import com.toy.project.domain.ProductMapping;
import com.toy.project.domain.ProductOption;
import com.toy.project.repository.ProductOptionRepository;
import com.toy.project.service.dto.ProductMappingDTO;
import com.toy.project.service.dto.ProductOptionDTO;
import com.toy.project.service.mapper.ProductOptionMapper;
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
 * Service Implementation for managing {@link ProductOption}.
 */
@Service
@Transactional
public class ProductOptionService {

    private final Logger log = LoggerFactory.getLogger(ProductOptionService.class);

    private final ProductOptionRepository productOptionRepository;

    private final ProductOptionMapper productOptionMapper;

    public ProductOptionService(ProductOptionRepository productOptionRepository, ProductOptionMapper productOptionMapper) {
        this.productOptionRepository = productOptionRepository;
        this.productOptionMapper = productOptionMapper;
    }

    public ProductOptionDTO save(ProductOptionDTO productOptionDTO) {
        log.debug("Request to save ProductOption : {}", productOptionDTO);
        ProductOption productOption = productOptionMapper.toEntity(productOptionDTO);
        productOption = productOptionRepository.save(productOption);
        return productOptionMapper.toDto(productOption);
    }

    public Optional<ProductOptionDTO> partialUpdate(ProductOptionDTO productOptionDTO) {
        log.debug("Request to partially update ProductOption : {}", productOptionDTO);

        return productOptionRepository
            .findById(productOptionDTO.getId())
            .map(
                existingProductOption -> {
                    productOptionMapper.partialUpdate(existingProductOption, productOptionDTO);
                    return existingProductOption;
                }
            )
            .map(productOptionRepository::save)
            .map(productOptionMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Page<ProductOptionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ProductOptions");
        return productOptionRepository.findAll(pageable).map(productOptionMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Optional<ProductOptionDTO> findOne(Long id) {
        log.debug("Request to get ProductOption : {}", id);
        return productOptionRepository.findById(id).map(productOptionMapper::toDto);
    }

    public void delete(Long id) {
        log.debug("Request to delete ProductOption : {}", id);
        productOptionRepository.deleteById(id);
    }
}
