package com.toy.project.service;

import com.toy.project.domain.ProductCategoryManage;
import com.toy.project.repository.ProductCategoryManageRepository;
import com.toy.project.service.dto.ProductCategoryManageDTO;
import com.toy.project.service.mapper.ProductCategoryManageMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ProductCategoryManage}.
 */
@Service
@Transactional
public class ProductCategoryManageService {

    private final Logger log = LoggerFactory.getLogger(ProductCategoryManageService.class);

    private final ProductCategoryManageRepository productCategoryManageRepository;

    private final ProductCategoryManageMapper productCategoryManageMapper;

    public ProductCategoryManageService(
        ProductCategoryManageRepository productCategoryManageRepository,
        ProductCategoryManageMapper productCategoryManageMapper
    ) {
        this.productCategoryManageRepository = productCategoryManageRepository;
        this.productCategoryManageMapper = productCategoryManageMapper;
    }

    /**
     * Save a productCategoryManage.
     *
     * @param productCategoryManageDTO the entity to save.
     * @return the persisted entity.
     */
    public ProductCategoryManageDTO save(ProductCategoryManageDTO productCategoryManageDTO) {
        log.debug("Request to save ProductCategoryManage : {}", productCategoryManageDTO);
        ProductCategoryManage productCategoryManage = productCategoryManageMapper.toEntity(productCategoryManageDTO);
        productCategoryManage = productCategoryManageRepository.save(productCategoryManage);
        return productCategoryManageMapper.toDto(productCategoryManage);
    }

    /**
     * Partially update a productCategoryManage.
     *
     * @param productCategoryManageDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ProductCategoryManageDTO> partialUpdate(ProductCategoryManageDTO productCategoryManageDTO) {
        log.debug("Request to partially update ProductCategoryManage : {}", productCategoryManageDTO);

        return productCategoryManageRepository
            .findById(productCategoryManageDTO.getId())
            .map(
                existingProductCategoryManage -> {
                    productCategoryManageMapper.partialUpdate(existingProductCategoryManage, productCategoryManageDTO);
                    return existingProductCategoryManage;
                }
            )
            .map(productCategoryManageRepository::save)
            .map(productCategoryManageMapper::toDto);
    }

    /**
     * Get all the productCategoryManages.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ProductCategoryManageDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ProductCategoryManages");
        return productCategoryManageRepository.findAll(pageable).map(productCategoryManageMapper::toDto);
    }

    /**
     * Get one productCategoryManage by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ProductCategoryManageDTO> findOne(Long id) {
        log.debug("Request to get ProductCategoryManage : {}", id);
        return productCategoryManageRepository.findById(id).map(productCategoryManageMapper::toDto);
    }

    /**
     * Delete the productCategoryManage by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ProductCategoryManage : {}", id);
        productCategoryManageRepository.deleteById(id);
    }
}
