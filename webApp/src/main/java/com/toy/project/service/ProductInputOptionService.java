package com.toy.project.service;

import com.toy.project.domain.ProductInputOption;
import com.toy.project.domain.ProductOption;
import com.toy.project.repository.ProductInputOptionRepository;
import com.toy.project.service.dto.ProductInputOptionDTO;
import com.toy.project.service.dto.ProductOptionDTO;
import com.toy.project.service.mapper.ProductInputOptionMapper;
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
 * Service Implementation for managing {@link ProductInputOption}.
 */
@Service
@Transactional
public class ProductInputOptionService {

    private final Logger log = LoggerFactory.getLogger(ProductInputOptionService.class);

    private final ProductInputOptionRepository productInputOptionRepository;

    private final ProductInputOptionMapper productInputOptionMapper;

    public ProductInputOptionService(
        ProductInputOptionRepository productInputOptionRepository,
        ProductInputOptionMapper productInputOptionMapper
    ) {
        this.productInputOptionRepository = productInputOptionRepository;
        this.productInputOptionMapper = productInputOptionMapper;
    }

    public ProductInputOptionDTO save(ProductInputOptionDTO productInputOptionDTO) {
        log.debug("Request to save ProductInputOption : {}", productInputOptionDTO);
        ProductInputOption productInputOption = productInputOptionMapper.toEntity(productInputOptionDTO);
        productInputOption = productInputOptionRepository.save(productInputOption);
        return productInputOptionMapper.toDto(productInputOption);
    }

    public Optional<ProductInputOptionDTO> partialUpdate(ProductInputOptionDTO productInputOptionDTO) {
        log.debug("Request to partially update ProductInputOption : {}", productInputOptionDTO);

        return productInputOptionRepository
            .findById(productInputOptionDTO.getId())
            .map(
                existingProductInputOption -> {
                    productInputOptionMapper.partialUpdate(existingProductInputOption, productInputOptionDTO);
                    return existingProductInputOption;
                }
            )
            .map(productInputOptionRepository::save)
            .map(productInputOptionMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Page<ProductInputOptionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ProductInputOptions");
        return productInputOptionRepository.findAll(pageable).map(productInputOptionMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Optional<ProductInputOptionDTO> findOne(Long id) {
        log.debug("Request to get ProductInputOption : {}", id);
        return productInputOptionRepository.findById(id).map(productInputOptionMapper::toDto);
    }

    public void delete(Long id) {
        log.debug("Request to delete ProductInputOption : {}", id);
        productInputOptionRepository.deleteById(id);
    }
}
