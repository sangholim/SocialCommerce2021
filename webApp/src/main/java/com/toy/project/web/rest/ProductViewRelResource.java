package com.toy.project.web.rest;

import com.toy.project.repository.ProductViewRelRepository;
import com.toy.project.service.ProductViewRelQueryService;
import com.toy.project.service.ProductViewRelService;
import com.toy.project.service.criteria.ProductViewRelCriteria;
import com.toy.project.service.dto.ProductViewRelDTO;
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
 * REST controller for managing {@link com.toy.project.domain.ProductViewRel}.
 */
@RestController
@RequestMapping("/api")
public class ProductViewRelResource {

    private final Logger log = LoggerFactory.getLogger(ProductViewRelResource.class);

    private static final String ENTITY_NAME = "productViewRel";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProductViewRelService productViewRelService;

    private final ProductViewRelRepository productViewRelRepository;

    private final ProductViewRelQueryService productViewRelQueryService;

    public ProductViewRelResource(
        ProductViewRelService productViewRelService,
        ProductViewRelRepository productViewRelRepository,
        ProductViewRelQueryService productViewRelQueryService
    ) {
        this.productViewRelService = productViewRelService;
        this.productViewRelRepository = productViewRelRepository;
        this.productViewRelQueryService = productViewRelQueryService;
    }

    /**
     * {@code POST  /product-view-rels} : Create a new productViewRel.
     *
     * @param productViewRelDTO the productViewRelDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new productViewRelDTO, or with status {@code 400 (Bad Request)} if the productViewRel has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/product-view-rels")
    public ResponseEntity<ProductViewRelDTO> createProductViewRel(@Valid @RequestBody ProductViewRelDTO productViewRelDTO)
        throws URISyntaxException {
        log.debug("REST request to save ProductViewRel : {}", productViewRelDTO);
        if (productViewRelDTO.getId() != null) {
            throw new BadRequestAlertException("A new productViewRel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductViewRelDTO result = productViewRelService.save(productViewRelDTO);
        return ResponseEntity
            .created(new URI("/api/product-view-rels/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /product-view-rels/:id} : Updates an existing productViewRel.
     *
     * @param id the id of the productViewRelDTO to save.
     * @param productViewRelDTO the productViewRelDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productViewRelDTO,
     * or with status {@code 400 (Bad Request)} if the productViewRelDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the productViewRelDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/product-view-rels/{id}")
    public ResponseEntity<ProductViewRelDTO> updateProductViewRel(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ProductViewRelDTO productViewRelDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ProductViewRel : {}, {}", id, productViewRelDTO);
        if (productViewRelDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, productViewRelDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!productViewRelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ProductViewRelDTO result = productViewRelService.save(productViewRelDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productViewRelDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /product-view-rels/:id} : Partial updates given fields of an existing productViewRel, field will ignore if it is null
     *
     * @param id the id of the productViewRelDTO to save.
     * @param productViewRelDTO the productViewRelDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productViewRelDTO,
     * or with status {@code 400 (Bad Request)} if the productViewRelDTO is not valid,
     * or with status {@code 404 (Not Found)} if the productViewRelDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the productViewRelDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/product-view-rels/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<ProductViewRelDTO> partialUpdateProductViewRel(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ProductViewRelDTO productViewRelDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ProductViewRel partially : {}, {}", id, productViewRelDTO);
        if (productViewRelDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, productViewRelDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!productViewRelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ProductViewRelDTO> result = productViewRelService.partialUpdate(productViewRelDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productViewRelDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /product-view-rels} : get all the productViewRels.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of productViewRels in body.
     */
    @GetMapping("/product-view-rels")
    public ResponseEntity<List<ProductViewRelDTO>> getAllProductViewRels(ProductViewRelCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ProductViewRels by criteria: {}", criteria);
        Page<ProductViewRelDTO> page = productViewRelQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /product-view-rels/count} : count all the productViewRels.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/product-view-rels/count")
    public ResponseEntity<Long> countProductViewRels(ProductViewRelCriteria criteria) {
        log.debug("REST request to count ProductViewRels by criteria: {}", criteria);
        return ResponseEntity.ok().body(productViewRelQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /product-view-rels/:id} : get the "id" productViewRel.
     *
     * @param id the id of the productViewRelDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the productViewRelDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/product-view-rels/{id}")
    public ResponseEntity<ProductViewRelDTO> getProductViewRel(@PathVariable Long id) {
        log.debug("REST request to get ProductViewRel : {}", id);
        Optional<ProductViewRelDTO> productViewRelDTO = productViewRelService.findOne(id);
        return ResponseUtil.wrapOrNotFound(productViewRelDTO);
    }

    /**
     * {@code DELETE  /product-view-rels/:id} : delete the "id" productViewRel.
     *
     * @param id the id of the productViewRelDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/product-view-rels/{id}")
    public ResponseEntity<Void> deleteProductViewRel(@PathVariable Long id) {
        log.debug("REST request to delete ProductViewRel : {}", id);
        productViewRelService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
