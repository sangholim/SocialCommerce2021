package com.toy.project.web.rest;

import com.toy.project.repository.ProductOptionRelRepository;
import com.toy.project.service.ProductOptionRelQueryService;
import com.toy.project.service.ProductOptionRelService;
import com.toy.project.service.criteria.ProductOptionRelCriteria;
import com.toy.project.service.dto.ProductOptionRelDTO;
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
 * REST controller for managing {@link com.toy.project.domain.ProductOptionRel}.
 */
@RestController
@RequestMapping("/api")
public class ProductOptionRelResource {

    private final Logger log = LoggerFactory.getLogger(ProductOptionRelResource.class);

    private static final String ENTITY_NAME = "productOptionRel";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProductOptionRelService productOptionRelService;

    private final ProductOptionRelRepository productOptionRelRepository;

    private final ProductOptionRelQueryService productOptionRelQueryService;

    public ProductOptionRelResource(
        ProductOptionRelService productOptionRelService,
        ProductOptionRelRepository productOptionRelRepository,
        ProductOptionRelQueryService productOptionRelQueryService
    ) {
        this.productOptionRelService = productOptionRelService;
        this.productOptionRelRepository = productOptionRelRepository;
        this.productOptionRelQueryService = productOptionRelQueryService;
    }

    /**
     * {@code POST  /product-option-rels} : Create a new productOptionRel.
     *
     * @param productOptionRelDTO the productOptionRelDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new productOptionRelDTO, or with status {@code 400 (Bad Request)} if the productOptionRel has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/product-option-rels")
    public ResponseEntity<ProductOptionRelDTO> createProductOptionRel(@Valid @RequestBody ProductOptionRelDTO productOptionRelDTO)
        throws URISyntaxException {
        log.debug("REST request to save ProductOptionRel : {}", productOptionRelDTO);
        if (productOptionRelDTO.getId() != null) {
            throw new BadRequestAlertException("A new productOptionRel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductOptionRelDTO result = productOptionRelService.save(productOptionRelDTO);
        return ResponseEntity
            .created(new URI("/api/product-option-rels/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /product-option-rels/:id} : Updates an existing productOptionRel.
     *
     * @param id the id of the productOptionRelDTO to save.
     * @param productOptionRelDTO the productOptionRelDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productOptionRelDTO,
     * or with status {@code 400 (Bad Request)} if the productOptionRelDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the productOptionRelDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/product-option-rels/{id}")
    public ResponseEntity<ProductOptionRelDTO> updateProductOptionRel(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ProductOptionRelDTO productOptionRelDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ProductOptionRel : {}, {}", id, productOptionRelDTO);
        if (productOptionRelDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, productOptionRelDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!productOptionRelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ProductOptionRelDTO result = productOptionRelService.save(productOptionRelDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productOptionRelDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /product-option-rels/:id} : Partial updates given fields of an existing productOptionRel, field will ignore if it is null
     *
     * @param id the id of the productOptionRelDTO to save.
     * @param productOptionRelDTO the productOptionRelDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productOptionRelDTO,
     * or with status {@code 400 (Bad Request)} if the productOptionRelDTO is not valid,
     * or with status {@code 404 (Not Found)} if the productOptionRelDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the productOptionRelDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/product-option-rels/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<ProductOptionRelDTO> partialUpdateProductOptionRel(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ProductOptionRelDTO productOptionRelDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ProductOptionRel partially : {}, {}", id, productOptionRelDTO);
        if (productOptionRelDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, productOptionRelDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!productOptionRelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ProductOptionRelDTO> result = productOptionRelService.partialUpdate(productOptionRelDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productOptionRelDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /product-option-rels} : get all the productOptionRels.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of productOptionRels in body.
     */
    @GetMapping("/product-option-rels")
    public ResponseEntity<List<ProductOptionRelDTO>> getAllProductOptionRels(ProductOptionRelCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ProductOptionRels by criteria: {}", criteria);
        Page<ProductOptionRelDTO> page = productOptionRelQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /product-option-rels/count} : count all the productOptionRels.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/product-option-rels/count")
    public ResponseEntity<Long> countProductOptionRels(ProductOptionRelCriteria criteria) {
        log.debug("REST request to count ProductOptionRels by criteria: {}", criteria);
        return ResponseEntity.ok().body(productOptionRelQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /product-option-rels/:id} : get the "id" productOptionRel.
     *
     * @param id the id of the productOptionRelDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the productOptionRelDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/product-option-rels/{id}")
    public ResponseEntity<ProductOptionRelDTO> getProductOptionRel(@PathVariable Long id) {
        log.debug("REST request to get ProductOptionRel : {}", id);
        Optional<ProductOptionRelDTO> productOptionRelDTO = productOptionRelService.findOne(id);
        return ResponseUtil.wrapOrNotFound(productOptionRelDTO);
    }

    /**
     * {@code DELETE  /product-option-rels/:id} : delete the "id" productOptionRel.
     *
     * @param id the id of the productOptionRelDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/product-option-rels/{id}")
    public ResponseEntity<Void> deleteProductOptionRel(@PathVariable Long id) {
        log.debug("REST request to delete ProductOptionRel : {}", id);
        productOptionRelService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
