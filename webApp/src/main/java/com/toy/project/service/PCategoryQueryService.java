package com.toy.project.service;

import com.toy.project.domain.*; // for static metamodels
import com.toy.project.domain.PCategory;
import com.toy.project.repository.PCategoryRepository;
import com.toy.project.service.criteria.PCategoryCriteria;
import com.toy.project.service.dto.PCategoryDTO;
import com.toy.project.service.mapper.PCategoryMapper;
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
 * Service for executing complex queries for {@link PCategory} entities in the database.
 * The main input is a {@link PCategoryCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PCategoryDTO} or a {@link Page} of {@link PCategoryDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PCategoryQueryService extends QueryService<PCategory> {

    private final Logger log = LoggerFactory.getLogger(PCategoryQueryService.class);

    private final PCategoryRepository pCategoryRepository;

    private final PCategoryMapper pCategoryMapper;

    public PCategoryQueryService(PCategoryRepository pCategoryRepository, PCategoryMapper pCategoryMapper) {
        this.pCategoryRepository = pCategoryRepository;
        this.pCategoryMapper = pCategoryMapper;
    }

    /**
     * Return a {@link List} of {@link PCategoryDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PCategoryDTO> findByCriteria(PCategoryCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<PCategory> specification = createSpecification(criteria);
        return pCategoryMapper.toDto(pCategoryRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link PCategoryDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PCategoryDTO> findByCriteria(PCategoryCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<PCategory> specification = createSpecification(criteria);
        return pCategoryRepository.findAll(specification, page).map(pCategoryMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PCategoryCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<PCategory> specification = createSpecification(criteria);
        return pCategoryRepository.count(specification);
    }

    /**
     * Function to convert {@link PCategoryCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<PCategory> createSpecification(PCategoryCriteria criteria) {
        Specification<PCategory> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), PCategory_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), PCategory_.name));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildSpecification(criteria.getType(), PCategory_.type));
            }
            if (criteria.getCategoryMain() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCategoryMain(), PCategory_.categoryMain));
            }
            if (criteria.getCategorySub() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCategorySub(), PCategory_.categorySub));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), PCategory_.description));
            }
            if (criteria.getIsUse() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getIsUse(), PCategory_.isUse));
            }
            if (criteria.getSortOrder() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSortOrder(), PCategory_.sortOrder));
            }
            if (criteria.getRegdate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRegdate(), PCategory_.regdate));
            }
            if (criteria.getIsUseDiscount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getIsUseDiscount(), PCategory_.isUseDiscount));
            }
        }
        return specification;
    }
}
