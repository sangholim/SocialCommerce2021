package com.toy.project.service;

import com.toy.project.domain.ProductShippingRel;
import com.toy.project.repository.ProductShippingRelRepository;
import com.toy.project.service.dto.ProductShippingRelDTO;
import com.toy.project.service.mapper.ProductShippingRelMapper;
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
 * Service Implementation for managing {@link ProductShippingRel}.
 */
@Service
@Transactional
public class ProductShippingRelService {

    private final Logger log = LoggerFactory.getLogger(ProductShippingRelService.class);

    private final ProductShippingRelRepository productShippingRelRepository;

    private final ProductShippingRelMapper productShippingRelMapper;

    public ProductShippingRelService(
        ProductShippingRelRepository productShippingRelRepository,
        ProductShippingRelMapper productShippingRelMapper
    ) {
        this.productShippingRelRepository = productShippingRelRepository;
        this.productShippingRelMapper = productShippingRelMapper;
    }

    /**
     * Save a productShippingRel.
     *
     * @param productShippingRelDTO the entity to save.
     * @return the persisted entity.
     */
    public ProductShippingRelDTO save(ProductShippingRelDTO productShippingRelDTO) {
        log.debug("Request to save ProductShippingRel : {}", productShippingRelDTO);
        ProductShippingRel productShippingRel = productShippingRelMapper.toEntity(productShippingRelDTO);
        productShippingRel = productShippingRelRepository.save(productShippingRel);
        return productShippingRelMapper.toDto(productShippingRel);
    }

    public Set<ProductShippingRelDTO> saveAll(Set<ProductShippingRelDTO> productShippingRelDTOS) {
        if (CollectionUtils.isEmpty(productShippingRelDTOS)) {
            return null;
        }
        Set<ProductShippingRel> productShippingRels = productShippingRelDTOS
            .stream()
            .map(productShippingRelMapper::toEntity)
            .collect(Collectors.toSet());
        return productShippingRelRepository
            .saveAll(productShippingRels)
            .stream()
            .map(productShippingRelMapper::toDto)
            .collect(Collectors.toSet());
    }

    /**
     * Partially update a productShippingRel.
     *
     * @param productShippingRelDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ProductShippingRelDTO> partialUpdate(ProductShippingRelDTO productShippingRelDTO) {
        log.debug("Request to partially update ProductShippingRel : {}", productShippingRelDTO);

        return productShippingRelRepository
            .findById(productShippingRelDTO.getId())
            .map(
                existingProductShippingRel -> {
                    productShippingRelMapper.partialUpdate(existingProductShippingRel, productShippingRelDTO);
                    return existingProductShippingRel;
                }
            )
            .map(productShippingRelRepository::save)
            .map(productShippingRelMapper::toDto);
    }

    /**
     * Get all the productShippingRels.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ProductShippingRelDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ProductShippingRels");
        return productShippingRelRepository.findAll(pageable).map(productShippingRelMapper::toDto);
    }

    /**
     * Get one productShippingRel by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ProductShippingRelDTO> findOne(Long id) {
        log.debug("Request to get ProductShippingRel : {}", id);
        return productShippingRelRepository.findById(id).map(productShippingRelMapper::toDto);
    }

    /**
     * Delete the productShippingRel by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ProductShippingRel : {}", id);
        productShippingRelRepository.deleteById(id);
    }
}
