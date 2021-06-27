package com.toy.project.web.rest;

import com.toy.project.repository.ProductOptionPackageRelRepository;
import com.toy.project.service.ProductOptionPackageRelQueryService;
import com.toy.project.service.ProductOptionPackageRelService;
import com.toy.project.service.criteria.ProductOptionPackageRelCriteria;
import com.toy.project.service.dto.ProductOptionPackageRelDTO;
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
 * REST controller for managing {@link com.toy.project.domain.ProductOptionPackageRel}.
 */
@RestController
@RequestMapping("/api")
public class ProductOptionPackageRelResource {

    private final Logger log = LoggerFactory.getLogger(ProductOptionPackageRelResource.class);

    private static final String ENTITY_NAME = "productOptionPackageRel";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProductOptionPackageRelService productOptionPackageRelService;

    private final ProductOptionPackageRelRepository productOptionPackageRelRepository;

    private final ProductOptionPackageRelQueryService productOptionPackageRelQueryService;

    public ProductOptionPackageRelResource(
        ProductOptionPackageRelService productOptionPackageRelService,
        ProductOptionPackageRelRepository productOptionPackageRelRepository,
        ProductOptionPackageRelQueryService productOptionPackageRelQueryService
    ) {
        this.productOptionPackageRelService = productOptionPackageRelService;
        this.productOptionPackageRelRepository = productOptionPackageRelRepository;
        this.productOptionPackageRelQueryService = productOptionPackageRelQueryService;
    }

    /**
     * {@code POST  /product-option-package-rels} : Create a new productOptionPackageRel.
     *
     * @param productOptionPackageRelDTO the productOptionPackageRelDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new productOptionPackageRelDTO, or with status {@code 400 (Bad Request)} if the productOptionPackageRel has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/product-option-package-rels")
    public ResponseEntity<ProductOptionPackageRelDTO> createProductOptionPackageRel(
        @Valid @RequestBody ProductOptionPackageRelDTO productOptionPackageRelDTO
    ) throws URISyntaxException {
        log.debug("REST request to save ProductOptionPackageRel : {}", productOptionPackageRelDTO);
        if (productOptionPackageRelDTO.getId() != null) {
            throw new BadRequestAlertException("A new productOptionPackageRel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductOptionPackageRelDTO result = productOptionPackageRelService.save(productOptionPackageRelDTO);
        return ResponseEntity
            .created(new URI("/api/product-option-package-rels/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /product-option-package-rels/:id} : Updates an existing productOptionPackageRel.
     *
     * @param id the id of the productOptionPackageRelDTO to save.
     * @param productOptionPackageRelDTO the productOptionPackageRelDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productOptionPackageRelDTO,
     * or with status {@code 400 (Bad Request)} if the productOptionPackageRelDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the productOptionPackageRelDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/product-option-package-rels/{id}")
    public ResponseEntity<ProductOptionPackageRelDTO> updateProductOptionPackageRel(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ProductOptionPackageRelDTO productOptionPackageRelDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ProductOptionPackageRel : {}, {}", id, productOptionPackageRelDTO);
        if (productOptionPackageRelDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, productOptionPackageRelDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!productOptionPackageRelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ProductOptionPackageRelDTO result = productOptionPackageRelService.save(productOptionPackageRelDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productOptionPackageRelDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /product-option-package-rels/:id} : Partial updates given fields of an existing productOptionPackageRel, field will ignore if it is null
     *
     * @param id the id of the productOptionPackageRelDTO to save.
     * @param productOptionPackageRelDTO the productOptionPackageRelDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productOptionPackageRelDTO,
     * or with status {@code 400 (Bad Request)} if the productOptionPackageRelDTO is not valid,
     * or with status {@code 404 (Not Found)} if the productOptionPackageRelDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the productOptionPackageRelDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/product-option-package-rels/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<ProductOptionPackageRelDTO> partialUpdateProductOptionPackageRel(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ProductOptionPackageRelDTO productOptionPackageRelDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ProductOptionPackageRel partially : {}, {}", id, productOptionPackageRelDTO);
        if (productOptionPackageRelDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, productOptionPackageRelDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!productOptionPackageRelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ProductOptionPackageRelDTO> result = productOptionPackageRelService.partialUpdate(productOptionPackageRelDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productOptionPackageRelDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /product-option-package-rels} : get all the productOptionPackageRels.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of productOptionPackageRels in body.
     */
    @GetMapping("/product-option-package-rels")
    public ResponseEntity<List<ProductOptionPackageRelDTO>> getAllProductOptionPackageRels(
        ProductOptionPackageRelCriteria criteria,
        Pageable pageable
    ) {
        log.debug("REST request to get ProductOptionPackageRels by criteria: {}", criteria);
        Page<ProductOptionPackageRelDTO> page = productOptionPackageRelQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /product-option-package-rels/count} : count all the productOptionPackageRels.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/product-option-package-rels/count")
    public ResponseEntity<Long> countProductOptionPackageRels(ProductOptionPackageRelCriteria criteria) {
        log.debug("REST request to count ProductOptionPackageRels by criteria: {}", criteria);
        return ResponseEntity.ok().body(productOptionPackageRelQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /product-option-package-rels/:id} : get the "id" productOptionPackageRel.
     *
     * @param id the id of the productOptionPackageRelDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the productOptionPackageRelDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/product-option-package-rels/{id}")
    public ResponseEntity<ProductOptionPackageRelDTO> getProductOptionPackageRel(@PathVariable Long id) {
        log.debug("REST request to get ProductOptionPackageRel : {}", id);
        Optional<ProductOptionPackageRelDTO> productOptionPackageRelDTO = productOptionPackageRelService.findOne(id);
        return ResponseUtil.wrapOrNotFound(productOptionPackageRelDTO);
    }

    /**
     * {@code DELETE  /product-option-package-rels/:id} : delete the "id" productOptionPackageRel.
     *
     * @param id the id of the productOptionPackageRelDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/product-option-package-rels/{id}")
    public ResponseEntity<Void> deleteProductOptionPackageRel(@PathVariable Long id) {
        log.debug("REST request to delete ProductOptionPackageRel : {}", id);
        productOptionPackageRelService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
