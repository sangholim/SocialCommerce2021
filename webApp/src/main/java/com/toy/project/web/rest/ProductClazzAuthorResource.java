package com.toy.project.web.rest;

import com.toy.project.repository.ProductClazzAuthorRepository;
import com.toy.project.service.ProductClazzAuthorService;
import com.toy.project.service.dto.ProductClazzAuthorDTO;
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
 * REST controller for managing {@link com.toy.project.domain.ProductClazzAuthor}.
 */
@RestController
@RequestMapping("/api")
public class ProductClazzAuthorResource {

    private final Logger log = LoggerFactory.getLogger(ProductClazzAuthorResource.class);

    private static final String ENTITY_NAME = "productClazzAuthor";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProductClazzAuthorService productClazzAuthorService;

    private final ProductClazzAuthorRepository productClazzAuthorRepository;

    public ProductClazzAuthorResource(
        ProductClazzAuthorService productClazzAuthorService,
        ProductClazzAuthorRepository productClazzAuthorRepository
    ) {
        this.productClazzAuthorService = productClazzAuthorService;
        this.productClazzAuthorRepository = productClazzAuthorRepository;
    }

    /**
     * {@code POST  /product-clazz-authors} : Create a new productClazzAuthor.
     *
     * @param productClazzAuthorDTO the productClazzAuthorDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new productClazzAuthorDTO, or with status {@code 400 (Bad Request)} if the productClazzAuthor has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/product-clazz-authors")
    public ResponseEntity<ProductClazzAuthorDTO> createProductClazzAuthor(@Valid @RequestBody ProductClazzAuthorDTO productClazzAuthorDTO)
        throws URISyntaxException {
        log.debug("REST request to save ProductClazzAuthor : {}", productClazzAuthorDTO);
        if (productClazzAuthorDTO.getId() != null) {
            throw new BadRequestAlertException("A new productClazzAuthor cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductClazzAuthorDTO result = productClazzAuthorService.save(productClazzAuthorDTO);
        return ResponseEntity
            .created(new URI("/api/product-clazz-authors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /product-clazz-authors/:id} : Updates an existing productClazzAuthor.
     *
     * @param id the id of the productClazzAuthorDTO to save.
     * @param productClazzAuthorDTO the productClazzAuthorDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productClazzAuthorDTO,
     * or with status {@code 400 (Bad Request)} if the productClazzAuthorDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the productClazzAuthorDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/product-clazz-authors/{id}")
    public ResponseEntity<ProductClazzAuthorDTO> updateProductClazzAuthor(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ProductClazzAuthorDTO productClazzAuthorDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ProductClazzAuthor : {}, {}", id, productClazzAuthorDTO);
        if (productClazzAuthorDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, productClazzAuthorDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!productClazzAuthorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ProductClazzAuthorDTO result = productClazzAuthorService.save(productClazzAuthorDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productClazzAuthorDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /product-clazz-authors/:id} : Partial updates given fields of an existing productClazzAuthor, field will ignore if it is null
     *
     * @param id the id of the productClazzAuthorDTO to save.
     * @param productClazzAuthorDTO the productClazzAuthorDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productClazzAuthorDTO,
     * or with status {@code 400 (Bad Request)} if the productClazzAuthorDTO is not valid,
     * or with status {@code 404 (Not Found)} if the productClazzAuthorDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the productClazzAuthorDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/product-clazz-authors/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<ProductClazzAuthorDTO> partialUpdateProductClazzAuthor(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ProductClazzAuthorDTO productClazzAuthorDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ProductClazzAuthor partially : {}, {}", id, productClazzAuthorDTO);
        if (productClazzAuthorDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, productClazzAuthorDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!productClazzAuthorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ProductClazzAuthorDTO> result = productClazzAuthorService.partialUpdate(productClazzAuthorDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productClazzAuthorDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /product-clazz-authors} : get all the productClazzAuthors.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of productClazzAuthors in body.
     */
    @GetMapping("/product-clazz-authors")
    public ResponseEntity<List<ProductClazzAuthorDTO>> getAllProductClazzAuthors(Pageable pageable) {
        log.debug("REST request to get a page of ProductClazzAuthors");
        Page<ProductClazzAuthorDTO> page = productClazzAuthorService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /product-clazz-authors/:id} : get the "id" productClazzAuthor.
     *
     * @param id the id of the productClazzAuthorDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the productClazzAuthorDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/product-clazz-authors/{id}")
    public ResponseEntity<ProductClazzAuthorDTO> getProductClazzAuthor(@PathVariable Long id) {
        log.debug("REST request to get ProductClazzAuthor : {}", id);
        Optional<ProductClazzAuthorDTO> productClazzAuthorDTO = productClazzAuthorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(productClazzAuthorDTO);
    }

    /**
     * {@code DELETE  /product-clazz-authors/:id} : delete the "id" productClazzAuthor.
     *
     * @param id the id of the productClazzAuthorDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/product-clazz-authors/{id}")
    public ResponseEntity<Void> deleteProductClazzAuthor(@PathVariable Long id) {
        log.debug("REST request to delete ProductClazzAuthor : {}", id);
        productClazzAuthorService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
