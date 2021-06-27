package com.toy.project.repository;

import com.toy.project.domain.ProductMapping;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ProductMapping entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductMappingRepository extends JpaRepository<ProductMapping, Long>, JpaSpecificationExecutor<ProductMapping> {}
