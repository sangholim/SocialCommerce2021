package com.toy.project.web.rest;

import com.toy.project.repository.PackageDescriptionImageRepository;
import com.toy.project.service.PackageDescriptionImageService;
import com.toy.project.service.dto.PackageDescriptionImageDTO;
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
 * REST controller for managing {@link com.toy.project.domain.PackageDescriptionImage}.
 */
@RestController
@RequestMapping("/api")
public class PackageDescriptionImageResource {

    private final Logger log = LoggerFactory.getLogger(PackageDescriptionImageResource.class);

    private static final String ENTITY_NAME = "packageDescriptionImage";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PackageDescriptionImageService packageDescriptionImageService;

    private final PackageDescriptionImageRepository packageDescriptionImageRepository;

    public PackageDescriptionImageResource(
        PackageDescriptionImageService packageDescriptionImageService,
        PackageDescriptionImageRepository packageDescriptionImageRepository
    ) {
        this.packageDescriptionImageService = packageDescriptionImageService;
        this.packageDescriptionImageRepository = packageDescriptionImageRepository;
    }

    /**
     * {@code POST  /package-description-images} : Create a new packageDescriptionImage.
     *
     * @param packageDescriptionImageDTO the packageDescriptionImageDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new packageDescriptionImageDTO, or with status {@code 400 (Bad Request)} if the packageDescriptionImage has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/package-description-images")
    public ResponseEntity<PackageDescriptionImageDTO> createPackageDescriptionImage(
        @Valid @RequestBody PackageDescriptionImageDTO packageDescriptionImageDTO
    ) throws URISyntaxException {
        log.debug("REST request to save PackageDescriptionImage : {}", packageDescriptionImageDTO);
        if (packageDescriptionImageDTO.getId() != null) {
            throw new BadRequestAlertException("A new packageDescriptionImage cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PackageDescriptionImageDTO result = packageDescriptionImageService.save(packageDescriptionImageDTO);
        return ResponseEntity
            .created(new URI("/api/package-description-images/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /package-description-images/:id} : Updates an existing packageDescriptionImage.
     *
     * @param id the id of the packageDescriptionImageDTO to save.
     * @param packageDescriptionImageDTO the packageDescriptionImageDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated packageDescriptionImageDTO,
     * or with status {@code 400 (Bad Request)} if the packageDescriptionImageDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the packageDescriptionImageDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/package-description-images/{id}")
    public ResponseEntity<PackageDescriptionImageDTO> updatePackageDescriptionImage(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody PackageDescriptionImageDTO packageDescriptionImageDTO
    ) throws URISyntaxException {
        log.debug("REST request to update PackageDescriptionImage : {}, {}", id, packageDescriptionImageDTO);
        if (packageDescriptionImageDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, packageDescriptionImageDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!packageDescriptionImageRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PackageDescriptionImageDTO result = packageDescriptionImageService.save(packageDescriptionImageDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, packageDescriptionImageDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /package-description-images/:id} : Partial updates given fields of an existing packageDescriptionImage, field will ignore if it is null
     *
     * @param id the id of the packageDescriptionImageDTO to save.
     * @param packageDescriptionImageDTO the packageDescriptionImageDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated packageDescriptionImageDTO,
     * or with status {@code 400 (Bad Request)} if the packageDescriptionImageDTO is not valid,
     * or with status {@code 404 (Not Found)} if the packageDescriptionImageDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the packageDescriptionImageDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/package-description-images/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<PackageDescriptionImageDTO> partialUpdatePackageDescriptionImage(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody PackageDescriptionImageDTO packageDescriptionImageDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update PackageDescriptionImage partially : {}, {}", id, packageDescriptionImageDTO);
        if (packageDescriptionImageDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, packageDescriptionImageDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!packageDescriptionImageRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PackageDescriptionImageDTO> result = packageDescriptionImageService.partialUpdate(packageDescriptionImageDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, packageDescriptionImageDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /package-description-images} : get all the packageDescriptionImages.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of packageDescriptionImages in body.
     */
    @GetMapping("/package-description-images")
    public ResponseEntity<List<PackageDescriptionImageDTO>> getAllPackageDescriptionImages(Pageable pageable) {
        log.debug("REST request to get a page of PackageDescriptionImages");
        Page<PackageDescriptionImageDTO> page = packageDescriptionImageService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /package-description-images/:id} : get the "id" packageDescriptionImage.
     *
     * @param id the id of the packageDescriptionImageDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the packageDescriptionImageDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/package-description-images/{id}")
    public ResponseEntity<PackageDescriptionImageDTO> getPackageDescriptionImage(@PathVariable Long id) {
        log.debug("REST request to get PackageDescriptionImage : {}", id);
        Optional<PackageDescriptionImageDTO> packageDescriptionImageDTO = packageDescriptionImageService.findOne(id);
        return ResponseUtil.wrapOrNotFound(packageDescriptionImageDTO);
    }

    /**
     * {@code DELETE  /package-description-images/:id} : delete the "id" packageDescriptionImage.
     *
     * @param id the id of the packageDescriptionImageDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/package-description-images/{id}")
    public ResponseEntity<Void> deletePackageDescriptionImage(@PathVariable Long id) {
        log.debug("REST request to delete PackageDescriptionImage : {}", id);
        packageDescriptionImageService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
