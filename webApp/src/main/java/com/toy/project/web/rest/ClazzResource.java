package com.toy.project.web.rest;

import com.toy.project.repository.ClazzRepository;
import com.toy.project.service.ClazzService;
import com.toy.project.service.dto.ClazzDTO;
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
 * REST controller for managing {@link com.toy.project.domain.Clazz}.
 */
@RestController
@RequestMapping("/api")
public class ClazzResource {

    private final Logger log = LoggerFactory.getLogger(ClazzResource.class);

    private static final String ENTITY_NAME = "clazz";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ClazzService clazzService;

    private final ClazzRepository clazzRepository;

    public ClazzResource(ClazzService clazzService, ClazzRepository clazzRepository) {
        this.clazzService = clazzService;
        this.clazzRepository = clazzRepository;
    }

    /**
     * {@code POST  /clazzes} : Create a new clazz.
     *
     * @param clazzDTO the clazzDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new clazzDTO, or with status {@code 400 (Bad Request)} if the clazz has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/clazzes")
    public ResponseEntity<ClazzDTO> createClazz(@Valid @RequestBody ClazzDTO clazzDTO) throws URISyntaxException {
        log.debug("REST request to save Clazz : {}", clazzDTO);
        if (clazzDTO.getId() != null) {
            throw new BadRequestAlertException("A new clazz cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ClazzDTO result = clazzService.save(clazzDTO);
        return ResponseEntity
            .created(new URI("/api/clazzes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /clazzes/:id} : Updates an existing clazz.
     *
     * @param id the id of the clazzDTO to save.
     * @param clazzDTO the clazzDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated clazzDTO,
     * or with status {@code 400 (Bad Request)} if the clazzDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the clazzDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/clazzes/{id}")
    public ResponseEntity<ClazzDTO> updateClazz(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ClazzDTO clazzDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Clazz : {}, {}", id, clazzDTO);
        if (clazzDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, clazzDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!clazzRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ClazzDTO result = clazzService.save(clazzDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, clazzDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /clazzes/:id} : Partial updates given fields of an existing clazz, field will ignore if it is null
     *
     * @param id the id of the clazzDTO to save.
     * @param clazzDTO the clazzDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated clazzDTO,
     * or with status {@code 400 (Bad Request)} if the clazzDTO is not valid,
     * or with status {@code 404 (Not Found)} if the clazzDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the clazzDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/clazzes/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<ClazzDTO> partialUpdateClazz(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ClazzDTO clazzDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Clazz partially : {}, {}", id, clazzDTO);
        if (clazzDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, clazzDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!clazzRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ClazzDTO> result = clazzService.partialUpdate(clazzDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, clazzDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /clazzes} : get all the clazzes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of clazzes in body.
     */
    @GetMapping("/clazzes")
    public ResponseEntity<List<ClazzDTO>> getAllClazzes(Pageable pageable) {
        log.debug("REST request to get a page of Clazzes");
        Page<ClazzDTO> page = clazzService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /clazzes/:id} : get the "id" clazz.
     *
     * @param id the id of the clazzDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the clazzDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/clazzes/{id}")
    public ResponseEntity<ClazzDTO> getClazz(@PathVariable Long id) {
        log.debug("REST request to get Clazz : {}", id);
        Optional<ClazzDTO> clazzDTO = clazzService.findOne(id);
        return ResponseUtil.wrapOrNotFound(clazzDTO);
    }

    /**
     * {@code DELETE  /clazzes/:id} : delete the "id" clazz.
     *
     * @param id the id of the clazzDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/clazzes/{id}")
    public ResponseEntity<Void> deleteClazz(@PathVariable Long id) {
        log.debug("REST request to delete Clazz : {}", id);
        clazzService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
