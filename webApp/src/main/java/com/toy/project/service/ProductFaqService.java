package com.toy.project.service;

import com.toy.project.domain.ProductFaq;
import com.toy.project.domain.ProductInputOption;
import com.toy.project.repository.ProductFaqRepository;
import com.toy.project.service.dto.ProductFaqDTO;
import com.toy.project.service.dto.ProductInputOptionDTO;
import com.toy.project.service.mapper.ProductFaqMapper;
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
 * Service Implementation for managing {@link ProductFaq}.
 */
@Service
@Transactional
public class ProductFaqService {

    private final Logger log = LoggerFactory.getLogger(ProductFaqService.class);

    private final ProductFaqRepository productFaqRepository;

    private final ProductFaqMapper productFaqMapper;

    public ProductFaqService(ProductFaqRepository productFaqRepository, ProductFaqMapper productFaqMapper) {
        this.productFaqRepository = productFaqRepository;
        this.productFaqMapper = productFaqMapper;
    }

    public ProductFaqDTO save(ProductFaqDTO productFaqDTO) {
        log.debug("Request to save ProductFaq : {}", productFaqDTO);
        ProductFaq productFaq = productFaqMapper.toEntity(productFaqDTO);
        productFaq = productFaqRepository.save(productFaq);
        return productFaqMapper.toDto(productFaq);
    }

    public Optional<ProductFaqDTO> partialUpdate(ProductFaqDTO productFaqDTO) {
        log.debug("Request to partially update ProductFaq : {}", productFaqDTO);

        return productFaqRepository
            .findById(productFaqDTO.getId())
            .map(
                existingProductFaq -> {
                    productFaqMapper.partialUpdate(existingProductFaq, productFaqDTO);
                    return existingProductFaq;
                }
            )
            .map(productFaqRepository::save)
            .map(productFaqMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Page<ProductFaqDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ProductFaqs");
        return productFaqRepository.findAll(pageable).map(productFaqMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Optional<ProductFaqDTO> findOne(Long id) {
        log.debug("Request to get ProductFaq : {}", id);
        return productFaqRepository.findById(id).map(productFaqMapper::toDto);
    }

    public void delete(Long id) {
        log.debug("Request to delete ProductFaq : {}", id);
        productFaqRepository.deleteById(id);
    }
}
