package com.toy.project.web.rest;

import com.toy.project.repository.ProductViewContentRepository;
import com.toy.project.service.ProductViewContentQueryService;
import com.toy.project.service.ProductViewContentService;
import com.toy.project.service.criteria.ProductViewContentCriteria;
import com.toy.project.service.dto.ProductViewContentDTO;
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
 * REST controller for managing {@link com.toy.project.domain.ProductViewContent}.
 */
@RestController
@RequestMapping("/api")
public class ProductViewContentResource {

    private final Logger log = LoggerFactory.getLogger(ProductViewContentResource.class);

    private static final String ENTITY_NAME = "productViewContent";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProductViewContentService productViewContentService;

    private final ProductViewContentRepository productViewContentRepository;

    private final ProductViewContentQueryService productViewContentQueryService;

    public ProductViewContentResource(
        ProductViewContentService productViewContentService,
        ProductViewContentRepository productViewContentRepository,
        ProductViewContentQueryService productViewContentQueryService
    ) {
        this.productViewContentService = productViewContentService;
        this.productViewContentRepository = productViewContentRepository;
        this.productViewContentQueryService = productViewContentQueryService;
    }

    /**
     * {@code POST  /product-view-contents} : Create a new productViewContent.
     *
     * @param productViewContentDTO the productViewContentDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new productViewContentDTO, or with status {@code 400 (Bad Request)} if the productViewContent has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/product-view-contents")
    public ResponseEntity<ProductViewContentDTO> createProductViewContent(@Valid @RequestBody ProductViewContentDTO productViewContentDTO)
        throws URISyntaxException {
        log.debug("REST request to save ProductViewContent : {}", productViewContentDTO);
        if (productViewContentDTO.getId() != null) {
            throw new BadRequestAlertException("A new productViewContent cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductViewContentDTO result = productViewContentService.save(productViewContentDTO);
        return ResponseEntity
            .created(new URI("/api/product-view-contents/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /product-view-contents/:id} : Updates an existing productViewContent.
     *
     * @param id the id of the productViewContentDTO to save.
     * @param productViewContentDTO the productViewContentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productViewContentDTO,
     * or with status {@code 400 (Bad Request)} if the productViewContentDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the productViewContentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/product-view-contents/{id}")
    public ResponseEntity<ProductViewContentDTO> updateProductViewContent(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ProductViewContentDTO productViewContentDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ProductViewContent : {}, {}", id, productViewContentDTO);
        if (productViewContentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, productViewContentDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!productViewContentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ProductViewContentDTO result = productViewContentService.save(productViewContentDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productViewContentDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /product-view-contents/:id} : Partial updates given fields of an existing productViewContent, field will ignore if it is null
     *
     * @param id the id of the productViewContentDTO to save.
     * @param productViewContentDTO the productViewContentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productViewContentDTO,
     * or with status {@code 400 (Bad Request)} if the productViewContentDTO is not valid,
     * or with status {@code 404 (Not Found)} if the productViewContentDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the productViewContentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/product-view-contents/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<ProductViewContentDTO> partialUpdateProductViewContent(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ProductViewContentDTO productViewContentDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ProductViewContent partially : {}, {}", id, productViewContentDTO);
        if (productViewContentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, productViewContentDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!productViewContentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ProductViewContentDTO> result = productViewContentService.partialUpdate(productViewContentDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productViewContentDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /product-view-contents} : get all the productViewContents.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of productViewContents in body.
     */
    @GetMapping("/product-view-contents")
    public ResponseEntity<List<ProductViewContentDTO>> getAllProductViewContents(ProductViewContentCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ProductViewContents by criteria: {}", criteria);
        Page<ProductViewContentDTO> page = productViewContentQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /product-view-contents/count} : count all the productViewContents.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/product-view-contents/count")
    public ResponseEntity<Long> countProductViewContents(ProductViewContentCriteria criteria) {
        log.debug("REST request to count ProductViewContents by criteria: {}", criteria);
        return ResponseEntity.ok().body(productViewContentQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /product-view-contents/:id} : get the "id" productViewContent.
     *
     * @param id the id of the productViewContentDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the productViewContentDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/product-view-contents/{id}")
    public ResponseEntity<ProductViewContentDTO> getProductViewContent(@PathVariable Long id) {
        log.debug("REST request to get ProductViewContent : {}", id);
        Optional<ProductViewContentDTO> productViewContentDTO = productViewContentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(productViewContentDTO);
    }

    /**
     * {@code DELETE  /product-view-contents/:id} : delete the "id" productViewContent.
     *
     * @param id the id of the productViewContentDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/product-view-contents/{id}")
    public ResponseEntity<Void> deleteProductViewContent(@PathVariable Long id) {
        log.debug("REST request to delete ProductViewContent : {}", id);
        productViewContentService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
