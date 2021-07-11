package com.toy.project.service;

import com.toy.project.domain.ClazzChapter;
import com.toy.project.repository.ClazzChapterRepository;
import com.toy.project.service.dto.ClazzChapterDTO;
import com.toy.project.service.mapper.ClazzChapterMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ClazzChapter}.
 */
@Service
@Transactional
public class ClazzChapterService {

    private final Logger log = LoggerFactory.getLogger(ClazzChapterService.class);

    private final ClazzChapterRepository clazzChapterRepository;

    private final ClazzChapterMapper clazzChapterMapper;

    public ClazzChapterService(ClazzChapterRepository clazzChapterRepository, ClazzChapterMapper clazzChapterMapper) {
        this.clazzChapterRepository = clazzChapterRepository;
        this.clazzChapterMapper = clazzChapterMapper;
    }

    /**
     * Save a clazzChapter.
     *
     * @param clazzChapterDTO the entity to save.
     * @return the persisted entity.
     */
    public ClazzChapterDTO save(ClazzChapterDTO clazzChapterDTO) {
        log.debug("Request to save ClazzChapter : {}", clazzChapterDTO);
        ClazzChapter clazzChapter = clazzChapterMapper.toEntity(clazzChapterDTO);
        clazzChapter = clazzChapterRepository.save(clazzChapter);
        return clazzChapterMapper.toDto(clazzChapter);
    }

    /**
     * Partially update a clazzChapter.
     *
     * @param clazzChapterDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ClazzChapterDTO> partialUpdate(ClazzChapterDTO clazzChapterDTO) {
        log.debug("Request to partially update ClazzChapter : {}", clazzChapterDTO);

        return clazzChapterRepository
            .findById(clazzChapterDTO.getId())
            .map(
                existingClazzChapter -> {
                    clazzChapterMapper.partialUpdate(existingClazzChapter, clazzChapterDTO);
                    return existingClazzChapter;
                }
            )
            .map(clazzChapterRepository::save)
            .map(clazzChapterMapper::toDto);
    }

    /**
     * Get all the clazzChapters.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ClazzChapterDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ClazzChapters");
        return clazzChapterRepository.findAll(pageable).map(clazzChapterMapper::toDto);
    }

    /**
     * Get one clazzChapter by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ClazzChapterDTO> findOne(Long id) {
        log.debug("Request to get ClazzChapter : {}", id);
        return clazzChapterRepository.findById(id).map(clazzChapterMapper::toDto);
    }

    /**
     * Delete the clazzChapter by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ClazzChapter : {}", id);
        clazzChapterRepository.deleteById(id);
    }
}
