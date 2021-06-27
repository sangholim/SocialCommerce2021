package com.toy.project.service;

import com.toy.project.domain.ProductView;
import com.toy.project.repository.ProductViewRepository;
import com.toy.project.service.dto.ProductViewDTO;
import com.toy.project.service.mapper.ProductViewMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ProductView}.
 */
@Service
@Transactional
public class ProductViewService {

    private final Logger log = LoggerFactory.getLogger(ProductViewService.class);

    private final ProductViewRepository productViewRepository;

    private final ProductViewMapper productViewMapper;

    public ProductViewService(ProductViewRepository productViewRepository, ProductViewMapper productViewMapper) {
        this.productViewRepository = productViewRepository;
        this.productViewMapper = productViewMapper;
    }

    /**
     * Save a productView.
     *
     * @param productViewDTO the entity to save.
     * @return the persisted entity.
     */
    public ProductViewDTO save(ProductViewDTO productViewDTO) {
        log.debug("Request to save ProductView : {}", productViewDTO);
        ProductView productView = productViewMapper.toEntity(productViewDTO);
        productView = productViewRepository.save(productView);
        return productViewMapper.toDto(productView);
    }

    /**
     * Partially update a productView.
     *
     * @param productViewDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ProductViewDTO> partialUpdate(ProductViewDTO productViewDTO) {
        log.debug("Request to partially update ProductView : {}", productViewDTO);

        return productViewRepository
            .findById(productViewDTO.getId())
            .map(
                existingProductView -> {
                    productViewMapper.partialUpdate(existingProductView, productViewDTO);
                    return existingProductView;
                }
            )
            .map(productViewRepository::save)
            .map(productViewMapper::toDto);
    }

    /**
     * Get all the productViews.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ProductViewDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ProductViews");
        return productViewRepository.findAll(pageable).map(productViewMapper::toDto);
    }

    /**
     * Get one productView by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ProductViewDTO> findOne(Long id) {
        log.debug("Request to get ProductView : {}", id);
        return productViewRepository.findById(id).map(productViewMapper::toDto);
    }

    /**
     * Delete the productView by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ProductView : {}", id);
        productViewRepository.deleteById(id);
    }
}
