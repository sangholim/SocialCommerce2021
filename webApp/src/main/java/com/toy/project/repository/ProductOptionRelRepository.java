package com.toy.project.repository;

import com.toy.project.domain.ProductOptionRel;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ProductOptionRel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductOptionRelRepository extends JpaRepository<ProductOptionRel, Long>, JpaSpecificationExecutor<ProductOptionRel> {}
