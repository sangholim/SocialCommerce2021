package com.toy.project.web.rest;

import com.toy.project.repository.ProductAnnounceTemplateRepository;
import com.toy.project.service.ProductAnnounceTemplateService;
import com.toy.project.service.dto.ProductAnnounceTemplateDTO;
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
 * REST controller for managing {@link com.toy.project.domain.ProductAnnounceTemplate}.
 */
@RestController
@RequestMapping("/api")
public class ProductAnnounceTemplateResource {

    private final Logger log = LoggerFactory.getLogger(ProductAnnounceTemplateResource.class);

    private static final String ENTITY_NAME = "productAnnounceTemplate";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProductAnnounceTemplateService productAnnounceTemplateService;

    private final ProductAnnounceTemplateRepository productAnnounceTemplateRepository;

    public ProductAnnounceTemplateResource(
        ProductAnnounceTemplateService productAnnounceTemplateService,
        ProductAnnounceTemplateRepository productAnnounceTemplateRepository
    ) {
        this.productAnnounceTemplateService = productAnnounceTemplateService;
        this.productAnnounceTemplateRepository = productAnnounceTemplateRepository;
    }

    /**
     * {@code POST  /product-announce-templates} : Create a new productAnnounceTemplate.
     *
     * @param productAnnounceTemplateDTO the productAnnounceTemplateDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new productAnnounceTemplateDTO, or with status {@code 400 (Bad Request)} if the productAnnounceTemplate has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/product-announce-templates")
    public ResponseEntity<ProductAnnounceTemplateDTO> createProductAnnounceTemplate(
        @Valid @RequestBody ProductAnnounceTemplateDTO productAnnounceTemplateDTO
    ) throws URISyntaxException {
        log.debug("REST request to save ProductAnnounceTemplate : {}", productAnnounceTemplateDTO);
        if (productAnnounceTemplateDTO.getId() != null) {
            throw new BadRequestAlertException("A new productAnnounceTemplate cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductAnnounceTemplateDTO result = productAnnounceTemplateService.save(productAnnounceTemplateDTO);
        return ResponseEntity
            .created(new URI("/api/product-announce-templates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /product-announce-templates/:id} : Updates an existing productAnnounceTemplate.
     *
     * @param id the id of the productAnnounceTemplateDTO to save.
     * @param productAnnounceTemplateDTO the productAnnounceTemplateDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productAnnounceTemplateDTO,
     * or with status {@code 400 (Bad Request)} if the productAnnounceTemplateDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the productAnnounceTemplateDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/product-announce-templates/{id}")
    public ResponseEntity<ProductAnnounceTemplateDTO> updateProductAnnounceTemplate(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ProductAnnounceTemplateDTO productAnnounceTemplateDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ProductAnnounceTemplate : {}, {}", id, productAnnounceTemplateDTO);
        if (productAnnounceTemplateDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, productAnnounceTemplateDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!productAnnounceTemplateRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ProductAnnounceTemplateDTO result = productAnnounceTemplateService.save(productAnnounceTemplateDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productAnnounceTemplateDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /product-announce-templates/:id} : Partial updates given fields of an existing productAnnounceTemplate, field will ignore if it is null
     *
     * @param id the id of the productAnnounceTemplateDTO to save.
     * @param productAnnounceTemplateDTO the productAnnounceTemplateDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productAnnounceTemplateDTO,
     * or with status {@code 400 (Bad Request)} if the productAnnounceTemplateDTO is not valid,
     * or with status {@code 404 (Not Found)} if the productAnnounceTemplateDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the productAnnounceTemplateDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/product-announce-templates/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<ProductAnnounceTemplateDTO> partialUpdateProductAnnounceTemplate(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ProductAnnounceTemplateDTO productAnnounceTemplateDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ProductAnnounceTemplate partially : {}, {}", id, productAnnounceTemplateDTO);
        if (productAnnounceTemplateDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, productAnnounceTemplateDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!productAnnounceTemplateRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ProductAnnounceTemplateDTO> result = productAnnounceTemplateService.partialUpdate(productAnnounceTemplateDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productAnnounceTemplateDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /product-announce-templates} : get all the productAnnounceTemplates.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of productAnnounceTemplates in body.
     */
    @GetMapping("/product-announce-templates")
    public ResponseEntity<List<ProductAnnounceTemplateDTO>> getAllProductAnnounceTemplates(Pageable pageable) {
        log.debug("REST request to get a page of ProductAnnounceTemplates");
        Page<ProductAnnounceTemplateDTO> page = productAnnounceTemplateService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /product-announce-templates/:id} : get the "id" productAnnounceTemplate.
     *
     * @param id the id of the productAnnounceTemplateDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the productAnnounceTemplateDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/product-announce-templates/{id}")
    public ResponseEntity<ProductAnnounceTemplateDTO> getProductAnnounceTemplate(@PathVariable Long id) {
        log.debug("REST request to get ProductAnnounceTemplate : {}", id);
        Optional<ProductAnnounceTemplateDTO> productAnnounceTemplateDTO = productAnnounceTemplateService.findOne(id);
        return ResponseUtil.wrapOrNotFound(productAnnounceTemplateDTO);
    }

    /**
     * {@code DELETE  /product-announce-templates/:id} : delete the "id" productAnnounceTemplate.
     *
     * @param id the id of the productAnnounceTemplateDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/product-announce-templates/{id}")
    public ResponseEntity<Void> deleteProductAnnounceTemplate(@PathVariable Long id) {
        log.debug("REST request to delete ProductAnnounceTemplate : {}", id);
        productAnnounceTemplateService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
