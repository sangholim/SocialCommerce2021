package com.toy.project.web.rest;

import com.toy.project.repository.ProductTemplateRepository;
import com.toy.project.service.ProductTemplateQueryService;
import com.toy.project.service.ProductTemplateService;
import com.toy.project.service.criteria.ProductTemplateCriteria;
import com.toy.project.service.dto.ProductTemplateDTO;
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
 * REST controller for managing {@link com.toy.project.domain.ProductTemplate}.
 */
@RestController
@RequestMapping("/api")
public class ProductTemplateResource {

    private final Logger log = LoggerFactory.getLogger(ProductTemplateResource.class);

    private static final String ENTITY_NAME = "productTemplate";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProductTemplateService productTemplateService;

    private final ProductTemplateRepository productTemplateRepository;

    private final ProductTemplateQueryService productTemplateQueryService;

    public ProductTemplateResource(
        ProductTemplateService productTemplateService,
        ProductTemplateRepository productTemplateRepository,
        ProductTemplateQueryService productTemplateQueryService
    ) {
        this.productTemplateService = productTemplateService;
        this.productTemplateRepository = productTemplateRepository;
        this.productTemplateQueryService = productTemplateQueryService;
    }

    /**
     * {@code POST  /product-templates} : Create a new productTemplate.
     *
     * @param productTemplateDTO the productTemplateDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new productTemplateDTO, or with status {@code 400 (Bad Request)} if the productTemplate has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/product-templates")
    public ResponseEntity<ProductTemplateDTO> createProductTemplate(@Valid @RequestBody ProductTemplateDTO productTemplateDTO)
        throws URISyntaxException {
        log.debug("REST request to save ProductTemplate : {}", productTemplateDTO);
        if (productTemplateDTO.getId() != null) {
            throw new BadRequestAlertException("A new productTemplate cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductTemplateDTO result = productTemplateService.save(productTemplateDTO);
        return ResponseEntity
            .created(new URI("/api/product-templates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /product-templates/:id} : Updates an existing productTemplate.
     *
     * @param id the id of the productTemplateDTO to save.
     * @param productTemplateDTO the productTemplateDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productTemplateDTO,
     * or with status {@code 400 (Bad Request)} if the productTemplateDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the productTemplateDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/product-templates/{id}")
    public ResponseEntity<ProductTemplateDTO> updateProductTemplate(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ProductTemplateDTO productTemplateDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ProductTemplate : {}, {}", id, productTemplateDTO);
        if (productTemplateDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, productTemplateDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!productTemplateRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ProductTemplateDTO result = productTemplateService.save(productTemplateDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productTemplateDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /product-templates/:id} : Partial updates given fields of an existing productTemplate, field will ignore if it is null
     *
     * @param id the id of the productTemplateDTO to save.
     * @param productTemplateDTO the productTemplateDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productTemplateDTO,
     * or with status {@code 400 (Bad Request)} if the productTemplateDTO is not valid,
     * or with status {@code 404 (Not Found)} if the productTemplateDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the productTemplateDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/product-templates/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<ProductTemplateDTO> partialUpdateProductTemplate(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ProductTemplateDTO productTemplateDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ProductTemplate partially : {}, {}", id, productTemplateDTO);
        if (productTemplateDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, productTemplateDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!productTemplateRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ProductTemplateDTO> result = productTemplateService.partialUpdate(productTemplateDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productTemplateDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /product-templates} : get all the productTemplates.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of productTemplates in body.
     */
    @GetMapping("/product-templates")
    public ResponseEntity<List<ProductTemplateDTO>> getAllProductTemplates(ProductTemplateCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ProductTemplates by criteria: {}", criteria);
        Page<ProductTemplateDTO> page = productTemplateQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /product-templates/count} : count all the productTemplates.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/product-templates/count")
    public ResponseEntity<Long> countProductTemplates(ProductTemplateCriteria criteria) {
        log.debug("REST request to count ProductTemplates by criteria: {}", criteria);
        return ResponseEntity.ok().body(productTemplateQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /product-templates/:id} : get the "id" productTemplate.
     *
     * @param id the id of the productTemplateDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the productTemplateDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/product-templates/{id}")
    public ResponseEntity<ProductTemplateDTO> getProductTemplate(@PathVariable Long id) {
        log.debug("REST request to get ProductTemplate : {}", id);
        Optional<ProductTemplateDTO> productTemplateDTO = productTemplateService.findOne(id);
        return ResponseUtil.wrapOrNotFound(productTemplateDTO);
    }

    /**
     * {@code DELETE  /product-templates/:id} : delete the "id" productTemplate.
     *
     * @param id the id of the productTemplateDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/product-templates/{id}")
    public ResponseEntity<Void> deleteProductTemplate(@PathVariable Long id) {
        log.debug("REST request to delete ProductTemplate : {}", id);
        productTemplateService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
