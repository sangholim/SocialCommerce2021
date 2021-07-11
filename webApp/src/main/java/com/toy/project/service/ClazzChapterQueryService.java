package com.toy.project.service;

import com.toy.project.domain.*; // for static metamodels
import com.toy.project.domain.ClazzChapter;
import com.toy.project.repository.ClazzChapterRepository;
import com.toy.project.service.criteria.ClazzChapterCriteria;
import com.toy.project.service.dto.ClazzChapterDTO;
import com.toy.project.service.mapper.ClazzChapterMapper;
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
 * Service for executing complex queries for {@link ClazzChapter} entities in the database.
 * The main input is a {@link ClazzChapterCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ClazzChapterDTO} or a {@link Page} of {@link ClazzChapterDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ClazzChapterQueryService extends QueryService<ClazzChapter> {

    private final Logger log = LoggerFactory.getLogger(ClazzChapterQueryService.class);

    private final ClazzChapterRepository clazzChapterRepository;

    private final ClazzChapterMapper clazzChapterMapper;

    public ClazzChapterQueryService(ClazzChapterRepository clazzChapterRepository, ClazzChapterMapper clazzChapterMapper) {
        this.clazzChapterRepository = clazzChapterRepository;
        this.clazzChapterMapper = clazzChapterMapper;
    }

    /**
     * Return a {@link List} of {@link ClazzChapterDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ClazzChapterDTO> findByCriteria(ClazzChapterCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ClazzChapter> specification = createSpecification(criteria);
        return clazzChapterMapper.toDto(clazzChapterRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ClazzChapterDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ClazzChapterDTO> findByCriteria(ClazzChapterCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ClazzChapter> specification = createSpecification(criteria);
        return clazzChapterRepository.findAll(specification, page).map(clazzChapterMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ClazzChapterCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ClazzChapter> specification = createSpecification(criteria);
        return clazzChapterRepository.count(specification);
    }

    /**
     * Function to convert {@link ClazzChapterCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ClazzChapter> createSpecification(ClazzChapterCriteria criteria) {
        Specification<ClazzChapter> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ClazzChapter_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), ClazzChapter_.name));
            }
            if (criteria.getFileUrl() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFileUrl(), ClazzChapter_.fileUrl));
            }
            if (criteria.getOrder() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOrder(), ClazzChapter_.order));
            }
            if (criteria.getActivated() != null) {
                specification = specification.and(buildSpecification(criteria.getActivated(), ClazzChapter_.activated));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), ClazzChapter_.createdBy));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), ClazzChapter_.createdDate));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), ClazzChapter_.lastModifiedBy));
            }
            if (criteria.getLastModifiedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModifiedDate(), ClazzChapter_.lastModifiedDate));
            }
            if (criteria.getClazzChapterVideoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getClazzChapterVideoId(),
                            root -> root.join(ClazzChapter_.clazzChapterVideos, JoinType.LEFT).get(ClazzChapterVideo_.id)
                        )
                    );
            }
            if (criteria.getClazzId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getClazzId(), root -> root.join(ClazzChapter_.clazz, JoinType.LEFT).get(Clazz_.id))
                    );
            }
        }
        return specification;
    }
}
