package com.toy.project.web.rest;

import com.toy.project.repository.ProductAddImageRepository;
import com.toy.project.service.ProductAddImageService;
import com.toy.project.service.dto.ProductAddImageDTO;
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
 * REST controller for managing {@link com.toy.project.domain.ProductAddImage}.
 */
@RestController
@RequestMapping("/api")
public class ProductAddImageResource {

    private final Logger log = LoggerFactory.getLogger(ProductAddImageResource.class);

    private static final String ENTITY_NAME = "productAddImage";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProductAddImageService productAddImageService;

    private final ProductAddImageRepository productAddImageRepository;

    public ProductAddImageResource(ProductAddImageService productAddImageService, ProductAddImageRepository productAddImageRepository) {
        this.productAddImageService = productAddImageService;
        this.productAddImageRepository = productAddImageRepository;
    }

    /**
     * {@code POST  /product-add-images} : Create a new productAddImage.
     *
     * @param productAddImageDTO the productAddImageDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new productAddImageDTO, or with status {@code 400 (Bad Request)} if the productAddImage has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/product-add-images")
    public ResponseEntity<ProductAddImageDTO> createProductAddImage(@Valid @RequestBody ProductAddImageDTO productAddImageDTO)
        throws URISyntaxException {
        log.debug("REST request to save ProductAddImage : {}", productAddImageDTO);
        if (productAddImageDTO.getId() != null) {
            throw new BadRequestAlertException("A new productAddImage cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductAddImageDTO result = productAddImageService.save(productAddImageDTO);
        return ResponseEntity
            .created(new URI("/api/product-add-images/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /product-add-images/:id} : Updates an existing productAddImage.
     *
     * @param id the id of the productAddImageDTO to save.
     * @param productAddImageDTO the productAddImageDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productAddImageDTO,
     * or with status {@code 400 (Bad Request)} if the productAddImageDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the productAddImageDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/product-add-images/{id}")
    public ResponseEntity<ProductAddImageDTO> updateProductAddImage(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ProductAddImageDTO productAddImageDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ProductAddImage : {}, {}", id, productAddImageDTO);
        if (productAddImageDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, productAddImageDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!productAddImageRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ProductAddImageDTO result = productAddImageService.save(productAddImageDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productAddImageDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /product-add-images/:id} : Partial updates given fields of an existing productAddImage, field will ignore if it is null
     *
     * @param id the id of the productAddImageDTO to save.
     * @param productAddImageDTO the productAddImageDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productAddImageDTO,
     * or with status {@code 400 (Bad Request)} if the productAddImageDTO is not valid,
     * or with status {@code 404 (Not Found)} if the productAddImageDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the productAddImageDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/product-add-images/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<ProductAddImageDTO> partialUpdateProductAddImage(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ProductAddImageDTO productAddImageDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ProductAddImage partially : {}, {}", id, productAddImageDTO);
        if (productAddImageDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, productAddImageDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!productAddImageRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ProductAddImageDTO> result = productAddImageService.partialUpdate(productAddImageDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productAddImageDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /product-add-images} : get all the productAddImages.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of productAddImages in body.
     */
    @GetMapping("/product-add-images")
    public ResponseEntity<List<ProductAddImageDTO>> getAllProductAddImages(Pageable pageable) {
        log.debug("REST request to get a page of ProductAddImages");
        Page<ProductAddImageDTO> page = productAddImageService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /product-add-images/:id} : get the "id" productAddImage.
     *
     * @param id the id of the productAddImageDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the productAddImageDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/product-add-images/{id}")
    public ResponseEntity<ProductAddImageDTO> getProductAddImage(@PathVariable Long id) {
        log.debug("REST request to get ProductAddImage : {}", id);
        Optional<ProductAddImageDTO> productAddImageDTO = productAddImageService.findOne(id);
        return ResponseUtil.wrapOrNotFound(productAddImageDTO);
    }

    /**
     * {@code DELETE  /product-add-images/:id} : delete the "id" productAddImage.
     *
     * @param id the id of the productAddImageDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/product-add-images/{id}")
    public ResponseEntity<Void> deleteProductAddImage(@PathVariable Long id) {
        log.debug("REST request to delete ProductAddImage : {}", id);
        productAddImageService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
