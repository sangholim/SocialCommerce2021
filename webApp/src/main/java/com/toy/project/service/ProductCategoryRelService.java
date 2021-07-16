package com.toy.project.service;

import com.toy.project.domain.ProductCategoryRel;
import com.toy.project.repository.ProductCategoryRelRepository;
import com.toy.project.service.dto.ProductCategoryDTO;
import com.toy.project.service.dto.ProductCategoryRelDTO;
import com.toy.project.service.mapper.ProductCategoryRelMapper;
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
 * Service Implementation for managing {@link ProductCategoryRel}.
 */
@Service
@Transactional
public class ProductCategoryRelService {

    private final Logger log = LoggerFactory.getLogger(ProductCategoryRelService.class);

    private final ProductCategoryRelRepository productCategoryRelRepository;

    private final ProductCategoryRelMapper productCategoryRelMapper;

    public ProductCategoryRelService(
        ProductCategoryRelRepository productCategoryRelRepository,
        ProductCategoryRelMapper productCategoryRelMapper
    ) {
        this.productCategoryRelRepository = productCategoryRelRepository;
        this.productCategoryRelMapper = productCategoryRelMapper;
    }

    public Set<ProductCategoryRelDTO> saveAll(Set<ProductCategoryRelDTO> productCategoryRelDTOs) {
        if (CollectionUtils.isEmpty(productCategoryRelDTOs)) {
            return null;
        }
        Set<ProductCategoryRel> productCategoryRels = productCategoryRelDTOs
            .stream()
            .map(productCategoryRelMapper::toEntity)
            .collect(Collectors.toSet());
        return productCategoryRelRepository
            .saveAll(productCategoryRels)
            .stream()
            .map(productCategoryRelMapper::toDto)
            .collect(Collectors.toSet());
    }

    public ProductCategoryRelDTO save(ProductCategoryRelDTO productCategoryRelDTO) {
        log.debug("Request to save ProductCategoryRel : {}", productCategoryRelDTO);
        ProductCategoryRel productCategoryRel = productCategoryRelMapper.toEntity(productCategoryRelDTO);
        productCategoryRel = productCategoryRelRepository.save(productCategoryRel);
        return productCategoryRelMapper.toDto(productCategoryRel);
    }

    public Set<ProductCategoryRelDTO> toProductCategoryRelDTOSet(
        Long productId,
        Boolean activated,
        Collection<ProductCategoryDTO> productCategoryDTOs
    ) {
        if (CollectionUtils.isEmpty(productCategoryDTOs)) {
            return null;
        }
        return productCategoryDTOs
            .stream()
            .filter(Objects::nonNull)
            .map(productCategoryDTO -> new ProductCategoryRelDTO(null, productId, productCategoryDTO.getId(), activated))
            .collect(Collectors.toSet());
    }

    public Optional<ProductCategoryRelDTO> partialUpdate(ProductCategoryRelDTO productCategoryRelDTO) {
        log.debug("Request to partially update ProductCategoryRel : {}", productCategoryRelDTO);

        return productCategoryRelRepository
            .findById(productCategoryRelDTO.getId())
            .map(
                existingProductCategoryRel -> {
                    productCategoryRelMapper.partialUpdate(existingProductCategoryRel, productCategoryRelDTO);
                    return existingProductCategoryRel;
                }
            )
            .map(productCategoryRelRepository::save)
            .map(productCategoryRelMapper::toDto);
    }

    /**
     * Get all the productCategoryRels.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ProductCategoryRelDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ProductCategoryRels");
        return productCategoryRelRepository.findAll(pageable).map(productCategoryRelMapper::toDto);
    }

    /**
     * Get one productCategoryRel by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ProductCategoryRelDTO> findOne(Long id) {
        log.debug("Request to get ProductCategoryRel : {}", id);
        return productCategoryRelRepository.findById(id).map(productCategoryRelMapper::toDto);
    }

    /**
     * Delete the productCategoryRel by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ProductCategoryRel : {}", id);
        productCategoryRelRepository.deleteById(id);
    }
}
