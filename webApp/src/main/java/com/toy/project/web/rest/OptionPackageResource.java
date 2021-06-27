package com.toy.project.web.rest;

import com.toy.project.repository.OptionPackageRepository;
import com.toy.project.service.OptionPackageQueryService;
import com.toy.project.service.OptionPackageService;
import com.toy.project.service.criteria.OptionPackageCriteria;
import com.toy.project.service.dto.OptionPackageDTO;
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
 * REST controller for managing {@link com.toy.project.domain.OptionPackage}.
 */
@RestController
@RequestMapping("/api")
public class OptionPackageResource {

    private final Logger log = LoggerFactory.getLogger(OptionPackageResource.class);

    private static final String ENTITY_NAME = "optionPackage";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OptionPackageService optionPackageService;

    private final OptionPackageRepository optionPackageRepository;

    private final OptionPackageQueryService optionPackageQueryService;

    public OptionPackageResource(
        OptionPackageService optionPackageService,
        OptionPackageRepository optionPackageRepository,
        OptionPackageQueryService optionPackageQueryService
    ) {
        this.optionPackageService = optionPackageService;
        this.optionPackageRepository = optionPackageRepository;
        this.optionPackageQueryService = optionPackageQueryService;
    }

    /**
     * {@code POST  /option-packages} : Create a new optionPackage.
     *
     * @param optionPackageDTO the optionPackageDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new optionPackageDTO, or with status {@code 400 (Bad Request)} if the optionPackage has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/option-packages")
    public ResponseEntity<OptionPackageDTO> createOptionPackage(@Valid @RequestBody OptionPackageDTO optionPackageDTO)
        throws URISyntaxException {
        log.debug("REST request to save OptionPackage : {}", optionPackageDTO);
        if (optionPackageDTO.getId() != null) {
            throw new BadRequestAlertException("A new optionPackage cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OptionPackageDTO result = optionPackageService.save(optionPackageDTO);
        return ResponseEntity
            .created(new URI("/api/option-packages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /option-packages/:id} : Updates an existing optionPackage.
     *
     * @param id the id of the optionPackageDTO to save.
     * @param optionPackageDTO the optionPackageDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated optionPackageDTO,
     * or with status {@code 400 (Bad Request)} if the optionPackageDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the optionPackageDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/option-packages/{id}")
    public ResponseEntity<OptionPackageDTO> updateOptionPackage(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody OptionPackageDTO optionPackageDTO
    ) throws URISyntaxException {
        log.debug("REST request to update OptionPackage : {}, {}", id, optionPackageDTO);
        if (optionPackageDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, optionPackageDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!optionPackageRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        OptionPackageDTO result = optionPackageService.save(optionPackageDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, optionPackageDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /option-packages/:id} : Partial updates given fields of an existing optionPackage, field will ignore if it is null
     *
     * @param id the id of the optionPackageDTO to save.
     * @param optionPackageDTO the optionPackageDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated optionPackageDTO,
     * or with status {@code 400 (Bad Request)} if the optionPackageDTO is not valid,
     * or with status {@code 404 (Not Found)} if the optionPackageDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the optionPackageDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/option-packages/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<OptionPackageDTO> partialUpdateOptionPackage(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody OptionPackageDTO optionPackageDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update OptionPackage partially : {}, {}", id, optionPackageDTO);
        if (optionPackageDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, optionPackageDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!optionPackageRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<OptionPackageDTO> result = optionPackageService.partialUpdate(optionPackageDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, optionPackageDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /option-packages} : get all the optionPackages.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of optionPackages in body.
     */
    @GetMapping("/option-packages")
    public ResponseEntity<List<OptionPackageDTO>> getAllOptionPackages(OptionPackageCriteria criteria, Pageable pageable) {
        log.debug("REST request to get OptionPackages by criteria: {}", criteria);
        Page<OptionPackageDTO> page = optionPackageQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /option-packages/count} : count all the optionPackages.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/option-packages/count")
    public ResponseEntity<Long> countOptionPackages(OptionPackageCriteria criteria) {
        log.debug("REST request to count OptionPackages by criteria: {}", criteria);
        return ResponseEntity.ok().body(optionPackageQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /option-packages/:id} : get the "id" optionPackage.
     *
     * @param id the id of the optionPackageDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the optionPackageDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/option-packages/{id}")
    public ResponseEntity<OptionPackageDTO> getOptionPackage(@PathVariable Long id) {
        log.debug("REST request to get OptionPackage : {}", id);
        Optional<OptionPackageDTO> optionPackageDTO = optionPackageService.findOne(id);
        return ResponseUtil.wrapOrNotFound(optionPackageDTO);
    }

    /**
     * {@code DELETE  /option-packages/:id} : delete the "id" optionPackage.
     *
     * @param id the id of the optionPackageDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/option-packages/{id}")
    public ResponseEntity<Void> deleteOptionPackage(@PathVariable Long id) {
        log.debug("REST request to delete OptionPackage : {}", id);
        optionPackageService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
