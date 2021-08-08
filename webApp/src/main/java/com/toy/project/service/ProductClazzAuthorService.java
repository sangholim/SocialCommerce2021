package com.toy.project.service;

import com.toy.project.domain.ProductClazzAuthor;
import com.toy.project.repository.ProductClazzAuthorRepository;
import com.toy.project.service.dto.ProductClazzAuthorDTO;
import com.toy.project.service.mapper.ProductClazzAuthorMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ProductClazzAuthor}.
 */
@Service
@Transactional
public class ProductClazzAuthorService {

    private final Logger log = LoggerFactory.getLogger(ProductClazzAuthorService.class);

    private final ProductClazzAuthorRepository productClazzAuthorRepository;

    private final ProductClazzAuthorMapper productClazzAuthorMapper;

    public ProductClazzAuthorService(
        ProductClazzAuthorRepository productClazzAuthorRepository,
        ProductClazzAuthorMapper productClazzAuthorMapper
    ) {
        this.productClazzAuthorRepository = productClazzAuthorRepository;
        this.productClazzAuthorMapper = productClazzAuthorMapper;
    }

    /**
     * Save a productClazzAuthor.
     *
     * @param productClazzAuthorDTO the entity to save.
     * @return the persisted entity.
     */
    public ProductClazzAuthorDTO save(ProductClazzAuthorDTO productClazzAuthorDTO) {
        log.debug("Request to save ProductClazzAuthor : {}", productClazzAuthorDTO);
        ProductClazzAuthor productClazzAuthor = productClazzAuthorMapper.toEntity(productClazzAuthorDTO);
        productClazzAuthor = productClazzAuthorRepository.save(productClazzAuthor);
        return productClazzAuthorMapper.toDto(productClazzAuthor);
    }

    /**
     * Partially update a productClazzAuthor.
     *
     * @param productClazzAuthorDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ProductClazzAuthorDTO> partialUpdate(ProductClazzAuthorDTO productClazzAuthorDTO) {
        log.debug("Request to partially update ProductClazzAuthor : {}", productClazzAuthorDTO);

        return productClazzAuthorRepository
            .findById(productClazzAuthorDTO.getId())
            .map(
                existingProductClazzAuthor -> {
                    productClazzAuthorMapper.partialUpdate(existingProductClazzAuthor, productClazzAuthorDTO);
                    return existingProductClazzAuthor;
                }
            )
            .map(productClazzAuthorRepository::save)
            .map(productClazzAuthorMapper::toDto);
    }

    /**
     * Get all the productClazzAuthors.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ProductClazzAuthorDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ProductClazzAuthors");
        return productClazzAuthorRepository.findAll(pageable).map(productClazzAuthorMapper::toDto);
    }

    /**
     * Get one productClazzAuthor by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ProductClazzAuthorDTO> findOne(Long id) {
        log.debug("Request to get ProductClazzAuthor : {}", id);
        return productClazzAuthorRepository.findById(id).map(productClazzAuthorMapper::toDto);
    }

    /**
     * Delete the productClazzAuthor by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ProductClazzAuthor : {}", id);
        productClazzAuthorRepository.deleteById(id);
    }
}
