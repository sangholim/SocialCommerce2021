package com.toy.project.service;

import com.toy.project.domain.*; // for static metamodels
import com.toy.project.domain.OptionDesign;
import com.toy.project.repository.OptionDesignRepository;
import com.toy.project.service.criteria.OptionDesignCriteria;
import com.toy.project.service.dto.OptionDesignDTO;
import com.toy.project.service.mapper.OptionDesignMapper;
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
 * Service for executing complex queries for {@link OptionDesign} entities in the database.
 * The main input is a {@link OptionDesignCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link OptionDesignDTO} or a {@link Page} of {@link OptionDesignDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class OptionDesignQueryService extends QueryService<OptionDesign> {

    private final Logger log = LoggerFactory.getLogger(OptionDesignQueryService.class);

    private final OptionDesignRepository optionDesignRepository;

    private final OptionDesignMapper optionDesignMapper;

    public OptionDesignQueryService(OptionDesignRepository optionDesignRepository, OptionDesignMapper optionDesignMapper) {
        this.optionDesignRepository = optionDesignRepository;
        this.optionDesignMapper = optionDesignMapper;
    }

    /**
     * Return a {@link List} of {@link OptionDesignDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<OptionDesignDTO> findByCriteria(OptionDesignCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<OptionDesign> specification = createSpecification(criteria);
        return optionDesignMapper.toDto(optionDesignRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link OptionDesignDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<OptionDesignDTO> findByCriteria(OptionDesignCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<OptionDesign> specification = createSpecification(criteria);
        return optionDesignRepository.findAll(specification, page).map(optionDesignMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(OptionDesignCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<OptionDesign> specification = createSpecification(criteria);
        return optionDesignRepository.count(specification);
    }

    /**
     * Function to convert {@link OptionDesignCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<OptionDesign> createSpecification(OptionDesignCriteria criteria) {
        Specification<OptionDesign> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), OptionDesign_.id));
            }
            if (criteria.getValue() != null) {
                specification = specification.and(buildStringSpecification(criteria.getValue(), OptionDesign_.value));
            }
            if (criteria.getActivated() != null) {
                specification = specification.and(buildSpecification(criteria.getActivated(), OptionDesign_.activated));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), OptionDesign_.createdBy));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), OptionDesign_.createdDate));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), OptionDesign_.lastModifiedBy));
            }
            if (criteria.getLastModifiedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModifiedDate(), OptionDesign_.lastModifiedDate));
            }
            if (criteria.getProductOptionDesignRelId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getProductOptionDesignRelId(),
                            root -> root.join(OptionDesign_.productOptionDesignRels, JoinType.LEFT).get(ProductOptionDesignRel_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
