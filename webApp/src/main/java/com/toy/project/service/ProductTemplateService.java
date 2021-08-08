package com.toy.project.service;

import com.toy.project.domain.ProductFaq;
import com.toy.project.domain.ProductTemplate;
import com.toy.project.repository.ProductTemplateRepository;
import com.toy.project.service.dto.ProductFaqDTO;
import com.toy.project.service.dto.ProductTemplateDTO;
import com.toy.project.service.mapper.ProductTemplateMapper;
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
 * Service Implementation for managing {@link ProductTemplate}.
 */
@Service
@Transactional
public class ProductTemplateService {

    private final Logger log = LoggerFactory.getLogger(ProductTemplateService.class);

    private final ProductTemplateRepository productTemplateRepository;

    private final ProductTemplateMapper productTemplateMapper;

    public ProductTemplateService(ProductTemplateRepository productTemplateRepository, ProductTemplateMapper productTemplateMapper) {
        this.productTemplateRepository = productTemplateRepository;
        this.productTemplateMapper = productTemplateMapper;
    }

    public ProductTemplateDTO save(ProductTemplateDTO productTemplateDTO) {
        log.debug("Request to save ProductTemplate : {}", productTemplateDTO);
        ProductTemplate productTemplate = productTemplateMapper.toEntity(productTemplateDTO);
        productTemplate = productTemplateRepository.save(productTemplate);
        return productTemplateMapper.toDto(productTemplate);
    }

    public Optional<ProductTemplateDTO> partialUpdate(ProductTemplateDTO productTemplateDTO) {
        log.debug("Request to partially update ProductTemplate : {}", productTemplateDTO);

        return productTemplateRepository
            .findById(productTemplateDTO.getId())
            .map(
                existingProductTemplate -> {
                    productTemplateMapper.partialUpdate(existingProductTemplate, productTemplateDTO);
                    return existingProductTemplate;
                }
            )
            .map(productTemplateRepository::save)
            .map(productTemplateMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Page<ProductTemplateDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ProductTemplates");
        return productTemplateRepository.findAll(pageable).map(productTemplateMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Optional<ProductTemplateDTO> findOne(Long id) {
        log.debug("Request to get ProductTemplate : {}", id);
        return productTemplateRepository.findById(id).map(productTemplateMapper::toDto);
    }

    public void delete(Long id) {
        log.debug("Request to delete ProductTemplate : {}", id);
        productTemplateRepository.deleteById(id);
    }
}
