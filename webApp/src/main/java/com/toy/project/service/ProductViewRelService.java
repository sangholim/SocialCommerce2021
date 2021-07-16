package com.toy.project.service;

import com.toy.project.domain.ProductViewRel;
import com.toy.project.repository.ProductViewRelRepository;
import com.toy.project.service.dto.ProductViewDTO;
import com.toy.project.service.dto.ProductViewRelDTO;
import com.toy.project.service.mapper.ProductViewRelMapper;
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
 * Service Implementation for managing {@link ProductViewRel}.
 */
@Service
@Transactional
public class ProductViewRelService {

    private final Logger log = LoggerFactory.getLogger(ProductViewRelService.class);

    private final ProductViewRelRepository productViewRelRepository;

    private final ProductViewRelMapper productViewRelMapper;

    public ProductViewRelService(ProductViewRelRepository productViewRelRepository, ProductViewRelMapper productViewRelMapper) {
        this.productViewRelRepository = productViewRelRepository;
        this.productViewRelMapper = productViewRelMapper;
    }

    /**
     * Save a productViewRel.
     *
     * @param productViewRelDTO the entity to save.
     * @return the persisted entity.
     */
    public ProductViewRelDTO save(ProductViewRelDTO productViewRelDTO) {
        log.debug("Request to save ProductViewRel : {}", productViewRelDTO);
        ProductViewRel productViewRel = productViewRelMapper.toEntity(productViewRelDTO);
        productViewRel = productViewRelRepository.save(productViewRel);
        return productViewRelMapper.toDto(productViewRel);
    }

    public Set<ProductViewRelDTO> saveAll(Set<ProductViewRelDTO> productViewRelDTOS) {
        if (CollectionUtils.isEmpty(productViewRelDTOS)) {
            return null;
        }
        Set<ProductViewRel> productViewRels = productViewRelDTOS.stream().map(productViewRelMapper::toEntity).collect(Collectors.toSet());
        return productViewRelRepository.saveAll(productViewRels).stream().map(productViewRelMapper::toDto).collect(Collectors.toSet());
    }

    public Set<ProductViewRelDTO> toProductViewRelDTOSet(Long productId, Boolean activated, Collection<ProductViewDTO> productViewDTOS) {
        if (CollectionUtils.isEmpty(productViewDTOS)) {
            return null;
        }
        return productViewDTOS
            .stream()
            .filter(Objects::nonNull)
            .map(productViewDTO -> new ProductViewRelDTO(null, productId, productViewDTO.getId(), activated))
            .collect(Collectors.toSet());
    }

    /**
     * Partially update a productViewRel.
     *
     * @param productViewRelDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ProductViewRelDTO> partialUpdate(ProductViewRelDTO productViewRelDTO) {
        log.debug("Request to partially update ProductViewRel : {}", productViewRelDTO);

        return productViewRelRepository
            .findById(productViewRelDTO.getId())
            .map(
                existingProductViewRel -> {
                    productViewRelMapper.partialUpdate(existingProductViewRel, productViewRelDTO);
                    return existingProductViewRel;
                }
            )
            .map(productViewRelRepository::save)
            .map(productViewRelMapper::toDto);
    }

    /**
     * Get all the productViewRels.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ProductViewRelDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ProductViewRels");
        return productViewRelRepository.findAll(pageable).map(productViewRelMapper::toDto);
    }

    /**
     * Get one productViewRel by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ProductViewRelDTO> findOne(Long id) {
        log.debug("Request to get ProductViewRel : {}", id);
        return productViewRelRepository.findById(id).map(productViewRelMapper::toDto);
    }

    /**
     * Delete the productViewRel by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ProductViewRel : {}", id);
        productViewRelRepository.deleteById(id);
    }
}
