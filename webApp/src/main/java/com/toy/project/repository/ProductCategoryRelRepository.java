package com.toy.project.repository;

import com.toy.project.domain.ProductCategoryRel;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ProductCategoryRel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductCategoryRelRepository
    extends JpaRepository<ProductCategoryRel, Long>, JpaSpecificationExecutor<ProductCategoryRel> {}
