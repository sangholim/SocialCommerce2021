package com.toy.project.service;

import com.toy.project.domain.ProductShippingManage;
import com.toy.project.repository.ProductShippingManageRepository;
import com.toy.project.service.dto.ProductShippingManageDTO;
import com.toy.project.service.mapper.ProductShippingManageMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ProductShippingManage}.
 */
@Service
@Transactional
public class ProductShippingManageService {

    private final Logger log = LoggerFactory.getLogger(ProductShippingManageService.class);

    private final ProductShippingManageRepository productShippingManageRepository;

    private final ProductShippingManageMapper productShippingManageMapper;

    public ProductShippingManageService(
        ProductShippingManageRepository productShippingManageRepository,
        ProductShippingManageMapper productShippingManageMapper
    ) {
        this.productShippingManageRepository = productShippingManageRepository;
        this.productShippingManageMapper = productShippingManageMapper;
    }

    /**
     * Save a productShippingManage.
     *
     * @param productShippingManageDTO the entity to save.
     * @return the persisted entity.
     */
    public ProductShippingManageDTO save(ProductShippingManageDTO productShippingManageDTO) {
        log.debug("Request to save ProductShippingManage : {}", productShippingManageDTO);
        ProductShippingManage productShippingManage = productShippingManageMapper.toEntity(productShippingManageDTO);
        productShippingManage = productShippingManageRepository.save(productShippingManage);
        return productShippingManageMapper.toDto(productShippingManage);
    }

    /**
     * Partially update a productShippingManage.
     *
     * @param productShippingManageDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ProductShippingManageDTO> partialUpdate(ProductShippingManageDTO productShippingManageDTO) {
        log.debug("Request to partially update ProductShippingManage : {}", productShippingManageDTO);

        return productShippingManageRepository
            .findById(productShippingManageDTO.getId())
            .map(
                existingProductShippingManage -> {
                    productShippingManageMapper.partialUpdate(existingProductShippingManage, productShippingManageDTO);
                    return existingProductShippingManage;
                }
            )
            .map(productShippingManageRepository::save)
            .map(productShippingManageMapper::toDto);
    }

    /**
     * Get all the productShippingManages.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ProductShippingManageDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ProductShippingManages");
        return productShippingManageRepository.findAll(pageable).map(productShippingManageMapper::toDto);
    }

    /**
     * Get one productShippingManage by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ProductShippingManageDTO> findOne(Long id) {
        log.debug("Request to get ProductShippingManage : {}", id);
        return productShippingManageRepository.findById(id).map(productShippingManageMapper::toDto);
    }

    /**
     * Delete the productShippingManage by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ProductShippingManage : {}", id);
        productShippingManageRepository.deleteById(id);
    }
}
