package com.toy.project.web.rest;

import com.toy.project.repository.ProductNoticeManageRepository;
import com.toy.project.service.ProductNoticeManageService;
import com.toy.project.service.dto.ProductNoticeManageDTO;
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
 * REST controller for managing {@link com.toy.project.domain.ProductNoticeManage}.
 */
@RestController
@RequestMapping("/api")
public class ProductNoticeManageResource {

    private final Logger log = LoggerFactory.getLogger(ProductNoticeManageResource.class);

    private static final String ENTITY_NAME = "productNoticeManage";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProductNoticeManageService productNoticeManageService;

    private final ProductNoticeManageRepository productNoticeManageRepository;

    public ProductNoticeManageResource(
        ProductNoticeManageService productNoticeManageService,
        ProductNoticeManageRepository productNoticeManageRepository
    ) {
        this.productNoticeManageService = productNoticeManageService;
        this.productNoticeManageRepository = productNoticeManageRepository;
    }

    /**
     * {@code POST  /product-notice-manages} : Create a new productNoticeManage.
     *
     * @param productNoticeManageDTO the productNoticeManageDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new productNoticeManageDTO, or with status {@code 400 (Bad Request)} if the productNoticeManage has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/product-notice-manages")
    public ResponseEntity<ProductNoticeManageDTO> createProductNoticeManage(
        @Valid @RequestBody ProductNoticeManageDTO productNoticeManageDTO
    ) throws URISyntaxException {
        log.debug("REST request to save ProductNoticeManage : {}", productNoticeManageDTO);
        if (productNoticeManageDTO.getId() != null) {
            throw new BadRequestAlertException("A new productNoticeManage cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductNoticeManageDTO result = productNoticeManageService.save(productNoticeManageDTO);
        return ResponseEntity
            .created(new URI("/api/product-notice-manages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /product-notice-manages/:id} : Updates an existing productNoticeManage.
     *
     * @param id the id of the productNoticeManageDTO to save.
     * @param productNoticeManageDTO the productNoticeManageDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productNoticeManageDTO,
     * or with status {@code 400 (Bad Request)} if the productNoticeManageDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the productNoticeManageDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/product-notice-manages/{id}")
    public ResponseEntity<ProductNoticeManageDTO> updateProductNoticeManage(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ProductNoticeManageDTO productNoticeManageDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ProductNoticeManage : {}, {}", id, productNoticeManageDTO);
        if (productNoticeManageDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, productNoticeManageDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!productNoticeManageRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ProductNoticeManageDTO result = productNoticeManageService.save(productNoticeManageDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productNoticeManageDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /product-notice-manages/:id} : Partial updates given fields of an existing productNoticeManage, field will ignore if it is null
     *
     * @param id the id of the productNoticeManageDTO to save.
     * @param productNoticeManageDTO the productNoticeManageDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productNoticeManageDTO,
     * or with status {@code 400 (Bad Request)} if the productNoticeManageDTO is not valid,
     * or with status {@code 404 (Not Found)} if the productNoticeManageDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the productNoticeManageDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/product-notice-manages/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<ProductNoticeManageDTO> partialUpdateProductNoticeManage(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ProductNoticeManageDTO productNoticeManageDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ProductNoticeManage partially : {}, {}", id, productNoticeManageDTO);
        if (productNoticeManageDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, productNoticeManageDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!productNoticeManageRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ProductNoticeManageDTO> result = productNoticeManageService.partialUpdate(productNoticeManageDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productNoticeManageDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /product-notice-manages} : get all the productNoticeManages.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of productNoticeManages in body.
     */
    @GetMapping("/product-notice-manages")
    public ResponseEntity<List<ProductNoticeManageDTO>> getAllProductNoticeManages(Pageable pageable) {
        log.debug("REST request to get a page of ProductNoticeManages");
        Page<ProductNoticeManageDTO> page = productNoticeManageService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /product-notice-manages/:id} : get the "id" productNoticeManage.
     *
     * @param id the id of the productNoticeManageDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the productNoticeManageDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/product-notice-manages/{id}")
    public ResponseEntity<ProductNoticeManageDTO> getProductNoticeManage(@PathVariable Long id) {
        log.debug("REST request to get ProductNoticeManage : {}", id);
        Optional<ProductNoticeManageDTO> productNoticeManageDTO = productNoticeManageService.findOne(id);
        return ResponseUtil.wrapOrNotFound(productNoticeManageDTO);
    }

    /**
     * {@code DELETE  /product-notice-manages/:id} : delete the "id" productNoticeManage.
     *
     * @param id the id of the productNoticeManageDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/product-notice-manages/{id}")
    public ResponseEntity<Void> deleteProductNoticeManage(@PathVariable Long id) {
        log.debug("REST request to delete ProductNoticeManage : {}", id);
        productNoticeManageService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
