package com.toy.project.web.rest;

import com.toy.project.repository.ProductShippingRepository;
import com.toy.project.service.ProductShippingQueryService;
import com.toy.project.service.ProductShippingService;
import com.toy.project.service.criteria.ProductShippingCriteria;
import com.toy.project.service.dto.ProductShippingDTO;
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
 * REST controller for managing {@link com.toy.project.domain.ProductShipping}.
 */
@RestController
@RequestMapping("/api")
public class ProductShippingResource {

    private final Logger log = LoggerFactory.getLogger(ProductShippingResource.class);

    private static final String ENTITY_NAME = "productShipping";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProductShippingService productShippingService;

    private final ProductShippingRepository productShippingRepository;

    private final ProductShippingQueryService productShippingQueryService;

    public ProductShippingResource(
        ProductShippingService productShippingService,
        ProductShippingRepository productShippingRepository,
        ProductShippingQueryService productShippingQueryService
    ) {
        this.productShippingService = productShippingService;
        this.productShippingRepository = productShippingRepository;
        this.productShippingQueryService = productShippingQueryService;
    }

    /**
     * {@code POST  /product-shippings} : Create a new productShipping.
     *
     * @param productShippingDTO the productShippingDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new productShippingDTO, or with status {@code 400 (Bad Request)} if the productShipping has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/product-shippings")
    public ResponseEntity<ProductShippingDTO> createProductShipping(@Valid @RequestBody ProductShippingDTO productShippingDTO)
        throws URISyntaxException {
        log.debug("REST request to save ProductShipping : {}", productShippingDTO);
        if (productShippingDTO.getId() != null) {
            throw new BadRequestAlertException("A new productShipping cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductShippingDTO result = productShippingService.save(productShippingDTO);
        return ResponseEntity
            .created(new URI("/api/product-shippings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /product-shippings/:id} : Updates an existing productShipping.
     *
     * @param id the id of the productShippingDTO to save.
     * @param productShippingDTO the productShippingDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productShippingDTO,
     * or with status {@code 400 (Bad Request)} if the productShippingDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the productShippingDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/product-shippings/{id}")
    public ResponseEntity<ProductShippingDTO> updateProductShipping(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ProductShippingDTO productShippingDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ProductShipping : {}, {}", id, productShippingDTO);
        if (productShippingDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, productShippingDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!productShippingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ProductShippingDTO result = productShippingService.save(productShippingDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productShippingDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /product-shippings/:id} : Partial updates given fields of an existing productShipping, field will ignore if it is null
     *
     * @param id the id of the productShippingDTO to save.
     * @param productShippingDTO the productShippingDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productShippingDTO,
     * or with status {@code 400 (Bad Request)} if the productShippingDTO is not valid,
     * or with status {@code 404 (Not Found)} if the productShippingDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the productShippingDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/product-shippings/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<ProductShippingDTO> partialUpdateProductShipping(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ProductShippingDTO productShippingDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ProductShipping partially : {}, {}", id, productShippingDTO);
        if (productShippingDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, productShippingDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!productShippingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ProductShippingDTO> result = productShippingService.partialUpdate(productShippingDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productShippingDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /product-shippings} : get all the productShippings.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of productShippings in body.
     */
    @GetMapping("/product-shippings")
    public ResponseEntity<List<ProductShippingDTO>> getAllProductShippings(ProductShippingCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ProductShippings by criteria: {}", criteria);
        Page<ProductShippingDTO> page = productShippingQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /product-shippings/count} : count all the productShippings.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/product-shippings/count")
    public ResponseEntity<Long> countProductShippings(ProductShippingCriteria criteria) {
        log.debug("REST request to count ProductShippings by criteria: {}", criteria);
        return ResponseEntity.ok().body(productShippingQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /product-shippings/:id} : get the "id" productShipping.
     *
     * @param id the id of the productShippingDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the productShippingDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/product-shippings/{id}")
    public ResponseEntity<ProductShippingDTO> getProductShipping(@PathVariable Long id) {
        log.debug("REST request to get ProductShipping : {}", id);
        Optional<ProductShippingDTO> productShippingDTO = productShippingService.findOne(id);
        return ResponseUtil.wrapOrNotFound(productShippingDTO);
    }

    /**
     * {@code DELETE  /product-shippings/:id} : delete the "id" productShipping.
     *
     * @param id the id of the productShippingDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/product-shippings/{id}")
    public ResponseEntity<Void> deleteProductShipping(@PathVariable Long id) {
        log.debug("REST request to delete ProductShipping : {}", id);
        productShippingService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
