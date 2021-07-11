package com.toy.project.service;

import com.toy.project.domain.*; // for static metamodels
import com.toy.project.domain.Store;
import com.toy.project.repository.StoreRepository;
import com.toy.project.service.criteria.StoreCriteria;
import com.toy.project.service.dto.StoreDTO;
import com.toy.project.service.mapper.StoreMapper;
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
 * Service for executing complex queries for {@link Store} entities in the database.
 * The main input is a {@link StoreCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link StoreDTO} or a {@link Page} of {@link StoreDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class StoreQueryService extends QueryService<Store> {

    private final Logger log = LoggerFactory.getLogger(StoreQueryService.class);

    private final StoreRepository storeRepository;

    private final StoreMapper storeMapper;

    public StoreQueryService(StoreRepository storeRepository, StoreMapper storeMapper) {
        this.storeRepository = storeRepository;
        this.storeMapper = storeMapper;
    }

    /**
     * Return a {@link List} of {@link StoreDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<StoreDTO> findByCriteria(StoreCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Store> specification = createSpecification(criteria);
        return storeMapper.toDto(storeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link StoreDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<StoreDTO> findByCriteria(StoreCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Store> specification = createSpecification(criteria);
        return storeRepository.findAll(specification, page).map(storeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(StoreCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Store> specification = createSpecification(criteria);
        return storeRepository.count(specification);
    }

    /**
     * Function to convert {@link StoreCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Store> createSpecification(StoreCriteria criteria) {
        Specification<Store> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Store_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Store_.name));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getType(), Store_.type));
            }
            if (criteria.getCalculation() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCalculation(), Store_.calculation));
            }
            if (criteria.getActivated() != null) {
                specification = specification.and(buildSpecification(criteria.getActivated(), Store_.activated));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), Store_.createdBy));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), Store_.createdDate));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), Store_.lastModifiedBy));
            }
            if (criteria.getLastModifiedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModifiedDate(), Store_.lastModifiedDate));
            }
            if (criteria.getProductStoreRelId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getProductStoreRelId(),
                            root -> root.join(Store_.productStoreRels, JoinType.LEFT).get(ProductStoreRel_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
