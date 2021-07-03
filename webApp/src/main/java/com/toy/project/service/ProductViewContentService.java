package com.toy.project.service;

import com.toy.project.domain.ProductViewContent;
import com.toy.project.repository.ProductViewContentRepository;
import com.toy.project.service.dto.ProductViewContentDTO;
import com.toy.project.service.mapper.ProductViewContentMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ProductViewContent}.
 */
@Service
@Transactional
public class ProductViewContentService {

    private final Logger log = LoggerFactory.getLogger(ProductViewContentService.class);

    private final ProductViewContentRepository productViewContentRepository;

    private final ProductViewContentMapper productViewContentMapper;

    public ProductViewContentService(
        ProductViewContentRepository productViewContentRepository,
        ProductViewContentMapper productViewContentMapper
    ) {
        this.productViewContentRepository = productViewContentRepository;
        this.productViewContentMapper = productViewContentMapper;
    }

    /**
     * Save a productViewContent.
     *
     * @param productViewContentDTO the entity to save.
     * @return the persisted entity.
     */
    public ProductViewContentDTO save(ProductViewContentDTO productViewContentDTO) {
        log.debug("Request to save ProductViewContent : {}", productViewContentDTO);
        ProductViewContent productViewContent = productViewContentMapper.toEntity(productViewContentDTO);
        productViewContent = productViewContentRepository.save(productViewContent);
        return productViewContentMapper.toDto(productViewContent);
    }

    /**
     * Partially update a productViewContent.
     *
     * @param productViewContentDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ProductViewContentDTO> partialUpdate(ProductViewContentDTO productViewContentDTO) {
        log.debug("Request to partially update ProductViewContent : {}", productViewContentDTO);

        return productViewContentRepository
            .findById(productViewContentDTO.getId())
            .map(
                existingProductViewContent -> {
                    productViewContentMapper.partialUpdate(existingProductViewContent, productViewContentDTO);
                    return existingProductViewContent;
                }
            )
            .map(productViewContentRepository::save)
            .map(productViewContentMapper::toDto);
    }

    /**
     * Get all the productViewContents.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ProductViewContentDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ProductViewContents");
        return productViewContentRepository.findAll(pageable).map(productViewContentMapper::toDto);
    }

    /**
     * Get one productViewContent by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ProductViewContentDTO> findOne(Long id) {
        log.debug("Request to get ProductViewContent : {}", id);
        return productViewContentRepository.findById(id).map(productViewContentMapper::toDto);
    }

    /**
     * Delete the productViewContent by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ProductViewContent : {}", id);
        productViewContentRepository.deleteById(id);
    }
}
