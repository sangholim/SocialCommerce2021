package com.toy.project.web.rest;

import com.toy.project.repository.ProductInputOptionRepository;
import com.toy.project.service.ProductInputOptionService;
import com.toy.project.service.dto.ProductInputOptionDTO;
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
 * REST controller for managing {@link com.toy.project.domain.ProductInputOption}.
 */
@RestController
@RequestMapping("/api")
public class ProductInputOptionResource {

    private final Logger log = LoggerFactory.getLogger(ProductInputOptionResource.class);

    private static final String ENTITY_NAME = "productInputOption";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProductInputOptionService productInputOptionService;

    private final ProductInputOptionRepository productInputOptionRepository;

    public ProductInputOptionResource(
        ProductInputOptionService productInputOptionService,
        ProductInputOptionRepository productInputOptionRepository
    ) {
        this.productInputOptionService = productInputOptionService;
        this.productInputOptionRepository = productInputOptionRepository;
    }

    /**
     * {@code POST  /product-input-options} : Create a new productInputOption.
     *
     * @param productInputOptionDTO the productInputOptionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new productInputOptionDTO, or with status {@code 400 (Bad Request)} if the productInputOption has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/product-input-options")
    public ResponseEntity<ProductInputOptionDTO> createProductInputOption(@Valid @RequestBody ProductInputOptionDTO productInputOptionDTO)
        throws URISyntaxException {
        log.debug("REST request to save ProductInputOption : {}", productInputOptionDTO);
        if (productInputOptionDTO.getId() != null) {
            throw new BadRequestAlertException("A new productInputOption cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductInputOptionDTO result = productInputOptionService.save(productInputOptionDTO);
        return ResponseEntity
            .created(new URI("/api/product-input-options/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /product-input-options/:id} : Updates an existing productInputOption.
     *
     * @param id the id of the productInputOptionDTO to save.
     * @param productInputOptionDTO the productInputOptionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productInputOptionDTO,
     * or with status {@code 400 (Bad Request)} if the productInputOptionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the productInputOptionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/product-input-options/{id}")
    public ResponseEntity<ProductInputOptionDTO> updateProductInputOption(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ProductInputOptionDTO productInputOptionDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ProductInputOption : {}, {}", id, productInputOptionDTO);
        if (productInputOptionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, productInputOptionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!productInputOptionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ProductInputOptionDTO result = productInputOptionService.save(productInputOptionDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productInputOptionDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /product-input-options/:id} : Partial updates given fields of an existing productInputOption, field will ignore if it is null
     *
     * @param id the id of the productInputOptionDTO to save.
     * @param productInputOptionDTO the productInputOptionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productInputOptionDTO,
     * or with status {@code 400 (Bad Request)} if the productInputOptionDTO is not valid,
     * or with status {@code 404 (Not Found)} if the productInputOptionDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the productInputOptionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/product-input-options/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<ProductInputOptionDTO> partialUpdateProductInputOption(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ProductInputOptionDTO productInputOptionDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ProductInputOption partially : {}, {}", id, productInputOptionDTO);
        if (productInputOptionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, productInputOptionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!productInputOptionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ProductInputOptionDTO> result = productInputOptionService.partialUpdate(productInputOptionDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productInputOptionDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /product-input-options} : get all the productInputOptions.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of productInputOptions in body.
     */
    @GetMapping("/product-input-options")
    public ResponseEntity<List<ProductInputOptionDTO>> getAllProductInputOptions(Pageable pageable) {
        log.debug("REST request to get a page of ProductInputOptions");
        Page<ProductInputOptionDTO> page = productInputOptionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /product-input-options/:id} : get the "id" productInputOption.
     *
     * @param id the id of the productInputOptionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the productInputOptionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/product-input-options/{id}")
    public ResponseEntity<ProductInputOptionDTO> getProductInputOption(@PathVariable Long id) {
        log.debug("REST request to get ProductInputOption : {}", id);
        Optional<ProductInputOptionDTO> productInputOptionDTO = productInputOptionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(productInputOptionDTO);
    }

    /**
     * {@code DELETE  /product-input-options/:id} : delete the "id" productInputOption.
     *
     * @param id the id of the productInputOptionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/product-input-options/{id}")
    public ResponseEntity<Void> deleteProductInputOption(@PathVariable Long id) {
        log.debug("REST request to delete ProductInputOption : {}", id);
        productInputOptionService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
