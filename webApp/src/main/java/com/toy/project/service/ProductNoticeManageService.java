package com.toy.project.service;

import com.toy.project.domain.ProductNoticeManage;
import com.toy.project.repository.ProductNoticeManageRepository;
import com.toy.project.service.dto.ProductNoticeManageDTO;
import com.toy.project.service.mapper.ProductNoticeManageMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ProductNoticeManage}.
 */
@Service
@Transactional
public class ProductNoticeManageService {

    private final Logger log = LoggerFactory.getLogger(ProductNoticeManageService.class);

    private final ProductNoticeManageRepository productNoticeManageRepository;

    private final ProductNoticeManageMapper productNoticeManageMapper;

    public ProductNoticeManageService(
        ProductNoticeManageRepository productNoticeManageRepository,
        ProductNoticeManageMapper productNoticeManageMapper
    ) {
        this.productNoticeManageRepository = productNoticeManageRepository;
        this.productNoticeManageMapper = productNoticeManageMapper;
    }

    /**
     * Save a productNoticeManage.
     *
     * @param productNoticeManageDTO the entity to save.
     * @return the persisted entity.
     */
    public ProductNoticeManageDTO save(ProductNoticeManageDTO productNoticeManageDTO) {
        log.debug("Request to save ProductNoticeManage : {}", productNoticeManageDTO);
        ProductNoticeManage productNoticeManage = productNoticeManageMapper.toEntity(productNoticeManageDTO);
        productNoticeManage = productNoticeManageRepository.save(productNoticeManage);
        return productNoticeManageMapper.toDto(productNoticeManage);
    }

    /**
     * Partially update a productNoticeManage.
     *
     * @param productNoticeManageDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ProductNoticeManageDTO> partialUpdate(ProductNoticeManageDTO productNoticeManageDTO) {
        log.debug("Request to partially update ProductNoticeManage : {}", productNoticeManageDTO);

        return productNoticeManageRepository
            .findById(productNoticeManageDTO.getId())
            .map(
                existingProductNoticeManage -> {
                    productNoticeManageMapper.partialUpdate(existingProductNoticeManage, productNoticeManageDTO);
                    return existingProductNoticeManage;
                }
            )
            .map(productNoticeManageRepository::save)
            .map(productNoticeManageMapper::toDto);
    }

    /**
     * Get all the productNoticeManages.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ProductNoticeManageDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ProductNoticeManages");
        return productNoticeManageRepository.findAll(pageable).map(productNoticeManageMapper::toDto);
    }

    /**
     * Get one productNoticeManage by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ProductNoticeManageDTO> findOne(Long id) {
        log.debug("Request to get ProductNoticeManage : {}", id);
        return productNoticeManageRepository.findById(id).map(productNoticeManageMapper::toDto);
    }

    /**
     * Delete the productNoticeManage by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ProductNoticeManage : {}", id);
        productNoticeManageRepository.deleteById(id);
    }
}
