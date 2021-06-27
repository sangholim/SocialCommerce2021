package com.toy.project.service;

import com.toy.project.domain.OptionColor;
import com.toy.project.repository.OptionColorRepository;
import com.toy.project.service.dto.OptionColorDTO;
import com.toy.project.service.mapper.OptionColorMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link OptionColor}.
 */
@Service
@Transactional
public class OptionColorService {

    private final Logger log = LoggerFactory.getLogger(OptionColorService.class);

    private final OptionColorRepository optionColorRepository;

    private final OptionColorMapper optionColorMapper;

    public OptionColorService(OptionColorRepository optionColorRepository, OptionColorMapper optionColorMapper) {
        this.optionColorRepository = optionColorRepository;
        this.optionColorMapper = optionColorMapper;
    }

    /**
     * Save a optionColor.
     *
     * @param optionColorDTO the entity to save.
     * @return the persisted entity.
     */
    public OptionColorDTO save(OptionColorDTO optionColorDTO) {
        log.debug("Request to save OptionColor : {}", optionColorDTO);
        OptionColor optionColor = optionColorMapper.toEntity(optionColorDTO);
        optionColor = optionColorRepository.save(optionColor);
        return optionColorMapper.toDto(optionColor);
    }

    /**
     * Partially update a optionColor.
     *
     * @param optionColorDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<OptionColorDTO> partialUpdate(OptionColorDTO optionColorDTO) {
        log.debug("Request to partially update OptionColor : {}", optionColorDTO);

        return optionColorRepository
            .findById(optionColorDTO.getId())
            .map(
                existingOptionColor -> {
                    optionColorMapper.partialUpdate(existingOptionColor, optionColorDTO);
                    return existingOptionColor;
                }
            )
            .map(optionColorRepository::save)
            .map(optionColorMapper::toDto);
    }

    /**
     * Get all the optionColors.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<OptionColorDTO> findAll(Pageable pageable) {
        log.debug("Request to get all OptionColors");
        return optionColorRepository.findAll(pageable).map(optionColorMapper::toDto);
    }

    /**
     * Get one optionColor by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<OptionColorDTO> findOne(Long id) {
        log.debug("Request to get OptionColor : {}", id);
        return optionColorRepository.findById(id).map(optionColorMapper::toDto);
    }

    /**
     * Delete the optionColor by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete OptionColor : {}", id);
        optionColorRepository.deleteById(id);
    }
}
