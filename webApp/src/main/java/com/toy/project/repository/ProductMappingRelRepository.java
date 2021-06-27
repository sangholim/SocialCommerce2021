package com.toy.project.repository;

import com.toy.project.domain.ProductMappingRel;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ProductMappingRel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductMappingRelRepository extends JpaRepository<ProductMappingRel, Long>, JpaSpecificationExecutor<ProductMappingRel> {}
