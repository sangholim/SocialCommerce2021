package com.toy.project.repository;

import com.toy.project.domain.ProductStoreRel;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ProductStoreRel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductStoreRelRepository extends JpaRepository<ProductStoreRel, Long>, JpaSpecificationExecutor<ProductStoreRel> {}
