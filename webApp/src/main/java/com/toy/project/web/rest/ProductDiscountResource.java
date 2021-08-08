package com.toy.project.web.rest;

import com.toy.project.repository.ProductDiscountRepository;
import com.toy.project.service.ProductDiscountService;
import com.toy.project.service.dto.ProductDiscountDTO;
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
 * REST controller for managing {@link com.toy.project.domain.ProductDiscount}.
 */
@RestController
@RequestMapping("/api")
public class ProductDiscountResource {

    private final Logger log = LoggerFactory.getLogger(ProductDiscountResource.class);

    private static final String ENTITY_NAME = "productDiscount";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProductDiscountService productDiscountService;

    private final ProductDiscountRepository productDiscountRepository;

    public ProductDiscountResource(ProductDiscountService productDiscountService, ProductDiscountRepository productDiscountRepository) {
        this.productDiscountService = productDiscountService;
        this.productDiscountRepository = productDiscountRepository;
    }

    /**
     * {@code POST  /product-discounts} : Create a new productDiscount.
     *
     * @param productDiscountDTO the productDiscountDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new productDiscountDTO, or with status {@code 400 (Bad Request)} if the productDiscount has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/product-discounts")
    public ResponseEntity<ProductDiscountDTO> createProductDiscount(@Valid @RequestBody ProductDiscountDTO productDiscountDTO)
        throws URISyntaxException {
        log.debug("REST request to save ProductDiscount : {}", productDiscountDTO);
        if (productDiscountDTO.getId() != null) {
            throw new BadRequestAlertException("A new productDiscount cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductDiscountDTO result = productDiscountService.save(productDiscountDTO);
        return ResponseEntity
            .created(new URI("/api/product-discounts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /product-discounts/:id} : Updates an existing productDiscount.
     *
     * @param id the id of the productDiscountDTO to save.
     * @param productDiscountDTO the productDiscountDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productDiscountDTO,
     * or with status {@code 400 (Bad Request)} if the productDiscountDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the productDiscountDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/product-discounts/{id}")
    public ResponseEntity<ProductDiscountDTO> updateProductDiscount(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ProductDiscountDTO productDiscountDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ProductDiscount : {}, {}", id, productDiscountDTO);
        if (productDiscountDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, productDiscountDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!productDiscountRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ProductDiscountDTO result = productDiscountService.save(productDiscountDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productDiscountDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /product-discounts/:id} : Partial updates given fields of an existing productDiscount, field will ignore if it is null
     *
     * @param id the id of the productDiscountDTO to save.
     * @param productDiscountDTO the productDiscountDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productDiscountDTO,
     * or with status {@code 400 (Bad Request)} if the productDiscountDTO is not valid,
     * or with status {@code 404 (Not Found)} if the productDiscountDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the productDiscountDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/product-discounts/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<ProductDiscountDTO> partialUpdateProductDiscount(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ProductDiscountDTO productDiscountDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ProductDiscount partially : {}, {}", id, productDiscountDTO);
        if (productDiscountDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, productDiscountDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!productDiscountRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ProductDiscountDTO> result = productDiscountService.partialUpdate(productDiscountDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productDiscountDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /product-discounts} : get all the productDiscounts.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of productDiscounts in body.
     */
    @GetMapping("/product-discounts")
    public ResponseEntity<List<ProductDiscountDTO>> getAllProductDiscounts(Pageable pageable) {
        log.debug("REST request to get a page of ProductDiscounts");
        Page<ProductDiscountDTO> page = productDiscountService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /product-discounts/:id} : get the "id" productDiscount.
     *
     * @param id the id of the productDiscountDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the productDiscountDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/product-discounts/{id}")
    public ResponseEntity<ProductDiscountDTO> getProductDiscount(@PathVariable Long id) {
        log.debug("REST request to get ProductDiscount : {}", id);
        Optional<ProductDiscountDTO> productDiscountDTO = productDiscountService.findOne(id);
        return ResponseUtil.wrapOrNotFound(productDiscountDTO);
    }

    /**
     * {@code DELETE  /product-discounts/:id} : delete the "id" productDiscount.
     *
     * @param id the id of the productDiscountDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/product-discounts/{id}")
    public ResponseEntity<Void> deleteProductDiscount(@PathVariable Long id) {
        log.debug("REST request to delete ProductDiscount : {}", id);
        productDiscountService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
