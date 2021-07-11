package com.toy.project.service;

import com.toy.project.domain.Product;
import com.toy.project.repository.ProductRepository;
import com.toy.project.service.dto.ProductDTO;
import com.toy.project.service.mapper.ProductMapper;
import com.toy.project.web.rest.vm.ProductVM;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Product}.
 */
@Service
@Transactional
public class ProductService {

    private final Logger log = LoggerFactory.getLogger(ProductService.class);

    private final ProductRepository productRepository;

    private final ProductMapper productMapper;

    public ProductService(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    public ProductDTO save(ProductDTO productDTO) {
        log.debug("Request to save Product : {}", productDTO);
        Product product = productMapper.toEntity(productDTO);
        product = productRepository.save(product);
        return productMapper.toDto(product);
    }

    public Optional<ProductDTO> partialUpdate(ProductDTO productDTO) {
        log.debug("Request to partially update Product : {}", productDTO);

        return productRepository
            .findById(productDTO.getId())
            .map(
                existingProduct -> {
                    productMapper.partialUpdate(existingProduct, productDTO);
                    return existingProduct;
                }
            )
            .map(productRepository::save)
            .map(productMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Page<ProductDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Products");
        return productRepository.findAll(pageable).map(productMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Optional<ProductVM> findOne(Long id) {
        log.debug("Request to get Product : {}", id);

        return Optional.of(productRepository.findOneById(id)).map(ProductVM::new);
    }

    public void delete(Long id) {
        log.debug("Request to delete Product : {}", id);
        productRepository.deleteById(id);
    }
}
