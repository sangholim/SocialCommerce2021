package com.toy.project.web.rest;

import com.toy.project.repository.PCategoryRepository;
import com.toy.project.service.PCategoryQueryService;
import com.toy.project.service.PCategoryService;
import com.toy.project.service.criteria.PCategoryCriteria;
import com.toy.project.service.dto.PCategoryDTO;
import com.toy.project.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
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
 * REST controller for managing {@link com.toy.project.domain.PCategory}.
 */
@RestController
@RequestMapping("/api")
public class PCategoryResource {

    private final Logger log = LoggerFactory.getLogger(PCategoryResource.class);

    private static final String ENTITY_NAME = "pCategory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PCategoryService pCategoryService;

    private final PCategoryRepository pCategoryRepository;

    private final PCategoryQueryService pCategoryQueryService;

    public PCategoryResource(
        PCategoryService pCategoryService,
        PCategoryRepository pCategoryRepository,
        PCategoryQueryService pCategoryQueryService
    ) {
        this.pCategoryService = pCategoryService;
        this.pCategoryRepository = pCategoryRepository;
        this.pCategoryQueryService = pCategoryQueryService;
    }

    /**
     * {@code POST  /p-categories} : Create a new pCategory.
     *
     * @param pCategoryDTO the pCategoryDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pCategoryDTO, or with status {@code 400 (Bad Request)} if the pCategory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/p-categories")
    public ResponseEntity<PCategoryDTO> createPCategory(@RequestBody PCategoryDTO pCategoryDTO) throws URISyntaxException {
        log.debug("REST request to save PCategory : {}", pCategoryDTO);
        if (pCategoryDTO.getId() != null) {
            throw new BadRequestAlertException("A new pCategory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PCategoryDTO result = pCategoryService.save(pCategoryDTO);
        return ResponseEntity
            .created(new URI("/api/p-categories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /p-categories/:id} : Updates an existing pCategory.
     *
     * @param id the id of the pCategoryDTO to save.
     * @param pCategoryDTO the pCategoryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pCategoryDTO,
     * or with status {@code 400 (Bad Request)} if the pCategoryDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pCategoryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/p-categories/{id}")
    public ResponseEntity<PCategoryDTO> updatePCategory(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PCategoryDTO pCategoryDTO
    ) throws URISyntaxException {
        log.debug("REST request to update PCategory : {}, {}", id, pCategoryDTO);
        if (pCategoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pCategoryDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pCategoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PCategoryDTO result = pCategoryService.save(pCategoryDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pCategoryDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /p-categories/:id} : Partial updates given fields of an existing pCategory, field will ignore if it is null
     *
     * @param id the id of the pCategoryDTO to save.
     * @param pCategoryDTO the pCategoryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pCategoryDTO,
     * or with status {@code 400 (Bad Request)} if the pCategoryDTO is not valid,
     * or with status {@code 404 (Not Found)} if the pCategoryDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the pCategoryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/p-categories/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<PCategoryDTO> partialUpdatePCategory(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PCategoryDTO pCategoryDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update PCategory partially : {}, {}", id, pCategoryDTO);
        if (pCategoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pCategoryDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pCategoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PCategoryDTO> result = pCategoryService.partialUpdate(pCategoryDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pCategoryDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /p-categories} : get all the pCategories.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pCategories in body.
     */
    @GetMapping("/p-categories")
    public ResponseEntity<List<PCategoryDTO>> getAllPCategories(PCategoryCriteria criteria, Pageable pageable) {
        log.debug("REST request to get PCategories by criteria: {}", criteria);
        Page<PCategoryDTO> page = pCategoryQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /p-categories/count} : count all the pCategories.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/p-categories/count")
    public ResponseEntity<Long> countPCategories(PCategoryCriteria criteria) {
        log.debug("REST request to count PCategories by criteria: {}", criteria);
        return ResponseEntity.ok().body(pCategoryQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /p-categories/:id} : get the "id" pCategory.
     *
     * @param id the id of the pCategoryDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pCategoryDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/p-categories/{id}")
    public ResponseEntity<PCategoryDTO> getPCategory(@PathVariable Long id) {
        log.debug("REST request to get PCategory : {}", id);
        Optional<PCategoryDTO> pCategoryDTO = pCategoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(pCategoryDTO);
    }

    /**
     * {@code DELETE  /p-categories/:id} : delete the "id" pCategory.
     *
     * @param id the id of the pCategoryDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/p-categories/{id}")
    public ResponseEntity<Void> deletePCategory(@PathVariable Long id) {
        log.debug("REST request to delete PCategory : {}", id);
        pCategoryService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
