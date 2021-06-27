package com.toy.project.service;

import com.toy.project.domain.ProductOptionRel;
import com.toy.project.repository.ProductOptionRelRepository;
import com.toy.project.service.dto.ProductOptionRelDTO;
import com.toy.project.service.mapper.ProductOptionRelMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ProductOptionRel}.
 */
@Service
@Transactional
public class ProductOptionRelService {

    private final Logger log = LoggerFactory.getLogger(ProductOptionRelService.class);

    private final ProductOptionRelRepository productOptionRelRepository;

    private final ProductOptionRelMapper productOptionRelMapper;

    public ProductOptionRelService(ProductOptionRelRepository productOptionRelRepository, ProductOptionRelMapper productOptionRelMapper) {
        this.productOptionRelRepository = productOptionRelRepository;
        this.productOptionRelMapper = productOptionRelMapper;
    }

    /**
     * Save a productOptionRel.
     *
     * @param productOptionRelDTO the entity to save.
     * @return the persisted entity.
     */
    public ProductOptionRelDTO save(ProductOptionRelDTO productOptionRelDTO) {
        log.debug("Request to save ProductOptionRel : {}", productOptionRelDTO);
        ProductOptionRel productOptionRel = productOptionRelMapper.toEntity(productOptionRelDTO);
        productOptionRel = productOptionRelRepository.save(productOptionRel);
        return productOptionRelMapper.toDto(productOptionRel);
    }

    /**
     * Partially update a productOptionRel.
     *
     * @param productOptionRelDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ProductOptionRelDTO> partialUpdate(ProductOptionRelDTO productOptionRelDTO) {
        log.debug("Request to partially update ProductOptionRel : {}", productOptionRelDTO);

        return productOptionRelRepository
            .findById(productOptionRelDTO.getId())
            .map(
                existingProductOptionRel -> {
                    productOptionRelMapper.partialUpdate(existingProductOptionRel, productOptionRelDTO);
                    return existingProductOptionRel;
                }
            )
            .map(productOptionRelRepository::save)
            .map(productOptionRelMapper::toDto);
    }

    /**
     * Get all the productOptionRels.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ProductOptionRelDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ProductOptionRels");
        return productOptionRelRepository.findAll(pageable).map(productOptionRelMapper::toDto);
    }

    /**
     * Get one productOptionRel by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ProductOptionRelDTO> findOne(Long id) {
        log.debug("Request to get ProductOptionRel : {}", id);
        return productOptionRelRepository.findById(id).map(productOptionRelMapper::toDto);
    }

    /**
     * Delete the productOptionRel by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ProductOptionRel : {}", id);
        productOptionRelRepository.deleteById(id);
    }
}
