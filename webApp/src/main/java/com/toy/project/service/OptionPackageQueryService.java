package com.toy.project.service;

import com.toy.project.domain.*; // for static metamodels
import com.toy.project.domain.OptionPackage;
import com.toy.project.repository.OptionPackageRepository;
import com.toy.project.service.criteria.OptionPackageCriteria;
import com.toy.project.service.dto.OptionPackageDTO;
import com.toy.project.service.mapper.OptionPackageMapper;
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
 * Service for executing complex queries for {@link OptionPackage} entities in the database.
 * The main input is a {@link OptionPackageCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link OptionPackageDTO} or a {@link Page} of {@link OptionPackageDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class OptionPackageQueryService extends QueryService<OptionPackage> {

    private final Logger log = LoggerFactory.getLogger(OptionPackageQueryService.class);

    private final OptionPackageRepository optionPackageRepository;

    private final OptionPackageMapper optionPackageMapper;

    public OptionPackageQueryService(OptionPackageRepository optionPackageRepository, OptionPackageMapper optionPackageMapper) {
        this.optionPackageRepository = optionPackageRepository;
        this.optionPackageMapper = optionPackageMapper;
    }

    /**
     * Return a {@link List} of {@link OptionPackageDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<OptionPackageDTO> findByCriteria(OptionPackageCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<OptionPackage> specification = createSpecification(criteria);
        return optionPackageMapper.toDto(optionPackageRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link OptionPackageDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<OptionPackageDTO> findByCriteria(OptionPackageCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<OptionPackage> specification = createSpecification(criteria);
        return optionPackageRepository.findAll(specification, page).map(optionPackageMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(OptionPackageCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<OptionPackage> specification = createSpecification(criteria);
        return optionPackageRepository.count(specification);
    }

    /**
     * Function to convert {@link OptionPackageCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<OptionPackage> createSpecification(OptionPackageCriteria criteria) {
        Specification<OptionPackage> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), OptionPackage_.id));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getType(), OptionPackage_.type));
            }
            if (criteria.getValue() != null) {
                specification = specification.and(buildStringSpecification(criteria.getValue(), OptionPackage_.value));
            }
            if (criteria.getDescriptionUsage() != null) {
                specification = specification.and(buildSpecification(criteria.getDescriptionUsage(), OptionPackage_.descriptionUsage));
            }
            if (criteria.getRecommendShow() != null) {
                specification = specification.and(buildSpecification(criteria.getRecommendShow(), OptionPackage_.recommendShow));
            }
            if (criteria.getThumbnailFileUrl() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getThumbnailFileUrl(), OptionPackage_.thumbnailFileUrl));
            }
            if (criteria.getActivated() != null) {
                specification = specification.and(buildSpecification(criteria.getActivated(), OptionPackage_.activated));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), OptionPackage_.createdBy));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), OptionPackage_.createdDate));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), OptionPackage_.lastModifiedBy));
            }
            if (criteria.getLastModifiedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModifiedDate(), OptionPackage_.lastModifiedDate));
            }
            if (criteria.getProductOptionPackageRelId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getProductOptionPackageRelId(),
                            root -> root.join(OptionPackage_.productOptionPackageRels, JoinType.LEFT).get(ProductOptionPackageRel_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
