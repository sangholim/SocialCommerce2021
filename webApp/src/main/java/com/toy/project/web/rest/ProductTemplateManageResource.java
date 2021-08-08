package com.toy.project.web.rest;

import com.toy.project.repository.ProductTemplateManageRepository;
import com.toy.project.service.ProductTemplateManageService;
import com.toy.project.service.dto.ProductTemplateManageDTO;
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
 * REST controller for managing {@link com.toy.project.domain.ProductTemplateManage}.
 */
@RestController
@RequestMapping("/api")
public class ProductTemplateManageResource {

    private final Logger log = LoggerFactory.getLogger(ProductTemplateManageResource.class);

    private static final String ENTITY_NAME = "productTemplateManage";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProductTemplateManageService productTemplateManageService;

    private final ProductTemplateManageRepository productTemplateManageRepository;

    public ProductTemplateManageResource(
        ProductTemplateManageService productTemplateManageService,
        ProductTemplateManageRepository productTemplateManageRepository
    ) {
        this.productTemplateManageService = productTemplateManageService;
        this.productTemplateManageRepository = productTemplateManageRepository;
    }

    /**
     * {@code POST  /product-template-manages} : Create a new productTemplateManage.
     *
     * @param productTemplateManageDTO the productTemplateManageDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new productTemplateManageDTO, or with status {@code 400 (Bad Request)} if the productTemplateManage has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/product-template-manages")
    public ResponseEntity<ProductTemplateManageDTO> createProductTemplateManage(
        @Valid @RequestBody ProductTemplateManageDTO productTemplateManageDTO
    ) throws URISyntaxException {
        log.debug("REST request to save ProductTemplateManage : {}", productTemplateManageDTO);
        if (productTemplateManageDTO.getId() != null) {
            throw new BadRequestAlertException("A new productTemplateManage cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductTemplateManageDTO result = productTemplateManageService.save(productTemplateManageDTO);
        return ResponseEntity
            .created(new URI("/api/product-template-manages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /product-template-manages/:id} : Updates an existing productTemplateManage.
     *
     * @param id the id of the productTemplateManageDTO to save.
     * @param productTemplateManageDTO the productTemplateManageDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productTemplateManageDTO,
     * or with status {@code 400 (Bad Request)} if the productTemplateManageDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the productTemplateManageDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/product-template-manages/{id}")
    public ResponseEntity<ProductTemplateManageDTO> updateProductTemplateManage(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ProductTemplateManageDTO productTemplateManageDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ProductTemplateManage : {}, {}", id, productTemplateManageDTO);
        if (productTemplateManageDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, productTemplateManageDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!productTemplateManageRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ProductTemplateManageDTO result = productTemplateManageService.save(productTemplateManageDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productTemplateManageDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /product-template-manages/:id} : Partial updates given fields of an existing productTemplateManage, field will ignore if it is null
     *
     * @param id the id of the productTemplateManageDTO to save.
     * @param productTemplateManageDTO the productTemplateManageDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productTemplateManageDTO,
     * or with status {@code 400 (Bad Request)} if the productTemplateManageDTO is not valid,
     * or with status {@code 404 (Not Found)} if the productTemplateManageDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the productTemplateManageDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/product-template-manages/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<ProductTemplateManageDTO> partialUpdateProductTemplateManage(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ProductTemplateManageDTO productTemplateManageDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ProductTemplateManage partially : {}, {}", id, productTemplateManageDTO);
        if (productTemplateManageDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, productTemplateManageDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!productTemplateManageRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ProductTemplateManageDTO> result = productTemplateManageService.partialUpdate(productTemplateManageDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productTemplateManageDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /product-template-manages} : get all the productTemplateManages.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of productTemplateManages in body.
     */
    @GetMapping("/product-template-manages")
    public ResponseEntity<List<ProductTemplateManageDTO>> getAllProductTemplateManages(Pageable pageable) {
        log.debug("REST request to get a page of ProductTemplateManages");
        Page<ProductTemplateManageDTO> page = productTemplateManageService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /product-template-manages/:id} : get the "id" productTemplateManage.
     *
     * @param id the id of the productTemplateManageDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the productTemplateManageDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/product-template-manages/{id}")
    public ResponseEntity<ProductTemplateManageDTO> getProductTemplateManage(@PathVariable Long id) {
        log.debug("REST request to get ProductTemplateManage : {}", id);
        Optional<ProductTemplateManageDTO> productTemplateManageDTO = productTemplateManageService.findOne(id);
        return ResponseUtil.wrapOrNotFound(productTemplateManageDTO);
    }

    /**
     * {@code DELETE  /product-template-manages/:id} : delete the "id" productTemplateManage.
     *
     * @param id the id of the productTemplateManageDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/product-template-manages/{id}")
    public ResponseEntity<Void> deleteProductTemplateManage(@PathVariable Long id) {
        log.debug("REST request to delete ProductTemplateManage : {}", id);
        productTemplateManageService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
