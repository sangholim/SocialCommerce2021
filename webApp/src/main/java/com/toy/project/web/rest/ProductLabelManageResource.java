package com.toy.project.web.rest;

import com.toy.project.repository.ProductLabelManageRepository;
import com.toy.project.service.ProductLabelManageService;
import com.toy.project.service.dto.ProductLabelManageDTO;
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
 * REST controller for managing {@link com.toy.project.domain.ProductLabelManage}.
 */
@RestController
@RequestMapping("/api")
public class ProductLabelManageResource {

    private final Logger log = LoggerFactory.getLogger(ProductLabelManageResource.class);

    private static final String ENTITY_NAME = "productLabelManage";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProductLabelManageService productLabelManageService;

    private final ProductLabelManageRepository productLabelManageRepository;

    public ProductLabelManageResource(
        ProductLabelManageService productLabelManageService,
        ProductLabelManageRepository productLabelManageRepository
    ) {
        this.productLabelManageService = productLabelManageService;
        this.productLabelManageRepository = productLabelManageRepository;
    }

    /**
     * {@code POST  /product-label-manages} : Create a new productLabelManage.
     *
     * @param productLabelManageDTO the productLabelManageDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new productLabelManageDTO, or with status {@code 400 (Bad Request)} if the productLabelManage has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/product-label-manages")
    public ResponseEntity<ProductLabelManageDTO> createProductLabelManage(@Valid @RequestBody ProductLabelManageDTO productLabelManageDTO)
        throws URISyntaxException {
        log.debug("REST request to save ProductLabelManage : {}", productLabelManageDTO);
        if (productLabelManageDTO.getId() != null) {
            throw new BadRequestAlertException("A new productLabelManage cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductLabelManageDTO result = productLabelManageService.save(productLabelManageDTO);
        return ResponseEntity
            .created(new URI("/api/product-label-manages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /product-label-manages/:id} : Updates an existing productLabelManage.
     *
     * @param id the id of the productLabelManageDTO to save.
     * @param productLabelManageDTO the productLabelManageDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productLabelManageDTO,
     * or with status {@code 400 (Bad Request)} if the productLabelManageDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the productLabelManageDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/product-label-manages/{id}")
    public ResponseEntity<ProductLabelManageDTO> updateProductLabelManage(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ProductLabelManageDTO productLabelManageDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ProductLabelManage : {}, {}", id, productLabelManageDTO);
        if (productLabelManageDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, productLabelManageDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!productLabelManageRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ProductLabelManageDTO result = productLabelManageService.save(productLabelManageDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productLabelManageDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /product-label-manages/:id} : Partial updates given fields of an existing productLabelManage, field will ignore if it is null
     *
     * @param id the id of the productLabelManageDTO to save.
     * @param productLabelManageDTO the productLabelManageDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productLabelManageDTO,
     * or with status {@code 400 (Bad Request)} if the productLabelManageDTO is not valid,
     * or with status {@code 404 (Not Found)} if the productLabelManageDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the productLabelManageDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/product-label-manages/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<ProductLabelManageDTO> partialUpdateProductLabelManage(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ProductLabelManageDTO productLabelManageDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ProductLabelManage partially : {}, {}", id, productLabelManageDTO);
        if (productLabelManageDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, productLabelManageDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!productLabelManageRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ProductLabelManageDTO> result = productLabelManageService.partialUpdate(productLabelManageDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productLabelManageDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /product-label-manages} : get all the productLabelManages.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of productLabelManages in body.
     */
    @GetMapping("/product-label-manages")
    public ResponseEntity<List<ProductLabelManageDTO>> getAllProductLabelManages(Pageable pageable) {
        log.debug("REST request to get a page of ProductLabelManages");
        Page<ProductLabelManageDTO> page = productLabelManageService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /product-label-manages/:id} : get the "id" productLabelManage.
     *
     * @param id the id of the productLabelManageDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the productLabelManageDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/product-label-manages/{id}")
    public ResponseEntity<ProductLabelManageDTO> getProductLabelManage(@PathVariable Long id) {
        log.debug("REST request to get ProductLabelManage : {}", id);
        Optional<ProductLabelManageDTO> productLabelManageDTO = productLabelManageService.findOne(id);
        return ResponseUtil.wrapOrNotFound(productLabelManageDTO);
    }

    /**
     * {@code DELETE  /product-label-manages/:id} : delete the "id" productLabelManage.
     *
     * @param id the id of the productLabelManageDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/product-label-manages/{id}")
    public ResponseEntity<Void> deleteProductLabelManage(@PathVariable Long id) {
        log.debug("REST request to delete ProductLabelManage : {}", id);
        productLabelManageService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
