package com.toy.project.service;

import com.toy.project.domain.*; // for static metamodels
import com.toy.project.domain.OptionColor;
import com.toy.project.repository.OptionColorRepository;
import com.toy.project.service.criteria.OptionColorCriteria;
import com.toy.project.service.dto.OptionColorDTO;
import com.toy.project.service.mapper.OptionColorMapper;
import java.util.List;
import javax.persistence.criteria.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link OptionColor} entities in the database.
 * The main input is a {@link OptionColorCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link OptionColorDTO} or a {@link Page} of {@link OptionColorDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class OptionColorQueryService extends QueryService<OptionColor> {

    private final Logger log = LoggerFactory.getLogger(OptionColorQueryService.class);

    private final OptionColorRepository optionColorRepository;

    private final OptionColorMapper optionColorMapper;

    public OptionColorQueryService(OptionColorRepository optionColorRepository, OptionColorMapper optionColorMapper) {
        this.optionColorRepository = optionColorRepository;
        this.optionColorMapper = optionColorMapper;
    }

    /**
     * Return a {@link List} of {@link OptionColorDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<OptionColorDTO> findByCriteria(OptionColorCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<OptionColor> specification = createSpecification(criteria);
        return optionColorMapper.toDto(optionColorRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link OptionColorDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<OptionColorDTO> findByCriteria(OptionColorCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<OptionColor> specification = createSpecification(criteria);
        return optionColorRepository.findAll(specification, page).map(optionColorMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(OptionColorCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<OptionColor> specification = createSpecification(criteria);
        return optionColorRepository.count(specification);
    }

    /**
     * Function to convert {@link OptionColorCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<OptionColor> createSpecification(OptionColorCriteria criteria) {
        Specification<OptionColor> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), OptionColor_.id));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), OptionColor_.code));
            }
            if (criteria.getValue() != null) {
                specification = specification.and(buildStringSpecification(criteria.getValue(), OptionColor_.value));
            }
            if (criteria.getActivated() != null) {
                specification = specification.and(buildSpecification(criteria.getActivated(), OptionColor_.activated));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), OptionColor_.createdBy));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), OptionColor_.createdDate));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), OptionColor_.lastModifiedBy));
            }
            if (criteria.getLastModifiedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModifiedDate(), OptionColor_.lastModifiedDate));
            }
            if (criteria.getProductOptionColorRelId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getProductOptionColorRelId(),
                            root -> root.join(OptionColor_.productOptionColorRels, JoinType.LEFT).get(ProductOptionColorRel_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
