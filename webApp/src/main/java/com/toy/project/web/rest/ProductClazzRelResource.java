package com.toy.project.web.rest;

import com.toy.project.repository.ProductClazzRelRepository;
import com.toy.project.service.ProductClazzRelQueryService;
import com.toy.project.service.ProductClazzRelService;
import com.toy.project.service.criteria.ProductClazzRelCriteria;
import com.toy.project.service.dto.ProductClazzRelDTO;
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
 * REST controller for managing {@link com.toy.project.domain.ProductClazzRel}.
 */
@RestController
@RequestMapping("/api")
public class ProductClazzRelResource {

    private final Logger log = LoggerFactory.getLogger(ProductClazzRelResource.class);

    private static final String ENTITY_NAME = "productClazzRel";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProductClazzRelService productClazzRelService;

    private final ProductClazzRelRepository productClazzRelRepository;

    private final ProductClazzRelQueryService productClazzRelQueryService;

    public ProductClazzRelResource(
        ProductClazzRelService productClazzRelService,
        ProductClazzRelRepository productClazzRelRepository,
        ProductClazzRelQueryService productClazzRelQueryService
    ) {
        this.productClazzRelService = productClazzRelService;
        this.productClazzRelRepository = productClazzRelRepository;
        this.productClazzRelQueryService = productClazzRelQueryService;
    }

    /**
     * {@code POST  /product-clazz-rels} : Create a new productClazzRel.
     *
     * @param productClazzRelDTO the productClazzRelDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new productClazzRelDTO, or with status {@code 400 (Bad Request)} if the productClazzRel has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/product-clazz-rels")
    public ResponseEntity<ProductClazzRelDTO> createProductClazzRel(@Valid @RequestBody ProductClazzRelDTO productClazzRelDTO)
        throws URISyntaxException {
        log.debug("REST request to save ProductClazzRel : {}", productClazzRelDTO);
        if (productClazzRelDTO.getId() != null) {
            throw new BadRequestAlertException("A new productClazzRel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductClazzRelDTO result = productClazzRelService.save(productClazzRelDTO);
        return ResponseEntity
            .created(new URI("/api/product-clazz-rels/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /product-clazz-rels/:id} : Updates an existing productClazzRel.
     *
     * @param id the id of the productClazzRelDTO to save.
     * @param productClazzRelDTO the productClazzRelDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productClazzRelDTO,
     * or with status {@code 400 (Bad Request)} if the productClazzRelDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the productClazzRelDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/product-clazz-rels/{id}")
    public ResponseEntity<ProductClazzRelDTO> updateProductClazzRel(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ProductClazzRelDTO productClazzRelDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ProductClazzRel : {}, {}", id, productClazzRelDTO);
        if (productClazzRelDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, productClazzRelDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!productClazzRelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ProductClazzRelDTO result = productClazzRelService.save(productClazzRelDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productClazzRelDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /product-clazz-rels/:id} : Partial updates given fields of an existing productClazzRel, field will ignore if it is null
     *
     * @param id the id of the productClazzRelDTO to save.
     * @param productClazzRelDTO the productClazzRelDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productClazzRelDTO,
     * or with status {@code 400 (Bad Request)} if the productClazzRelDTO is not valid,
     * or with status {@code 404 (Not Found)} if the productClazzRelDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the productClazzRelDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/product-clazz-rels/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<ProductClazzRelDTO> partialUpdateProductClazzRel(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ProductClazzRelDTO productClazzRelDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ProductClazzRel partially : {}, {}", id, productClazzRelDTO);
        if (productClazzRelDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, productClazzRelDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!productClazzRelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ProductClazzRelDTO> result = productClazzRelService.partialUpdate(productClazzRelDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productClazzRelDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /product-clazz-rels} : get all the productClazzRels.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of productClazzRels in body.
     */
    @GetMapping("/product-clazz-rels")
    public ResponseEntity<List<ProductClazzRelDTO>> getAllProductClazzRels(ProductClazzRelCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ProductClazzRels by criteria: {}", criteria);
        Page<ProductClazzRelDTO> page = productClazzRelQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /product-clazz-rels/count} : count all the productClazzRels.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/product-clazz-rels/count")
    public ResponseEntity<Long> countProductClazzRels(ProductClazzRelCriteria criteria) {
        log.debug("REST request to count ProductClazzRels by criteria: {}", criteria);
        return ResponseEntity.ok().body(productClazzRelQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /product-clazz-rels/:id} : get the "id" productClazzRel.
     *
     * @param id the id of the productClazzRelDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the productClazzRelDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/product-clazz-rels/{id}")
    public ResponseEntity<ProductClazzRelDTO> getProductClazzRel(@PathVariable Long id) {
        log.debug("REST request to get ProductClazzRel : {}", id);
        Optional<ProductClazzRelDTO> productClazzRelDTO = productClazzRelService.findOne(id);
        return ResponseUtil.wrapOrNotFound(productClazzRelDTO);
    }

    /**
     * {@code DELETE  /product-clazz-rels/:id} : delete the "id" productClazzRel.
     *
     * @param id the id of the productClazzRelDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/product-clazz-rels/{id}")
    public ResponseEntity<Void> deleteProductClazzRel(@PathVariable Long id) {
        log.debug("REST request to delete ProductClazzRel : {}", id);
        productClazzRelService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
