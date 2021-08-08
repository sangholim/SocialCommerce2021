package com.toy.project.service;

import com.toy.project.domain.ClazzChapterVideo;
import com.toy.project.repository.ClazzChapterVideoRepository;
import com.toy.project.service.dto.ClazzChapterVideoDTO;
import com.toy.project.service.mapper.ClazzChapterVideoMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ClazzChapterVideo}.
 */
@Service
@Transactional
public class ClazzChapterVideoService {

    private final Logger log = LoggerFactory.getLogger(ClazzChapterVideoService.class);

    private final ClazzChapterVideoRepository clazzChapterVideoRepository;

    private final ClazzChapterVideoMapper clazzChapterVideoMapper;

    public ClazzChapterVideoService(
        ClazzChapterVideoRepository clazzChapterVideoRepository,
        ClazzChapterVideoMapper clazzChapterVideoMapper
    ) {
        this.clazzChapterVideoRepository = clazzChapterVideoRepository;
        this.clazzChapterVideoMapper = clazzChapterVideoMapper;
    }

    /**
     * Save a clazzChapterVideo.
     *
     * @param clazzChapterVideoDTO the entity to save.
     * @return the persisted entity.
     */
    public ClazzChapterVideoDTO save(ClazzChapterVideoDTO clazzChapterVideoDTO) {
        log.debug("Request to save ClazzChapterVideo : {}", clazzChapterVideoDTO);
        ClazzChapterVideo clazzChapterVideo = clazzChapterVideoMapper.toEntity(clazzChapterVideoDTO);
        clazzChapterVideo = clazzChapterVideoRepository.save(clazzChapterVideo);
        return clazzChapterVideoMapper.toDto(clazzChapterVideo);
    }

    /**
     * Partially update a clazzChapterVideo.
     *
     * @param clazzChapterVideoDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ClazzChapterVideoDTO> partialUpdate(ClazzChapterVideoDTO clazzChapterVideoDTO) {
        log.debug("Request to partially update ClazzChapterVideo : {}", clazzChapterVideoDTO);

        return clazzChapterVideoRepository
            .findById(clazzChapterVideoDTO.getId())
            .map(
                existingClazzChapterVideo -> {
                    clazzChapterVideoMapper.partialUpdate(existingClazzChapterVideo, clazzChapterVideoDTO);
                    return existingClazzChapterVideo;
                }
            )
            .map(clazzChapterVideoRepository::save)
            .map(clazzChapterVideoMapper::toDto);
    }

    /**
     * Get all the clazzChapterVideos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ClazzChapterVideoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ClazzChapterVideos");
        return clazzChapterVideoRepository.findAll(pageable).map(clazzChapterVideoMapper::toDto);
    }

    /**
     * Get one clazzChapterVideo by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ClazzChapterVideoDTO> findOne(Long id) {
        log.debug("Request to get ClazzChapterVideo : {}", id);
        return clazzChapterVideoRepository.findById(id).map(clazzChapterVideoMapper::toDto);
    }

    /**
     * Delete the clazzChapterVideo by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ClazzChapterVideo : {}", id);
        clazzChapterVideoRepository.deleteById(id);
    }
}
