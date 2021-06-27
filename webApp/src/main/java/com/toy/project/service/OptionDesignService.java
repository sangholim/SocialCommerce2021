package com.toy.project.service;

import com.toy.project.domain.OptionDesign;
import com.toy.project.repository.OptionDesignRepository;
import com.toy.project.service.dto.OptionDesignDTO;
import com.toy.project.service.mapper.OptionDesignMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link OptionDesign}.
 */
@Service
@Transactional
public class OptionDesignService {

    private final Logger log = LoggerFactory.getLogger(OptionDesignService.class);

    private final OptionDesignRepository optionDesignRepository;

    private final OptionDesignMapper optionDesignMapper;

    public OptionDesignService(OptionDesignRepository optionDesignRepository, OptionDesignMapper optionDesignMapper) {
        this.optionDesignRepository = optionDesignRepository;
        this.optionDesignMapper = optionDesignMapper;
    }

    /**
     * Save a optionDesign.
     *
     * @param optionDesignDTO the entity to save.
     * @return the persisted entity.
     */
    public OptionDesignDTO save(OptionDesignDTO optionDesignDTO) {
        log.debug("Request to save OptionDesign : {}", optionDesignDTO);
        OptionDesign optionDesign = optionDesignMapper.toEntity(optionDesignDTO);
        optionDesign = optionDesignRepository.save(optionDesign);
        return optionDesignMapper.toDto(optionDesign);
    }

    /**
     * Partially update a optionDesign.
     *
     * @param optionDesignDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<OptionDesignDTO> partialUpdate(OptionDesignDTO optionDesignDTO) {
        log.debug("Request to partially update OptionDesign : {}", optionDesignDTO);

        return optionDesignRepository
            .findById(optionDesignDTO.getId())
            .map(
                existingOptionDesign -> {
                    optionDesignMapper.partialUpdate(existingOptionDesign, optionDesignDTO);
                    return existingOptionDesign;
                }
            )
            .map(optionDesignRepository::save)
            .map(optionDesignMapper::toDto);
    }

    /**
     * Get all the optionDesigns.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<OptionDesignDTO> findAll(Pageable pageable) {
        log.debug("Request to get all OptionDesigns");
        return optionDesignRepository.findAll(pageable).map(optionDesignMapper::toDto);
    }

    /**
     * Get one optionDesign by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<OptionDesignDTO> findOne(Long id) {
        log.debug("Request to get OptionDesign : {}", id);
        return optionDesignRepository.findById(id).map(optionDesignMapper::toDto);
    }

    /**
     * Delete the optionDesign by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete OptionDesign : {}", id);
        optionDesignRepository.deleteById(id);
    }
}
