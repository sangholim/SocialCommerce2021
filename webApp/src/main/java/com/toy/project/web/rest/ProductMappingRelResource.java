package com.toy.project.web.rest;

import com.toy.project.repository.ProductMappingRelRepository;
import com.toy.project.service.ProductMappingRelQueryService;
import com.toy.project.service.ProductMappingRelService;
import com.toy.project.service.criteria.ProductMappingRelCriteria;
import com.toy.project.service.dto.ProductMappingRelDTO;
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
 * REST controller for managing {@link com.toy.project.domain.ProductMappingRel}.
 */
@RestController
@RequestMapping("/api")
public class ProductMappingRelResource {

    private final Logger log = LoggerFactory.getLogger(ProductMappingRelResource.class);

    private static final String ENTITY_NAME = "productMappingRel";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProductMappingRelService productMappingRelService;

    private final ProductMappingRelRepository productMappingRelRepository;

    private final ProductMappingRelQueryService productMappingRelQueryService;

    public ProductMappingRelResource(
        ProductMappingRelService productMappingRelService,
        ProductMappingRelRepository productMappingRelRepository,
        ProductMappingRelQueryService productMappingRelQueryService
    ) {
        this.productMappingRelService = productMappingRelService;
        this.productMappingRelRepository = productMappingRelRepository;
        this.productMappingRelQueryService = productMappingRelQueryService;
    }

    /**
     * {@code POST  /product-mapping-rels} : Create a new productMappingRel.
     *
     * @param productMappingRelDTO the productMappingRelDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new productMappingRelDTO, or with status {@code 400 (Bad Request)} if the productMappingRel has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/product-mapping-rels")
    public ResponseEntity<ProductMappingRelDTO> createProductMappingRel(@Valid @RequestBody ProductMappingRelDTO productMappingRelDTO)
        throws URISyntaxException {
        log.debug("REST request to save ProductMappingRel : {}", productMappingRelDTO);
        if (productMappingRelDTO.getId() != null) {
            throw new BadRequestAlertException("A new productMappingRel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductMappingRelDTO result = productMappingRelService.save(productMappingRelDTO);
        return ResponseEntity
            .created(new URI("/api/product-mapping-rels/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /product-mapping-rels/:id} : Updates an existing productMappingRel.
     *
     * @param id the id of the productMappingRelDTO to save.
     * @param productMappingRelDTO the productMappingRelDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productMappingRelDTO,
     * or with status {@code 400 (Bad Request)} if the productMappingRelDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the productMappingRelDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/product-mapping-rels/{id}")
    public ResponseEntity<ProductMappingRelDTO> updateProductMappingRel(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ProductMappingRelDTO productMappingRelDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ProductMappingRel : {}, {}", id, productMappingRelDTO);
        if (productMappingRelDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, productMappingRelDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!productMappingRelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ProductMappingRelDTO result = productMappingRelService.save(productMappingRelDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productMappingRelDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /product-mapping-rels/:id} : Partial updates given fields of an existing productMappingRel, field will ignore if it is null
     *
     * @param id the id of the productMappingRelDTO to save.
     * @param productMappingRelDTO the productMappingRelDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productMappingRelDTO,
     * or with status {@code 400 (Bad Request)} if the productMappingRelDTO is not valid,
     * or with status {@code 404 (Not Found)} if the productMappingRelDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the productMappingRelDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/product-mapping-rels/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<ProductMappingRelDTO> partialUpdateProductMappingRel(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ProductMappingRelDTO productMappingRelDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ProductMappingRel partially : {}, {}", id, productMappingRelDTO);
        if (productMappingRelDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, productMappingRelDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!productMappingRelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ProductMappingRelDTO> result = productMappingRelService.partialUpdate(productMappingRelDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productMappingRelDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /product-mapping-rels} : get all the productMappingRels.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of productMappingRels in body.
     */
    @GetMapping("/product-mapping-rels")
    public ResponseEntity<List<ProductMappingRelDTO>> getAllProductMappingRels(ProductMappingRelCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ProductMappingRels by criteria: {}", criteria);
        Page<ProductMappingRelDTO> page = productMappingRelQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /product-mapping-rels/count} : count all the productMappingRels.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/product-mapping-rels/count")
    public ResponseEntity<Long> countProductMappingRels(ProductMappingRelCriteria criteria) {
        log.debug("REST request to count ProductMappingRels by criteria: {}", criteria);
        return ResponseEntity.ok().body(productMappingRelQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /product-mapping-rels/:id} : get the "id" productMappingRel.
     *
     * @param id the id of the productMappingRelDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the productMappingRelDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/product-mapping-rels/{id}")
    public ResponseEntity<ProductMappingRelDTO> getProductMappingRel(@PathVariable Long id) {
        log.debug("REST request to get ProductMappingRel : {}", id);
        Optional<ProductMappingRelDTO> productMappingRelDTO = productMappingRelService.findOne(id);
        return ResponseUtil.wrapOrNotFound(productMappingRelDTO);
    }

    /**
     * {@code DELETE  /product-mapping-rels/:id} : delete the "id" productMappingRel.
     *
     * @param id the id of the productMappingRelDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/product-mapping-rels/{id}")
    public ResponseEntity<Void> deleteProductMappingRel(@PathVariable Long id) {
        log.debug("REST request to delete ProductMappingRel : {}", id);
        productMappingRelService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
