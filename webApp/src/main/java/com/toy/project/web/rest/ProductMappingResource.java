package com.toy.project.web.rest;

import com.toy.project.repository.ProductMappingRepository;
import com.toy.project.service.ProductMappingService;
import com.toy.project.service.dto.ProductMappingDTO;
import com.toy.project.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.toy.project.domain.ProductMapping}.
 */
@RestController
@RequestMapping("/api")
public class ProductMappingResource {

    private final Logger log = LoggerFactory.getLogger(ProductMappingResource.class);

    private static final String ENTITY_NAME = "productMapping";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProductMappingService productMappingService;

    private final ProductMappingRepository productMappingRepository;

    public ProductMappingResource(ProductMappingService productMappingService, ProductMappingRepository productMappingRepository) {
        this.productMappingService = productMappingService;
        this.productMappingRepository = productMappingRepository;
    }

    /**
     * {@code POST  /product-mappings} : Create a new productMapping.
     *
     * @param productMappingDTO the productMappingDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new productMappingDTO, or with status {@code 400 (Bad Request)} if the productMapping has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/product-mappings")
    public ResponseEntity<ProductMappingDTO> createProductMapping(@Valid @RequestBody ProductMappingDTO productMappingDTO)
        throws URISyntaxException {
        log.debug("REST request to save ProductMapping : {}", productMappingDTO);
        if (productMappingDTO.getId() != null) {
            throw new BadRequestAlertException("A new productMapping cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductMappingDTO result = productMappingService.save(productMappingDTO);
        return ResponseEntity
            .created(new URI("/api/product-mappings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /product-mappings/:id} : Updates an existing productMapping.
     *
     * @param id the id of the productMappingDTO to save.
     * @param productMappingDTO the productMappingDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productMappingDTO,
     * or with status {@code 400 (Bad Request)} if the productMappingDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the productMappingDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/product-mappings/{id}")
    public ResponseEntity<ProductMappingDTO> updateProductMapping(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ProductMappingDTO productMappingDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ProductMapping : {}, {}", id, productMappingDTO);
        if (productMappingDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, productMappingDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!productMappingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ProductMappingDTO result = productMappingService.save(productMappingDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productMappingDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /product-mappings/:id} : Partial updates given fields of an existing productMapping, field will ignore if it is null
     *
     * @param id the id of the productMappingDTO to save.
     * @param productMappingDTO the productMappingDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productMappingDTO,
     * or with status {@code 400 (Bad Request)} if the productMappingDTO is not valid,
     * or with status {@code 404 (Not Found)} if the productMappingDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the productMappingDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/product-mappings/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<ProductMappingDTO> partialUpdateProductMapping(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ProductMappingDTO productMappingDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ProductMapping partially : {}, {}", id, productMappingDTO);
        if (productMappingDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, productMappingDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!productMappingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ProductMappingDTO> result = productMappingService.partialUpdate(productMappingDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productMappingDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /product-mappings} : get all the productMappings.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of productMappings in body.
     */
    @GetMapping("/product-mappings")
    public ResponseEntity<List<ProductMappingDTO>> getAllProductMappings(Pageable pageable) {
        log.debug("REST request to get a page of ProductMappings");
        Page<ProductMappingDTO> page = productMappingService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /product-mappings/:id} : get the "id" productMapping.
     *
     * @param id the id of the productMappingDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the productMappingDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/product-mappings/{id}")
    public ResponseEntity<ProductMappingDTO> getProductMapping(@PathVariable Long id) {
        log.debug("REST request to get ProductMapping : {}", id);
        Optional<ProductMappingDTO> productMappingDTO = productMappingService.findOne(id);
        return ResponseUtil.wrapOrNotFound(productMappingDTO);
    }

    /**
     * {@code DELETE  /product-mappings/:id} : delete the "id" productMapping.
     *
     * @param id the id of the productMappingDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/product-mappings/{id}")
    public ResponseEntity<Void> deleteProductMapping(@PathVariable Long id) {
        log.debug("REST request to delete ProductMapping : {}", id);
        productMappingService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
