package com.toy.project.service;

import com.toy.project.domain.ProductLabelRel;
import com.toy.project.repository.ProductLabelRelRepository;
import com.toy.project.service.dto.ProductLabelExtendDTO;
import com.toy.project.service.dto.ProductLabelRelDTO;
import com.toy.project.service.mapper.ProductLabelRelMapper;
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
 * Service Implementation for managing {@link ProductLabelRel}.
 */
@Service
@Transactional
public class ProductLabelRelService {

    private final Logger log = LoggerFactory.getLogger(ProductLabelRelService.class);

    private final ProductLabelRelRepository productLabelRelRepository;

    private final ProductLabelRelMapper productLabelRelMapper;

    public ProductLabelRelService(ProductLabelRelRepository productLabelRelRepository, ProductLabelRelMapper productLabelRelMapper) {
        this.productLabelRelRepository = productLabelRelRepository;
        this.productLabelRelMapper = productLabelRelMapper;
    }

    /**
     * Save a productLabelRel.
     *
     * @param productLabelRelDTO the entity to save.
     * @return the persisted entity.
     */
    public ProductLabelRelDTO save(ProductLabelRelDTO productLabelRelDTO) {
        log.debug("Request to save ProductLabelRel : {}", productLabelRelDTO);
        ProductLabelRel productLabelRel = productLabelRelMapper.toEntity(productLabelRelDTO);
        productLabelRel = productLabelRelRepository.save(productLabelRel);
        return productLabelRelMapper.toDto(productLabelRel);
    }

    /**
     * Partially update a productLabelRel.
     *
     * @param productLabelRelDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ProductLabelRelDTO> partialUpdate(ProductLabelRelDTO productLabelRelDTO) {
        log.debug("Request to partially update ProductLabelRel : {}", productLabelRelDTO);

        return productLabelRelRepository
            .findById(productLabelRelDTO.getId())
            .map(
                existingProductLabelRel -> {
                    productLabelRelMapper.partialUpdate(existingProductLabelRel, productLabelRelDTO);
                    return existingProductLabelRel;
                }
            )
            .map(productLabelRelRepository::save)
            .map(productLabelRelMapper::toDto);
    }

    /**
     * Get all the productLabelRels.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ProductLabelRelDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ProductLabelRels");
        return productLabelRelRepository.findAll(pageable).map(productLabelRelMapper::toDto);
    }

    /**
     * Get one productLabelRel by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ProductLabelRelDTO> findOne(Long id) {
        log.debug("Request to get ProductLabelRel : {}", id);
        return productLabelRelRepository.findById(id).map(productLabelRelMapper::toDto);
    }

    /**
     * Delete the productLabelRel by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ProductLabelRel : {}", id);
        productLabelRelRepository.deleteById(id);
    }

    public Set<ProductLabelRelDTO> saveAll(Set<ProductLabelRelDTO> productLabelRelDTOS) {
        if (CollectionUtils.isEmpty(productLabelRelDTOS)) {
            return null;
        }
        Set<ProductLabelRel> productLabelRels = productLabelRelDTOS
            .stream()
            .map(productLabelRelMapper::toEntity)
            .collect(Collectors.toSet());
        return productLabelRelRepository.saveAll(productLabelRels).stream().map(productLabelRelMapper::toDto).collect(Collectors.toSet());
    }

    public Set<ProductLabelRelDTO> toProductLabelRelDTOSet(
        Long productId,
        Boolean activated,
        Collection<ProductLabelExtendDTO> productLabelExtendDTOS
    ) {
        if (CollectionUtils.isEmpty(productLabelExtendDTOS)) {
            return null;
        }

        return productLabelExtendDTOS
            .stream()
            .filter(Objects::nonNull)
            .map(
                productLabelExtendDTO ->
                    new ProductLabelRelDTO(
                        null,
                        productId,
                        productLabelExtendDTO.getId(),
                        productLabelExtendDTO.getDisplayDate(),
                        productLabelExtendDTO.getDisplayDateFrom(),
                        productLabelExtendDTO.getDisplayDateTo(),
                        true
                    )
            )
            .collect(Collectors.toSet());
    }
}
