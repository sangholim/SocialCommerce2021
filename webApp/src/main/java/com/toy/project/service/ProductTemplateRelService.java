package com.toy.project.service;

import com.toy.project.domain.ProductTemplateRel;
import com.toy.project.repository.ProductTemplateRelRepository;
import com.toy.project.service.dto.ProductTemplateDTO;
import com.toy.project.service.dto.ProductTemplateRelDTO;
import com.toy.project.service.mapper.ProductTemplateRelMapper;
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
 * Service Implementation for managing {@link ProductTemplateRel}.
 */
@Service
@Transactional
public class ProductTemplateRelService {

    private final Logger log = LoggerFactory.getLogger(ProductTemplateRelService.class);

    private final ProductTemplateRelRepository productTemplateRelRepository;

    private final ProductTemplateRelMapper productTemplateRelMapper;

    public ProductTemplateRelService(
        ProductTemplateRelRepository productTemplateRelRepository,
        ProductTemplateRelMapper productTemplateRelMapper
    ) {
        this.productTemplateRelRepository = productTemplateRelRepository;
        this.productTemplateRelMapper = productTemplateRelMapper;
    }

    /**
     * Save a productTemplateRel.
     *
     * @param productTemplateRelDTO the entity to save.
     * @return the persisted entity.
     */
    public ProductTemplateRelDTO save(ProductTemplateRelDTO productTemplateRelDTO) {
        log.debug("Request to save ProductTemplateRel : {}", productTemplateRelDTO);
        ProductTemplateRel productTemplateRel = productTemplateRelMapper.toEntity(productTemplateRelDTO);
        productTemplateRel = productTemplateRelRepository.save(productTemplateRel);
        return productTemplateRelMapper.toDto(productTemplateRel);
    }

    public Set<ProductTemplateRelDTO> saveAll(Set<ProductTemplateRelDTO> productTemplateRelDTOS) {
        if (CollectionUtils.isEmpty(productTemplateRelDTOS)) {
            return null;
        }
        Set<ProductTemplateRel> productTemplateRels = productTemplateRelDTOS
            .stream()
            .map(productTemplateRelMapper::toEntity)
            .collect(Collectors.toSet());
        return productTemplateRelRepository
            .saveAll(productTemplateRels)
            .stream()
            .map(productTemplateRelMapper::toDto)
            .collect(Collectors.toSet());
    }

    public Set<ProductTemplateRelDTO> toProductTemplateRelDTOSet(
        Long productId,
        Boolean activated,
        Collection<ProductTemplateDTO> productTemplateDTOS
    ) {
        if (CollectionUtils.isEmpty(productTemplateDTOS)) {
            return null;
        }
        return productTemplateDTOS
            .stream()
            .filter(Objects::nonNull)
            .map(productTemplateDTO -> new ProductTemplateRelDTO(null, productId, productTemplateDTO.getId(), activated))
            .collect(Collectors.toSet());
    }

    /**
     * Partially update a productTemplateRel.
     *
     * @param productTemplateRelDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ProductTemplateRelDTO> partialUpdate(ProductTemplateRelDTO productTemplateRelDTO) {
        log.debug("Request to partially update ProductTemplateRel : {}", productTemplateRelDTO);

        return productTemplateRelRepository
            .findById(productTemplateRelDTO.getId())
            .map(
                existingProductTemplateRel -> {
                    productTemplateRelMapper.partialUpdate(existingProductTemplateRel, productTemplateRelDTO);
                    return existingProductTemplateRel;
                }
            )
            .map(productTemplateRelRepository::save)
            .map(productTemplateRelMapper::toDto);
    }

    /**
     * Get all the productTemplateRels.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ProductTemplateRelDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ProductTemplateRels");
        return productTemplateRelRepository.findAll(pageable).map(productTemplateRelMapper::toDto);
    }

    /**
     * Get one productTemplateRel by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ProductTemplateRelDTO> findOne(Long id) {
        log.debug("Request to get ProductTemplateRel : {}", id);
        return productTemplateRelRepository.findById(id).map(productTemplateRelMapper::toDto);
    }

    /**
     * Delete the productTemplateRel by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ProductTemplateRel : {}", id);
        productTemplateRelRepository.deleteById(id);
    }
}
