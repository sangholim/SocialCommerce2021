package com.toy.project.service;

import com.toy.project.domain.ProductMappingManage;
import com.toy.project.repository.ProductMappingManageRepository;
import com.toy.project.service.dto.ProductMappingManageDTO;
import com.toy.project.service.mapper.ProductMappingManageMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ProductMappingManage}.
 */
@Service
@Transactional
public class ProductMappingManageService {

    private final Logger log = LoggerFactory.getLogger(ProductMappingManageService.class);

    private final ProductMappingManageRepository productMappingManageRepository;

    private final ProductMappingManageMapper productMappingManageMapper;

    public ProductMappingManageService(
        ProductMappingManageRepository productMappingManageRepository,
        ProductMappingManageMapper productMappingManageMapper
    ) {
        this.productMappingManageRepository = productMappingManageRepository;
        this.productMappingManageMapper = productMappingManageMapper;
    }

    /**
     * Save a productMappingManage.
     *
     * @param productMappingManageDTO the entity to save.
     * @return the persisted entity.
     */
    public ProductMappingManageDTO save(ProductMappingManageDTO productMappingManageDTO) {
        log.debug("Request to save ProductMappingManage : {}", productMappingManageDTO);
        ProductMappingManage productMappingManage = productMappingManageMapper.toEntity(productMappingManageDTO);
        productMappingManage = productMappingManageRepository.save(productMappingManage);
        return productMappingManageMapper.toDto(productMappingManage);
    }

    /**
     * Partially update a productMappingManage.
     *
     * @param productMappingManageDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ProductMappingManageDTO> partialUpdate(ProductMappingManageDTO productMappingManageDTO) {
        log.debug("Request to partially update ProductMappingManage : {}", productMappingManageDTO);

        return productMappingManageRepository
            .findById(productMappingManageDTO.getId())
            .map(
                existingProductMappingManage -> {
                    productMappingManageMapper.partialUpdate(existingProductMappingManage, productMappingManageDTO);
                    return existingProductMappingManage;
                }
            )
            .map(productMappingManageRepository::save)
            .map(productMappingManageMapper::toDto);
    }

    /**
     * Get all the productMappingManages.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ProductMappingManageDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ProductMappingManages");
        return productMappingManageRepository.findAll(pageable).map(productMappingManageMapper::toDto);
    }

    /**
     * Get one productMappingManage by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ProductMappingManageDTO> findOne(Long id) {
        log.debug("Request to get ProductMappingManage : {}", id);
        return productMappingManageRepository.findById(id).map(productMappingManageMapper::toDto);
    }

    /**
     * Delete the productMappingManage by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ProductMappingManage : {}", id);
        productMappingManageRepository.deleteById(id);
    }
}
