package com.toy.project.web.rest;

import com.toy.project.repository.ProductFaqRepository;
import com.toy.project.service.ProductFaqService;
import com.toy.project.service.dto.ProductFaqDTO;
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
 * REST controller for managing {@link com.toy.project.domain.ProductFaq}.
 */
@RestController
@RequestMapping("/api")
public class ProductFaqResource {

    private final Logger log = LoggerFactory.getLogger(ProductFaqResource.class);

    private static final String ENTITY_NAME = "productFaq";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProductFaqService productFaqService;

    private final ProductFaqRepository productFaqRepository;

    public ProductFaqResource(ProductFaqService productFaqService, ProductFaqRepository productFaqRepository) {
        this.productFaqService = productFaqService;
        this.productFaqRepository = productFaqRepository;
    }

    /**
     * {@code POST  /product-faqs} : Create a new productFaq.
     *
     * @param productFaqDTO the productFaqDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new productFaqDTO, or with status {@code 400 (Bad Request)} if the productFaq has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/product-faqs")
    public ResponseEntity<ProductFaqDTO> createProductFaq(@Valid @RequestBody ProductFaqDTO productFaqDTO) throws URISyntaxException {
        log.debug("REST request to save ProductFaq : {}", productFaqDTO);
        if (productFaqDTO.getId() != null) {
            throw new BadRequestAlertException("A new productFaq cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductFaqDTO result = productFaqService.save(productFaqDTO);
        return ResponseEntity
            .created(new URI("/api/product-faqs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /product-faqs/:id} : Updates an existing productFaq.
     *
     * @param id the id of the productFaqDTO to save.
     * @param productFaqDTO the productFaqDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productFaqDTO,
     * or with status {@code 400 (Bad Request)} if the productFaqDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the productFaqDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/product-faqs/{id}")
    public ResponseEntity<ProductFaqDTO> updateProductFaq(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ProductFaqDTO productFaqDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ProductFaq : {}, {}", id, productFaqDTO);
        if (productFaqDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, productFaqDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!productFaqRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ProductFaqDTO result = productFaqService.save(productFaqDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productFaqDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /product-faqs/:id} : Partial updates given fields of an existing productFaq, field will ignore if it is null
     *
     * @param id the id of the productFaqDTO to save.
     * @param productFaqDTO the productFaqDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productFaqDTO,
     * or with status {@code 400 (Bad Request)} if the productFaqDTO is not valid,
     * or with status {@code 404 (Not Found)} if the productFaqDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the productFaqDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/product-faqs/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<ProductFaqDTO> partialUpdateProductFaq(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ProductFaqDTO productFaqDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ProductFaq partially : {}, {}", id, productFaqDTO);
        if (productFaqDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, productFaqDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!productFaqRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ProductFaqDTO> result = productFaqService.partialUpdate(productFaqDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productFaqDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /product-faqs} : get all the productFaqs.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of productFaqs in body.
     */
    @GetMapping("/product-faqs")
    public ResponseEntity<List<ProductFaqDTO>> getAllProductFaqs(Pageable pageable) {
        log.debug("REST request to get a page of ProductFaqs");
        Page<ProductFaqDTO> page = productFaqService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /product-faqs/:id} : get the "id" productFaq.
     *
     * @param id the id of the productFaqDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the productFaqDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/product-faqs/{id}")
    public ResponseEntity<ProductFaqDTO> getProductFaq(@PathVariable Long id) {
        log.debug("REST request to get ProductFaq : {}", id);
        Optional<ProductFaqDTO> productFaqDTO = productFaqService.findOne(id);
        return ResponseUtil.wrapOrNotFound(productFaqDTO);
    }

    /**
     * {@code DELETE  /product-faqs/:id} : delete the "id" productFaq.
     *
     * @param id the id of the productFaqDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/product-faqs/{id}")
    public ResponseEntity<Void> deleteProductFaq(@PathVariable Long id) {
        log.debug("REST request to delete ProductFaq : {}", id);
        productFaqService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
