package com.toy.project.web.rest;

import com.toy.project.repository.ProductAnnounceRepository;
import com.toy.project.service.ProductAnnounceService;
import com.toy.project.service.dto.ProductAnnounceDTO;
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
 * REST controller for managing {@link com.toy.project.domain.ProductAnnounce}.
 */
@RestController
@RequestMapping("/api")
public class ProductAnnounceResource {

    private final Logger log = LoggerFactory.getLogger(ProductAnnounceResource.class);

    private static final String ENTITY_NAME = "productAnnounce";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProductAnnounceService productAnnounceService;

    private final ProductAnnounceRepository productAnnounceRepository;

    public ProductAnnounceResource(ProductAnnounceService productAnnounceService, ProductAnnounceRepository productAnnounceRepository) {
        this.productAnnounceService = productAnnounceService;
        this.productAnnounceRepository = productAnnounceRepository;
    }

    /**
     * {@code POST  /product-announces} : Create a new productAnnounce.
     *
     * @param productAnnounceDTO the productAnnounceDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new productAnnounceDTO, or with status {@code 400 (Bad Request)} if the productAnnounce has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/product-announces")
    public ResponseEntity<ProductAnnounceDTO> createProductAnnounce(@Valid @RequestBody ProductAnnounceDTO productAnnounceDTO)
        throws URISyntaxException {
        log.debug("REST request to save ProductAnnounce : {}", productAnnounceDTO);
        if (productAnnounceDTO.getId() != null) {
            throw new BadRequestAlertException("A new productAnnounce cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductAnnounceDTO result = productAnnounceService.save(productAnnounceDTO);
        return ResponseEntity
            .created(new URI("/api/product-announces/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /product-announces/:id} : Updates an existing productAnnounce.
     *
     * @param id the id of the productAnnounceDTO to save.
     * @param productAnnounceDTO the productAnnounceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productAnnounceDTO,
     * or with status {@code 400 (Bad Request)} if the productAnnounceDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the productAnnounceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/product-announces/{id}")
    public ResponseEntity<ProductAnnounceDTO> updateProductAnnounce(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ProductAnnounceDTO productAnnounceDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ProductAnnounce : {}, {}", id, productAnnounceDTO);
        if (productAnnounceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, productAnnounceDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!productAnnounceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ProductAnnounceDTO result = productAnnounceService.save(productAnnounceDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productAnnounceDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /product-announces/:id} : Partial updates given fields of an existing productAnnounce, field will ignore if it is null
     *
     * @param id the id of the productAnnounceDTO to save.
     * @param productAnnounceDTO the productAnnounceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productAnnounceDTO,
     * or with status {@code 400 (Bad Request)} if the productAnnounceDTO is not valid,
     * or with status {@code 404 (Not Found)} if the productAnnounceDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the productAnnounceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/product-announces/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<ProductAnnounceDTO> partialUpdateProductAnnounce(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ProductAnnounceDTO productAnnounceDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ProductAnnounce partially : {}, {}", id, productAnnounceDTO);
        if (productAnnounceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, productAnnounceDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!productAnnounceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ProductAnnounceDTO> result = productAnnounceService.partialUpdate(productAnnounceDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productAnnounceDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /product-announces} : get all the productAnnounces.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of productAnnounces in body.
     */
    @GetMapping("/product-announces")
    public ResponseEntity<List<ProductAnnounceDTO>> getAllProductAnnounces(Pageable pageable) {
        log.debug("REST request to get a page of ProductAnnounces");
        Page<ProductAnnounceDTO> page = productAnnounceService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /product-announces/:id} : get the "id" productAnnounce.
     *
     * @param id the id of the productAnnounceDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the productAnnounceDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/product-announces/{id}")
    public ResponseEntity<ProductAnnounceDTO> getProductAnnounce(@PathVariable Long id) {
        log.debug("REST request to get ProductAnnounce : {}", id);
        Optional<ProductAnnounceDTO> productAnnounceDTO = productAnnounceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(productAnnounceDTO);
    }

    /**
     * {@code DELETE  /product-announces/:id} : delete the "id" productAnnounce.
     *
     * @param id the id of the productAnnounceDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/product-announces/{id}")
    public ResponseEntity<Void> deleteProductAnnounce(@PathVariable Long id) {
        log.debug("REST request to delete ProductAnnounce : {}", id);
        productAnnounceService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
