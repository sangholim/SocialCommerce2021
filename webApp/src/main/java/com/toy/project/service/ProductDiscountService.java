package com.toy.project.service;

import com.toy.project.domain.ProductDiscount;
import com.toy.project.repository.ProductDiscountRepository;
import com.toy.project.service.dto.ProductDiscountDTO;
import com.toy.project.service.mapper.ProductDiscountMapper;
import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.constraints.Size;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

/**
 * Service Implementation for managing {@link ProductDiscount}.
 */
@Service
@Transactional
public class ProductDiscountService {

    private final Logger log = LoggerFactory.getLogger(ProductDiscountService.class);

    private final ProductDiscountRepository productDiscountRepository;

    private final ProductDiscountMapper productDiscountMapper;

    public ProductDiscountService(ProductDiscountRepository productDiscountRepository, ProductDiscountMapper productDiscountMapper) {
        this.productDiscountRepository = productDiscountRepository;
        this.productDiscountMapper = productDiscountMapper;
    }

    public ProductDiscountDTO save(ProductDiscountDTO productDiscountDTO) {
        log.debug("Request to save ProductDiscount : {}", productDiscountDTO);
        ProductDiscount productDiscount = productDiscountMapper.toEntity(productDiscountDTO);
        productDiscount = productDiscountRepository.save(productDiscount);
        return productDiscountMapper.toDto(productDiscount);
    }

    /**
     * Partially update a productDiscount.
     *
     * @param productDiscountDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ProductDiscountDTO> partialUpdate(ProductDiscountDTO productDiscountDTO) {
        log.debug("Request to partially update ProductDiscount : {}", productDiscountDTO);

        return productDiscountRepository
            .findById(productDiscountDTO.getId())
            .map(
                existingProductDiscount -> {
                    productDiscountMapper.partialUpdate(existingProductDiscount, productDiscountDTO);
                    return existingProductDiscount;
                }
            )
            .map(productDiscountRepository::save)
            .map(productDiscountMapper::toDto);
    }

    /**
     * Get all the productDiscounts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ProductDiscountDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ProductDiscounts");
        return productDiscountRepository.findAll(pageable).map(productDiscountMapper::toDto);
    }

    /**
     * Get one productDiscount by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ProductDiscountDTO> findOne(Long id) {
        log.debug("Request to get ProductDiscount : {}", id);
        return productDiscountRepository.findById(id).map(productDiscountMapper::toDto);
    }

    /**
     * Delete the productDiscount by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ProductDiscount : {}", id);
        productDiscountRepository.deleteById(id);
    }
}
