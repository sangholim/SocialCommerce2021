package com.toy.project.repository;

import com.toy.project.domain.ProductOptionPackageRel;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ProductOptionPackageRel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductOptionPackageRelRepository
    extends JpaRepository<ProductOptionPackageRel, Long>, JpaSpecificationExecutor<ProductOptionPackageRel> {}
