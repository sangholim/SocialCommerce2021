package com.toy.project.service;

import com.toy.project.domain.ProductTemplate;
import com.toy.project.repository.ProductTemplateRepository;
import com.toy.project.service.dto.ProductTemplateDTO;
import com.toy.project.service.mapper.ProductTemplateMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    /**
     * Save a productTemplate.
     *
     * @param productTemplateDTO the entity to save.
     * @return the persisted entity.
     */
    public ProductTemplateDTO save(ProductTemplateDTO productTemplateDTO) {
        log.debug("Request to save ProductTemplate : {}", productTemplateDTO);
        ProductTemplate productTemplate = productTemplateMapper.toEntity(productTemplateDTO);
        productTemplate = productTemplateRepository.save(productTemplate);
        return productTemplateMapper.toDto(productTemplate);
    }

    /**
     * Partially update a productTemplate.
     *
     * @param productTemplateDTO the entity to update partially.
     * @return the persisted entity.
     */
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

    /**
     * Get all the productTemplates.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ProductTemplateDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ProductTemplates");
        return productTemplateRepository.findAll(pageable).map(productTemplateMapper::toDto);
    }

    /**
     * Get one productTemplate by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ProductTemplateDTO> findOne(Long id) {
        log.debug("Request to get ProductTemplate : {}", id);
        return productTemplateRepository.findById(id).map(productTemplateMapper::toDto);
    }

    /**
     * Delete the productTemplate by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ProductTemplate : {}", id);
        productTemplateRepository.deleteById(id);
    }
}
