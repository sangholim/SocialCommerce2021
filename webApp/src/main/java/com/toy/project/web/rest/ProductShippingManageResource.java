package com.toy.project.web.rest;

import com.toy.project.repository.ProductShippingManageRepository;
import com.toy.project.service.ProductShippingManageService;
import com.toy.project.service.dto.ProductShippingManageDTO;
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
 * REST controller for managing {@link com.toy.project.domain.ProductShippingManage}.
 */
@RestController
@RequestMapping("/api")
public class ProductShippingManageResource {

    private final Logger log = LoggerFactory.getLogger(ProductShippingManageResource.class);

    private static final String ENTITY_NAME = "productShippingManage";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProductShippingManageService productShippingManageService;

    private final ProductShippingManageRepository productShippingManageRepository;

    public ProductShippingManageResource(
        ProductShippingManageService productShippingManageService,
        ProductShippingManageRepository productShippingManageRepository
    ) {
        this.productShippingManageService = productShippingManageService;
        this.productShippingManageRepository = productShippingManageRepository;
    }

    /**
     * {@code POST  /product-shipping-manages} : Create a new productShippingManage.
     *
     * @param productShippingManageDTO the productShippingManageDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new productShippingManageDTO, or with status {@code 400 (Bad Request)} if the productShippingManage has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/product-shipping-manages")
    public ResponseEntity<ProductShippingManageDTO> createProductShippingManage(
        @Valid @RequestBody ProductShippingManageDTO productShippingManageDTO
    ) throws URISyntaxException {
        log.debug("REST request to save ProductShippingManage : {}", productShippingManageDTO);
        if (productShippingManageDTO.getId() != null) {
            throw new BadRequestAlertException("A new productShippingManage cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductShippingManageDTO result = productShippingManageService.save(productShippingManageDTO);
        return ResponseEntity
            .created(new URI("/api/product-shipping-manages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /product-shipping-manages/:id} : Updates an existing productShippingManage.
     *
     * @param id the id of the productShippingManageDTO to save.
     * @param productShippingManageDTO the productShippingManageDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productShippingManageDTO,
     * or with status {@code 400 (Bad Request)} if the productShippingManageDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the productShippingManageDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/product-shipping-manages/{id}")
    public ResponseEntity<ProductShippingManageDTO> updateProductShippingManage(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ProductShippingManageDTO productShippingManageDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ProductShippingManage : {}, {}", id, productShippingManageDTO);
        if (productShippingManageDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, productShippingManageDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!productShippingManageRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ProductShippingManageDTO result = productShippingManageService.save(productShippingManageDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productShippingManageDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /product-shipping-manages/:id} : Partial updates given fields of an existing productShippingManage, field will ignore if it is null
     *
     * @param id the id of the productShippingManageDTO to save.
     * @param productShippingManageDTO the productShippingManageDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productShippingManageDTO,
     * or with status {@code 400 (Bad Request)} if the productShippingManageDTO is not valid,
     * or with status {@code 404 (Not Found)} if the productShippingManageDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the productShippingManageDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/product-shipping-manages/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<ProductShippingManageDTO> partialUpdateProductShippingManage(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ProductShippingManageDTO productShippingManageDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ProductShippingManage partially : {}, {}", id, productShippingManageDTO);
        if (productShippingManageDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, productShippingManageDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!productShippingManageRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ProductShippingManageDTO> result = productShippingManageService.partialUpdate(productShippingManageDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productShippingManageDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /product-shipping-manages} : get all the productShippingManages.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of productShippingManages in body.
     */
    @GetMapping("/product-shipping-manages")
    public ResponseEntity<List<ProductShippingManageDTO>> getAllProductShippingManages(Pageable pageable) {
        log.debug("REST request to get a page of ProductShippingManages");
        Page<ProductShippingManageDTO> page = productShippingManageService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /product-shipping-manages/:id} : get the "id" productShippingManage.
     *
     * @param id the id of the productShippingManageDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the productShippingManageDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/product-shipping-manages/{id}")
    public ResponseEntity<ProductShippingManageDTO> getProductShippingManage(@PathVariable Long id) {
        log.debug("REST request to get ProductShippingManage : {}", id);
        Optional<ProductShippingManageDTO> productShippingManageDTO = productShippingManageService.findOne(id);
        return ResponseUtil.wrapOrNotFound(productShippingManageDTO);
    }

    /**
     * {@code DELETE  /product-shipping-manages/:id} : delete the "id" productShippingManage.
     *
     * @param id the id of the productShippingManageDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/product-shipping-manages/{id}")
    public ResponseEntity<Void> deleteProductShippingManage(@PathVariable Long id) {
        log.debug("REST request to delete ProductShippingManage : {}", id);
        productShippingManageService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
