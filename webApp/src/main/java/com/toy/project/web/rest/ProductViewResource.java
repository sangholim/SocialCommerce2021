package com.toy.project.web.rest;

import com.toy.project.repository.ProductViewRepository;
import com.toy.project.service.ProductViewQueryService;
import com.toy.project.service.ProductViewService;
import com.toy.project.service.criteria.ProductViewCriteria;
import com.toy.project.service.dto.ProductViewDTO;
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
 * REST controller for managing {@link com.toy.project.domain.ProductView}.
 */
@RestController
@RequestMapping("/api")
public class ProductViewResource {

    private final Logger log = LoggerFactory.getLogger(ProductViewResource.class);

    private static final String ENTITY_NAME = "productView";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProductViewService productViewService;

    private final ProductViewRepository productViewRepository;

    private final ProductViewQueryService productViewQueryService;

    public ProductViewResource(
        ProductViewService productViewService,
        ProductViewRepository productViewRepository,
        ProductViewQueryService productViewQueryService
    ) {
        this.productViewService = productViewService;
        this.productViewRepository = productViewRepository;
        this.productViewQueryService = productViewQueryService;
    }

    /**
     * {@code POST  /product-views} : Create a new productView.
     *
     * @param productViewDTO the productViewDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new productViewDTO, or with status {@code 400 (Bad Request)} if the productView has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/product-views")
    public ResponseEntity<ProductViewDTO> createProductView(@Valid @RequestBody ProductViewDTO productViewDTO) throws URISyntaxException {
        log.debug("REST request to save ProductView : {}", productViewDTO);
        if (productViewDTO.getId() != null) {
            throw new BadRequestAlertException("A new productView cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductViewDTO result = productViewService.save(productViewDTO);
        return ResponseEntity
            .created(new URI("/api/product-views/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /product-views/:id} : Updates an existing productView.
     *
     * @param id the id of the productViewDTO to save.
     * @param productViewDTO the productViewDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productViewDTO,
     * or with status {@code 400 (Bad Request)} if the productViewDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the productViewDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/product-views/{id}")
    public ResponseEntity<ProductViewDTO> updateProductView(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ProductViewDTO productViewDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ProductView : {}, {}", id, productViewDTO);
        if (productViewDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, productViewDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!productViewRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ProductViewDTO result = productViewService.save(productViewDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productViewDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /product-views/:id} : Partial updates given fields of an existing productView, field will ignore if it is null
     *
     * @param id the id of the productViewDTO to save.
     * @param productViewDTO the productViewDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productViewDTO,
     * or with status {@code 400 (Bad Request)} if the productViewDTO is not valid,
     * or with status {@code 404 (Not Found)} if the productViewDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the productViewDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/product-views/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<ProductViewDTO> partialUpdateProductView(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ProductViewDTO productViewDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ProductView partially : {}, {}", id, productViewDTO);
        if (productViewDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, productViewDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!productViewRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ProductViewDTO> result = productViewService.partialUpdate(productViewDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productViewDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /product-views} : get all the productViews.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of productViews in body.
     */
    @GetMapping("/product-views")
    public ResponseEntity<List<ProductViewDTO>> getAllProductViews(ProductViewCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ProductViews by criteria: {}", criteria);
        Page<ProductViewDTO> page = productViewQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /product-views/count} : count all the productViews.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/product-views/count")
    public ResponseEntity<Long> countProductViews(ProductViewCriteria criteria) {
        log.debug("REST request to count ProductViews by criteria: {}", criteria);
        return ResponseEntity.ok().body(productViewQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /product-views/:id} : get the "id" productView.
     *
     * @param id the id of the productViewDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the productViewDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/product-views/{id}")
    public ResponseEntity<ProductViewDTO> getProductView(@PathVariable Long id) {
        log.debug("REST request to get ProductView : {}", id);
        Optional<ProductViewDTO> productViewDTO = productViewService.findOne(id);
        return ResponseUtil.wrapOrNotFound(productViewDTO);
    }

    /**
     * {@code DELETE  /product-views/:id} : delete the "id" productView.
     *
     * @param id the id of the productViewDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/product-views/{id}")
    public ResponseEntity<Void> deleteProductView(@PathVariable Long id) {
        log.debug("REST request to delete ProductView : {}", id);
        productViewService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
