package com.toy.project.web.rest;

import com.toy.project.repository.ProductStoreRelRepository;
import com.toy.project.service.ProductStoreRelQueryService;
import com.toy.project.service.ProductStoreRelService;
import com.toy.project.service.criteria.ProductStoreRelCriteria;
import com.toy.project.service.dto.ProductStoreRelDTO;
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
 * REST controller for managing {@link com.toy.project.domain.ProductStoreRel}.
 */
@RestController
@RequestMapping("/api")
public class ProductStoreRelResource {

    private final Logger log = LoggerFactory.getLogger(ProductStoreRelResource.class);

    private static final String ENTITY_NAME = "productStoreRel";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProductStoreRelService productStoreRelService;

    private final ProductStoreRelRepository productStoreRelRepository;

    private final ProductStoreRelQueryService productStoreRelQueryService;

    public ProductStoreRelResource(
        ProductStoreRelService productStoreRelService,
        ProductStoreRelRepository productStoreRelRepository,
        ProductStoreRelQueryService productStoreRelQueryService
    ) {
        this.productStoreRelService = productStoreRelService;
        this.productStoreRelRepository = productStoreRelRepository;
        this.productStoreRelQueryService = productStoreRelQueryService;
    }

    /**
     * {@code POST  /product-store-rels} : Create a new productStoreRel.
     *
     * @param productStoreRelDTO the productStoreRelDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new productStoreRelDTO, or with status {@code 400 (Bad Request)} if the productStoreRel has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/product-store-rels")
    public ResponseEntity<ProductStoreRelDTO> createProductStoreRel(@Valid @RequestBody ProductStoreRelDTO productStoreRelDTO)
        throws URISyntaxException {
        log.debug("REST request to save ProductStoreRel : {}", productStoreRelDTO);
        if (productStoreRelDTO.getId() != null) {
            throw new BadRequestAlertException("A new productStoreRel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductStoreRelDTO result = productStoreRelService.save(productStoreRelDTO);
        return ResponseEntity
            .created(new URI("/api/product-store-rels/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /product-store-rels/:id} : Updates an existing productStoreRel.
     *
     * @param id the id of the productStoreRelDTO to save.
     * @param productStoreRelDTO the productStoreRelDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productStoreRelDTO,
     * or with status {@code 400 (Bad Request)} if the productStoreRelDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the productStoreRelDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/product-store-rels/{id}")
    public ResponseEntity<ProductStoreRelDTO> updateProductStoreRel(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ProductStoreRelDTO productStoreRelDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ProductStoreRel : {}, {}", id, productStoreRelDTO);
        if (productStoreRelDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, productStoreRelDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!productStoreRelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ProductStoreRelDTO result = productStoreRelService.save(productStoreRelDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productStoreRelDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /product-store-rels/:id} : Partial updates given fields of an existing productStoreRel, field will ignore if it is null
     *
     * @param id the id of the productStoreRelDTO to save.
     * @param productStoreRelDTO the productStoreRelDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productStoreRelDTO,
     * or with status {@code 400 (Bad Request)} if the productStoreRelDTO is not valid,
     * or with status {@code 404 (Not Found)} if the productStoreRelDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the productStoreRelDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/product-store-rels/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<ProductStoreRelDTO> partialUpdateProductStoreRel(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ProductStoreRelDTO productStoreRelDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ProductStoreRel partially : {}, {}", id, productStoreRelDTO);
        if (productStoreRelDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, productStoreRelDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!productStoreRelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ProductStoreRelDTO> result = productStoreRelService.partialUpdate(productStoreRelDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productStoreRelDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /product-store-rels} : get all the productStoreRels.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of productStoreRels in body.
     */
    @GetMapping("/product-store-rels")
    public ResponseEntity<List<ProductStoreRelDTO>> getAllProductStoreRels(ProductStoreRelCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ProductStoreRels by criteria: {}", criteria);
        Page<ProductStoreRelDTO> page = productStoreRelQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /product-store-rels/count} : count all the productStoreRels.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/product-store-rels/count")
    public ResponseEntity<Long> countProductStoreRels(ProductStoreRelCriteria criteria) {
        log.debug("REST request to count ProductStoreRels by criteria: {}", criteria);
        return ResponseEntity.ok().body(productStoreRelQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /product-store-rels/:id} : get the "id" productStoreRel.
     *
     * @param id the id of the productStoreRelDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the productStoreRelDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/product-store-rels/{id}")
    public ResponseEntity<ProductStoreRelDTO> getProductStoreRel(@PathVariable Long id) {
        log.debug("REST request to get ProductStoreRel : {}", id);
        Optional<ProductStoreRelDTO> productStoreRelDTO = productStoreRelService.findOne(id);
        return ResponseUtil.wrapOrNotFound(productStoreRelDTO);
    }

    /**
     * {@code DELETE  /product-store-rels/:id} : delete the "id" productStoreRel.
     *
     * @param id the id of the productStoreRelDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/product-store-rels/{id}")
    public ResponseEntity<Void> deleteProductStoreRel(@PathVariable Long id) {
        log.debug("REST request to delete ProductStoreRel : {}", id);
        productStoreRelService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
