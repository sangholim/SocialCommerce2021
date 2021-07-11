package com.toy.project.web.rest;

import com.toy.project.repository.ClazzChapterRepository;
import com.toy.project.service.ClazzChapterQueryService;
import com.toy.project.service.ClazzChapterService;
import com.toy.project.service.criteria.ClazzChapterCriteria;
import com.toy.project.service.dto.ClazzChapterDTO;
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
 * REST controller for managing {@link com.toy.project.domain.ClazzChapter}.
 */
@RestController
@RequestMapping("/api")
public class ClazzChapterResource {

    private final Logger log = LoggerFactory.getLogger(ClazzChapterResource.class);

    private static final String ENTITY_NAME = "clazzChapter";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ClazzChapterService clazzChapterService;

    private final ClazzChapterRepository clazzChapterRepository;

    private final ClazzChapterQueryService clazzChapterQueryService;

    public ClazzChapterResource(
        ClazzChapterService clazzChapterService,
        ClazzChapterRepository clazzChapterRepository,
        ClazzChapterQueryService clazzChapterQueryService
    ) {
        this.clazzChapterService = clazzChapterService;
        this.clazzChapterRepository = clazzChapterRepository;
        this.clazzChapterQueryService = clazzChapterQueryService;
    }

    /**
     * {@code POST  /clazz-chapters} : Create a new clazzChapter.
     *
     * @param clazzChapterDTO the clazzChapterDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new clazzChapterDTO, or with status {@code 400 (Bad Request)} if the clazzChapter has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/clazz-chapters")
    public ResponseEntity<ClazzChapterDTO> createClazzChapter(@Valid @RequestBody ClazzChapterDTO clazzChapterDTO)
        throws URISyntaxException {
        log.debug("REST request to save ClazzChapter : {}", clazzChapterDTO);
        if (clazzChapterDTO.getId() != null) {
            throw new BadRequestAlertException("A new clazzChapter cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ClazzChapterDTO result = clazzChapterService.save(clazzChapterDTO);
        return ResponseEntity
            .created(new URI("/api/clazz-chapters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /clazz-chapters/:id} : Updates an existing clazzChapter.
     *
     * @param id the id of the clazzChapterDTO to save.
     * @param clazzChapterDTO the clazzChapterDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated clazzChapterDTO,
     * or with status {@code 400 (Bad Request)} if the clazzChapterDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the clazzChapterDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/clazz-chapters/{id}")
    public ResponseEntity<ClazzChapterDTO> updateClazzChapter(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ClazzChapterDTO clazzChapterDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ClazzChapter : {}, {}", id, clazzChapterDTO);
        if (clazzChapterDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, clazzChapterDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!clazzChapterRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ClazzChapterDTO result = clazzChapterService.save(clazzChapterDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, clazzChapterDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /clazz-chapters/:id} : Partial updates given fields of an existing clazzChapter, field will ignore if it is null
     *
     * @param id the id of the clazzChapterDTO to save.
     * @param clazzChapterDTO the clazzChapterDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated clazzChapterDTO,
     * or with status {@code 400 (Bad Request)} if the clazzChapterDTO is not valid,
     * or with status {@code 404 (Not Found)} if the clazzChapterDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the clazzChapterDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/clazz-chapters/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<ClazzChapterDTO> partialUpdateClazzChapter(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ClazzChapterDTO clazzChapterDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ClazzChapter partially : {}, {}", id, clazzChapterDTO);
        if (clazzChapterDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, clazzChapterDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!clazzChapterRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ClazzChapterDTO> result = clazzChapterService.partialUpdate(clazzChapterDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, clazzChapterDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /clazz-chapters} : get all the clazzChapters.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of clazzChapters in body.
     */
    @GetMapping("/clazz-chapters")
    public ResponseEntity<List<ClazzChapterDTO>> getAllClazzChapters(ClazzChapterCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ClazzChapters by criteria: {}", criteria);
        Page<ClazzChapterDTO> page = clazzChapterQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /clazz-chapters/count} : count all the clazzChapters.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/clazz-chapters/count")
    public ResponseEntity<Long> countClazzChapters(ClazzChapterCriteria criteria) {
        log.debug("REST request to count ClazzChapters by criteria: {}", criteria);
        return ResponseEntity.ok().body(clazzChapterQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /clazz-chapters/:id} : get the "id" clazzChapter.
     *
     * @param id the id of the clazzChapterDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the clazzChapterDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/clazz-chapters/{id}")
    public ResponseEntity<ClazzChapterDTO> getClazzChapter(@PathVariable Long id) {
        log.debug("REST request to get ClazzChapter : {}", id);
        Optional<ClazzChapterDTO> clazzChapterDTO = clazzChapterService.findOne(id);
        return ResponseUtil.wrapOrNotFound(clazzChapterDTO);
    }

    /**
     * {@code DELETE  /clazz-chapters/:id} : delete the "id" clazzChapter.
     *
     * @param id the id of the clazzChapterDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/clazz-chapters/{id}")
    public ResponseEntity<Void> deleteClazzChapter(@PathVariable Long id) {
        log.debug("REST request to delete ClazzChapter : {}", id);
        clazzChapterService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
