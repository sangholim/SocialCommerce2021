package com.toy.project.service;

import com.toy.project.domain.Clazz;
import com.toy.project.repository.ClazzRepository;
import com.toy.project.service.dto.ClazzDTO;
import com.toy.project.service.mapper.ClazzMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Clazz}.
 */
@Service
@Transactional
public class ClazzService {

    private final Logger log = LoggerFactory.getLogger(ClazzService.class);

    private final ClazzRepository clazzRepository;

    private final ClazzMapper clazzMapper;

    public ClazzService(ClazzRepository clazzRepository, ClazzMapper clazzMapper) {
        this.clazzRepository = clazzRepository;
        this.clazzMapper = clazzMapper;
    }

    /**
     * Save a clazz.
     *
     * @param clazzDTO the entity to save.
     * @return the persisted entity.
     */
    public ClazzDTO save(ClazzDTO clazzDTO) {
        log.debug("Request to save Clazz : {}", clazzDTO);
        Clazz clazz = clazzMapper.toEntity(clazzDTO);
        clazz = clazzRepository.save(clazz);
        return clazzMapper.toDto(clazz);
    }

    /**
     * Partially update a clazz.
     *
     * @param clazzDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ClazzDTO> partialUpdate(ClazzDTO clazzDTO) {
        log.debug("Request to partially update Clazz : {}", clazzDTO);

        return clazzRepository
            .findById(clazzDTO.getId())
            .map(
                existingClazz -> {
                    clazzMapper.partialUpdate(existingClazz, clazzDTO);
                    return existingClazz;
                }
            )
            .map(clazzRepository::save)
            .map(clazzMapper::toDto);
    }

    /**
     * Get all the clazzes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ClazzDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Clazzes");
        return clazzRepository.findAll(pageable).map(clazzMapper::toDto);
    }

    /**
     * Get one clazz by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ClazzDTO> findOne(Long id) {
        log.debug("Request to get Clazz : {}", id);
        return clazzRepository.findById(id).map(clazzMapper::toDto);
    }

    /**
     * Delete the clazz by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Clazz : {}", id);
        clazzRepository.deleteById(id);
    }
}
