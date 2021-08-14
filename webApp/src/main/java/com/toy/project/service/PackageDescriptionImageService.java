package com.toy.project.service;

import com.toy.project.domain.PackageDescriptionImage;
import com.toy.project.repository.PackageDescriptionImageRepository;
import com.toy.project.service.dto.PackageDescriptionImageDTO;
import com.toy.project.service.mapper.PackageDescriptionImageMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link PackageDescriptionImage}.
 */
@Service
@Transactional
public class PackageDescriptionImageService {

    private final Logger log = LoggerFactory.getLogger(PackageDescriptionImageService.class);

    private final PackageDescriptionImageRepository packageDescriptionImageRepository;

    private final PackageDescriptionImageMapper packageDescriptionImageMapper;

    public PackageDescriptionImageService(
        PackageDescriptionImageRepository packageDescriptionImageRepository,
        PackageDescriptionImageMapper packageDescriptionImageMapper
    ) {
        this.packageDescriptionImageRepository = packageDescriptionImageRepository;
        this.packageDescriptionImageMapper = packageDescriptionImageMapper;
    }

    /**
     * Save a packageDescriptionImage.
     *
     * @param packageDescriptionImageDTO the entity to save.
     * @return the persisted entity.
     */
    public PackageDescriptionImageDTO save(PackageDescriptionImageDTO packageDescriptionImageDTO) {
        log.debug("Request to save PackageDescriptionImage : {}", packageDescriptionImageDTO);
        PackageDescriptionImage packageDescriptionImage = packageDescriptionImageMapper.toEntity(packageDescriptionImageDTO);
        packageDescriptionImage = packageDescriptionImageRepository.save(packageDescriptionImage);
        return packageDescriptionImageMapper.toDto(packageDescriptionImage);
    }

    /**
     * Partially update a packageDescriptionImage.
     *
     * @param packageDescriptionImageDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<PackageDescriptionImageDTO> partialUpdate(PackageDescriptionImageDTO packageDescriptionImageDTO) {
        log.debug("Request to partially update PackageDescriptionImage : {}", packageDescriptionImageDTO);

        return packageDescriptionImageRepository
            .findById(packageDescriptionImageDTO.getId())
            .map(
                existingPackageDescriptionImage -> {
                    packageDescriptionImageMapper.partialUpdate(existingPackageDescriptionImage, packageDescriptionImageDTO);
                    return existingPackageDescriptionImage;
                }
            )
            .map(packageDescriptionImageRepository::save)
            .map(packageDescriptionImageMapper::toDto);
    }

    /**
     * Get all the packageDescriptionImages.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PackageDescriptionImageDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PackageDescriptionImages");
        return packageDescriptionImageRepository.findAll(pageable).map(packageDescriptionImageMapper::toDto);
    }

    /**
     * Get one packageDescriptionImage by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PackageDescriptionImageDTO> findOne(Long id) {
        log.debug("Request to get PackageDescriptionImage : {}", id);
        return packageDescriptionImageRepository.findById(id).map(packageDescriptionImageMapper::toDto);
    }

    /**
     * Delete the packageDescriptionImage by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete PackageDescriptionImage : {}", id);
        packageDescriptionImageRepository.deleteById(id);
    }
}
