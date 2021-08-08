package com.toy.project.web.rest;

import com.toy.project.repository.ProductCategoryManageRepository;
import com.toy.project.service.ProductCategoryManageService;
import com.toy.project.service.dto.ProductCategoryManageDTO;
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
 * REST controller for managing {@link com.toy.project.domain.ProductCategoryManage}.
 */
@RestController
@RequestMapping("/api")
public class ProductCategoryManageResource {

    private final Logger log = LoggerFactory.getLogger(ProductCategoryManageResource.class);

    private static final String ENTITY_NAME = "productCategoryManage";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProductCategoryManageService productCategoryManageService;

    private final ProductCategoryManageRepository productCategoryManageRepository;

    public ProductCategoryManageResource(
        ProductCategoryManageService productCategoryManageService,
        ProductCategoryManageRepository productCategoryManageRepository
    ) {
        this.productCategoryManageService = productCategoryManageService;
        this.productCategoryManageRepository = productCategoryManageRepository;
    }

    /**
     * {@code POST  /product-category-manages} : Create a new productCategoryManage.
     *
     * @param productCategoryManageDTO the productCategoryManageDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new productCategoryManageDTO, or with status {@code 400 (Bad Request)} if the productCategoryManage has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/product-category-manages")
    public ResponseEntity<ProductCategoryManageDTO> createProductCategoryManage(
        @Valid @RequestBody ProductCategoryManageDTO productCategoryManageDTO
    ) throws URISyntaxException {
        log.debug("REST request to save ProductCategoryManage : {}", productCategoryManageDTO);
        if (productCategoryManageDTO.getId() != null) {
            throw new BadRequestAlertException("A new productCategoryManage cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductCategoryManageDTO result = productCategoryManageService.save(productCategoryManageDTO);
        return ResponseEntity
            .created(new URI("/api/product-category-manages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /product-category-manages/:id} : Updates an existing productCategoryManage.
     *
     * @param id the id of the productCategoryManageDTO to save.
     * @param productCategoryManageDTO the productCategoryManageDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productCategoryManageDTO,
     * or with status {@code 400 (Bad Request)} if the productCategoryManageDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the productCategoryManageDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/product-category-manages/{id}")
    public ResponseEntity<ProductCategoryManageDTO> updateProductCategoryManage(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ProductCategoryManageDTO productCategoryManageDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ProductCategoryManage : {}, {}", id, productCategoryManageDTO);
        if (productCategoryManageDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, productCategoryManageDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!productCategoryManageRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ProductCategoryManageDTO result = productCategoryManageService.save(productCategoryManageDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productCategoryManageDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /product-category-manages/:id} : Partial updates given fields of an existing productCategoryManage, field will ignore if it is null
     *
     * @param id the id of the productCategoryManageDTO to save.
     * @param productCategoryManageDTO the productCategoryManageDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productCategoryManageDTO,
     * or with status {@code 400 (Bad Request)} if the productCategoryManageDTO is not valid,
     * or with status {@code 404 (Not Found)} if the productCategoryManageDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the productCategoryManageDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/product-category-manages/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<ProductCategoryManageDTO> partialUpdateProductCategoryManage(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ProductCategoryManageDTO productCategoryManageDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ProductCategoryManage partially : {}, {}", id, productCategoryManageDTO);
        if (productCategoryManageDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, productCategoryManageDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!productCategoryManageRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ProductCategoryManageDTO> result = productCategoryManageService.partialUpdate(productCategoryManageDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productCategoryManageDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /product-category-manages} : get all the productCategoryManages.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of productCategoryManages in body.
     */
    @GetMapping("/product-category-manages")
    public ResponseEntity<List<ProductCategoryManageDTO>> getAllProductCategoryManages(Pageable pageable) {
        log.debug("REST request to get a page of ProductCategoryManages");
        Page<ProductCategoryManageDTO> page = productCategoryManageService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /product-category-manages/:id} : get the "id" productCategoryManage.
     *
     * @param id the id of the productCategoryManageDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the productCategoryManageDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/product-category-manages/{id}")
    public ResponseEntity<ProductCategoryManageDTO> getProductCategoryManage(@PathVariable Long id) {
        log.debug("REST request to get ProductCategoryManage : {}", id);
        Optional<ProductCategoryManageDTO> productCategoryManageDTO = productCategoryManageService.findOne(id);
        return ResponseUtil.wrapOrNotFound(productCategoryManageDTO);
    }

    /**
     * {@code DELETE  /product-category-manages/:id} : delete the "id" productCategoryManage.
     *
     * @param id the id of the productCategoryManageDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/product-category-manages/{id}")
    public ResponseEntity<Void> deleteProductCategoryManage(@PathVariable Long id) {
        log.debug("REST request to delete ProductCategoryManage : {}", id);
        productCategoryManageService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
