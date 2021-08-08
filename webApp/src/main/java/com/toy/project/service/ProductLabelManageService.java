package com.toy.project.service;

import com.toy.project.domain.ProductLabelManage;
import com.toy.project.repository.ProductLabelManageRepository;
import com.toy.project.service.dto.ProductLabelManageDTO;
import com.toy.project.service.mapper.ProductLabelManageMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ProductLabelManage}.
 */
@Service
@Transactional
public class ProductLabelManageService {

    private final Logger log = LoggerFactory.getLogger(ProductLabelManageService.class);

    private final ProductLabelManageRepository productLabelManageRepository;

    private final ProductLabelManageMapper productLabelManageMapper;

    public ProductLabelManageService(
        ProductLabelManageRepository productLabelManageRepository,
        ProductLabelManageMapper productLabelManageMapper
    ) {
        this.productLabelManageRepository = productLabelManageRepository;
        this.productLabelManageMapper = productLabelManageMapper;
    }

    /**
     * Save a productLabelManage.
     *
     * @param productLabelManageDTO the entity to save.
     * @return the persisted entity.
     */
    public ProductLabelManageDTO save(ProductLabelManageDTO productLabelManageDTO) {
        log.debug("Request to save ProductLabelManage : {}", productLabelManageDTO);
        ProductLabelManage productLabelManage = productLabelManageMapper.toEntity(productLabelManageDTO);
        productLabelManage = productLabelManageRepository.save(productLabelManage);
        return productLabelManageMapper.toDto(productLabelManage);
    }

    /**
     * Partially update a productLabelManage.
     *
     * @param productLabelManageDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ProductLabelManageDTO> partialUpdate(ProductLabelManageDTO productLabelManageDTO) {
        log.debug("Request to partially update ProductLabelManage : {}", productLabelManageDTO);

        return productLabelManageRepository
            .findById(productLabelManageDTO.getId())
            .map(
                existingProductLabelManage -> {
                    productLabelManageMapper.partialUpdate(existingProductLabelManage, productLabelManageDTO);
                    return existingProductLabelManage;
                }
            )
            .map(productLabelManageRepository::save)
            .map(productLabelManageMapper::toDto);
    }

    /**
     * Get all the productLabelManages.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ProductLabelManageDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ProductLabelManages");
        return productLabelManageRepository.findAll(pageable).map(productLabelManageMapper::toDto);
    }

    /**
     * Get one productLabelManage by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ProductLabelManageDTO> findOne(Long id) {
        log.debug("Request to get ProductLabelManage : {}", id);
        return productLabelManageRepository.findById(id).map(productLabelManageMapper::toDto);
    }

    /**
     * Delete the productLabelManage by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ProductLabelManage : {}", id);
        productLabelManageRepository.deleteById(id);
    }
}
