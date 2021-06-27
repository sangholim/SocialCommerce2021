package com.toy.project.web.rest;

import com.toy.project.repository.OptionDesignRepository;
import com.toy.project.service.OptionDesignQueryService;
import com.toy.project.service.OptionDesignService;
import com.toy.project.service.criteria.OptionDesignCriteria;
import com.toy.project.service.dto.OptionDesignDTO;
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
 * REST controller for managing {@link com.toy.project.domain.OptionDesign}.
 */
@RestController
@RequestMapping("/api")
public class OptionDesignResource {

    private final Logger log = LoggerFactory.getLogger(OptionDesignResource.class);

    private static final String ENTITY_NAME = "optionDesign";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OptionDesignService optionDesignService;

    private final OptionDesignRepository optionDesignRepository;

    private final OptionDesignQueryService optionDesignQueryService;

    public OptionDesignResource(
        OptionDesignService optionDesignService,
        OptionDesignRepository optionDesignRepository,
        OptionDesignQueryService optionDesignQueryService
    ) {
        this.optionDesignService = optionDesignService;
        this.optionDesignRepository = optionDesignRepository;
        this.optionDesignQueryService = optionDesignQueryService;
    }

    /**
     * {@code POST  /option-designs} : Create a new optionDesign.
     *
     * @param optionDesignDTO the optionDesignDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new optionDesignDTO, or with status {@code 400 (Bad Request)} if the optionDesign has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/option-designs")
    public ResponseEntity<OptionDesignDTO> createOptionDesign(@Valid @RequestBody OptionDesignDTO optionDesignDTO)
        throws URISyntaxException {
        log.debug("REST request to save OptionDesign : {}", optionDesignDTO);
        if (optionDesignDTO.getId() != null) {
            throw new BadRequestAlertException("A new optionDesign cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OptionDesignDTO result = optionDesignService.save(optionDesignDTO);
        return ResponseEntity
            .created(new URI("/api/option-designs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /option-designs/:id} : Updates an existing optionDesign.
     *
     * @param id the id of the optionDesignDTO to save.
     * @param optionDesignDTO the optionDesignDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated optionDesignDTO,
     * or with status {@code 400 (Bad Request)} if the optionDesignDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the optionDesignDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/option-designs/{id}")
    public ResponseEntity<OptionDesignDTO> updateOptionDesign(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody OptionDesignDTO optionDesignDTO
    ) throws URISyntaxException {
        log.debug("REST request to update OptionDesign : {}, {}", id, optionDesignDTO);
        if (optionDesignDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, optionDesignDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!optionDesignRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        OptionDesignDTO result = optionDesignService.save(optionDesignDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, optionDesignDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /option-designs/:id} : Partial updates given fields of an existing optionDesign, field will ignore if it is null
     *
     * @param id the id of the optionDesignDTO to save.
     * @param optionDesignDTO the optionDesignDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated optionDesignDTO,
     * or with status {@code 400 (Bad Request)} if the optionDesignDTO is not valid,
     * or with status {@code 404 (Not Found)} if the optionDesignDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the optionDesignDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/option-designs/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<OptionDesignDTO> partialUpdateOptionDesign(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody OptionDesignDTO optionDesignDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update OptionDesign partially : {}, {}", id, optionDesignDTO);
        if (optionDesignDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, optionDesignDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!optionDesignRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<OptionDesignDTO> result = optionDesignService.partialUpdate(optionDesignDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, optionDesignDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /option-designs} : get all the optionDesigns.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of optionDesigns in body.
     */
    @GetMapping("/option-designs")
    public ResponseEntity<List<OptionDesignDTO>> getAllOptionDesigns(OptionDesignCriteria criteria, Pageable pageable) {
        log.debug("REST request to get OptionDesigns by criteria: {}", criteria);
        Page<OptionDesignDTO> page = optionDesignQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /option-designs/count} : count all the optionDesigns.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/option-designs/count")
    public ResponseEntity<Long> countOptionDesigns(OptionDesignCriteria criteria) {
        log.debug("REST request to count OptionDesigns by criteria: {}", criteria);
        return ResponseEntity.ok().body(optionDesignQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /option-designs/:id} : get the "id" optionDesign.
     *
     * @param id the id of the optionDesignDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the optionDesignDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/option-designs/{id}")
    public ResponseEntity<OptionDesignDTO> getOptionDesign(@PathVariable Long id) {
        log.debug("REST request to get OptionDesign : {}", id);
        Optional<OptionDesignDTO> optionDesignDTO = optionDesignService.findOne(id);
        return ResponseUtil.wrapOrNotFound(optionDesignDTO);
    }

    /**
     * {@code DELETE  /option-designs/:id} : delete the "id" optionDesign.
     *
     * @param id the id of the optionDesignDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/option-designs/{id}")
    public ResponseEntity<Void> deleteOptionDesign(@PathVariable Long id) {
        log.debug("REST request to delete OptionDesign : {}", id);
        optionDesignService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
