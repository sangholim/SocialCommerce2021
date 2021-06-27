package com.toy.project.service;

import com.toy.project.domain.ProductOptionColorRel;
import com.toy.project.repository.ProductOptionColorRelRepository;
import com.toy.project.service.dto.ProductOptionColorRelDTO;
import com.toy.project.service.mapper.ProductOptionColorRelMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ProductOptionColorRel}.
 */
@Service
@Transactional
public class ProductOptionColorRelService {

    private final Logger log = LoggerFactory.getLogger(ProductOptionColorRelService.class);

    private final ProductOptionColorRelRepository productOptionColorRelRepository;

    private final ProductOptionColorRelMapper productOptionColorRelMapper;

    public ProductOptionColorRelService(
        ProductOptionColorRelRepository productOptionColorRelRepository,
        ProductOptionColorRelMapper productOptionColorRelMapper
    ) {
        this.productOptionColorRelRepository = productOptionColorRelRepository;
        this.productOptionColorRelMapper = productOptionColorRelMapper;
    }

    /**
     * Save a productOptionColorRel.
     *
     * @param productOptionColorRelDTO the entity to save.
     * @return the persisted entity.
     */
    public ProductOptionColorRelDTO save(ProductOptionColorRelDTO productOptionColorRelDTO) {
        log.debug("Request to save ProductOptionColorRel : {}", productOptionColorRelDTO);
        ProductOptionColorRel productOptionColorRel = productOptionColorRelMapper.toEntity(productOptionColorRelDTO);
        productOptionColorRel = productOptionColorRelRepository.save(productOptionColorRel);
        return productOptionColorRelMapper.toDto(productOptionColorRel);
    }

    /**
     * Partially update a productOptionColorRel.
     *
     * @param productOptionColorRelDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ProductOptionColorRelDTO> partialUpdate(ProductOptionColorRelDTO productOptionColorRelDTO) {
        log.debug("Request to partially update ProductOptionColorRel : {}", productOptionColorRelDTO);

        return productOptionColorRelRepository
            .findById(productOptionColorRelDTO.getId())
            .map(
                existingProductOptionColorRel -> {
                    productOptionColorRelMapper.partialUpdate(existingProductOptionColorRel, productOptionColorRelDTO);
                    return existingProductOptionColorRel;
                }
            )
            .map(productOptionColorRelRepository::save)
            .map(productOptionColorRelMapper::toDto);
    }

    /**
     * Get all the productOptionColorRels.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ProductOptionColorRelDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ProductOptionColorRels");
        return productOptionColorRelRepository.findAll(pageable).map(productOptionColorRelMapper::toDto);
    }

    /**
     * Get one productOptionColorRel by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ProductOptionColorRelDTO> findOne(Long id) {
        log.debug("Request to get ProductOptionColorRel : {}", id);
        return productOptionColorRelRepository.findById(id).map(productOptionColorRelMapper::toDto);
    }

    /**
     * Delete the productOptionColorRel by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ProductOptionColorRel : {}", id);
        productOptionColorRelRepository.deleteById(id);
    }
}
