package com.toy.project.service;

import com.toy.project.domain.ProductFaq;
import com.toy.project.domain.ProductLabel;
import com.toy.project.repository.ProductLabelRepository;
import com.toy.project.service.dto.ProductFaqDTO;
import com.toy.project.service.dto.ProductLabelDTO;
import com.toy.project.service.mapper.ProductLabelMapper;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

/**
 * Service Implementation for managing {@link ProductLabel}.
 */
@Service
@Transactional
public class ProductLabelService {

    private final Logger log = LoggerFactory.getLogger(ProductLabelService.class);

    private final ProductLabelRepository productLabelRepository;

    private final ProductLabelMapper productLabelMapper;

    public ProductLabelService(ProductLabelRepository productLabelRepository, ProductLabelMapper productLabelMapper) {
        this.productLabelRepository = productLabelRepository;
        this.productLabelMapper = productLabelMapper;
    }

    /**
     * Save a productLabel.
     *
     * @param productLabelDTO the entity to save.
     * @return the persisted entity.
     */
    public ProductLabelDTO save(ProductLabelDTO productLabelDTO) {
        log.debug("Request to save ProductLabel : {}", productLabelDTO);
        ProductLabel productLabel = productLabelMapper.toEntity(productLabelDTO);
        productLabel = productLabelRepository.save(productLabel);
        return productLabelMapper.toDto(productLabel);
    }

    /**
     * Partially update a productLabel.
     *
     * @param productLabelDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ProductLabelDTO> partialUpdate(ProductLabelDTO productLabelDTO) {
        log.debug("Request to partially update ProductLabel : {}", productLabelDTO);

        return productLabelRepository
            .findById(productLabelDTO.getId())
            .map(
                existingProductLabel -> {
                    productLabelMapper.partialUpdate(existingProductLabel, productLabelDTO);
                    return existingProductLabel;
                }
            )
            .map(productLabelRepository::save)
            .map(productLabelMapper::toDto);
    }

    /**
     * Get all the productLabels.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ProductLabelDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ProductLabels");
        return productLabelRepository.findAll(pageable).map(productLabelMapper::toDto);
    }

    /**
     * Get one productLabel by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ProductLabelDTO> findOne(Long id) {
        log.debug("Request to get ProductLabel : {}", id);
        return productLabelRepository.findById(id).map(productLabelMapper::toDto);
    }

    /**
     * Delete the productLabel by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ProductLabel : {}", id);
        productLabelRepository.deleteById(id);
    }
}
