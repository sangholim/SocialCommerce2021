package com.toy.project.service;

import com.toy.project.domain.ProductAddOption;
import com.toy.project.domain.ProductOption;
import com.toy.project.repository.ProductAddOptionRepository;
import com.toy.project.service.dto.ProductAddOptionDTO;
import com.toy.project.service.dto.ProductOptionDTO;
import com.toy.project.service.mapper.ProductAddOptionMapper;
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
 * Service Implementation for managing {@link ProductAddOption}.
 */
@Service
@Transactional
public class ProductAddOptionService {

    private final Logger log = LoggerFactory.getLogger(ProductAddOptionService.class);

    private final ProductAddOptionRepository productAddOptionRepository;

    private final ProductAddOptionMapper productAddOptionMapper;

    public ProductAddOptionService(ProductAddOptionRepository productAddOptionRepository, ProductAddOptionMapper productAddOptionMapper) {
        this.productAddOptionRepository = productAddOptionRepository;
        this.productAddOptionMapper = productAddOptionMapper;
    }

    public ProductAddOptionDTO save(ProductAddOptionDTO productAddOptionDTO) {
        log.debug("Request to save ProductAddOption : {}", productAddOptionDTO);
        ProductAddOption productAddOption = productAddOptionMapper.toEntity(productAddOptionDTO);
        productAddOption = productAddOptionRepository.save(productAddOption);
        return productAddOptionMapper.toDto(productAddOption);
    }

    public Optional<ProductAddOptionDTO> partialUpdate(ProductAddOptionDTO productAddOptionDTO) {
        log.debug("Request to partially update ProductAddOption : {}", productAddOptionDTO);

        return productAddOptionRepository
            .findById(productAddOptionDTO.getId())
            .map(
                existingProductAddOption -> {
                    productAddOptionMapper.partialUpdate(existingProductAddOption, productAddOptionDTO);
                    return existingProductAddOption;
                }
            )
            .map(productAddOptionRepository::save)
            .map(productAddOptionMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Page<ProductAddOptionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ProductAddOptions");
        return productAddOptionRepository.findAll(pageable).map(productAddOptionMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Optional<ProductAddOptionDTO> findOne(Long id) {
        log.debug("Request to get ProductAddOption : {}", id);
        return productAddOptionRepository.findById(id).map(productAddOptionMapper::toDto);
    }

    public void delete(Long id) {
        log.debug("Request to delete ProductAddOption : {}", id);
        productAddOptionRepository.deleteById(id);
    }
}
