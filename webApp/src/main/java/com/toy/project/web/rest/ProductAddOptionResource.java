package com.toy.project.web.rest;

import com.toy.project.repository.ProductAddOptionRepository;
import com.toy.project.service.ProductAddOptionService;
import com.toy.project.service.dto.ProductAddOptionDTO;
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
 * REST controller for managing {@link com.toy.project.domain.ProductAddOption}.
 */
@RestController
@RequestMapping("/api")
public class ProductAddOptionResource {

    private final Logger log = LoggerFactory.getLogger(ProductAddOptionResource.class);

    private static final String ENTITY_NAME = "productAddOption";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProductAddOptionService productAddOptionService;

    private final ProductAddOptionRepository productAddOptionRepository;

    public ProductAddOptionResource(
        ProductAddOptionService productAddOptionService,
        ProductAddOptionRepository productAddOptionRepository
    ) {
        this.productAddOptionService = productAddOptionService;
        this.productAddOptionRepository = productAddOptionRepository;
    }

    /**
     * {@code POST  /product-add-options} : Create a new productAddOption.
     *
     * @param productAddOptionDTO the productAddOptionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new productAddOptionDTO, or with status {@code 400 (Bad Request)} if the productAddOption has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/product-add-options")
    public ResponseEntity<ProductAddOptionDTO> createProductAddOption(@Valid @RequestBody ProductAddOptionDTO productAddOptionDTO)
        throws URISyntaxException {
        log.debug("REST request to save ProductAddOption : {}", productAddOptionDTO);
        if (productAddOptionDTO.getId() != null) {
            throw new BadRequestAlertException("A new productAddOption cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductAddOptionDTO result = productAddOptionService.save(productAddOptionDTO);
        return ResponseEntity
            .created(new URI("/api/product-add-options/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /product-add-options/:id} : Updates an existing productAddOption.
     *
     * @param id the id of the productAddOptionDTO to save.
     * @param productAddOptionDTO the productAddOptionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productAddOptionDTO,
     * or with status {@code 400 (Bad Request)} if the productAddOptionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the productAddOptionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/product-add-options/{id}")
    public ResponseEntity<ProductAddOptionDTO> updateProductAddOption(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ProductAddOptionDTO productAddOptionDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ProductAddOption : {}, {}", id, productAddOptionDTO);
        if (productAddOptionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, productAddOptionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!productAddOptionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ProductAddOptionDTO result = productAddOptionService.save(productAddOptionDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productAddOptionDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /product-add-options/:id} : Partial updates given fields of an existing productAddOption, field will ignore if it is null
     *
     * @param id the id of the productAddOptionDTO to save.
     * @param productAddOptionDTO the productAddOptionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productAddOptionDTO,
     * or with status {@code 400 (Bad Request)} if the productAddOptionDTO is not valid,
     * or with status {@code 404 (Not Found)} if the productAddOptionDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the productAddOptionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/product-add-options/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<ProductAddOptionDTO> partialUpdateProductAddOption(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ProductAddOptionDTO productAddOptionDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ProductAddOption partially : {}, {}", id, productAddOptionDTO);
        if (productAddOptionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, productAddOptionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!productAddOptionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ProductAddOptionDTO> result = productAddOptionService.partialUpdate(productAddOptionDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productAddOptionDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /product-add-options} : get all the productAddOptions.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of productAddOptions in body.
     */
    @GetMapping("/product-add-options")
    public ResponseEntity<List<ProductAddOptionDTO>> getAllProductAddOptions(Pageable pageable) {
        log.debug("REST request to get a page of ProductAddOptions");
        Page<ProductAddOptionDTO> page = productAddOptionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /product-add-options/:id} : get the "id" productAddOption.
     *
     * @param id the id of the productAddOptionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the productAddOptionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/product-add-options/{id}")
    public ResponseEntity<ProductAddOptionDTO> getProductAddOption(@PathVariable Long id) {
        log.debug("REST request to get ProductAddOption : {}", id);
        Optional<ProductAddOptionDTO> productAddOptionDTO = productAddOptionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(productAddOptionDTO);
    }

    /**
     * {@code DELETE  /product-add-options/:id} : delete the "id" productAddOption.
     *
     * @param id the id of the productAddOptionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/product-add-options/{id}")
    public ResponseEntity<Void> deleteProductAddOption(@PathVariable Long id) {
        log.debug("REST request to delete ProductAddOption : {}", id);
        productAddOptionService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
