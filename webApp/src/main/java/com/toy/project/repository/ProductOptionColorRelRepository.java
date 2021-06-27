package com.toy.project.repository;

import com.toy.project.domain.ProductOptionColorRel;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ProductOptionColorRel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductOptionColorRelRepository
    extends JpaRepository<ProductOptionColorRel, Long>, JpaSpecificationExecutor<ProductOptionColorRel> {}
