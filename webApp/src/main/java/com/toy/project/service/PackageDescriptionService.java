package com.toy.project.service;

import com.toy.project.domain.PackageDescription;
import com.toy.project.repository.PackageDescriptionRepository;
import com.toy.project.service.dto.PackageDescriptionDTO;
import com.toy.project.service.mapper.PackageDescriptionMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link PackageDescription}.
 */
@Service
@Transactional
public class PackageDescriptionService {

    private final Logger log = LoggerFactory.getLogger(PackageDescriptionService.class);

    private final PackageDescriptionRepository packageDescriptionRepository;

    private final PackageDescriptionMapper packageDescriptionMapper;

    public PackageDescriptionService(
        PackageDescriptionRepository packageDescriptionRepository,
        PackageDescriptionMapper packageDescriptionMapper
    ) {
        this.packageDescriptionRepository = packageDescriptionRepository;
        this.packageDescriptionMapper = packageDescriptionMapper;
    }

    /**
     * Save a packageDescription.
     *
     * @param packageDescriptionDTO the entity to save.
     * @return the persisted entity.
     */
    public PackageDescriptionDTO save(PackageDescriptionDTO packageDescriptionDTO) {
        log.debug("Request to save PackageDescription : {}", packageDescriptionDTO);
        PackageDescription packageDescription = packageDescriptionMapper.toEntity(packageDescriptionDTO);
        packageDescription = packageDescriptionRepository.save(packageDescription);
        return packageDescriptionMapper.toDto(packageDescription);
    }

    /**
     * Partially update a packageDescription.
     *
     * @param packageDescriptionDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<PackageDescriptionDTO> partialUpdate(PackageDescriptionDTO packageDescriptionDTO) {
        log.debug("Request to partially update PackageDescription : {}", packageDescriptionDTO);

        return packageDescriptionRepository
            .findById(packageDescriptionDTO.getId())
            .map(
                existingPackageDescription -> {
                    packageDescriptionMapper.partialUpdate(existingPackageDescription, packageDescriptionDTO);
                    return existingPackageDescription;
                }
            )
            .map(packageDescriptionRepository::save)
            .map(packageDescriptionMapper::toDto);
    }

    /**
     * Get all the packageDescriptions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PackageDescriptionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PackageDescriptions");
        return packageDescriptionRepository.findAll(pageable).map(packageDescriptionMapper::toDto);
    }

    /**
     * Get one packageDescription by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PackageDescriptionDTO> findOne(Long id) {
        log.debug("Request to get PackageDescription : {}", id);
        return packageDescriptionRepository.findById(id).map(packageDescriptionMapper::toDto);
    }

    /**
     * Delete the packageDescription by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete PackageDescription : {}", id);
        packageDescriptionRepository.deleteById(id);
    }
}
