package com.toy.project.web.rest;

import com.toy.project.repository.ProductNoticeRelRepository;
import com.toy.project.service.ProductNoticeRelQueryService;
import com.toy.project.service.ProductNoticeRelService;
import com.toy.project.service.criteria.ProductNoticeRelCriteria;
import com.toy.project.service.dto.ProductNoticeRelDTO;
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
 * REST controller for managing {@link com.toy.project.domain.ProductNoticeRel}.
 */
@RestController
@RequestMapping("/api")
public class ProductNoticeRelResource {

    private final Logger log = LoggerFactory.getLogger(ProductNoticeRelResource.class);

    private static final String ENTITY_NAME = "productNoticeRel";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProductNoticeRelService productNoticeRelService;

    private final ProductNoticeRelRepository productNoticeRelRepository;

    private final ProductNoticeRelQueryService productNoticeRelQueryService;

    public ProductNoticeRelResource(
        ProductNoticeRelService productNoticeRelService,
        ProductNoticeRelRepository productNoticeRelRepository,
        ProductNoticeRelQueryService productNoticeRelQueryService
    ) {
        this.productNoticeRelService = productNoticeRelService;
        this.productNoticeRelRepository = productNoticeRelRepository;
        this.productNoticeRelQueryService = productNoticeRelQueryService;
    }

    /**
     * {@code POST  /product-notice-rels} : Create a new productNoticeRel.
     *
     * @param productNoticeRelDTO the productNoticeRelDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new productNoticeRelDTO, or with status {@code 400 (Bad Request)} if the productNoticeRel has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/product-notice-rels")
    public ResponseEntity<ProductNoticeRelDTO> createProductNoticeRel(@Valid @RequestBody ProductNoticeRelDTO productNoticeRelDTO)
        throws URISyntaxException {
        log.debug("REST request to save ProductNoticeRel : {}", productNoticeRelDTO);
        if (productNoticeRelDTO.getId() != null) {
            throw new BadRequestAlertException("A new productNoticeRel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductNoticeRelDTO result = productNoticeRelService.save(productNoticeRelDTO);
        return ResponseEntity
            .created(new URI("/api/product-notice-rels/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /product-notice-rels/:id} : Updates an existing productNoticeRel.
     *
     * @param id the id of the productNoticeRelDTO to save.
     * @param productNoticeRelDTO the productNoticeRelDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productNoticeRelDTO,
     * or with status {@code 400 (Bad Request)} if the productNoticeRelDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the productNoticeRelDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/product-notice-rels/{id}")
    public ResponseEntity<ProductNoticeRelDTO> updateProductNoticeRel(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ProductNoticeRelDTO productNoticeRelDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ProductNoticeRel : {}, {}", id, productNoticeRelDTO);
        if (productNoticeRelDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, productNoticeRelDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!productNoticeRelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ProductNoticeRelDTO result = productNoticeRelService.save(productNoticeRelDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productNoticeRelDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /product-notice-rels/:id} : Partial updates given fields of an existing productNoticeRel, field will ignore if it is null
     *
     * @param id the id of the productNoticeRelDTO to save.
     * @param productNoticeRelDTO the productNoticeRelDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productNoticeRelDTO,
     * or with status {@code 400 (Bad Request)} if the productNoticeRelDTO is not valid,
     * or with status {@code 404 (Not Found)} if the productNoticeRelDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the productNoticeRelDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/product-notice-rels/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<ProductNoticeRelDTO> partialUpdateProductNoticeRel(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ProductNoticeRelDTO productNoticeRelDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ProductNoticeRel partially : {}, {}", id, productNoticeRelDTO);
        if (productNoticeRelDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, productNoticeRelDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!productNoticeRelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ProductNoticeRelDTO> result = productNoticeRelService.partialUpdate(productNoticeRelDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productNoticeRelDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /product-notice-rels} : get all the productNoticeRels.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of productNoticeRels in body.
     */
    @GetMapping("/product-notice-rels")
    public ResponseEntity<List<ProductNoticeRelDTO>> getAllProductNoticeRels(ProductNoticeRelCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ProductNoticeRels by criteria: {}", criteria);
        Page<ProductNoticeRelDTO> page = productNoticeRelQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /product-notice-rels/count} : count all the productNoticeRels.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/product-notice-rels/count")
    public ResponseEntity<Long> countProductNoticeRels(ProductNoticeRelCriteria criteria) {
        log.debug("REST request to count ProductNoticeRels by criteria: {}", criteria);
        return ResponseEntity.ok().body(productNoticeRelQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /product-notice-rels/:id} : get the "id" productNoticeRel.
     *
     * @param id the id of the productNoticeRelDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the productNoticeRelDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/product-notice-rels/{id}")
    public ResponseEntity<ProductNoticeRelDTO> getProductNoticeRel(@PathVariable Long id) {
        log.debug("REST request to get ProductNoticeRel : {}", id);
        Optional<ProductNoticeRelDTO> productNoticeRelDTO = productNoticeRelService.findOne(id);
        return ResponseUtil.wrapOrNotFound(productNoticeRelDTO);
    }

    /**
     * {@code DELETE  /product-notice-rels/:id} : delete the "id" productNoticeRel.
     *
     * @param id the id of the productNoticeRelDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/product-notice-rels/{id}")
    public ResponseEntity<Void> deleteProductNoticeRel(@PathVariable Long id) {
        log.debug("REST request to delete ProductNoticeRel : {}", id);
        productNoticeRelService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
