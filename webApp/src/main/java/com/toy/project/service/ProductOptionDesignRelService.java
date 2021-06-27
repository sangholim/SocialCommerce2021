package com.toy.project.service;

import com.toy.project.domain.ProductOptionDesignRel;
import com.toy.project.repository.ProductOptionDesignRelRepository;
import com.toy.project.service.dto.ProductOptionDesignRelDTO;
import com.toy.project.service.mapper.ProductOptionDesignRelMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ProductOptionDesignRel}.
 */
@Service
@Transactional
public class ProductOptionDesignRelService {

    private final Logger log = LoggerFactory.getLogger(ProductOptionDesignRelService.class);

    private final ProductOptionDesignRelRepository productOptionDesignRelRepository;

    private final ProductOptionDesignRelMapper productOptionDesignRelMapper;

    public ProductOptionDesignRelService(
        ProductOptionDesignRelRepository productOptionDesignRelRepository,
        ProductOptionDesignRelMapper productOptionDesignRelMapper
    ) {
        this.productOptionDesignRelRepository = productOptionDesignRelRepository;
        this.productOptionDesignRelMapper = productOptionDesignRelMapper;
    }

    /**
     * Save a productOptionDesignRel.
     *
     * @param productOptionDesignRelDTO the entity to save.
     * @return the persisted entity.
     */
    public ProductOptionDesignRelDTO save(ProductOptionDesignRelDTO productOptionDesignRelDTO) {
        log.debug("Request to save ProductOptionDesignRel : {}", productOptionDesignRelDTO);
        ProductOptionDesignRel productOptionDesignRel = productOptionDesignRelMapper.toEntity(productOptionDesignRelDTO);
        productOptionDesignRel = productOptionDesignRelRepository.save(productOptionDesignRel);
        return productOptionDesignRelMapper.toDto(productOptionDesignRel);
    }

    /**
     * Partially update a productOptionDesignRel.
     *
     * @param productOptionDesignRelDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ProductOptionDesignRelDTO> partialUpdate(ProductOptionDesignRelDTO productOptionDesignRelDTO) {
        log.debug("Request to partially update ProductOptionDesignRel : {}", productOptionDesignRelDTO);

        return productOptionDesignRelRepository
            .findById(productOptionDesignRelDTO.getId())
            .map(
                existingProductOptionDesignRel -> {
                    productOptionDesignRelMapper.partialUpdate(existingProductOptionDesignRel, productOptionDesignRelDTO);
                    return existingProductOptionDesignRel;
                }
            )
            .map(productOptionDesignRelRepository::save)
            .map(productOptionDesignRelMapper::toDto);
    }

    /**
     * Get all the productOptionDesignRels.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ProductOptionDesignRelDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ProductOptionDesignRels");
        return productOptionDesignRelRepository.findAll(pageable).map(productOptionDesignRelMapper::toDto);
    }

    /**
     * Get one productOptionDesignRel by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ProductOptionDesignRelDTO> findOne(Long id) {
        log.debug("Request to get ProductOptionDesignRel : {}", id);
        return productOptionDesignRelRepository.findById(id).map(productOptionDesignRelMapper::toDto);
    }

    /**
     * Delete the productOptionDesignRel by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ProductOptionDesignRel : {}", id);
        productOptionDesignRelRepository.deleteById(id);
    }
}
