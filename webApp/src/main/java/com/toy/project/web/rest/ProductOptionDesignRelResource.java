package com.toy.project.web.rest;

import com.toy.project.repository.ProductOptionDesignRelRepository;
import com.toy.project.service.ProductOptionDesignRelQueryService;
import com.toy.project.service.ProductOptionDesignRelService;
import com.toy.project.service.criteria.ProductOptionDesignRelCriteria;
import com.toy.project.service.dto.ProductOptionDesignRelDTO;
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
 * REST controller for managing {@link com.toy.project.domain.ProductOptionDesignRel}.
 */
@RestController
@RequestMapping("/api")
public class ProductOptionDesignRelResource {

    private final Logger log = LoggerFactory.getLogger(ProductOptionDesignRelResource.class);

    private static final String ENTITY_NAME = "productOptionDesignRel";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProductOptionDesignRelService productOptionDesignRelService;

    private final ProductOptionDesignRelRepository productOptionDesignRelRepository;

    private final ProductOptionDesignRelQueryService productOptionDesignRelQueryService;

    public ProductOptionDesignRelResource(
        ProductOptionDesignRelService productOptionDesignRelService,
        ProductOptionDesignRelRepository productOptionDesignRelRepository,
        ProductOptionDesignRelQueryService productOptionDesignRelQueryService
    ) {
        this.productOptionDesignRelService = productOptionDesignRelService;
        this.productOptionDesignRelRepository = productOptionDesignRelRepository;
        this.productOptionDesignRelQueryService = productOptionDesignRelQueryService;
    }

    /**
     * {@code POST  /product-option-design-rels} : Create a new productOptionDesignRel.
     *
     * @param productOptionDesignRelDTO the productOptionDesignRelDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new productOptionDesignRelDTO, or with status {@code 400 (Bad Request)} if the productOptionDesignRel has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/product-option-design-rels")
    public ResponseEntity<ProductOptionDesignRelDTO> createProductOptionDesignRel(
        @Valid @RequestBody ProductOptionDesignRelDTO productOptionDesignRelDTO
    ) throws URISyntaxException {
        log.debug("REST request to save ProductOptionDesignRel : {}", productOptionDesignRelDTO);
        if (productOptionDesignRelDTO.getId() != null) {
            throw new BadRequestAlertException("A new productOptionDesignRel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductOptionDesignRelDTO result = productOptionDesignRelService.save(productOptionDesignRelDTO);
        return ResponseEntity
            .created(new URI("/api/product-option-design-rels/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /product-option-design-rels/:id} : Updates an existing productOptionDesignRel.
     *
     * @param id the id of the productOptionDesignRelDTO to save.
     * @param productOptionDesignRelDTO the productOptionDesignRelDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productOptionDesignRelDTO,
     * or with status {@code 400 (Bad Request)} if the productOptionDesignRelDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the productOptionDesignRelDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/product-option-design-rels/{id}")
    public ResponseEntity<ProductOptionDesignRelDTO> updateProductOptionDesignRel(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ProductOptionDesignRelDTO productOptionDesignRelDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ProductOptionDesignRel : {}, {}", id, productOptionDesignRelDTO);
        if (productOptionDesignRelDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, productOptionDesignRelDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!productOptionDesignRelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ProductOptionDesignRelDTO result = productOptionDesignRelService.save(productOptionDesignRelDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productOptionDesignRelDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /product-option-design-rels/:id} : Partial updates given fields of an existing productOptionDesignRel, field will ignore if it is null
     *
     * @param id the id of the productOptionDesignRelDTO to save.
     * @param productOptionDesignRelDTO the productOptionDesignRelDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productOptionDesignRelDTO,
     * or with status {@code 400 (Bad Request)} if the productOptionDesignRelDTO is not valid,
     * or with status {@code 404 (Not Found)} if the productOptionDesignRelDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the productOptionDesignRelDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/product-option-design-rels/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<ProductOptionDesignRelDTO> partialUpdateProductOptionDesignRel(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ProductOptionDesignRelDTO productOptionDesignRelDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ProductOptionDesignRel partially : {}, {}", id, productOptionDesignRelDTO);
        if (productOptionDesignRelDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, productOptionDesignRelDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!productOptionDesignRelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ProductOptionDesignRelDTO> result = productOptionDesignRelService.partialUpdate(productOptionDesignRelDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productOptionDesignRelDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /product-option-design-rels} : get all the productOptionDesignRels.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of productOptionDesignRels in body.
     */
    @GetMapping("/product-option-design-rels")
    public ResponseEntity<List<ProductOptionDesignRelDTO>> getAllProductOptionDesignRels(
        ProductOptionDesignRelCriteria criteria,
        Pageable pageable
    ) {
        log.debug("REST request to get ProductOptionDesignRels by criteria: {}", criteria);
        Page<ProductOptionDesignRelDTO> page = productOptionDesignRelQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /product-option-design-rels/count} : count all the productOptionDesignRels.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/product-option-design-rels/count")
    public ResponseEntity<Long> countProductOptionDesignRels(ProductOptionDesignRelCriteria criteria) {
        log.debug("REST request to count ProductOptionDesignRels by criteria: {}", criteria);
        return ResponseEntity.ok().body(productOptionDesignRelQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /product-option-design-rels/:id} : get the "id" productOptionDesignRel.
     *
     * @param id the id of the productOptionDesignRelDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the productOptionDesignRelDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/product-option-design-rels/{id}")
    public ResponseEntity<ProductOptionDesignRelDTO> getProductOptionDesignRel(@PathVariable Long id) {
        log.debug("REST request to get ProductOptionDesignRel : {}", id);
        Optional<ProductOptionDesignRelDTO> productOptionDesignRelDTO = productOptionDesignRelService.findOne(id);
        return ResponseUtil.wrapOrNotFound(productOptionDesignRelDTO);
    }

    /**
     * {@code DELETE  /product-option-design-rels/:id} : delete the "id" productOptionDesignRel.
     *
     * @param id the id of the productOptionDesignRelDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/product-option-design-rels/{id}")
    public ResponseEntity<Void> deleteProductOptionDesignRel(@PathVariable Long id) {
        log.debug("REST request to delete ProductOptionDesignRel : {}", id);
        productOptionDesignRelService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
