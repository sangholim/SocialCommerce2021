package com.toy.project.service;

import com.toy.project.domain.ProductNoticeRel;
import com.toy.project.repository.ProductNoticeRelRepository;
import com.toy.project.service.dto.ProductNoticeRelDTO;
import com.toy.project.service.mapper.ProductNoticeRelMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ProductNoticeRel}.
 */
@Service
@Transactional
public class ProductNoticeRelService {

    private final Logger log = LoggerFactory.getLogger(ProductNoticeRelService.class);

    private final ProductNoticeRelRepository productNoticeRelRepository;

    private final ProductNoticeRelMapper productNoticeRelMapper;

    public ProductNoticeRelService(ProductNoticeRelRepository productNoticeRelRepository, ProductNoticeRelMapper productNoticeRelMapper) {
        this.productNoticeRelRepository = productNoticeRelRepository;
        this.productNoticeRelMapper = productNoticeRelMapper;
    }

    /**
     * Save a productNoticeRel.
     *
     * @param productNoticeRelDTO the entity to save.
     * @return the persisted entity.
     */
    public ProductNoticeRelDTO save(ProductNoticeRelDTO productNoticeRelDTO) {
        log.debug("Request to save ProductNoticeRel : {}", productNoticeRelDTO);
        ProductNoticeRel productNoticeRel = productNoticeRelMapper.toEntity(productNoticeRelDTO);
        productNoticeRel = productNoticeRelRepository.save(productNoticeRel);
        return productNoticeRelMapper.toDto(productNoticeRel);
    }

    /**
     * Partially update a productNoticeRel.
     *
     * @param productNoticeRelDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ProductNoticeRelDTO> partialUpdate(ProductNoticeRelDTO productNoticeRelDTO) {
        log.debug("Request to partially update ProductNoticeRel : {}", productNoticeRelDTO);

        return productNoticeRelRepository
            .findById(productNoticeRelDTO.getId())
            .map(
                existingProductNoticeRel -> {
                    productNoticeRelMapper.partialUpdate(existingProductNoticeRel, productNoticeRelDTO);
                    return existingProductNoticeRel;
                }
            )
            .map(productNoticeRelRepository::save)
            .map(productNoticeRelMapper::toDto);
    }

    /**
     * Get all the productNoticeRels.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ProductNoticeRelDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ProductNoticeRels");
        return productNoticeRelRepository.findAll(pageable).map(productNoticeRelMapper::toDto);
    }

    /**
     * Get one productNoticeRel by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ProductNoticeRelDTO> findOne(Long id) {
        log.debug("Request to get ProductNoticeRel : {}", id);
        return productNoticeRelRepository.findById(id).map(productNoticeRelMapper::toDto);
    }

    /**
     * Delete the productNoticeRel by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ProductNoticeRel : {}", id);
        productNoticeRelRepository.deleteById(id);
    }
}
