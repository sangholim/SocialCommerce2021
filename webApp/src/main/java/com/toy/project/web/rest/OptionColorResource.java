package com.toy.project.web.rest;

import com.toy.project.repository.OptionColorRepository;
import com.toy.project.service.OptionColorQueryService;
import com.toy.project.service.OptionColorService;
import com.toy.project.service.criteria.OptionColorCriteria;
import com.toy.project.service.dto.OptionColorDTO;
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
 * REST controller for managing {@link com.toy.project.domain.OptionColor}.
 */
@RestController
@RequestMapping("/api")
public class OptionColorResource {

    private final Logger log = LoggerFactory.getLogger(OptionColorResource.class);

    private static final String ENTITY_NAME = "optionColor";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OptionColorService optionColorService;

    private final OptionColorRepository optionColorRepository;

    private final OptionColorQueryService optionColorQueryService;

    public OptionColorResource(
        OptionColorService optionColorService,
        OptionColorRepository optionColorRepository,
        OptionColorQueryService optionColorQueryService
    ) {
        this.optionColorService = optionColorService;
        this.optionColorRepository = optionColorRepository;
        this.optionColorQueryService = optionColorQueryService;
    }

    /**
     * {@code POST  /option-colors} : Create a new optionColor.
     *
     * @param optionColorDTO the optionColorDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new optionColorDTO, or with status {@code 400 (Bad Request)} if the optionColor has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/option-colors")
    public ResponseEntity<OptionColorDTO> createOptionColor(@Valid @RequestBody OptionColorDTO optionColorDTO) throws URISyntaxException {
        log.debug("REST request to save OptionColor : {}", optionColorDTO);
        if (optionColorDTO.getId() != null) {
            throw new BadRequestAlertException("A new optionColor cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OptionColorDTO result = optionColorService.save(optionColorDTO);
        return ResponseEntity
            .created(new URI("/api/option-colors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /option-colors/:id} : Updates an existing optionColor.
     *
     * @param id the id of the optionColorDTO to save.
     * @param optionColorDTO the optionColorDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated optionColorDTO,
     * or with status {@code 400 (Bad Request)} if the optionColorDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the optionColorDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/option-colors/{id}")
    public ResponseEntity<OptionColorDTO> updateOptionColor(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody OptionColorDTO optionColorDTO
    ) throws URISyntaxException {
        log.debug("REST request to update OptionColor : {}, {}", id, optionColorDTO);
        if (optionColorDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, optionColorDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!optionColorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        OptionColorDTO result = optionColorService.save(optionColorDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, optionColorDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /option-colors/:id} : Partial updates given fields of an existing optionColor, field will ignore if it is null
     *
     * @param id the id of the optionColorDTO to save.
     * @param optionColorDTO the optionColorDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated optionColorDTO,
     * or with status {@code 400 (Bad Request)} if the optionColorDTO is not valid,
     * or with status {@code 404 (Not Found)} if the optionColorDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the optionColorDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/option-colors/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<OptionColorDTO> partialUpdateOptionColor(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody OptionColorDTO optionColorDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update OptionColor partially : {}, {}", id, optionColorDTO);
        if (optionColorDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, optionColorDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!optionColorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<OptionColorDTO> result = optionColorService.partialUpdate(optionColorDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, optionColorDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /option-colors} : get all the optionColors.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of optionColors in body.
     */
    @GetMapping("/option-colors")
    public ResponseEntity<List<OptionColorDTO>> getAllOptionColors(OptionColorCriteria criteria, Pageable pageable) {
        log.debug("REST request to get OptionColors by criteria: {}", criteria);
        Page<OptionColorDTO> page = optionColorQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /option-colors/count} : count all the optionColors.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/option-colors/count")
    public ResponseEntity<Long> countOptionColors(OptionColorCriteria criteria) {
        log.debug("REST request to count OptionColors by criteria: {}", criteria);
        return ResponseEntity.ok().body(optionColorQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /option-colors/:id} : get the "id" optionColor.
     *
     * @param id the id of the optionColorDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the optionColorDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/option-colors/{id}")
    public ResponseEntity<OptionColorDTO> getOptionColor(@PathVariable Long id) {
        log.debug("REST request to get OptionColor : {}", id);
        Optional<OptionColorDTO> optionColorDTO = optionColorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(optionColorDTO);
    }

    /**
     * {@code DELETE  /option-colors/:id} : delete the "id" optionColor.
     *
     * @param id the id of the optionColorDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/option-colors/{id}")
    public ResponseEntity<Void> deleteOptionColor(@PathVariable Long id) {
        log.debug("REST request to delete OptionColor : {}", id);
        optionColorService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
