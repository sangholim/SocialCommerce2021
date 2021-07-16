package com.toy.project.service;

import com.toy.project.domain.ProductMappingRel;
import com.toy.project.repository.ProductMappingRelRepository;
import com.toy.project.service.dto.ProductMappingDTO;
import com.toy.project.service.dto.ProductMappingRelDTO;
import com.toy.project.service.mapper.ProductMappingRelMapper;
import java.util.Collection;
import java.util.Objects;
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
 * Service Implementation for managing {@link ProductMappingRel}.
 */
@Service
@Transactional
public class ProductMappingRelService {

    private final Logger log = LoggerFactory.getLogger(ProductMappingRelService.class);

    private final ProductMappingRelRepository productMappingRelRepository;

    private final ProductMappingRelMapper productMappingRelMapper;

    public ProductMappingRelService(
        ProductMappingRelRepository productMappingRelRepository,
        ProductMappingRelMapper productMappingRelMapper
    ) {
        this.productMappingRelRepository = productMappingRelRepository;
        this.productMappingRelMapper = productMappingRelMapper;
    }

    /**
     * Save a productMappingRel.
     *
     * @param productMappingRelDTO the entity to save.
     * @return the persisted entity.
     */
    public ProductMappingRelDTO save(ProductMappingRelDTO productMappingRelDTO) {
        log.debug("Request to save ProductMappingRel : {}", productMappingRelDTO);
        ProductMappingRel productMappingRel = productMappingRelMapper.toEntity(productMappingRelDTO);
        productMappingRel = productMappingRelRepository.save(productMappingRel);
        return productMappingRelMapper.toDto(productMappingRel);
    }

    public Set<ProductMappingRelDTO> saveAll(Set<ProductMappingRelDTO> productMappingRelDTOS) {
        if (CollectionUtils.isEmpty(productMappingRelDTOS)) {
            return null;
        }
        Set<ProductMappingRel> productMappingRels = productMappingRelDTOS
            .stream()
            .map(productMappingRelMapper::toEntity)
            .collect(Collectors.toSet());
        return productMappingRelRepository
            .saveAll(productMappingRels)
            .stream()
            .map(productMappingRelMapper::toDto)
            .collect(Collectors.toSet());
    }

    public Set<ProductMappingRelDTO> toProductMappingRelDTOSet(
        Long productId,
        Boolean activated,
        Collection<ProductMappingDTO> productMappingDTOS
    ) {
        if (CollectionUtils.isEmpty(productMappingDTOS)) {
            return null;
        }
        return productMappingDTOS
            .stream()
            .filter(Objects::nonNull)
            .map(productMappingDTO -> new ProductMappingRelDTO(null, productId, productMappingDTO.getId(), activated))
            .collect(Collectors.toSet());
    }

    /**
     * Partially update a productMappingRel.
     *
     * @param productMappingRelDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ProductMappingRelDTO> partialUpdate(ProductMappingRelDTO productMappingRelDTO) {
        log.debug("Request to partially update ProductMappingRel : {}", productMappingRelDTO);

        return productMappingRelRepository
            .findById(productMappingRelDTO.getId())
            .map(
                existingProductMappingRel -> {
                    productMappingRelMapper.partialUpdate(existingProductMappingRel, productMappingRelDTO);
                    return existingProductMappingRel;
                }
            )
            .map(productMappingRelRepository::save)
            .map(productMappingRelMapper::toDto);
    }

    /**
     * Get all the productMappingRels.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ProductMappingRelDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ProductMappingRels");
        return productMappingRelRepository.findAll(pageable).map(productMappingRelMapper::toDto);
    }

    /**
     * Get one productMappingRel by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ProductMappingRelDTO> findOne(Long id) {
        log.debug("Request to get ProductMappingRel : {}", id);
        return productMappingRelRepository.findById(id).map(productMappingRelMapper::toDto);
    }

    /**
     * Delete the productMappingRel by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ProductMappingRel : {}", id);
        productMappingRelRepository.deleteById(id);
    }
}
