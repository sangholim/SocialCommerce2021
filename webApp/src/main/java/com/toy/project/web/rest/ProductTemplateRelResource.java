package com.toy.project.web.rest;

import com.toy.project.repository.ProductTemplateRelRepository;
import com.toy.project.service.ProductTemplateRelQueryService;
import com.toy.project.service.ProductTemplateRelService;
import com.toy.project.service.criteria.ProductTemplateRelCriteria;
import com.toy.project.service.dto.ProductTemplateRelDTO;
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
 * REST controller for managing {@link com.toy.project.domain.ProductTemplateRel}.
 */
@RestController
@RequestMapping("/api")
public class ProductTemplateRelResource {

    private final Logger log = LoggerFactory.getLogger(ProductTemplateRelResource.class);

    private static final String ENTITY_NAME = "productTemplateRel";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProductTemplateRelService productTemplateRelService;

    private final ProductTemplateRelRepository productTemplateRelRepository;

    private final ProductTemplateRelQueryService productTemplateRelQueryService;

    public ProductTemplateRelResource(
        ProductTemplateRelService productTemplateRelService,
        ProductTemplateRelRepository productTemplateRelRepository,
        ProductTemplateRelQueryService productTemplateRelQueryService
    ) {
        this.productTemplateRelService = productTemplateRelService;
        this.productTemplateRelRepository = productTemplateRelRepository;
        this.productTemplateRelQueryService = productTemplateRelQueryService;
    }

    /**
     * {@code POST  /product-template-rels} : Create a new productTemplateRel.
     *
     * @param productTemplateRelDTO the productTemplateRelDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new productTemplateRelDTO, or with status {@code 400 (Bad Request)} if the productTemplateRel has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/product-template-rels")
    public ResponseEntity<ProductTemplateRelDTO> createProductTemplateRel(@Valid @RequestBody ProductTemplateRelDTO productTemplateRelDTO)
        throws URISyntaxException {
        log.debug("REST request to save ProductTemplateRel : {}", productTemplateRelDTO);
        if (productTemplateRelDTO.getId() != null) {
            throw new BadRequestAlertException("A new productTemplateRel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductTemplateRelDTO result = productTemplateRelService.save(productTemplateRelDTO);
        return ResponseEntity
            .created(new URI("/api/product-template-rels/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /product-template-rels/:id} : Updates an existing productTemplateRel.
     *
     * @param id the id of the productTemplateRelDTO to save.
     * @param productTemplateRelDTO the productTemplateRelDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productTemplateRelDTO,
     * or with status {@code 400 (Bad Request)} if the productTemplateRelDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the productTemplateRelDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/product-template-rels/{id}")
    public ResponseEntity<ProductTemplateRelDTO> updateProductTemplateRel(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ProductTemplateRelDTO productTemplateRelDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ProductTemplateRel : {}, {}", id, productTemplateRelDTO);
        if (productTemplateRelDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, productTemplateRelDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!productTemplateRelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ProductTemplateRelDTO result = productTemplateRelService.save(productTemplateRelDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productTemplateRelDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /product-template-rels/:id} : Partial updates given fields of an existing productTemplateRel, field will ignore if it is null
     *
     * @param id the id of the productTemplateRelDTO to save.
     * @param productTemplateRelDTO the productTemplateRelDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productTemplateRelDTO,
     * or with status {@code 400 (Bad Request)} if the productTemplateRelDTO is not valid,
     * or with status {@code 404 (Not Found)} if the productTemplateRelDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the productTemplateRelDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/product-template-rels/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<ProductTemplateRelDTO> partialUpdateProductTemplateRel(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ProductTemplateRelDTO productTemplateRelDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ProductTemplateRel partially : {}, {}", id, productTemplateRelDTO);
        if (productTemplateRelDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, productTemplateRelDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!productTemplateRelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ProductTemplateRelDTO> result = productTemplateRelService.partialUpdate(productTemplateRelDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productTemplateRelDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /product-template-rels} : get all the productTemplateRels.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of productTemplateRels in body.
     */
    @GetMapping("/product-template-rels")
    public ResponseEntity<List<ProductTemplateRelDTO>> getAllProductTemplateRels(ProductTemplateRelCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ProductTemplateRels by criteria: {}", criteria);
        Page<ProductTemplateRelDTO> page = productTemplateRelQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /product-template-rels/count} : count all the productTemplateRels.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/product-template-rels/count")
    public ResponseEntity<Long> countProductTemplateRels(ProductTemplateRelCriteria criteria) {
        log.debug("REST request to count ProductTemplateRels by criteria: {}", criteria);
        return ResponseEntity.ok().body(productTemplateRelQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /product-template-rels/:id} : get the "id" productTemplateRel.
     *
     * @param id the id of the productTemplateRelDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the productTemplateRelDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/product-template-rels/{id}")
    public ResponseEntity<ProductTemplateRelDTO> getProductTemplateRel(@PathVariable Long id) {
        log.debug("REST request to get ProductTemplateRel : {}", id);
        Optional<ProductTemplateRelDTO> productTemplateRelDTO = productTemplateRelService.findOne(id);
        return ResponseUtil.wrapOrNotFound(productTemplateRelDTO);
    }

    /**
     * {@code DELETE  /product-template-rels/:id} : delete the "id" productTemplateRel.
     *
     * @param id the id of the productTemplateRelDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/product-template-rels/{id}")
    public ResponseEntity<Void> deleteProductTemplateRel(@PathVariable Long id) {
        log.debug("REST request to delete ProductTemplateRel : {}", id);
        productTemplateRelService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
