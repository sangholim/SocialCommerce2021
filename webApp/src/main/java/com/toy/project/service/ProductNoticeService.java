package com.toy.project.service;

import com.toy.project.domain.ProductNotice;
import com.toy.project.repository.ProductNoticeRepository;
import com.toy.project.service.dto.ProductNoticeDTO;
import com.toy.project.service.mapper.ProductNoticeMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ProductNotice}.
 */
@Service
@Transactional
public class ProductNoticeService {

    private final Logger log = LoggerFactory.getLogger(ProductNoticeService.class);

    private final ProductNoticeRepository productNoticeRepository;

    private final ProductNoticeMapper productNoticeMapper;

    public ProductNoticeService(ProductNoticeRepository productNoticeRepository, ProductNoticeMapper productNoticeMapper) {
        this.productNoticeRepository = productNoticeRepository;
        this.productNoticeMapper = productNoticeMapper;
    }

    /**
     * Save a productNotice.
     *
     * @param productNoticeDTO the entity to save.
     * @return the persisted entity.
     */
    public ProductNoticeDTO save(ProductNoticeDTO productNoticeDTO) {
        log.debug("Request to save ProductNotice : {}", productNoticeDTO);
        ProductNotice productNotice = productNoticeMapper.toEntity(productNoticeDTO);
        productNotice = productNoticeRepository.save(productNotice);
        return productNoticeMapper.toDto(productNotice);
    }

    /**
     * Partially update a productNotice.
     *
     * @param productNoticeDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ProductNoticeDTO> partialUpdate(ProductNoticeDTO productNoticeDTO) {
        log.debug("Request to partially update ProductNotice : {}", productNoticeDTO);

        return productNoticeRepository
            .findById(productNoticeDTO.getId())
            .map(
                existingProductNotice -> {
                    productNoticeMapper.partialUpdate(existingProductNotice, productNoticeDTO);
                    return existingProductNotice;
                }
            )
            .map(productNoticeRepository::save)
            .map(productNoticeMapper::toDto);
    }

    /**
     * Get all the productNotices.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ProductNoticeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ProductNotices");
        return productNoticeRepository.findAll(pageable).map(productNoticeMapper::toDto);
    }

    /**
     * Get one productNotice by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ProductNoticeDTO> findOne(Long id) {
        log.debug("Request to get ProductNotice : {}", id);
        return productNoticeRepository.findById(id).map(productNoticeMapper::toDto);
    }

    /**
     * Delete the productNotice by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ProductNotice : {}", id);
        productNoticeRepository.deleteById(id);
    }
}
