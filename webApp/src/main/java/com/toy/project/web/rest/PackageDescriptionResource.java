package com.toy.project.web.rest;

import com.toy.project.repository.PackageDescriptionRepository;
import com.toy.project.service.PackageDescriptionService;
import com.toy.project.service.dto.PackageDescriptionDTO;
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
 * REST controller for managing {@link com.toy.project.domain.PackageDescription}.
 */
@RestController
@RequestMapping("/api")
public class PackageDescriptionResource {

    private final Logger log = LoggerFactory.getLogger(PackageDescriptionResource.class);

    private static final String ENTITY_NAME = "packageDescription";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PackageDescriptionService packageDescriptionService;

    private final PackageDescriptionRepository packageDescriptionRepository;

    public PackageDescriptionResource(
        PackageDescriptionService packageDescriptionService,
        PackageDescriptionRepository packageDescriptionRepository
    ) {
        this.packageDescriptionService = packageDescriptionService;
        this.packageDescriptionRepository = packageDescriptionRepository;
    }

    /**
     * {@code POST  /package-descriptions} : Create a new packageDescription.
     *
     * @param packageDescriptionDTO the packageDescriptionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new packageDescriptionDTO, or with status {@code 400 (Bad Request)} if the packageDescription has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/package-descriptions")
    public ResponseEntity<PackageDescriptionDTO> createPackageDescription(@Valid @RequestBody PackageDescriptionDTO packageDescriptionDTO)
        throws URISyntaxException {
        log.debug("REST request to save PackageDescription : {}", packageDescriptionDTO);
        if (packageDescriptionDTO.getId() != null) {
            throw new BadRequestAlertException("A new packageDescription cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PackageDescriptionDTO result = packageDescriptionService.save(packageDescriptionDTO);
        return ResponseEntity
            .created(new URI("/api/package-descriptions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /package-descriptions/:id} : Updates an existing packageDescription.
     *
     * @param id the id of the packageDescriptionDTO to save.
     * @param packageDescriptionDTO the packageDescriptionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated packageDescriptionDTO,
     * or with status {@code 400 (Bad Request)} if the packageDescriptionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the packageDescriptionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/package-descriptions/{id}")
    public ResponseEntity<PackageDescriptionDTO> updatePackageDescription(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody PackageDescriptionDTO packageDescriptionDTO
    ) throws URISyntaxException {
        log.debug("REST request to update PackageDescription : {}, {}", id, packageDescriptionDTO);
        if (packageDescriptionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, packageDescriptionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!packageDescriptionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PackageDescriptionDTO result = packageDescriptionService.save(packageDescriptionDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, packageDescriptionDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /package-descriptions/:id} : Partial updates given fields of an existing packageDescription, field will ignore if it is null
     *
     * @param id the id of the packageDescriptionDTO to save.
     * @param packageDescriptionDTO the packageDescriptionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated packageDescriptionDTO,
     * or with status {@code 400 (Bad Request)} if the packageDescriptionDTO is not valid,
     * or with status {@code 404 (Not Found)} if the packageDescriptionDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the packageDescriptionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/package-descriptions/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<PackageDescriptionDTO> partialUpdatePackageDescription(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody PackageDescriptionDTO packageDescriptionDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update PackageDescription partially : {}, {}", id, packageDescriptionDTO);
        if (packageDescriptionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, packageDescriptionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!packageDescriptionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PackageDescriptionDTO> result = packageDescriptionService.partialUpdate(packageDescriptionDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, packageDescriptionDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /package-descriptions} : get all the packageDescriptions.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of packageDescriptions in body.
     */
    @GetMapping("/package-descriptions")
    public ResponseEntity<List<PackageDescriptionDTO>> getAllPackageDescriptions(Pageable pageable) {
        log.debug("REST request to get a page of PackageDescriptions");
        Page<PackageDescriptionDTO> page = packageDescriptionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /package-descriptions/:id} : get the "id" packageDescription.
     *
     * @param id the id of the packageDescriptionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the packageDescriptionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/package-descriptions/{id}")
    public ResponseEntity<PackageDescriptionDTO> getPackageDescription(@PathVariable Long id) {
        log.debug("REST request to get PackageDescription : {}", id);
        Optional<PackageDescriptionDTO> packageDescriptionDTO = packageDescriptionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(packageDescriptionDTO);
    }

    /**
     * {@code DELETE  /package-descriptions/:id} : delete the "id" packageDescription.
     *
     * @param id the id of the packageDescriptionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/package-descriptions/{id}")
    public ResponseEntity<Void> deletePackageDescription(@PathVariable Long id) {
        log.debug("REST request to delete PackageDescription : {}", id);
        packageDescriptionService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
