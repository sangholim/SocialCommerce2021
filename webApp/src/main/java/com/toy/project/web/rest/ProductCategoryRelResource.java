package com.toy.project.web.rest;

import com.toy.project.repository.ProductCategoryRelRepository;
import com.toy.project.service.ProductCategoryRelQueryService;
import com.toy.project.service.ProductCategoryRelService;
import com.toy.project.service.criteria.ProductCategoryRelCriteria;
import com.toy.project.service.dto.ProductCategoryRelDTO;
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
 * REST controller for managing {@link com.toy.project.domain.ProductCategoryRel}.
 */
@RestController
@RequestMapping("/api")
public class ProductCategoryRelResource {

    private final Logger log = LoggerFactory.getLogger(ProductCategoryRelResource.class);

    private static final String ENTITY_NAME = "productCategoryRel";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProductCategoryRelService productCategoryRelService;

    private final ProductCategoryRelRepository productCategoryRelRepository;

    private final ProductCategoryRelQueryService productCategoryRelQueryService;

    public ProductCategoryRelResource(
        ProductCategoryRelService productCategoryRelService,
        ProductCategoryRelRepository productCategoryRelRepository,
        ProductCategoryRelQueryService productCategoryRelQueryService
    ) {
        this.productCategoryRelService = productCategoryRelService;
        this.productCategoryRelRepository = productCategoryRelRepository;
        this.productCategoryRelQueryService = productCategoryRelQueryService;
    }

    /**
     * {@code POST  /product-category-rels} : Create a new productCategoryRel.
     *
     * @param productCategoryRelDTO the productCategoryRelDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new productCategoryRelDTO, or with status {@code 400 (Bad Request)} if the productCategoryRel has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/product-category-rels")
    public ResponseEntity<ProductCategoryRelDTO> createProductCategoryRel(@Valid @RequestBody ProductCategoryRelDTO productCategoryRelDTO)
        throws URISyntaxException {
        log.debug("REST request to save ProductCategoryRel : {}", productCategoryRelDTO);
        if (productCategoryRelDTO.getId() != null) {
            throw new BadRequestAlertException("A new productCategoryRel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductCategoryRelDTO result = productCategoryRelService.save(productCategoryRelDTO);
        return ResponseEntity
            .created(new URI("/api/product-category-rels/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /product-category-rels/:id} : Updates an existing productCategoryRel.
     *
     * @param id the id of the productCategoryRelDTO to save.
     * @param productCategoryRelDTO the productCategoryRelDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productCategoryRelDTO,
     * or with status {@code 400 (Bad Request)} if the productCategoryRelDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the productCategoryRelDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/product-category-rels/{id}")
    public ResponseEntity<ProductCategoryRelDTO> updateProductCategoryRel(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ProductCategoryRelDTO productCategoryRelDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ProductCategoryRel : {}, {}", id, productCategoryRelDTO);
        if (productCategoryRelDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, productCategoryRelDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!productCategoryRelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ProductCategoryRelDTO result = productCategoryRelService.save(productCategoryRelDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productCategoryRelDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /product-category-rels/:id} : Partial updates given fields of an existing productCategoryRel, field will ignore if it is null
     *
     * @param id the id of the productCategoryRelDTO to save.
     * @param productCategoryRelDTO the productCategoryRelDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productCategoryRelDTO,
     * or with status {@code 400 (Bad Request)} if the productCategoryRelDTO is not valid,
     * or with status {@code 404 (Not Found)} if the productCategoryRelDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the productCategoryRelDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/product-category-rels/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<ProductCategoryRelDTO> partialUpdateProductCategoryRel(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ProductCategoryRelDTO productCategoryRelDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ProductCategoryRel partially : {}, {}", id, productCategoryRelDTO);
        if (productCategoryRelDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, productCategoryRelDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!productCategoryRelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ProductCategoryRelDTO> result = productCategoryRelService.partialUpdate(productCategoryRelDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productCategoryRelDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /product-category-rels} : get all the productCategoryRels.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of productCategoryRels in body.
     */
    @GetMapping("/product-category-rels")
    public ResponseEntity<List<ProductCategoryRelDTO>> getAllProductCategoryRels(ProductCategoryRelCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ProductCategoryRels by criteria: {}", criteria);
        Page<ProductCategoryRelDTO> page = productCategoryRelQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /product-category-rels/count} : count all the productCategoryRels.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/product-category-rels/count")
    public ResponseEntity<Long> countProductCategoryRels(ProductCategoryRelCriteria criteria) {
        log.debug("REST request to count ProductCategoryRels by criteria: {}", criteria);
        return ResponseEntity.ok().body(productCategoryRelQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /product-category-rels/:id} : get the "id" productCategoryRel.
     *
     * @param id the id of the productCategoryRelDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the productCategoryRelDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/product-category-rels/{id}")
    public ResponseEntity<ProductCategoryRelDTO> getProductCategoryRel(@PathVariable Long id) {
        log.debug("REST request to get ProductCategoryRel : {}", id);
        Optional<ProductCategoryRelDTO> productCategoryRelDTO = productCategoryRelService.findOne(id);
        return ResponseUtil.wrapOrNotFound(productCategoryRelDTO);
    }

    /**
     * {@code DELETE  /product-category-rels/:id} : delete the "id" productCategoryRel.
     *
     * @param id the id of the productCategoryRelDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/product-category-rels/{id}")
    public ResponseEntity<Void> deleteProductCategoryRel(@PathVariable Long id) {
        log.debug("REST request to delete ProductCategoryRel : {}", id);
        productCategoryRelService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
