package com.toy.project.service;

import com.toy.project.domain.ProductStore;
import com.toy.project.repository.ProductStoreRepository;
import com.toy.project.service.dto.ProductStoreDTO;
import com.toy.project.service.mapper.ProductStoreMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ProductStore}.
 */
@Service
@Transactional
public class ProductStoreService {

    private final Logger log = LoggerFactory.getLogger(ProductStoreService.class);

    private final ProductStoreRepository productStoreRepository;

    private final ProductStoreMapper productStoreMapper;

    public ProductStoreService(ProductStoreRepository productStoreRepository, ProductStoreMapper productStoreMapper) {
        this.productStoreRepository = productStoreRepository;
        this.productStoreMapper = productStoreMapper;
    }

    /**
     * Save a productStore.
     *
     * @param productStoreDTO the entity to save.
     * @return the persisted entity.
     */
    public ProductStoreDTO save(ProductStoreDTO productStoreDTO) {
        log.debug("Request to save ProductStore : {}", productStoreDTO);
        ProductStore productStore = productStoreMapper.toEntity(productStoreDTO);
        productStore = productStoreRepository.save(productStore);
        return productStoreMapper.toDto(productStore);
    }

    /**
     * Partially update a productStore.
     *
     * @param productStoreDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ProductStoreDTO> partialUpdate(ProductStoreDTO productStoreDTO) {
        log.debug("Request to partially update ProductStore : {}", productStoreDTO);

        return productStoreRepository
            .findById(productStoreDTO.getId())
            .map(
                existingProductStore -> {
                    productStoreMapper.partialUpdate(existingProductStore, productStoreDTO);
                    return existingProductStore;
                }
            )
            .map(productStoreRepository::save)
            .map(productStoreMapper::toDto);
    }

    /**
     * Get all the productStores.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ProductStoreDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ProductStores");
        return productStoreRepository.findAll(pageable).map(productStoreMapper::toDto);
    }

    /**
     * Get one productStore by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ProductStoreDTO> findOne(Long id) {
        log.debug("Request to get ProductStore : {}", id);
        return productStoreRepository.findById(id).map(productStoreMapper::toDto);
    }

    /**
     * Delete the productStore by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ProductStore : {}", id);
        productStoreRepository.deleteById(id);
    }
}
