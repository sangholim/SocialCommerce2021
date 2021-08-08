package com.toy.project.web.rest;

import com.toy.project.repository.ProductLabelRepository;
import com.toy.project.service.ProductLabelService;
import com.toy.project.service.dto.ProductLabelDTO;
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
 * REST controller for managing {@link com.toy.project.domain.ProductLabel}.
 */
@RestController
@RequestMapping("/api")
public class ProductLabelResource {

    private final Logger log = LoggerFactory.getLogger(ProductLabelResource.class);

    private static final String ENTITY_NAME = "productLabel";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProductLabelService productLabelService;

    private final ProductLabelRepository productLabelRepository;

    public ProductLabelResource(ProductLabelService productLabelService, ProductLabelRepository productLabelRepository) {
        this.productLabelService = productLabelService;
        this.productLabelRepository = productLabelRepository;
    }

    /**
     * {@code POST  /product-labels} : Create a new productLabel.
     *
     * @param productLabelDTO the productLabelDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new productLabelDTO, or with status {@code 400 (Bad Request)} if the productLabel has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/product-labels")
    public ResponseEntity<ProductLabelDTO> createProductLabel(@Valid @RequestBody ProductLabelDTO productLabelDTO)
        throws URISyntaxException {
        log.debug("REST request to save ProductLabel : {}", productLabelDTO);
        if (productLabelDTO.getId() != null) {
            throw new BadRequestAlertException("A new productLabel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductLabelDTO result = productLabelService.save(productLabelDTO);
        return ResponseEntity
            .created(new URI("/api/product-labels/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /product-labels/:id} : Updates an existing productLabel.
     *
     * @param id the id of the productLabelDTO to save.
     * @param productLabelDTO the productLabelDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productLabelDTO,
     * or with status {@code 400 (Bad Request)} if the productLabelDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the productLabelDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/product-labels/{id}")
    public ResponseEntity<ProductLabelDTO> updateProductLabel(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ProductLabelDTO productLabelDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ProductLabel : {}, {}", id, productLabelDTO);
        if (productLabelDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, productLabelDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!productLabelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ProductLabelDTO result = productLabelService.save(productLabelDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productLabelDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /product-labels/:id} : Partial updates given fields of an existing productLabel, field will ignore if it is null
     *
     * @param id the id of the productLabelDTO to save.
     * @param productLabelDTO the productLabelDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productLabelDTO,
     * or with status {@code 400 (Bad Request)} if the productLabelDTO is not valid,
     * or with status {@code 404 (Not Found)} if the productLabelDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the productLabelDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/product-labels/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<ProductLabelDTO> partialUpdateProductLabel(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ProductLabelDTO productLabelDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ProductLabel partially : {}, {}", id, productLabelDTO);
        if (productLabelDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, productLabelDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!productLabelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ProductLabelDTO> result = productLabelService.partialUpdate(productLabelDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productLabelDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /product-labels} : get all the productLabels.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of productLabels in body.
     */
    @GetMapping("/product-labels")
    public ResponseEntity<List<ProductLabelDTO>> getAllProductLabels(Pageable pageable) {
        log.debug("REST request to get a page of ProductLabels");
        Page<ProductLabelDTO> page = productLabelService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /product-labels/:id} : get the "id" productLabel.
     *
     * @param id the id of the productLabelDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the productLabelDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/product-labels/{id}")
    public ResponseEntity<ProductLabelDTO> getProductLabel(@PathVariable Long id) {
        log.debug("REST request to get ProductLabel : {}", id);
        Optional<ProductLabelDTO> productLabelDTO = productLabelService.findOne(id);
        return ResponseUtil.wrapOrNotFound(productLabelDTO);
    }

    /**
     * {@code DELETE  /product-labels/:id} : delete the "id" productLabel.
     *
     * @param id the id of the productLabelDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/product-labels/{id}")
    public ResponseEntity<Void> deleteProductLabel(@PathVariable Long id) {
        log.debug("REST request to delete ProductLabel : {}", id);
        productLabelService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
