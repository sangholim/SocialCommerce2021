package com.toy.project.service;

import com.toy.project.domain.ProductMappingRel;
import com.toy.project.domain.ProductStoreRel;
import com.toy.project.repository.ProductStoreRelRepository;
import com.toy.project.service.dto.ProductMappingRelDTO;
import com.toy.project.service.dto.ProductStoreRelDTO;
import com.toy.project.service.mapper.ProductStoreRelMapper;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ProductStoreRel}.
 */
@Service
@Transactional
public class ProductStoreRelService {

    private final Logger log = LoggerFactory.getLogger(ProductStoreRelService.class);

    private final ProductStoreRelRepository productStoreRelRepository;

    private final ProductStoreRelMapper productStoreRelMapper;

    public ProductStoreRelService(ProductStoreRelRepository productStoreRelRepository, ProductStoreRelMapper productStoreRelMapper) {
        this.productStoreRelRepository = productStoreRelRepository;
        this.productStoreRelMapper = productStoreRelMapper;
    }

    /**
     * Save a productStoreRel.
     *
     * @param productStoreRelDTO the entity to save.
     * @return the persisted entity.
     */
    public ProductStoreRelDTO save(ProductStoreRelDTO productStoreRelDTO) {
        log.debug("Request to save ProductStoreRel : {}", productStoreRelDTO);
        ProductStoreRel productStoreRel = productStoreRelMapper.toEntity(productStoreRelDTO);
        productStoreRel = productStoreRelRepository.save(productStoreRel);
        return productStoreRelMapper.toDto(productStoreRel);
    }

    public Set<ProductStoreRelDTO> saveAll(Set<ProductStoreRelDTO> productStoreRelDTOS) {
        Set<ProductStoreRel> productStoreRels = productStoreRelDTOS
            .stream()
            .map(productStoreRelMapper::toEntity)
            .collect(Collectors.toSet());
        return productStoreRelRepository.saveAll(productStoreRels).stream().map(productStoreRelMapper::toDto).collect(Collectors.toSet());
    }

    /**
     * Partially update a productStoreRel.
     *
     * @param productStoreRelDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ProductStoreRelDTO> partialUpdate(ProductStoreRelDTO productStoreRelDTO) {
        log.debug("Request to partially update ProductStoreRel : {}", productStoreRelDTO);

        return productStoreRelRepository
            .findById(productStoreRelDTO.getId())
            .map(
                existingProductStoreRel -> {
                    productStoreRelMapper.partialUpdate(existingProductStoreRel, productStoreRelDTO);
                    return existingProductStoreRel;
                }
            )
            .map(productStoreRelRepository::save)
            .map(productStoreRelMapper::toDto);
    }

    /**
     * Get all the productStoreRels.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ProductStoreRelDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ProductStoreRels");
        return productStoreRelRepository.findAll(pageable).map(productStoreRelMapper::toDto);
    }

    /**
     * Get one productStoreRel by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ProductStoreRelDTO> findOne(Long id) {
        log.debug("Request to get ProductStoreRel : {}", id);
        return productStoreRelRepository.findById(id).map(productStoreRelMapper::toDto);
    }

    /**
     * Delete the productStoreRel by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ProductStoreRel : {}", id);
        productStoreRelRepository.deleteById(id);
    }
}
