package com.toy.project.service;

import com.toy.project.domain.OptionPackage;
import com.toy.project.repository.OptionPackageRepository;
import com.toy.project.service.dto.OptionPackageDTO;
import com.toy.project.service.mapper.OptionPackageMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link OptionPackage}.
 */
@Service
@Transactional
public class OptionPackageService {

    private final Logger log = LoggerFactory.getLogger(OptionPackageService.class);

    private final OptionPackageRepository optionPackageRepository;

    private final OptionPackageMapper optionPackageMapper;

    public OptionPackageService(OptionPackageRepository optionPackageRepository, OptionPackageMapper optionPackageMapper) {
        this.optionPackageRepository = optionPackageRepository;
        this.optionPackageMapper = optionPackageMapper;
    }

    /**
     * Save a optionPackage.
     *
     * @param optionPackageDTO the entity to save.
     * @return the persisted entity.
     */
    public OptionPackageDTO save(OptionPackageDTO optionPackageDTO) {
        log.debug("Request to save OptionPackage : {}", optionPackageDTO);
        OptionPackage optionPackage = optionPackageMapper.toEntity(optionPackageDTO);
        optionPackage = optionPackageRepository.save(optionPackage);
        return optionPackageMapper.toDto(optionPackage);
    }

    /**
     * Partially update a optionPackage.
     *
     * @param optionPackageDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<OptionPackageDTO> partialUpdate(OptionPackageDTO optionPackageDTO) {
        log.debug("Request to partially update OptionPackage : {}", optionPackageDTO);

        return optionPackageRepository
            .findById(optionPackageDTO.getId())
            .map(
                existingOptionPackage -> {
                    optionPackageMapper.partialUpdate(existingOptionPackage, optionPackageDTO);
                    return existingOptionPackage;
                }
            )
            .map(optionPackageRepository::save)
            .map(optionPackageMapper::toDto);
    }

    /**
     * Get all the optionPackages.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<OptionPackageDTO> findAll(Pageable pageable) {
        log.debug("Request to get all OptionPackages");
        return optionPackageRepository.findAll(pageable).map(optionPackageMapper::toDto);
    }

    /**
     * Get one optionPackage by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<OptionPackageDTO> findOne(Long id) {
        log.debug("Request to get OptionPackage : {}", id);
        return optionPackageRepository.findById(id).map(optionPackageMapper::toDto);
    }

    /**
     * Delete the optionPackage by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete OptionPackage : {}", id);
        optionPackageRepository.deleteById(id);
    }
}
