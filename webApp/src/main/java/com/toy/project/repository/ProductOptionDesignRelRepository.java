package com.toy.project.repository;

import com.toy.project.domain.ProductOptionDesignRel;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ProductOptionDesignRel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductOptionDesignRelRepository
    extends JpaRepository<ProductOptionDesignRel, Long>, JpaSpecificationExecutor<ProductOptionDesignRel> {}
