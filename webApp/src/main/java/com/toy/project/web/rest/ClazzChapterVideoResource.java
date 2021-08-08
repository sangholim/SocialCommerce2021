package com.toy.project.web.rest;

import com.toy.project.repository.ClazzChapterVideoRepository;
import com.toy.project.service.ClazzChapterVideoService;
import com.toy.project.service.dto.ClazzChapterVideoDTO;
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
 * REST controller for managing {@link com.toy.project.domain.ClazzChapterVideo}.
 */
@RestController
@RequestMapping("/api")
public class ClazzChapterVideoResource {

    private final Logger log = LoggerFactory.getLogger(ClazzChapterVideoResource.class);

    private static final String ENTITY_NAME = "clazzChapterVideo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ClazzChapterVideoService clazzChapterVideoService;

    private final ClazzChapterVideoRepository clazzChapterVideoRepository;

    public ClazzChapterVideoResource(
        ClazzChapterVideoService clazzChapterVideoService,
        ClazzChapterVideoRepository clazzChapterVideoRepository
    ) {
        this.clazzChapterVideoService = clazzChapterVideoService;
        this.clazzChapterVideoRepository = clazzChapterVideoRepository;
    }

    /**
     * {@code POST  /clazz-chapter-videos} : Create a new clazzChapterVideo.
     *
     * @param clazzChapterVideoDTO the clazzChapterVideoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new clazzChapterVideoDTO, or with status {@code 400 (Bad Request)} if the clazzChapterVideo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/clazz-chapter-videos")
    public ResponseEntity<ClazzChapterVideoDTO> createClazzChapterVideo(@Valid @RequestBody ClazzChapterVideoDTO clazzChapterVideoDTO)
        throws URISyntaxException {
        log.debug("REST request to save ClazzChapterVideo : {}", clazzChapterVideoDTO);
        if (clazzChapterVideoDTO.getId() != null) {
            throw new BadRequestAlertException("A new clazzChapterVideo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ClazzChapterVideoDTO result = clazzChapterVideoService.save(clazzChapterVideoDTO);
        return ResponseEntity
            .created(new URI("/api/clazz-chapter-videos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /clazz-chapter-videos/:id} : Updates an existing clazzChapterVideo.
     *
     * @param id the id of the clazzChapterVideoDTO to save.
     * @param clazzChapterVideoDTO the clazzChapterVideoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated clazzChapterVideoDTO,
     * or with status {@code 400 (Bad Request)} if the clazzChapterVideoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the clazzChapterVideoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/clazz-chapter-videos/{id}")
    public ResponseEntity<ClazzChapterVideoDTO> updateClazzChapterVideo(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ClazzChapterVideoDTO clazzChapterVideoDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ClazzChapterVideo : {}, {}", id, clazzChapterVideoDTO);
        if (clazzChapterVideoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, clazzChapterVideoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!clazzChapterVideoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ClazzChapterVideoDTO result = clazzChapterVideoService.save(clazzChapterVideoDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, clazzChapterVideoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /clazz-chapter-videos/:id} : Partial updates given fields of an existing clazzChapterVideo, field will ignore if it is null
     *
     * @param id the id of the clazzChapterVideoDTO to save.
     * @param clazzChapterVideoDTO the clazzChapterVideoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated clazzChapterVideoDTO,
     * or with status {@code 400 (Bad Request)} if the clazzChapterVideoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the clazzChapterVideoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the clazzChapterVideoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/clazz-chapter-videos/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<ClazzChapterVideoDTO> partialUpdateClazzChapterVideo(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ClazzChapterVideoDTO clazzChapterVideoDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ClazzChapterVideo partially : {}, {}", id, clazzChapterVideoDTO);
        if (clazzChapterVideoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, clazzChapterVideoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!clazzChapterVideoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ClazzChapterVideoDTO> result = clazzChapterVideoService.partialUpdate(clazzChapterVideoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, clazzChapterVideoDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /clazz-chapter-videos} : get all the clazzChapterVideos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of clazzChapterVideos in body.
     */
    @GetMapping("/clazz-chapter-videos")
    public ResponseEntity<List<ClazzChapterVideoDTO>> getAllClazzChapterVideos(Pageable pageable) {
        log.debug("REST request to get a page of ClazzChapterVideos");
        Page<ClazzChapterVideoDTO> page = clazzChapterVideoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /clazz-chapter-videos/:id} : get the "id" clazzChapterVideo.
     *
     * @param id the id of the clazzChapterVideoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the clazzChapterVideoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/clazz-chapter-videos/{id}")
    public ResponseEntity<ClazzChapterVideoDTO> getClazzChapterVideo(@PathVariable Long id) {
        log.debug("REST request to get ClazzChapterVideo : {}", id);
        Optional<ClazzChapterVideoDTO> clazzChapterVideoDTO = clazzChapterVideoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(clazzChapterVideoDTO);
    }

    /**
     * {@code DELETE  /clazz-chapter-videos/:id} : delete the "id" clazzChapterVideo.
     *
     * @param id the id of the clazzChapterVideoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/clazz-chapter-videos/{id}")
    public ResponseEntity<Void> deleteClazzChapterVideo(@PathVariable Long id) {
        log.debug("REST request to delete ClazzChapterVideo : {}", id);
        clazzChapterVideoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
