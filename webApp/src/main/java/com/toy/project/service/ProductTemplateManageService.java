package com.toy.project.service;

import com.toy.project.domain.ProductTemplateManage;
import com.toy.project.repository.ProductTemplateManageRepository;
import com.toy.project.service.dto.ProductTemplateManageDTO;
import com.toy.project.service.mapper.ProductTemplateManageMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ProductTemplateManage}.
 */
@Service
@Transactional
public class ProductTemplateManageService {

    private final Logger log = LoggerFactory.getLogger(ProductTemplateManageService.class);

    private final ProductTemplateManageRepository productTemplateManageRepository;

    private final ProductTemplateManageMapper productTemplateManageMapper;

    public ProductTemplateManageService(
        ProductTemplateManageRepository productTemplateManageRepository,
        ProductTemplateManageMapper productTemplateManageMapper
    ) {
        this.productTemplateManageRepository = productTemplateManageRepository;
        this.productTemplateManageMapper = productTemplateManageMapper;
    }

    /**
     * Save a productTemplateManage.
     *
     * @param productTemplateManageDTO the entity to save.
     * @return the persisted entity.
     */
    public ProductTemplateManageDTO save(ProductTemplateManageDTO productTemplateManageDTO) {
        log.debug("Request to save ProductTemplateManage : {}", productTemplateManageDTO);
        ProductTemplateManage productTemplateManage = productTemplateManageMapper.toEntity(productTemplateManageDTO);
        productTemplateManage = productTemplateManageRepository.save(productTemplateManage);
        return productTemplateManageMapper.toDto(productTemplateManage);
    }

    /**
     * Partially update a productTemplateManage.
     *
     * @param productTemplateManageDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ProductTemplateManageDTO> partialUpdate(ProductTemplateManageDTO productTemplateManageDTO) {
        log.debug("Request to partially update ProductTemplateManage : {}", productTemplateManageDTO);

        return productTemplateManageRepository
            .findById(productTemplateManageDTO.getId())
            .map(
                existingProductTemplateManage -> {
                    productTemplateManageMapper.partialUpdate(existingProductTemplateManage, productTemplateManageDTO);
                    return existingProductTemplateManage;
                }
            )
            .map(productTemplateManageRepository::save)
            .map(productTemplateManageMapper::toDto);
    }

    /**
     * Get all the productTemplateManages.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ProductTemplateManageDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ProductTemplateManages");
        return productTemplateManageRepository.findAll(pageable).map(productTemplateManageMapper::toDto);
    }

    /**
     * Get one productTemplateManage by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ProductTemplateManageDTO> findOne(Long id) {
        log.debug("Request to get ProductTemplateManage : {}", id);
        return productTemplateManageRepository.findById(id).map(productTemplateManageMapper::toDto);
    }

    /**
     * Delete the productTemplateManage by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ProductTemplateManage : {}", id);
        productTemplateManageRepository.deleteById(id);
    }
}
