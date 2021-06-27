package com.toy.project.web.rest;

import com.toy.project.repository.ProductShippingRelRepository;
import com.toy.project.service.ProductShippingRelQueryService;
import com.toy.project.service.ProductShippingRelService;
import com.toy.project.service.criteria.ProductShippingRelCriteria;
import com.toy.project.service.dto.ProductShippingRelDTO;
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
 * REST controller for managing {@link com.toy.project.domain.ProductShippingRel}.
 */
@RestController
@RequestMapping("/api")
public class ProductShippingRelResource {

    private final Logger log = LoggerFactory.getLogger(ProductShippingRelResource.class);

    private static final String ENTITY_NAME = "productShippingRel";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProductShippingRelService productShippingRelService;

    private final ProductShippingRelRepository productShippingRelRepository;

    private final ProductShippingRelQueryService productShippingRelQueryService;

    public ProductShippingRelResource(
        ProductShippingRelService productShippingRelService,
        ProductShippingRelRepository productShippingRelRepository,
        ProductShippingRelQueryService productShippingRelQueryService
    ) {
        this.productShippingRelService = productShippingRelService;
        this.productShippingRelRepository = productShippingRelRepository;
        this.productShippingRelQueryService = productShippingRelQueryService;
    }

    /**
     * {@code POST  /product-shipping-rels} : Create a new productShippingRel.
     *
     * @param productShippingRelDTO the productShippingRelDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new productShippingRelDTO, or with status {@code 400 (Bad Request)} if the productShippingRel has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/product-shipping-rels")
    public ResponseEntity<ProductShippingRelDTO> createProductShippingRel(@Valid @RequestBody ProductShippingRelDTO productShippingRelDTO)
        throws URISyntaxException {
        log.debug("REST request to save ProductShippingRel : {}", productShippingRelDTO);
        if (productShippingRelDTO.getId() != null) {
            throw new BadRequestAlertException("A new productShippingRel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductShippingRelDTO result = productShippingRelService.save(productShippingRelDTO);
        return ResponseEntity
            .created(new URI("/api/product-shipping-rels/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /product-shipping-rels/:id} : Updates an existing productShippingRel.
     *
     * @param id the id of the productShippingRelDTO to save.
     * @param productShippingRelDTO the productShippingRelDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productShippingRelDTO,
     * or with status {@code 400 (Bad Request)} if the productShippingRelDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the productShippingRelDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/product-shipping-rels/{id}")
    public ResponseEntity<ProductShippingRelDTO> updateProductShippingRel(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ProductShippingRelDTO productShippingRelDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ProductShippingRel : {}, {}", id, productShippingRelDTO);
        if (productShippingRelDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, productShippingRelDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!productShippingRelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ProductShippingRelDTO result = productShippingRelService.save(productShippingRelDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productShippingRelDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /product-shipping-rels/:id} : Partial updates given fields of an existing productShippingRel, field will ignore if it is null
     *
     * @param id the id of the productShippingRelDTO to save.
     * @param productShippingRelDTO the productShippingRelDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productShippingRelDTO,
     * or with status {@code 400 (Bad Request)} if the productShippingRelDTO is not valid,
     * or with status {@code 404 (Not Found)} if the productShippingRelDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the productShippingRelDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/product-shipping-rels/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<ProductShippingRelDTO> partialUpdateProductShippingRel(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ProductShippingRelDTO productShippingRelDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ProductShippingRel partially : {}, {}", id, productShippingRelDTO);
        if (productShippingRelDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, productShippingRelDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!productShippingRelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ProductShippingRelDTO> result = productShippingRelService.partialUpdate(productShippingRelDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productShippingRelDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /product-shipping-rels} : get all the productShippingRels.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of productShippingRels in body.
     */
    @GetMapping("/product-shipping-rels")
    public ResponseEntity<List<ProductShippingRelDTO>> getAllProductShippingRels(ProductShippingRelCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ProductShippingRels by criteria: {}", criteria);
        Page<ProductShippingRelDTO> page = productShippingRelQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /product-shipping-rels/count} : count all the productShippingRels.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/product-shipping-rels/count")
    public ResponseEntity<Long> countProductShippingRels(ProductShippingRelCriteria criteria) {
        log.debug("REST request to count ProductShippingRels by criteria: {}", criteria);
        return ResponseEntity.ok().body(productShippingRelQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /product-shipping-rels/:id} : get the "id" productShippingRel.
     *
     * @param id the id of the productShippingRelDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the productShippingRelDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/product-shipping-rels/{id}")
    public ResponseEntity<ProductShippingRelDTO> getProductShippingRel(@PathVariable Long id) {
        log.debug("REST request to get ProductShippingRel : {}", id);
        Optional<ProductShippingRelDTO> productShippingRelDTO = productShippingRelService.findOne(id);
        return ResponseUtil.wrapOrNotFound(productShippingRelDTO);
    }

    /**
     * {@code DELETE  /product-shipping-rels/:id} : delete the "id" productShippingRel.
     *
     * @param id the id of the productShippingRelDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/product-shipping-rels/{id}")
    public ResponseEntity<Void> deleteProductShippingRel(@PathVariable Long id) {
        log.debug("REST request to delete ProductShippingRel : {}", id);
        productShippingRelService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
