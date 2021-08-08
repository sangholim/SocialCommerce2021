package com.toy.project.service;

import com.toy.project.domain.ProductAnnounceTemplate;
import com.toy.project.repository.ProductAnnounceTemplateRepository;
import com.toy.project.service.dto.ProductAnnounceTemplateDTO;
import com.toy.project.service.mapper.ProductAnnounceTemplateMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ProductAnnounceTemplate}.
 */
@Service
@Transactional
public class ProductAnnounceTemplateService {

    private final Logger log = LoggerFactory.getLogger(ProductAnnounceTemplateService.class);

    private final ProductAnnounceTemplateRepository productAnnounceTemplateRepository;

    private final ProductAnnounceTemplateMapper productAnnounceTemplateMapper;

    public ProductAnnounceTemplateService(
        ProductAnnounceTemplateRepository productAnnounceTemplateRepository,
        ProductAnnounceTemplateMapper productAnnounceTemplateMapper
    ) {
        this.productAnnounceTemplateRepository = productAnnounceTemplateRepository;
        this.productAnnounceTemplateMapper = productAnnounceTemplateMapper;
    }

    /**
     * Save a productAnnounceTemplate.
     *
     * @param productAnnounceTemplateDTO the entity to save.
     * @return the persisted entity.
     */
    public ProductAnnounceTemplateDTO save(ProductAnnounceTemplateDTO productAnnounceTemplateDTO) {
        log.debug("Request to save ProductAnnounceTemplate : {}", productAnnounceTemplateDTO);
        ProductAnnounceTemplate productAnnounceTemplate = productAnnounceTemplateMapper.toEntity(productAnnounceTemplateDTO);
        productAnnounceTemplate = productAnnounceTemplateRepository.save(productAnnounceTemplate);
        return productAnnounceTemplateMapper.toDto(productAnnounceTemplate);
    }

    /**
     * Partially update a productAnnounceTemplate.
     *
     * @param productAnnounceTemplateDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ProductAnnounceTemplateDTO> partialUpdate(ProductAnnounceTemplateDTO productAnnounceTemplateDTO) {
        log.debug("Request to partially update ProductAnnounceTemplate : {}", productAnnounceTemplateDTO);

        return productAnnounceTemplateRepository
            .findById(productAnnounceTemplateDTO.getId())
            .map(
                existingProductAnnounceTemplate -> {
                    productAnnounceTemplateMapper.partialUpdate(existingProductAnnounceTemplate, productAnnounceTemplateDTO);
                    return existingProductAnnounceTemplate;
                }
            )
            .map(productAnnounceTemplateRepository::save)
            .map(productAnnounceTemplateMapper::toDto);
    }

    /**
     * Get all the productAnnounceTemplates.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ProductAnnounceTemplateDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ProductAnnounceTemplates");
        return productAnnounceTemplateRepository.findAll(pageable).map(productAnnounceTemplateMapper::toDto);
    }

    /**
     * Get one productAnnounceTemplate by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ProductAnnounceTemplateDTO> findOne(Long id) {
        log.debug("Request to get ProductAnnounceTemplate : {}", id);
        return productAnnounceTemplateRepository.findById(id).map(productAnnounceTemplateMapper::toDto);
    }

    /**
     * Delete the productAnnounceTemplate by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ProductAnnounceTemplate : {}", id);
        productAnnounceTemplateRepository.deleteById(id);
    }
}
