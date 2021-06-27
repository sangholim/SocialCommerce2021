package com.toy.project.service;

import com.toy.project.domain.ProductShipping;
import com.toy.project.repository.ProductShippingRepository;
import com.toy.project.service.dto.ProductShippingDTO;
import com.toy.project.service.mapper.ProductShippingMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ProductShipping}.
 */
@Service
@Transactional
public class ProductShippingService {

    private final Logger log = LoggerFactory.getLogger(ProductShippingService.class);

    private final ProductShippingRepository productShippingRepository;

    private final ProductShippingMapper productShippingMapper;

    public ProductShippingService(ProductShippingRepository productShippingRepository, ProductShippingMapper productShippingMapper) {
        this.productShippingRepository = productShippingRepository;
        this.productShippingMapper = productShippingMapper;
    }

    /**
     * Save a productShipping.
     *
     * @param productShippingDTO the entity to save.
     * @return the persisted entity.
     */
    public ProductShippingDTO save(ProductShippingDTO productShippingDTO) {
        log.debug("Request to save ProductShipping : {}", productShippingDTO);
        ProductShipping productShipping = productShippingMapper.toEntity(productShippingDTO);
        productShipping = productShippingRepository.save(productShipping);
        return productShippingMapper.toDto(productShipping);
    }

    /**
     * Partially update a productShipping.
     *
     * @param productShippingDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ProductShippingDTO> partialUpdate(ProductShippingDTO productShippingDTO) {
        log.debug("Request to partially update ProductShipping : {}", productShippingDTO);

        return productShippingRepository
            .findById(productShippingDTO.getId())
            .map(
                existingProductShipping -> {
                    productShippingMapper.partialUpdate(existingProductShipping, productShippingDTO);
                    return existingProductShipping;
                }
            )
            .map(productShippingRepository::save)
            .map(productShippingMapper::toDto);
    }

    /**
     * Get all the productShippings.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ProductShippingDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ProductShippings");
        return productShippingRepository.findAll(pageable).map(productShippingMapper::toDto);
    }

    /**
     * Get one productShipping by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ProductShippingDTO> findOne(Long id) {
        log.debug("Request to get ProductShipping : {}", id);
        return productShippingRepository.findById(id).map(productShippingMapper::toDto);
    }

    /**
     * Delete the productShipping by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ProductShipping : {}", id);
        productShippingRepository.deleteById(id);
    }
}
