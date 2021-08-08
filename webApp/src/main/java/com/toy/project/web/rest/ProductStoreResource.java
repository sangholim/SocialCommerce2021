package com.toy.project.web.rest;

import com.toy.project.repository.ProductStoreRepository;
import com.toy.project.service.ProductStoreService;
import com.toy.project.service.dto.ProductStoreDTO;
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
 * REST controller for managing {@link com.toy.project.domain.ProductStore}.
 */
@RestController
@RequestMapping("/api")
public class ProductStoreResource {

    private final Logger log = LoggerFactory.getLogger(ProductStoreResource.class);

    private static final String ENTITY_NAME = "productStore";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProductStoreService productStoreService;

    private final ProductStoreRepository productStoreRepository;

    public ProductStoreResource(ProductStoreService productStoreService, ProductStoreRepository productStoreRepository) {
        this.productStoreService = productStoreService;
        this.productStoreRepository = productStoreRepository;
    }

    /**
     * {@code POST  /product-stores} : Create a new productStore.
     *
     * @param productStoreDTO the productStoreDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new productStoreDTO, or with status {@code 400 (Bad Request)} if the productStore has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/product-stores")
    public ResponseEntity<ProductStoreDTO> createProductStore(@Valid @RequestBody ProductStoreDTO productStoreDTO)
        throws URISyntaxException {
        log.debug("REST request to save ProductStore : {}", productStoreDTO);
        if (productStoreDTO.getId() != null) {
            throw new BadRequestAlertException("A new productStore cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductStoreDTO result = productStoreService.save(productStoreDTO);
        return ResponseEntity
            .created(new URI("/api/product-stores/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /product-stores/:id} : Updates an existing productStore.
     *
     * @param id the id of the productStoreDTO to save.
     * @param productStoreDTO the productStoreDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productStoreDTO,
     * or with status {@code 400 (Bad Request)} if the productStoreDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the productStoreDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/product-stores/{id}")
    public ResponseEntity<ProductStoreDTO> updateProductStore(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ProductStoreDTO productStoreDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ProductStore : {}, {}", id, productStoreDTO);
        if (productStoreDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, productStoreDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!productStoreRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ProductStoreDTO result = productStoreService.save(productStoreDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productStoreDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /product-stores/:id} : Partial updates given fields of an existing productStore, field will ignore if it is null
     *
     * @param id the id of the productStoreDTO to save.
     * @param productStoreDTO the productStoreDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productStoreDTO,
     * or with status {@code 400 (Bad Request)} if the productStoreDTO is not valid,
     * or with status {@code 404 (Not Found)} if the productStoreDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the productStoreDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/product-stores/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<ProductStoreDTO> partialUpdateProductStore(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ProductStoreDTO productStoreDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ProductStore partially : {}, {}", id, productStoreDTO);
        if (productStoreDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, productStoreDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!productStoreRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ProductStoreDTO> result = productStoreService.partialUpdate(productStoreDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productStoreDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /product-stores} : get all the productStores.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of productStores in body.
     */
    @GetMapping("/product-stores")
    public ResponseEntity<List<ProductStoreDTO>> getAllProductStores(Pageable pageable) {
        log.debug("REST request to get a page of ProductStores");
        Page<ProductStoreDTO> page = productStoreService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /product-stores/:id} : get the "id" productStore.
     *
     * @param id the id of the productStoreDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the productStoreDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/product-stores/{id}")
    public ResponseEntity<ProductStoreDTO> getProductStore(@PathVariable Long id) {
        log.debug("REST request to get ProductStore : {}", id);
        Optional<ProductStoreDTO> productStoreDTO = productStoreService.findOne(id);
        return ResponseUtil.wrapOrNotFound(productStoreDTO);
    }

    /**
     * {@code DELETE  /product-stores/:id} : delete the "id" productStore.
     *
     * @param id the id of the productStoreDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/product-stores/{id}")
    public ResponseEntity<Void> deleteProductStore(@PathVariable Long id) {
        log.debug("REST request to delete ProductStore : {}", id);
        productStoreService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
