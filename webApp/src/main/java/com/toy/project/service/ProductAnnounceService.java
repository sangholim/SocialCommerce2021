package com.toy.project.service;

import com.toy.project.domain.ProductAddOption;
import com.toy.project.domain.ProductAnnounce;
import com.toy.project.repository.ProductAnnounceRepository;
import com.toy.project.service.dto.ProductAddOptionDTO;
import com.toy.project.service.dto.ProductAnnounceDTO;
import com.toy.project.service.mapper.ProductAnnounceMapper;
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
 * Service Implementation for managing {@link ProductAnnounce}.
 */
@Service
@Transactional
public class ProductAnnounceService {

    private final Logger log = LoggerFactory.getLogger(ProductAnnounceService.class);

    private final ProductAnnounceRepository productAnnounceRepository;

    private final ProductAnnounceMapper productAnnounceMapper;

    public ProductAnnounceService(ProductAnnounceRepository productAnnounceRepository, ProductAnnounceMapper productAnnounceMapper) {
        this.productAnnounceRepository = productAnnounceRepository;
        this.productAnnounceMapper = productAnnounceMapper;
    }

    public ProductAnnounceDTO save(ProductAnnounceDTO productAnnounceDTO) {
        log.debug("Request to save ProductAnnounce : {}", productAnnounceDTO);
        ProductAnnounce productAnnounce = productAnnounceMapper.toEntity(productAnnounceDTO);
        productAnnounce = productAnnounceRepository.save(productAnnounce);
        return productAnnounceMapper.toDto(productAnnounce);
    }

    public Optional<ProductAnnounceDTO> partialUpdate(ProductAnnounceDTO productAnnounceDTO) {
        log.debug("Request to partially update ProductAnnounce : {}", productAnnounceDTO);

        return productAnnounceRepository
            .findById(productAnnounceDTO.getId())
            .map(
                existingProductAnnounce -> {
                    productAnnounceMapper.partialUpdate(existingProductAnnounce, productAnnounceDTO);
                    return existingProductAnnounce;
                }
            )
            .map(productAnnounceRepository::save)
            .map(productAnnounceMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Page<ProductAnnounceDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ProductAnnounces");
        return productAnnounceRepository.findAll(pageable).map(productAnnounceMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Optional<ProductAnnounceDTO> findOne(Long id) {
        log.debug("Request to get ProductAnnounce : {}", id);
        return productAnnounceRepository.findById(id).map(productAnnounceMapper::toDto);
    }

    public void delete(Long id) {
        log.debug("Request to delete ProductAnnounce : {}", id);
        productAnnounceRepository.deleteById(id);
    }
}
