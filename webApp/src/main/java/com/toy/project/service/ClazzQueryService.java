package com.toy.project.service;

import com.toy.project.domain.*; // for static metamodels
import com.toy.project.domain.Clazz;
import com.toy.project.repository.ClazzRepository;
import com.toy.project.service.criteria.ClazzCriteria;
import com.toy.project.service.dto.ClazzDTO;
import com.toy.project.service.mapper.ClazzMapper;
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
 * Service for executing complex queries for {@link Clazz} entities in the database.
 * The main input is a {@link ClazzCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ClazzDTO} or a {@link Page} of {@link ClazzDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ClazzQueryService extends QueryService<Clazz> {

    private final Logger log = LoggerFactory.getLogger(ClazzQueryService.class);

    private final ClazzRepository clazzRepository;

    private final ClazzMapper clazzMapper;

    public ClazzQueryService(ClazzRepository clazzRepository, ClazzMapper clazzMapper) {
        this.clazzRepository = clazzRepository;
        this.clazzMapper = clazzMapper;
    }

    /**
     * Return a {@link List} of {@link ClazzDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ClazzDTO> findByCriteria(ClazzCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Clazz> specification = createSpecification(criteria);
        return clazzMapper.toDto(clazzRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ClazzDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ClazzDTO> findByCriteria(ClazzCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Clazz> specification = createSpecification(criteria);
        return clazzRepository.findAll(specification, page).map(clazzMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ClazzCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Clazz> specification = createSpecification(criteria);
        return clazzRepository.count(specification);
    }

    /**
     * Function to convert {@link ClazzCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Clazz> createSpecification(ClazzCriteria criteria) {
        Specification<Clazz> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Clazz_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Clazz_.name));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getType(), Clazz_.type));
            }
            if (criteria.getMainImageFileUrl() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMainImageFileUrl(), Clazz_.mainImageFileUrl));
            }
            if (criteria.getLevel() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLevel(), Clazz_.level));
            }
            if (criteria.getEnableLecture() != null) {
                specification = specification.and(buildSpecification(criteria.getEnableLecture(), Clazz_.enableLecture));
            }
            if (criteria.getFreeLecture() != null) {
                specification = specification.and(buildSpecification(criteria.getFreeLecture(), Clazz_.freeLecture));
            }
            if (criteria.getPriceLecture() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPriceLecture(), Clazz_.priceLecture));
            }
            if (criteria.getPriceUnitLecture() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPriceUnitLecture(), Clazz_.priceUnitLecture));
            }
            if (criteria.getLectureStartDateFrom() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLectureStartDateFrom(), Clazz_.lectureStartDateFrom));
            }
            if (criteria.getLectureInterval() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLectureInterval(), Clazz_.lectureInterval));
            }
            if (criteria.getLecturer() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLecturer(), Clazz_.lecturer));
            }
            if (criteria.getCalculation() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCalculation(), Clazz_.calculation));
            }
            if (criteria.getIsView() != null) {
                specification = specification.and(buildSpecification(criteria.getIsView(), Clazz_.isView));
            }
            if (criteria.getIsSell() != null) {
                specification = specification.and(buildSpecification(criteria.getIsSell(), Clazz_.isSell));
            }
            if (criteria.getActivated() != null) {
                specification = specification.and(buildSpecification(criteria.getActivated(), Clazz_.activated));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), Clazz_.createdBy));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), Clazz_.createdDate));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), Clazz_.lastModifiedBy));
            }
            if (criteria.getLastModifiedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModifiedDate(), Clazz_.lastModifiedDate));
            }
            if (criteria.getProductClazzRelId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getProductClazzRelId(),
                            root -> root.join(Clazz_.productClazzRels, JoinType.LEFT).get(ProductClazzRel_.id)
                        )
                    );
            }
            if (criteria.getClazzChapterId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getClazzChapterId(),
                            root -> root.join(Clazz_.clazzChapters, JoinType.LEFT).get(ClazzChapter_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
