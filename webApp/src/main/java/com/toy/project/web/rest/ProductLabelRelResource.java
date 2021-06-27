package com.toy.project.web.rest;

import com.toy.project.repository.ProductLabelRelRepository;
import com.toy.project.service.ProductLabelRelQueryService;
import com.toy.project.service.ProductLabelRelService;
import com.toy.project.service.criteria.ProductLabelRelCriteria;
import com.toy.project.service.dto.ProductLabelRelDTO;
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
 * REST controller for managing {@link com.toy.project.domain.ProductLabelRel}.
 */
@RestController
@RequestMapping("/api")
public class ProductLabelRelResource {

    private final Logger log = LoggerFactory.getLogger(ProductLabelRelResource.class);

    private static final String ENTITY_NAME = "productLabelRel";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProductLabelRelService productLabelRelService;

    private final ProductLabelRelRepository productLabelRelRepository;

    private final ProductLabelRelQueryService productLabelRelQueryService;

    public ProductLabelRelResource(
        ProductLabelRelService productLabelRelService,
        ProductLabelRelRepository productLabelRelRepository,
        ProductLabelRelQueryService productLabelRelQueryService
    ) {
        this.productLabelRelService = productLabelRelService;
        this.productLabelRelRepository = productLabelRelRepository;
        this.productLabelRelQueryService = productLabelRelQueryService;
    }

    /**
     * {@code POST  /product-label-rels} : Create a new productLabelRel.
     *
     * @param productLabelRelDTO the productLabelRelDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new productLabelRelDTO, or with status {@code 400 (Bad Request)} if the productLabelRel has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/product-label-rels")
    public ResponseEntity<ProductLabelRelDTO> createProductLabelRel(@Valid @RequestBody ProductLabelRelDTO productLabelRelDTO)
        throws URISyntaxException {
        log.debug("REST request to save ProductLabelRel : {}", productLabelRelDTO);
        if (productLabelRelDTO.getId() != null) {
            throw new BadRequestAlertException("A new productLabelRel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductLabelRelDTO result = productLabelRelService.save(productLabelRelDTO);
        return ResponseEntity
            .created(new URI("/api/product-label-rels/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /product-label-rels/:id} : Updates an existing productLabelRel.
     *
     * @param id the id of the productLabelRelDTO to save.
     * @param productLabelRelDTO the productLabelRelDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productLabelRelDTO,
     * or with status {@code 400 (Bad Request)} if the productLabelRelDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the productLabelRelDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/product-label-rels/{id}")
    public ResponseEntity<ProductLabelRelDTO> updateProductLabelRel(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ProductLabelRelDTO productLabelRelDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ProductLabelRel : {}, {}", id, productLabelRelDTO);
        if (productLabelRelDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, productLabelRelDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!productLabelRelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ProductLabelRelDTO result = productLabelRelService.save(productLabelRelDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productLabelRelDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /product-label-rels/:id} : Partial updates given fields of an existing productLabelRel, field will ignore if it is null
     *
     * @param id the id of the productLabelRelDTO to save.
     * @param productLabelRelDTO the productLabelRelDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productLabelRelDTO,
     * or with status {@code 400 (Bad Request)} if the productLabelRelDTO is not valid,
     * or with status {@code 404 (Not Found)} if the productLabelRelDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the productLabelRelDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/product-label-rels/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<ProductLabelRelDTO> partialUpdateProductLabelRel(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ProductLabelRelDTO productLabelRelDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ProductLabelRel partially : {}, {}", id, productLabelRelDTO);
        if (productLabelRelDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, productLabelRelDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!productLabelRelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ProductLabelRelDTO> result = productLabelRelService.partialUpdate(productLabelRelDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productLabelRelDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /product-label-rels} : get all the productLabelRels.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of productLabelRels in body.
     */
    @GetMapping("/product-label-rels")
    public ResponseEntity<List<ProductLabelRelDTO>> getAllProductLabelRels(ProductLabelRelCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ProductLabelRels by criteria: {}", criteria);
        Page<ProductLabelRelDTO> page = productLabelRelQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /product-label-rels/count} : count all the productLabelRels.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/product-label-rels/count")
    public ResponseEntity<Long> countProductLabelRels(ProductLabelRelCriteria criteria) {
        log.debug("REST request to count ProductLabelRels by criteria: {}", criteria);
        return ResponseEntity.ok().body(productLabelRelQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /product-label-rels/:id} : get the "id" productLabelRel.
     *
     * @param id the id of the productLabelRelDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the productLabelRelDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/product-label-rels/{id}")
    public ResponseEntity<ProductLabelRelDTO> getProductLabelRel(@PathVariable Long id) {
        log.debug("REST request to get ProductLabelRel : {}", id);
        Optional<ProductLabelRelDTO> productLabelRelDTO = productLabelRelService.findOne(id);
        return ResponseUtil.wrapOrNotFound(productLabelRelDTO);
    }

    /**
     * {@code DELETE  /product-label-rels/:id} : delete the "id" productLabelRel.
     *
     * @param id the id of the productLabelRelDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/product-label-rels/{id}")
    public ResponseEntity<Void> deleteProductLabelRel(@PathVariable Long id) {
        log.debug("REST request to delete ProductLabelRel : {}", id);
        productLabelRelService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
