package com.toy.project.service;

import com.toy.project.domain.ProductClazzRel;
import com.toy.project.repository.ProductClazzRelRepository;
import com.toy.project.service.dto.ProductClazzRelDTO;
import com.toy.project.service.mapper.ProductClazzRelMapper;
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
 * Service Implementation for managing {@link ProductClazzRel}.
 */
@Service
@Transactional
public class ProductClazzRelService {

    private final Logger log = LoggerFactory.getLogger(ProductClazzRelService.class);

    private final ProductClazzRelRepository productClazzRelRepository;

    private final ProductClazzRelMapper productClazzRelMapper;

    public ProductClazzRelService(ProductClazzRelRepository productClazzRelRepository, ProductClazzRelMapper productClazzRelMapper) {
        this.productClazzRelRepository = productClazzRelRepository;
        this.productClazzRelMapper = productClazzRelMapper;
    }

    /**
     * Save a productClazzRel.
     *
     * @param productClazzRelDTO the entity to save.
     * @return the persisted entity.
     */
    public ProductClazzRelDTO save(ProductClazzRelDTO productClazzRelDTO) {
        log.debug("Request to save ProductClazzRel : {}", productClazzRelDTO);
        ProductClazzRel productClazzRel = productClazzRelMapper.toEntity(productClazzRelDTO);
        productClazzRel = productClazzRelRepository.save(productClazzRel);
        return productClazzRelMapper.toDto(productClazzRel);
    }

    public Set<ProductClazzRelDTO> saveAll(Set<ProductClazzRelDTO> productClazzRelDTOS) {
        if (CollectionUtils.isEmpty(productClazzRelDTOS)) {
            return null;
        }
        Set<ProductClazzRel> productClazzRels = productClazzRelDTOS
            .stream()
            .map(productClazzRelMapper::toEntity)
            .collect(Collectors.toSet());
        return productClazzRelRepository.saveAll(productClazzRels).stream().map(productClazzRelMapper::toDto).collect(Collectors.toSet());
    }

    /**
     * Partially update a productClazzRel.
     *
     * @param productClazzRelDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ProductClazzRelDTO> partialUpdate(ProductClazzRelDTO productClazzRelDTO) {
        log.debug("Request to partially update ProductClazzRel : {}", productClazzRelDTO);

        return productClazzRelRepository
            .findById(productClazzRelDTO.getId())
            .map(
                existingProductClazzRel -> {
                    productClazzRelMapper.partialUpdate(existingProductClazzRel, productClazzRelDTO);
                    return existingProductClazzRel;
                }
            )
            .map(productClazzRelRepository::save)
            .map(productClazzRelMapper::toDto);
    }

    /**
     * Get all the productClazzRels.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ProductClazzRelDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ProductClazzRels");
        return productClazzRelRepository.findAll(pageable).map(productClazzRelMapper::toDto);
    }

    /**
     * Get one productClazzRel by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ProductClazzRelDTO> findOne(Long id) {
        log.debug("Request to get ProductClazzRel : {}", id);
        return productClazzRelRepository.findById(id).map(productClazzRelMapper::toDto);
    }

    /**
     * Delete the productClazzRel by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ProductClazzRel : {}", id);
        productClazzRelRepository.deleteById(id);
    }
}
