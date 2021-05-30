package com.toy.project.service;

import com.toy.project.domain.PCategory;
import com.toy.project.repository.PCategoryRepository;
import com.toy.project.service.dto.PCategoryDTO;
import com.toy.project.service.mapper.PCategoryMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link PCategory}.
 */
@Service
@Transactional
public class PCategoryService {

    private final Logger log = LoggerFactory.getLogger(PCategoryService.class);

    private final PCategoryRepository pCategoryRepository;

    private final PCategoryMapper pCategoryMapper;

    public PCategoryService(PCategoryRepository pCategoryRepository, PCategoryMapper pCategoryMapper) {
        this.pCategoryRepository = pCategoryRepository;
        this.pCategoryMapper = pCategoryMapper;
    }

    /**
     * Save a pCategory.
     *
     * @param pCategoryDTO the entity to save.
     * @return the persisted entity.
     */
    public PCategoryDTO save(PCategoryDTO pCategoryDTO) {
        log.debug("Request to save PCategory : {}", pCategoryDTO);
        PCategory pCategory = pCategoryMapper.toEntity(pCategoryDTO);
        pCategory = pCategoryRepository.save(pCategory);
        return pCategoryMapper.toDto(pCategory);
    }

    /**
     * Partially update a pCategory.
     *
     * @param pCategoryDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<PCategoryDTO> partialUpdate(PCategoryDTO pCategoryDTO) {
        log.debug("Request to partially update PCategory : {}", pCategoryDTO);

        return pCategoryRepository
            .findById(pCategoryDTO.getId())
            .map(
                existingPCategory -> {
                    pCategoryMapper.partialUpdate(existingPCategory, pCategoryDTO);
                    return existingPCategory;
                }
            )
            .map(pCategoryRepository::save)
            .map(pCategoryMapper::toDto);
    }

    /**
     * Get all the pCategories.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PCategoryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PCategories");
        return pCategoryRepository.findAll(pageable).map(pCategoryMapper::toDto);
    }

    /**
     * Get one pCategory by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PCategoryDTO> findOne(Long id) {
        log.debug("Request to get PCategory : {}", id);
        return pCategoryRepository.findById(id).map(pCategoryMapper::toDto);
    }

    /**
     * Delete the pCategory by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete PCategory : {}", id);
        pCategoryRepository.deleteById(id);
    }
}
