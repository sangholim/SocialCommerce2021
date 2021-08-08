package com.toy.project.web.rest;

import com.toy.project.repository.ProductMappingManageRepository;
import com.toy.project.service.ProductMappingManageService;
import com.toy.project.service.dto.ProductMappingManageDTO;
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
 * REST controller for managing {@link com.toy.project.domain.ProductMappingManage}.
 */
@RestController
@RequestMapping("/api")
public class ProductMappingManageResource {

    private final Logger log = LoggerFactory.getLogger(ProductMappingManageResource.class);

    private static final String ENTITY_NAME = "productMappingManage";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProductMappingManageService productMappingManageService;

    private final ProductMappingManageRepository productMappingManageRepository;

    public ProductMappingManageResource(
        ProductMappingManageService productMappingManageService,
        ProductMappingManageRepository productMappingManageRepository
    ) {
        this.productMappingManageService = productMappingManageService;
        this.productMappingManageRepository = productMappingManageRepository;
    }

    /**
     * {@code POST  /product-mapping-manages} : Create a new productMappingManage.
     *
     * @param productMappingManageDTO the productMappingManageDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new productMappingManageDTO, or with status {@code 400 (Bad Request)} if the productMappingManage has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/product-mapping-manages")
    public ResponseEntity<ProductMappingManageDTO> createProductMappingManage(
        @Valid @RequestBody ProductMappingManageDTO productMappingManageDTO
    ) throws URISyntaxException {
        log.debug("REST request to save ProductMappingManage : {}", productMappingManageDTO);
        if (productMappingManageDTO.getId() != null) {
            throw new BadRequestAlertException("A new productMappingManage cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductMappingManageDTO result = productMappingManageService.save(productMappingManageDTO);
        return ResponseEntity
            .created(new URI("/api/product-mapping-manages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /product-mapping-manages/:id} : Updates an existing productMappingManage.
     *
     * @param id the id of the productMappingManageDTO to save.
     * @param productMappingManageDTO the productMappingManageDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productMappingManageDTO,
     * or with status {@code 400 (Bad Request)} if the productMappingManageDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the productMappingManageDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/product-mapping-manages/{id}")
    public ResponseEntity<ProductMappingManageDTO> updateProductMappingManage(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ProductMappingManageDTO productMappingManageDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ProductMappingManage : {}, {}", id, productMappingManageDTO);
        if (productMappingManageDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, productMappingManageDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!productMappingManageRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ProductMappingManageDTO result = productMappingManageService.save(productMappingManageDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productMappingManageDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /product-mapping-manages/:id} : Partial updates given fields of an existing productMappingManage, field will ignore if it is null
     *
     * @param id the id of the productMappingManageDTO to save.
     * @param productMappingManageDTO the productMappingManageDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productMappingManageDTO,
     * or with status {@code 400 (Bad Request)} if the productMappingManageDTO is not valid,
     * or with status {@code 404 (Not Found)} if the productMappingManageDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the productMappingManageDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/product-mapping-manages/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<ProductMappingManageDTO> partialUpdateProductMappingManage(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ProductMappingManageDTO productMappingManageDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ProductMappingManage partially : {}, {}", id, productMappingManageDTO);
        if (productMappingManageDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, productMappingManageDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!productMappingManageRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ProductMappingManageDTO> result = productMappingManageService.partialUpdate(productMappingManageDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productMappingManageDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /product-mapping-manages} : get all the productMappingManages.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of productMappingManages in body.
     */
    @GetMapping("/product-mapping-manages")
    public ResponseEntity<List<ProductMappingManageDTO>> getAllProductMappingManages(Pageable pageable) {
        log.debug("REST request to get a page of ProductMappingManages");
        Page<ProductMappingManageDTO> page = productMappingManageService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /product-mapping-manages/:id} : get the "id" productMappingManage.
     *
     * @param id the id of the productMappingManageDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the productMappingManageDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/product-mapping-manages/{id}")
    public ResponseEntity<ProductMappingManageDTO> getProductMappingManage(@PathVariable Long id) {
        log.debug("REST request to get ProductMappingManage : {}", id);
        Optional<ProductMappingManageDTO> productMappingManageDTO = productMappingManageService.findOne(id);
        return ResponseUtil.wrapOrNotFound(productMappingManageDTO);
    }

    /**
     * {@code DELETE  /product-mapping-manages/:id} : delete the "id" productMappingManage.
     *
     * @param id the id of the productMappingManageDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/product-mapping-manages/{id}")
    public ResponseEntity<Void> deleteProductMappingManage(@PathVariable Long id) {
        log.debug("REST request to delete ProductMappingManage : {}", id);
        productMappingManageService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
