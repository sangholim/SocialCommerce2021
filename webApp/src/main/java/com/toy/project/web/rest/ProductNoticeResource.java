package com.toy.project.web.rest;

import com.toy.project.repository.ProductNoticeRepository;
import com.toy.project.service.ProductNoticeQueryService;
import com.toy.project.service.ProductNoticeService;
import com.toy.project.service.criteria.ProductNoticeCriteria;
import com.toy.project.service.dto.ProductNoticeDTO;
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
 * REST controller for managing {@link com.toy.project.domain.ProductNotice}.
 */
@RestController
@RequestMapping("/api")
public class ProductNoticeResource {

    private final Logger log = LoggerFactory.getLogger(ProductNoticeResource.class);

    private static final String ENTITY_NAME = "productNotice";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProductNoticeService productNoticeService;

    private final ProductNoticeRepository productNoticeRepository;

    private final ProductNoticeQueryService productNoticeQueryService;

    public ProductNoticeResource(
        ProductNoticeService productNoticeService,
        ProductNoticeRepository productNoticeRepository,
        ProductNoticeQueryService productNoticeQueryService
    ) {
        this.productNoticeService = productNoticeService;
        this.productNoticeRepository = productNoticeRepository;
        this.productNoticeQueryService = productNoticeQueryService;
    }

    /**
     * {@code POST  /product-notices} : Create a new productNotice.
     *
     * @param productNoticeDTO the productNoticeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new productNoticeDTO, or with status {@code 400 (Bad Request)} if the productNotice has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/product-notices")
    public ResponseEntity<ProductNoticeDTO> createProductNotice(@Valid @RequestBody ProductNoticeDTO productNoticeDTO)
        throws URISyntaxException {
        log.debug("REST request to save ProductNotice : {}", productNoticeDTO);
        if (productNoticeDTO.getId() != null) {
            throw new BadRequestAlertException("A new productNotice cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductNoticeDTO result = productNoticeService.save(productNoticeDTO);
        return ResponseEntity
            .created(new URI("/api/product-notices/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /product-notices/:id} : Updates an existing productNotice.
     *
     * @param id the id of the productNoticeDTO to save.
     * @param productNoticeDTO the productNoticeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productNoticeDTO,
     * or with status {@code 400 (Bad Request)} if the productNoticeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the productNoticeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/product-notices/{id}")
    public ResponseEntity<ProductNoticeDTO> updateProductNotice(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ProductNoticeDTO productNoticeDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ProductNotice : {}, {}", id, productNoticeDTO);
        if (productNoticeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, productNoticeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!productNoticeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ProductNoticeDTO result = productNoticeService.save(productNoticeDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productNoticeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /product-notices/:id} : Partial updates given fields of an existing productNotice, field will ignore if it is null
     *
     * @param id the id of the productNoticeDTO to save.
     * @param productNoticeDTO the productNoticeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productNoticeDTO,
     * or with status {@code 400 (Bad Request)} if the productNoticeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the productNoticeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the productNoticeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/product-notices/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<ProductNoticeDTO> partialUpdateProductNotice(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ProductNoticeDTO productNoticeDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ProductNotice partially : {}, {}", id, productNoticeDTO);
        if (productNoticeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, productNoticeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!productNoticeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ProductNoticeDTO> result = productNoticeService.partialUpdate(productNoticeDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productNoticeDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /product-notices} : get all the productNotices.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of productNotices in body.
     */
    @GetMapping("/product-notices")
    public ResponseEntity<List<ProductNoticeDTO>> getAllProductNotices(ProductNoticeCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ProductNotices by criteria: {}", criteria);
        Page<ProductNoticeDTO> page = productNoticeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /product-notices/count} : count all the productNotices.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/product-notices/count")
    public ResponseEntity<Long> countProductNotices(ProductNoticeCriteria criteria) {
        log.debug("REST request to count ProductNotices by criteria: {}", criteria);
        return ResponseEntity.ok().body(productNoticeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /product-notices/:id} : get the "id" productNotice.
     *
     * @param id the id of the productNoticeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the productNoticeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/product-notices/{id}")
    public ResponseEntity<ProductNoticeDTO> getProductNotice(@PathVariable Long id) {
        log.debug("REST request to get ProductNotice : {}", id);
        Optional<ProductNoticeDTO> productNoticeDTO = productNoticeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(productNoticeDTO);
    }

    /**
     * {@code DELETE  /product-notices/:id} : delete the "id" productNotice.
     *
     * @param id the id of the productNoticeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/product-notices/{id}")
    public ResponseEntity<Void> deleteProductNotice(@PathVariable Long id) {
        log.debug("REST request to delete ProductNotice : {}", id);
        productNoticeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
