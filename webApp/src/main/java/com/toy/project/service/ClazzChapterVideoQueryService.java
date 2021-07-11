package com.toy.project.service;

import com.toy.project.domain.*; // for static metamodels
import com.toy.project.domain.ClazzChapterVideo;
import com.toy.project.repository.ClazzChapterVideoRepository;
import com.toy.project.service.criteria.ClazzChapterVideoCriteria;
import com.toy.project.service.dto.ClazzChapterVideoDTO;
import com.toy.project.service.mapper.ClazzChapterVideoMapper;
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
 * Service for executing complex queries for {@link ClazzChapterVideo} entities in the database.
 * The main input is a {@link ClazzChapterVideoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ClazzChapterVideoDTO} or a {@link Page} of {@link ClazzChapterVideoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ClazzChapterVideoQueryService extends QueryService<ClazzChapterVideo> {

    private final Logger log = LoggerFactory.getLogger(ClazzChapterVideoQueryService.class);

    private final ClazzChapterVideoRepository clazzChapterVideoRepository;

    private final ClazzChapterVideoMapper clazzChapterVideoMapper;

    public ClazzChapterVideoQueryService(
        ClazzChapterVideoRepository clazzChapterVideoRepository,
        ClazzChapterVideoMapper clazzChapterVideoMapper
    ) {
        this.clazzChapterVideoRepository = clazzChapterVideoRepository;
        this.clazzChapterVideoMapper = clazzChapterVideoMapper;
    }

    /**
     * Return a {@link List} of {@link ClazzChapterVideoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ClazzChapterVideoDTO> findByCriteria(ClazzChapterVideoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ClazzChapterVideo> specification = createSpecification(criteria);
        return clazzChapterVideoMapper.toDto(clazzChapterVideoRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ClazzChapterVideoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ClazzChapterVideoDTO> findByCriteria(ClazzChapterVideoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ClazzChapterVideo> specification = createSpecification(criteria);
        return clazzChapterVideoRepository.findAll(specification, page).map(clazzChapterVideoMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ClazzChapterVideoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ClazzChapterVideo> specification = createSpecification(criteria);
        return clazzChapterVideoRepository.count(specification);
    }

    /**
     * Function to convert {@link ClazzChapterVideoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ClazzChapterVideo> createSpecification(ClazzChapterVideoCriteria criteria) {
        Specification<ClazzChapterVideo> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ClazzChapterVideo_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), ClazzChapterVideo_.name));
            }
            if (criteria.getThumbFileUrl() != null) {
                specification = specification.and(buildStringSpecification(criteria.getThumbFileUrl(), ClazzChapterVideo_.thumbFileUrl));
            }
            if (criteria.getOriginalLinkUrl() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getOriginalLinkUrl(), ClazzChapterVideo_.originalLinkUrl));
            }
            if (criteria.getTime() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTime(), ClazzChapterVideo_.time));
            }
            if (criteria.getOrder() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOrder(), ClazzChapterVideo_.order));
            }
            if (criteria.getActivated() != null) {
                specification = specification.and(buildSpecification(criteria.getActivated(), ClazzChapterVideo_.activated));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), ClazzChapterVideo_.createdBy));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), ClazzChapterVideo_.createdDate));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getLastModifiedBy(), ClazzChapterVideo_.lastModifiedBy));
            }
            if (criteria.getLastModifiedDate() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getLastModifiedDate(), ClazzChapterVideo_.lastModifiedDate));
            }
            if (criteria.getClazzChapterId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getClazzChapterId(),
                            root -> root.join(ClazzChapterVideo_.clazzChapter, JoinType.LEFT).get(ClazzChapter_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
