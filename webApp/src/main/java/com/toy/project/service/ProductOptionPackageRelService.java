package com.toy.project.service;

import com.toy.project.domain.ProductOptionPackageRel;
import com.toy.project.repository.ProductOptionPackageRelRepository;
import com.toy.project.service.dto.ProductOptionPackageRelDTO;
import com.toy.project.service.mapper.ProductOptionPackageRelMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ProductOptionPackageRel}.
 */
@Service
@Transactional
public class ProductOptionPackageRelService {

    private final Logger log = LoggerFactory.getLogger(ProductOptionPackageRelService.class);

    private final ProductOptionPackageRelRepository productOptionPackageRelRepository;

    private final ProductOptionPackageRelMapper productOptionPackageRelMapper;

    public ProductOptionPackageRelService(
        ProductOptionPackageRelRepository productOptionPackageRelRepository,
        ProductOptionPackageRelMapper productOptionPackageRelMapper
    ) {
        this.productOptionPackageRelRepository = productOptionPackageRelRepository;
        this.productOptionPackageRelMapper = productOptionPackageRelMapper;
    }

    /**
     * Save a productOptionPackageRel.
     *
     * @param productOptionPackageRelDTO the entity to save.
     * @return the persisted entity.
     */
    public ProductOptionPackageRelDTO save(ProductOptionPackageRelDTO productOptionPackageRelDTO) {
        log.debug("Request to save ProductOptionPackageRel : {}", productOptionPackageRelDTO);
        ProductOptionPackageRel productOptionPackageRel = productOptionPackageRelMapper.toEntity(productOptionPackageRelDTO);
        productOptionPackageRel = productOptionPackageRelRepository.save(productOptionPackageRel);
        return productOptionPackageRelMapper.toDto(productOptionPackageRel);
    }

    /**
     * Partially update a productOptionPackageRel.
     *
     * @param productOptionPackageRelDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ProductOptionPackageRelDTO> partialUpdate(ProductOptionPackageRelDTO productOptionPackageRelDTO) {
        log.debug("Request to partially update ProductOptionPackageRel : {}", productOptionPackageRelDTO);

        return productOptionPackageRelRepository
            .findById(productOptionPackageRelDTO.getId())
            .map(
                existingProductOptionPackageRel -> {
                    productOptionPackageRelMapper.partialUpdate(existingProductOptionPackageRel, productOptionPackageRelDTO);
                    return existingProductOptionPackageRel;
                }
            )
            .map(productOptionPackageRelRepository::save)
            .map(productOptionPackageRelMapper::toDto);
    }

    /**
     * Get all the productOptionPackageRels.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ProductOptionPackageRelDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ProductOptionPackageRels");
        return productOptionPackageRelRepository.findAll(pageable).map(productOptionPackageRelMapper::toDto);
    }

    /**
     * Get one productOptionPackageRel by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ProductOptionPackageRelDTO> findOne(Long id) {
        log.debug("Request to get ProductOptionPackageRel : {}", id);
        return productOptionPackageRelRepository.findById(id).map(productOptionPackageRelMapper::toDto);
    }

    /**
     * Delete the productOptionPackageRel by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ProductOptionPackageRel : {}", id);
        productOptionPackageRelRepository.deleteById(id);
    }
}
